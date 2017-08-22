package com.bawei.ssm.role.controller;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bawei.ssm.role.model.Role;
import com.bawei.ssm.role.service.RoleService;
import com.bawei.ssm.util.JsonUtil;

@RestController
@RequestMapping("/role")
public class RoleController {

	@Resource
	private RoleService roleService;

	@RequestMapping("/addRole")
	public String addRole(Role role) {
		roleService.addRole(role);
		return JsonUtil.success();
	}

	@RequestMapping("/getRole")
	public String getRole() {
		return JsonUtil.success(roleService.getRole());
	}

	@RequestMapping("/updateRole")
	public String updateRole(Role role) {
		roleService.updateRole(role);
		return JsonUtil.success();
	}

	@RequestMapping("/deleteRole")
	public String deleteRole(String id) {
		roleService.deleteRole(Long.valueOf(id));
		return JsonUtil.success();
	}

	@RequestMapping("/getRoleById")
	public String getRoleById(String id) {
		return JsonUtil.success(roleService.getRoleById(Long.valueOf(id)));
	}
}
