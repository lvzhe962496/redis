package com.bawei.ssm.resource.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bawei.ssm.resource.model.Resource;
import com.bawei.ssm.resource.service.ResourceService;
import com.bawei.ssm.util.JsonUtil;

@RestController
@RequestMapping("/resource")
public class ResourceController {

	@javax.annotation.Resource
	private ResourceService resourceService;

	@RequestMapping("/addResource")
	public String addResource(Resource resource) {
		resourceService.addResource(resource);
		return JsonUtil.success();
	}

	@RequestMapping("/getResource")
	public String getResource() {
		return JsonUtil.success(resourceService.getResource());
	}

	@RequestMapping("/updateResource")
	public String updateResource(Resource resource) {
		resourceService.updateResource(resource);
		return JsonUtil.success();
	}

	@RequestMapping("/deleteResource")
	public String deleteResource(String id) {
		resourceService.deleteResource(Long.valueOf(id));
		return JsonUtil.success();
	}

	@RequestMapping("/getResourceById")
	public String getResourceById(String id) {
		return JsonUtil.success(resourceService.getResourceById(Long.valueOf(id)));
	}
}
