package  com.bawei.ssm.reroleresource.service;

import java.util.List;

import  com.bawei.ssm.reroleresource.model.RelationRoleResource;

public interface RelationRoleResourceService {

	void addRelationRoleResource(RelationRoleResource relationRoleResource);

	void updateRelationRoleResource(RelationRoleResource relationRoleResource);

	List<RelationRoleResource> getRelationRoleResource();

	RelationRoleResource getRelationRoleResourceById(Long id);

	void deleteRelationRoleResource(Long id);

}
