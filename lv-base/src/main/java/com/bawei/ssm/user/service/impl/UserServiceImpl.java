package com.bawei.ssm.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.bawei.ssm.common.Page;
import com.bawei.ssm.common.PageHandle;
import com.bawei.ssm.common.RedisKeys;
import com.bawei.ssm.user.mapper.UserMapper;
import com.bawei.ssm.user.model.User;
import com.bawei.ssm.user.model.UserExample;
import com.bawei.ssm.user.service.UserService;
import com.bawei.ssm.util.BeanUtil;
import com.bawei.ssm.util.BlankUtil;

/**
 * 
 * @author 威廉
 *
 */
@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class UserServiceImpl implements UserService {

	@Resource
	private UserMapper userMapper;

	@Resource
	private RedisTemplate redisTemplate;
	/**
	 * 
	 * @author 威廉
	 *
	 */
	@Override
	public void addUser(final User user) {
		final Long id = getId();
		user.setId(id);
		final byte[] key = redisTemplate.getStringSerializer()
				.serialize(String.format(RedisKeys.MODEL_KEY, User.class.getName(), id.toString()));
		redisTemplate.execute(new RedisCallback<User>() {
			@Override
			public User doInRedis(RedisConnection connection) throws DataAccessException {
				connection.set(key, redisTemplate.getValueSerializer().serialize(user));
				connection.zAdd(redisTemplate.getStringSerializer()
						.serialize(String.format(RedisKeys.MODEL_ALL, User.class.getName())), id, key);
				return null;
			}
		});
		userMapper.insert(user);
	}

	/**
	 * 
	 * @author 威廉
	 *
	 */
	@Override
	public void updateUser(final User user) {
		final byte[] key = redisTemplate.getStringSerializer()
				.serialize(String.format(RedisKeys.MODEL_KEY, User.class.getName(), user.getId().toString()));
		redisTemplate.execute(new RedisCallback<User>() {
			@Override
			public User doInRedis(RedisConnection connection) throws DataAccessException {
				User userCache = (User) redisTemplate.getValueSerializer().deserialize(connection.get(key));
				BeanUtil.copyProperty(user, userCache);
				connection.set(key, redisTemplate.getValueSerializer().serialize(userCache));
				return null;
			}
		});
		userMapper.updateByPrimaryKeySelective(user);
	}

	/**
	 * 
	 * @author 威廉
	 *
	 */
	@Override
	public List<User> getUser() {

		final Page page = PageHandle.getPage();

		final		List<User> userList = new ArrayList<User>();
		redisTemplate.execute(new RedisCallback<User>() {
			@Override
			public User doInRedis(RedisConnection connection) throws DataAccessException {
				Set<byte[ ]> bytes=connection.zRange(redisTemplate.getStringSerializer()
						.serialize(String.format(RedisKeys.MODEL_ALL, User.class.getName()))
						, page.getStart(), page.getStart() + page.getPageSize() - 1);

				for(byte[] b:bytes){
					userList.add((User) redisTemplate.getValueSerializer().deserialize(connection.get(b)));
				}
				return null;
			}
		});
		if(BlankUtil.isNotBlank(userList))
			return userList;
		return userMapper.selectByExample(new UserExample());
	}

	/**
	 * 
	 * @author 威廉
	 *
	 */
	@Override
	public User getUserById(final Long id) {
		User user = null;
		user = (User) redisTemplate.execute(new RedisCallback<User>() {
			@Override
			public User doInRedis(RedisConnection connection) throws DataAccessException {
				return (User) redisTemplate.getValueSerializer()
						.deserialize(connection.get(redisTemplate.getStringSerializer()
								.serialize(String.format(RedisKeys.MODEL_KEY, User.class.getName(), id.toString()))));
			}
		});
		return user == null ? userMapper.selectByPrimaryKey(id) : user;
	}

	/**
	 * 
	 * @author 威廉
	 *
	 */
	@Override
	public void deleteUser(Long id) {
		final byte[] key = redisTemplate.getStringSerializer()
				.serialize(String.format(RedisKeys.MODEL_KEY, User.class.getName(), id.toString()));
		redisTemplate.execute(new RedisCallback<User>() {
			@Override
			public User doInRedis(RedisConnection connection) throws DataAccessException {
				connection.del(key);
				return null;
			}
		});
		userMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 
	 * @author 威廉
	 *
	 */
	public Long getId() {
		return (Long) redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.incr(redisTemplate.getStringSerializer().serialize(User.class.getName()));
			}
		});
	}
}
