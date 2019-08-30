package com.service;

import java.util.Date;
import java.util.List;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mapper.SecorderMapper;
import com.mapper.SeckillMapper;
import com.pojo.Secorder;
import com.pojo.Seckill;
import com.rabbitMQ.RabbitmqSendMessage;
import com.redis.RedisClusterClient;

/**
 * 
 * @author 雅波
 *  时间 2019/08/20
 */

@Service
public class SeckillService {

	@Autowired
	 private RedisClusterClient rt;

	@Autowired
	private SeckillMapper sm;

	@Autowired
	private RedissonClient redissonClient; // 加锁

	@Autowired
	private RabbitmqSendMessage rsm;

	@Autowired
	private SecorderMapper om;
    
	/**
	 * 初始化 ，将mysql中的商品信息缓存到redis中
	 * @return
	 */
	public List<Seckill> querySeckill() {
		List<Seckill> list =   (List<Seckill>) rt.get("secgoods");
		if(list==null) {
			list = sm.selectByExample(null);
			rt.set("secgoods", list, 60*30);
		}
		return list;
	}

	public boolean queryStartTime(Seckill sec) {
		Date date = new Date();// 比较时间，是否到秒杀时间
		Date startTime = sec.getStarttime();
		// 秒杀活动还未开始
		if (startTime.getTime() > date.getTime()) {
			return false;
		}

		return true;
	}

	// 减库存redis
	public void decreaseStock(String id) {
        int goodsid = Integer.parseInt(id);
        List<Seckill> list =   (List<Seckill>) rt.get("secgoods");
		if (list!=null)
		{
			for (Seckill sec : list)
			{
				if (goodsid==sec.getId())
				{
					sec.setCount(sec.getCount()-1);
					//写回redis
					rt.set("secgoods", list, 60*30);
					
					return ;
				}
			}
		}
	}

	//
	public Seckill findSec(String secid) {
		 List<Seckill> list =   (List<Seckill>) rt.get("secgoods");
		int id = Integer.parseInt(secid);
		for(Seckill sec:list) {
			if(sec.getId()==id) {
				return sec;
			}
		}
		return null;
	}

	// 开始秒杀
	public String goSeckill(String goodsid, String username) {
		String key = username + ":" + goodsid;

		String secid = goodsid;

		Long value = (Long) rt.get(key);
		if (value != null) {
			return "exist";
		}

		Seckill sec = findSec(secid);
		boolean flag = queryStartTime(sec);
		if (!flag) {
			return "notTime";
		}

		RLock rLock = redissonClient.getLock("miaosha");
		rLock.lock();
		if (sec.getCount() > 0) {

			decreaseStock(goodsid); // 减少库存

			rt.set(key, System.currentTimeMillis(), 60*30);

			Secorder newOrder = new Secorder();

			newOrder.setCreatetime(new Date());
			newOrder.setGoodsid(Integer.parseInt(goodsid));
			newOrder.setStatus("未付款");
			newOrder.setUsername(username);

			String json = JSONObject.toJSONString(newOrder);

			rsm.send(json); // 
			rLock.unlock(); // 解锁

			return "success";

		} else {
			rLock.unlock();
			return "failed";
		}
	}

	// 写入mysql
	public void saveOrder(String json) {
		Secorder order = JSON.parseObject(json, Secorder.class);
		int n = sm.updateCount(order.getGoodsid());
		int m = om.insert(order);
	}
	

}
