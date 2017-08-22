package  com.bawei.ssm.reroleresource.service.impl;

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
import  com.bawei.ssm.reroleresource.mapper.RelationRoleResourceMapper;
import  com.bawei.ssm.reroleresource.model.RelationRoleResource;
import  com.bawei.ssm.reroleresource.model.RelationRoleResourceExample;
import  com.bawei.ssm.reroleresource.model.RelationRoleResourceExample.Criteria;
import  com.bawei.ssm.reroleresource.service.RelationRoleResourceService;
import com.bawei.ssm.util.BeanUtil;
import com.bawei.ssm.util.BlankUtil;

@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class RelationRoleResourceServiceImpl implements RelationRoleResourceService {

	@Resource
	private RelationRoleResourceMapper relationRoleResourceMapper;

	@Resource
	private RedisTemplate redisTemplate;

	@Override
	public void addRelationRoleResource(final RelationRoleResource relationRoleResource) {
		final Long id = getId();
		relationRoleResource.setId(id);
		final byte[] key = redisTemplate.getStringSerializer()
				.serialize(String.format(RedisKeys.MODEL_KEY, RelationRoleResource.class.getName(), id.toString()));
		redisTemplate.execute(new RedisCallback<RelationRoleResource>() {
			@Override
			public RelationRoleResource doInRedis(RedisConnection connection) throws DataAccessException {
				connection.set(key, redisTemplate.getValueSerializer().serialize(relationRoleResource));
				connection.zAdd(redisTemplate.getStringSerializer()
						.serialize(String.format(RedisKeys.MODEL_ALL, RelationRoleResource.class.getName())), id, key);
				return null;
			}
		});
		relationRoleResourceMapper.insert(relationRoleResource);
	}

	@Override
	public void updateRelationRoleResource(final RelationRoleResource relationRoleResource) {
		final byte[] key = redisTemplate.getStringSerializer()
				.serialize(String.format(RedisKeys.MODEL_KEY, RelationRoleResource.class.getName(), relationRoleResource.getId().toString()));
		redisTemplate.execute(new RedisCallback<RelationRoleResource>() {
			@Override
			public RelationRoleResource doInRedis(RedisConnection connection) throws DataAccessException {
				RelationRoleResource relationRoleResourceCache = (RelationRoleResource) redisTemplate.getValueSerializer().deserialize(connection.get(key));
				BeanUtil.copyProperty(relationRoleResource, relationRoleResourceCache);
				connection.set(key, redisTemplate.getValueSerializer().serialize(relationRoleResourceCache));
				return null;
			}
		});
		relationRoleResourceMapper.updateByPrimaryKeySelective(relationRoleResource);
	}

	@Override
	public List<RelationRoleResource> getRelationRoleResource() {

		final Page page = PageHandle.getPage();

		final List<RelationRoleResource> relationRoleResourceList = new ArrayList<RelationRoleResource>();
		redisTemplate.execute(new RedisCallback<RelationRoleResource>() {
			@Override
			public RelationRoleResource doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keys = redisTemplate.getStringSerializer()
						.serialize(String.format(RedisKeys.MODEL_ALL, RelationRoleResource.class.getName()));
				page.setPageCount(connection.zCard(keys));
				Set<byte[]> bytes = connection.zRange(keys, page.getStart(), page.getStart() + page.getPageSize() - 1);
				for (byte[] b : bytes) {
					relationRoleResourceList.add((RelationRoleResource) redisTemplate.getValueSerializer().deserialize(connection.get(b)));
				}
				return null;
			}
		});
		if (BlankUtil.isNotBlank(relationRoleResourceList))
			return relationRoleResourceList;
		return relationRoleResourceMapper.selectByExample(new RelationRoleResourceExample());
	}

	@Override
	public RelationRoleResource getRelationRoleResourceById(final Long id) {
		RelationRoleResource relationRoleResource = null;
		relationRoleResource = (RelationRoleResource) redisTemplate.execute(new RedisCallback<RelationRoleResource>() {
			@Override
			public RelationRoleResource doInRedis(RedisConnection connection) throws DataAccessException {
				return (RelationRoleResource) redisTemplate.getValueSerializer()
						.deserialize(connection.get(redisTemplate.getStringSerializer()
								.serialize(String.format(RedisKeys.MODEL_KEY, RelationRoleResource.class.getName(), id.toString()))));
			}
		});
		return relationRoleResource == null ? relationRoleResourceMapper.selectByPrimaryKey(id) : relationRoleResource;
	}

	@Override
	public void deleteRelationRoleResource(Long id) {
		final byte[] key = redisTemplate.getStringSerializer()
				.serialize(String.format(RedisKeys.MODEL_KEY, RelationRoleResource.class.getName(), id.toString()));
		redisTemplate.execute(new RedisCallback<RelationRoleResource>() {
			@Override
			public RelationRoleResource doInRedis(RedisConnection connection) throws DataAccessException {
				connection.del(key);
				connection.zRem(redisTemplate.getStringSerializer()
						.serialize(String.format(RedisKeys.MODEL_ALL, RelationRoleResource.class.getName())), key);
				return null;
			}
		});
		relationRoleResourceMapper.deleteByPrimaryKey(id);
	}

	public Long getId() {
		return (Long) redisTemplate.execute(new RedisCallback<Long>() {
			@Override
			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				return connection.incr(redisTemplate.getStringSerializer().serialize(RelationRoleResource.class.getName()));
			}
		});
	}
}
