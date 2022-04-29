package com.turkcell.rentACar.business.concretes;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turkcell.rentACar.business.abstracts.RoleService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.dataAccess.abstracts.RoleDao;
import com.turkcell.rentACar.entities.concretes.Role;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleManager implements RoleService {
	private final RoleDao roleDao;

	@Override
	public DataResult<Role> saveRole(Role role) {
		checkIfRoleExistsByName(role.getName());
		log.info("Saving new role {} to the database", role.getName());
		roleDao.save(role);
		return new SuccessDataResult<Role>(role, BusinessMessages.ROLE_SAVE_SUCCESSFULLY);
	}

	private void checkIfRoleExistsByName(String name) {
		if (roleDao.existsByName(name)) {
			throw new BusinessException(BusinessMessages.ROLE_NOT_FOUND);
		}
	}

	@Override
	public DataResult<Role> getRoleById(int id) {
		checkIfRoleExistsById(id);
		Role role = roleDao.findById(id);
		return new SuccessDataResult<Role>(role, BusinessMessages.ROLE_GET_SUCCESSFULLY);
	}

	@Override
	public void checkIfRoleExistsById(int id) {

		if (!roleDao.existsById(id)) {
			throw new BusinessException(BusinessMessages.ROLE_NOT_FOUND);
		}
	}

}
