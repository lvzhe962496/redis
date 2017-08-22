package com.bawei.ssm.resource.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.bawei.ssm.common.Page;
import com.bawei.ssm.common.PageHandle;
import com.bawei.ssm.common.RedisKeys;
import com.bawei.ssm.resource.mapper.ResourceMapper;
import com.bawei.ssm.resource.model.Resource;
import com.bawei.ssm.resource.model.ResourceExample;
import com.bawei.ssm.resource.service.ResourceService;
import com.bawei.ssm.util.BeanUtil;
import com.bawei.ssm.util.BlankUtil;

@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ResourceServiceImpl implements ResourceService {

	@javax.annotation.Resource
	private ResourceMapper resourceMapper;

	@javax.annotation.Resource
	private RedisTemplate redisTemplate;

	@Override
	public void addResource(final Resource resource) {
		final Long id = getId();
		resource.setId(id);
		final byte[] key = redisTemplate.getStringSerializer()
				.serialize(String.format(RedisKeys.MODEL_KEY, Resource.class.getName(), id.toString()));
		redisTemplate.execute(new RedisCallback<Resource>() {
			@Override
			public Resource doInRedis(RedisConnection connection) throws DataAccessException {
				connection.set(key, redisTemplate.getValueSerializer().serialize(resource));
				connection.zAdd(redisTemplate.getStringSerializer()
						.serialize(String.format(RedisKeys.MODEL_ALL, Resource.class.getName())), id, key);
				return null;
			}
		});
		resourceMapper.insert(resource);
	}

	@Override
	public void updateResource(final Resource resource) {
		final byte[] key = redisTemplate.getStringSerializer()
				.serialize(String.format(RedisKeys.MODEL_KEY, Resource.class.getName(), resource.getId().toString()));
		redisTemplate.execute(new RedisCallback<Resource>() {
			@Override
			public Resource doInRedis(RedisConnection connection) throws DataAccessException {
				Resource resourceCache = (Resource) redisTemplate.getValueSerializer().deserialize(connection.get(key));
				BeanUtil.copyProperty(resource, resourceCache);
				connection.set(key, redisTemplate.getValueSerializer().serialize(resourceCache));
				return null;
			}
		});
		resourceMapper.updateByPrimaryKeySelective(resource);
	}

	@Override
	public List<Resource> getResource() {

		final Page page = PageHandle.getPage();

		final List<Resource> resourceList = new ArrayList<Resource>();
		redisTemplate.execute(new RedisCallback<Resource>() {
			@Override
			public Resource doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keys = redisTemplate.getStringSerializer()
						.serialize(String.format(RedisKeys.MODEL_ALL, Resource.class.getName()));
				page.setPageCount(connection.zCard(keys));
				Set<byte[]> bytes = connection.zRange(keys, page.getStart(), page.getStart() + page.getPageSize() - 1);
				for (byte[] b : bytes) {
					resourceList.add((Resource) redisTemplate.getValueSerializer().deserialize(connection.get(b)));
				}
				return null;
			}
		});
		if (BlankUtil.isNotBlank(resourceList))
			return resourceList;
		return resourceMapper.selectByExample(new ResourceExample());
	}

	@Override
	public Resource getResourceById(final Long id) {
		Resource resource = null;
		resource = (Resource) redisTemplate.execute(new RedisCallback<Resource>() {
			@Override
			public Resource doInRedis(RedisConnection connection) throws DataAccessException {
				return (Resource) redisTemplate.getValueSerializer()
						.deserialize(connection.get(redisTemplate.getStringSerializer().serialize(
								String.format(RedisKeys.MODEL_KEY, Resource.class.getName(), id.toString()))));
			}
		});
		return resource == null ? resourceMapper.selectByPrimaryKey(id) : resource;
	}

	@Override
	public void deleteResource(Long id) {
		final byte[] key = redisTemplate.getStringSerializer()
				.serialize(String.format(RedisKeys.MODEL_KEY, Resource.class.getName(), id.toString()));
		redisTemplate.execute(new RedisCallback<Resource>() {
			@Override
			public Resource doInRedis(RedisConnection connection) throws DataAccessException {
				connection.del(key);
				connection.zRem(redisTemplate.getStringSerializer()
						.serialize(String.format(RedisKeys.MODEL_ALL, Resource.class.getName())), key);
				return null;
			}
		});
		resourceMapper.deleteByPrimaryKey(id);
	}

	public Long getId() {
		return (Long) redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.incr(redisTemplate.getStringSerializer().serialize(Resource.class.getName()));
			}
		});
	}
}
