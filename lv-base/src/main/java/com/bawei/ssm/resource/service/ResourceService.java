package  com.bawei.ssm.resource.service;

import java.util.List;

import  com.bawei.ssm.resource.model.Resource;

public interface ResourceService {

	void addResource(Resource resource);

	void updateResource(Resource resource);

	List<Resource> getResource();

	Resource getResourceById(Long id);

	void deleteResource(Long id);

}
