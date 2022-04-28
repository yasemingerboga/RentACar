package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.entities.concretes.Role;

public interface RoleService {

	DataResult<Role> saveRole(Role role);

	DataResult<Role> getRoleById(int id);

	void checkIfRoleExistsById(int id);

}
