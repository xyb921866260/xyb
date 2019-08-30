package com.task;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mapper.SeckillMapper;
import com.rabbitMQ.RabbitmqSendMessage;
import com.redis.RedisClusterClient;
import com.pojo.*;


public class Scheduled {
	
	@Autowired
	private SeckillMapper sm;
	
	@Autowired
	private RabbitmqSendMessage rs;
	
	@Autowired
    private RedisClusterClient re;
	
	//每个整点的前一分钟更新redis
	@org.springframework.scheduling.annotation.Scheduled(cron="0 10 * * * ?")
	public void updateSeckillGoods() {
		List<Seckill> list = sm.selectlastestgoods(new Date());
		System.out.println("定时任务开始执行");
		for(Seckill l:list) {
			System.out.println("缓存:"+l.getGoodsname());
			re.set("sec"+l.getId(), l, 3600);
		}
	}
}
