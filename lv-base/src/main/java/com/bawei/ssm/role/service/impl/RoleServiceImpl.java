package com.bawei.ssm.role.service.impl;

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
import com.bawei.ssm.role.mapper.RoleMapper;
import com.bawei.ssm.role.model.Role;
import com.bawei.ssm.role.model.RoleExample;
import com.bawei.ssm.role.service.RoleService;
import com.bawei.ssm.util.BeanUtil;
import com.bawei.ssm.util.BlankUtil;

@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class RoleServiceImpl implements RoleService {

	@Resource
	private RoleMapper roleMapper;

	@Resource
	private RedisTemplate redisTemplate;

	@Override
	public void addRole(final Role role) {
		final Long id = getId();
		role.setId(id);
		final byte[] key = redisTemplate.getStringSerializer()
				.serialize(String.format(RedisKeys.MODEL_KEY, Role.class.getName(), id.toString()));
		redisTemplate.execute(new RedisCallback<Role>() {
			@Override
			public Role doInRedis(RedisConnection connection) throws DataAccessException {
				connection.set(key, redisTemplate.getValueSerializer().serialize(role));
				connection.zAdd(redisTemplate.getStringSerializer()
						.serialize(String.format(RedisKeys.MODEL_ALL, Role.class.getName())), id, key);
				return null;
			}
		});
		roleMapper.insert(role);
	}

	@Override
	public void updateRole(final Role role) {
		final byte[] key = redisTemplate.getStringSerializer()
				.serialize(String.format(RedisKeys.MODEL_KEY, Role.class.getName(), role.getId().toString()));
		redisTemplate.execute(new RedisCallback<Role>() {
			@Override
			public Role doInRedis(RedisConnection connection) throws DataAccessException {
				Role roleCache = (Role) redisTemplate.getValueSerializer().deserialize(connection.get(key));
				BeanUtil.copyProperty(role, roleCache);
				connection.set(key, redisTemplate.getValueSerializer().serialize(roleCache));
				return null;
			}
		});
		roleMapper.updateByPrimaryKeySelective(role);
	}

	@Override
	public List<Role> getRole() {

		final Page page = PageHandle.getPage();

		final List<Role> roleList = new ArrayList<Role>();
		redisTemplate.execute(new RedisCallback<Role>() {
			@Override
			public Role doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keys = redisTemplate.getStringSerializer()
						.serialize(String.format(RedisKeys.MODEL_ALL, Role.class.getName()));
				page.setPageCount(connection.zCard(keys));
				Set<byte[]> bytes = connection.zRange(keys, page.getStart(), page.getStart() + page.getPageSize() - 1);
				for (byte[] b : bytes) {
					roleList.add((Role) redisTemplate.getValueSerializer().deserialize(connection.get(b)));
				}
				return null;
			}
		});
		if (BlankUtil.isNotBlank(roleList))
			return roleList;
		return roleMapper.selectByExample(new RoleExample());
	}

	@Override
	public Role getRoleById(final Long id) {
		Role role = null;
		role = (Role) redisTemplate.execute(new RedisCallback<Role>() {
			@Override
			public Role doInRedis(RedisConnection connection) throws DataAccessException {
				return (Role) redisTemplate.getValueSerializer()
						.deserialize(connection.get(redisTemplate.getStringSerializer()
								.serialize(String.format(RedisKeys.MODEL_KEY, Role.class.getName(), id.toString()))));
			}
		});
		return role == null ? roleMapper.selectByPrimaryKey(id) : role;
	}

	@Override
	public void deleteRole(Long id) {
		final byte[] key = redisTemplate.getStringSerializer()
				.serialize(String.format(RedisKeys.MODEL_KEY, Role.class.getName(), id.toString()));
		redisTemplate.execute(new RedisCallback<Role>() {
			@Override
			public Role doInRedis(RedisConnection connection) throws DataAccessException {
				connection.del(key);
				connection.zRem(redisTemplate.getStringSerializer()
						.serialize(String.format(RedisKeys.MODEL_ALL, Role.class.getName())), key);
				return null;
			}
		});
		roleMapper.deleteByPrimaryKey(id);
	}

	public Long getId() {
		return (Long) redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.incr(redisTemplate.getStringSerializer().serialize(Role.class.getName()));
			}
		});
	}

}
