package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pojo.Seckill;
import com.service.MainService;
import com.service.SeckillService;

@Controller
public class MainController {

	@Autowired
	private SeckillService sec;

	@Autowired
	private MainService ms;


	@RequestMapping(value = "home")
	public String home(ModelMap model ) {
		List<Seckill> list = sec.querySeckill();
		model.addAttribute("time", list.get(0).getStarttime());
		model.addAttribute("list", list);
		return "home";
	}

	@RequestMapping(value = "login")
	public String login() {

		return "login";
	}

	@RequestMapping(value = "dologin", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> dologin(String username, String password, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag = ms.dologin(username, password);
		if (flag) {
			request.getSession().setAttribute( "user",username);
			resultMap.put("data", "success");
			return resultMap;
		}
		resultMap.put("data", "fail");
		return resultMap;
	}

	@RequestMapping(value = "seckill")
	@ResponseBody
	public Map<String, Object> seckill(HttpServletRequest request,String id) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String username = (String) request.getSession().getAttribute("user");
		String goodsid=id; 
		if(username==null) {
			resultMap.put("data", "notLogin");
			return resultMap;
		}else {
			String data = sec.goSeckill(goodsid, username);
			resultMap.put("data", data);
			return resultMap;
		}
	}
	
}
