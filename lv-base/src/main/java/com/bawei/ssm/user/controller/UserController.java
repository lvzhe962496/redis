package com.bawei.ssm.user.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bawei.ssm.user.model.User;
import com.bawei.ssm.user.service.UserService;
import com.bawei.ssm.util.JsonUtil;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Resource
	private UserService userService;
	
	@RequestMapping("/addUser")
	public String getUser(User user){
		userService.addUser(user);
		return JsonUtil.success();
	}
	
	@RequestMapping("/getUser")
	public String getUser() {
		return JsonUtil.success(userService.getUser());
	}
	@RequestMapping("updateUser")
	public String updateUser(User user) {
		userService.updateUser(user);
		return JsonUtil.success();
	}
	@RequestMapping("deleteUser")
	public String deleteUser(String id) {
		userService.deleteUser(Long.valueOf(id));
		return JsonUtil.success();
	}

}
