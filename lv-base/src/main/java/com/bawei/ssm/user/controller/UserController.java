package com.bawei.ssm.user.controller;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bawei.ssm.user.model.User;
import com.bawei.ssm.user.service.UserService;
import com.bawei.ssm.util.JsonUtil;

/**
 * @author liuzeke
 * @version 1.0
 * @date 2017年8月17日
 * @time 下午1:49:58
 */
@RestController
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;

	/**
	 * @author liuzeke
	 * @date 2017年8月17日
	 * @time 下午2:04:59
	 * @param user
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/addUser")
	public String addUser(User user) throws UnsupportedEncodingException {
		userService.addUser(user);
		return JsonUtil.success();
	}

	/**
	 * @author liuzeke
	 * @date 2017年8月17日
	 * @time 下午2:06:21
	 * @return
	 */
	@RequestMapping("/getUser")
	public String getUser() {
		return JsonUtil.success(userService.getUser());
	}

	/**
	 * @author liuzeke
	 * @date 2017年8月17日
	 * @time 下午2:07:11
	 * @param user
	 * @return
	 */
	@RequestMapping("/updateUser")
	public String updateUser(User user) {
		userService.updateUser(user);
		return JsonUtil.success();
	}

	/**
	 * @author liuzeke
	 * @date 2017年8月17日
	 * @time 下午2:09:12
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteUser")
	public String deleteUser(String id) {
		userService.deleteUser(Long.valueOf(id));
		return JsonUtil.success();
	}

	/**
	 * @author liuzeke
	 * @date 2017年8月18日
	 * @time 下午2:08:53
	 * @param id
	 * @return
	 */
	@RequestMapping("/getUserById")
	public String getUserById(String id) {
		return JsonUtil.success(userService.getUserById(Long.valueOf(id)));
	}

	/**
	 * @author liuzeke
	 * @date 2017年8月22日
	 * @time 上午8:35:09
	 * @param username
	 * @param name
	 * @return
	 */
	@RequestMapping("/getUserByUsernameAndName")
	public String getUserByUsernameAndName(String username, String name) {
		return JsonUtil.success(userService.getUserByUsernameAndName(username, name));
	}

	/**
	 * @author liuzeke
	 * @date 2017年8月22日
	 * @time 下午3:01:54
	 * @return
	 */
	@RequestMapping("/getUserForm")
	public String getUserForm() {
		return JsonUtil.success(userService.getUserForm());
	}
}
