package  com.bawei.ssm.reroleresource.controller;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import  com.bawei.ssm.reroleresource.model.RelationRoleResource;
import  com.bawei.ssm.reroleresource.service.RelationRoleResourceService;
import com.bawei.ssm.util.JsonUtil;

@RestController
@RequestMapping("/relationRoleResource")
public class RelationRoleResourceController {

	@Resource
	private RelationRoleResourceService relationRoleResourceService;

	@RequestMapping("/addRelationRoleResource")
	public String addRelationRoleResource(RelationRoleResource relationRoleResource) {
		relationRoleResourceService.addRelationRoleResource(relationRoleResource);
		return JsonUtil.success();
	}

	@RequestMapping("/getRelationRoleResource")
	public String getRelationRoleResource() {
		return JsonUtil.success(relationRoleResourceService.getRelationRoleResource());
	}

	@RequestMapping("/updateRelationRoleResource")
	public String updateRelationRoleResource(RelationRoleResource relationRoleResource) {
		relationRoleResourceService.updateRelationRoleResource(relationRoleResource);
		return JsonUtil.success();
	}

	@RequestMapping("/deleteRelationRoleResource")
	public String deleteRelationRoleResource(String id) {
		relationRoleResourceService.deleteRelationRoleResource(Long.valueOf(id));
		return JsonUtil.success();
	}

	@RequestMapping("/getRelationRoleResourceById")
	public String getRelationRoleResourceById(String id) {
		return JsonUtil.success(relationRoleResourceService.getRelationRoleResourceById(Long.valueOf(id)));
	}
}
