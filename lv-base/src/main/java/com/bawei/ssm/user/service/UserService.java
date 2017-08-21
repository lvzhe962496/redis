package com.bawei.ssm.user.service;

import java.util.List;

import com.bawei.ssm.user.model.User;

public interface UserService {
	/**
	 * addUser
	 * 作者:威廉
	 * 时间:2017年8月17日下午4:51:16
	 */
	void addUser(User user);
	/**
	 * updateUser
	 * 作者:威廉
	 * 时间:2017年8月17日下午4:51:29
	 */
	void updateUser(User user);
	/**
	 * select
	 * 作者:威廉
	 * 时间:2017年8月17日下午4:51:36
	 */
	List<User> getUser();
	/**
	 * getUserById
	 * 作者:威廉
	 * 时间:2017年8月17日下午4:51:44
	 */
	User getUserById(Long id);
	/**
	 * deleteUser
	 * 作者:威廉
	 * 时间:2017年8月17日下午4:51:49
	 */
	void deleteUser(Long id);
}
