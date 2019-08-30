package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapper.UserinfoMapper;
import com.pojo.Userinfo;
import com.pojo.UserinfoExample;
@Service
public class MainService {

	@Autowired
	private UserinfoMapper um;
	
	public boolean dologin(String username,String password) {
		UserinfoExample ex = new UserinfoExample();
		ex.createCriteria().andUsernameEqualTo(username).andPasswordEqualTo(password);
		List<Userinfo> list =um.selectByExample(ex);
		if(list==null) {
			return false;
		}
		return true;
	}
}
