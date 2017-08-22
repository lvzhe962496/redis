package com.bawei.ssm.role.service;

import java.util.List;

import com.bawei.ssm.role.model.Role;

public interface RoleService {

	void addRole(Role role);

	void updateRole(Role role);

	List<Role> getRole();

	Role getRoleById(Long id);

	void deleteRole(Long id);

}
