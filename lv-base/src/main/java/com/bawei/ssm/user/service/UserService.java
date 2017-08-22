package com.bawei.ssm.user.service;

import java.util.List;

import com.bawei.ssm.user.model.User;
import com.bawei.ssm.user.model.UserForm;

/**
 * @author liuzeke
 * @version 1.0
 * @date 2017年8月17日
 * @time 下午1:40:40
 */
public interface UserService {

	/**
	 * 新增用户
	 * 
	 * @author liuzeke
	 * @date 2017年8月17日
	 * @time 下午1:42:18
	 * @param user
	 */
	void addUser(User user);

	/**
	 * 更新用户
	 * 
	 * @author liuzeke
	 * @date 2017年8月17日
	 * @time 下午1:42:53
	 * @param user
	 */
	void updateUser(User user);

	/**
	 * 查询用户
	 * 
	 * @author liuzeke
	 * @date 2017年8月17日
	 * @time 下午1:42:59
	 * @return
	 */
	List<User> getUser();

	/**
	 * 根据id查询用户
	 * 
	 * @author liuzeke
	 * @date 2017年8月17日
	 * @time 下午1:43:06
	 * @param id
	 * @return
	 */
	User getUserById(Long id);

	/**
	 * 删除用户
	 * 
	 * @author liuzeke
	 * @date 2017年8月17日
	 * @time 下午1:43:17
	 * @param id
	 */
	void deleteUser(Long id);

	/**
	 * @author liuzeke
	 * @date 2017年8月22日
	 * @time 上午8:24:33
	 * @param username
	 * @param name
	 * @return
	 */
	List<User> getUserByUsernameAndName(String username, String name);

	/**
	 * @author liuzeke
	 * @date 2017年8月22日
	 * @time 下午2:56:04
	 * @return
	 */
	List<UserForm> getUserForm();

}
