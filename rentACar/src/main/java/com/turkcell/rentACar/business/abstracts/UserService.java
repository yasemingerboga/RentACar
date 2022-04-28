package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.User.GetUserDto;
import com.turkcell.rentACar.business.dtos.User.UserListDto;
import com.turkcell.rentACar.business.requests.User.RoleToUserForm;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concretes.User;

public interface UserService {

	Result addRoleToUser(RoleToUserForm roleToUserForm);

	Result deleteRoleFromUser(RoleToUserForm roleToUserForm);

	DataResult<GetUserDto> getUser(int userId);

	DataResult<User> getUserByEmail(String email);

	DataResult<List<UserListDto>> getUsers();

}
