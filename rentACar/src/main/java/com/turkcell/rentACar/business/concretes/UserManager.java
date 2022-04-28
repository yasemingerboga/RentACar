package com.turkcell.rentACar.business.concretes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turkcell.rentACar.business.abstracts.RoleService;
import com.turkcell.rentACar.business.abstracts.UserService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.User.GetUserDto;
import com.turkcell.rentACar.business.dtos.User.UserListDto;
import com.turkcell.rentACar.business.requests.User.RoleToUserForm;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.UserDao;
import com.turkcell.rentACar.entities.concretes.Role;
import com.turkcell.rentACar.entities.concretes.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserManager implements UserService, UserDetailsService {
	private final UserDao userDao;
	private final RoleService roleService;
	private final ModelMapperService modelMapperService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		checkIfExistsByEmail(email);
		User user = userDao.findByEmail(email);
		Collection<SimpleGrantedAuthority> autorities = new ArrayList<SimpleGrantedAuthority>();
		user.getRoles().forEach(role -> {
			autorities.add(new SimpleGrantedAuthority(role.getName()));
		});
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), autorities);

	}

	@Override
	public Result addRoleToUser(RoleToUserForm roleToUserForm) {
		checkIfExistsById(roleToUserForm.getUserId());
		roleService.checkIfRoleExistsById(roleToUserForm.getRoleId());

		User user = userDao.getById(roleToUserForm.getUserId());

		Role role = roleService.getRoleById(roleToUserForm.getRoleId()).getData();

		log.info("Adding role {} to user {}", role.getName(), user.getEmail());

		user.getRoles().add(role);

		return new SuccessResult(BusinessMessages.ADD_ROLE_TO_USER_SUCCESSFULLY);

	}

	private void checkIfExistsById(int userId) {

		if (!userDao.existsById(userId)) {

			log.error(BusinessMessages.USER_NOT_FOUND);
			throw new BusinessException(BusinessMessages.USER_NOT_FOUND);
		}
	}

	private void checkIfExistsByEmail(String email) {

		if (!userDao.existsByEmail(email)) {

			log.error(BusinessMessages.USER_NOT_FOUND);
			throw new BusinessException(BusinessMessages.USER_NOT_FOUND);
		}
	}

	@Override
	public DataResult<GetUserDto> getUser(int userId) {

		checkIfExistsById(userId);

		log.info("Fetching user {} ", userId);

		User user = userDao.getById(userId);

		GetUserDto response = this.modelMapperService.forDto().map(user, GetUserDto.class);

		return new SuccessDataResult<GetUserDto>(response, BusinessMessages.USER_GET_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<UserListDto>> getUsers() {

		log.info("Fetching all users");

		List<User> users = userDao.findAll();

		List<UserListDto> response = users.stream()
				.map(user -> this.modelMapperService.forDto().map(user, UserListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<UserListDto>>(response, BusinessMessages.USER_GET_SUCCESSFULLY);
	}

	@Override
	public Result deleteRoleFromUser(RoleToUserForm roleToUserForm) {

		checkIfExistsById(roleToUserForm.getUserId());
		roleService.checkIfRoleExistsById(roleToUserForm.getRoleId());

		User user = userDao.getById(roleToUserForm.getUserId());

		Role role = roleService.getRoleById(roleToUserForm.getRoleId()).getData();

		log.info("Removing role {} from user {}", role.getName(), user.getEmail());

		user.getRoles().remove(role);

		return new SuccessResult(BusinessMessages.DELETE_ROLE_FROM_USER_SUCCESSFULLY);
	}

	@Override
	public DataResult<User> getUserByEmail(String email) {
		checkIfExistsByEmail(email);
		User user = userDao.findByEmail(email);
		return new SuccessDataResult<User>(user, BusinessMessages.USER_GET_SUCCESSFULLY);
	}

}
