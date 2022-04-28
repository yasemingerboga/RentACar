package com.turkcell.rentACar.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.business.abstracts.UserService;
import com.turkcell.rentACar.business.dtos.User.GetUserDto;
import com.turkcell.rentACar.business.dtos.User.UserListDto;
import com.turkcell.rentACar.business.requests.User.RoleToUserForm;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {

	private final UserService userService;

	@PostMapping("/addRoleToUser")
	public Result addRoleToUser(@RequestBody RoleToUserForm roleToUserForm) {
		return userService.addRoleToUser(roleToUserForm);
	}

	@GetMapping("/getByUserId")
	public DataResult<GetUserDto> getUser(@RequestParam int userId) {
		return userService.getUser(userId);
	}

	@GetMapping("/getall")
	public DataResult<List<UserListDto>> getUsers() {
		return userService.getUsers();
	}

	@DeleteMapping("/deleteRoleFromUser")
	public Result deleteRoleFromUser(@RequestBody RoleToUserForm roleToUserForm) {
		return userService.deleteRoleFromUser(roleToUserForm);
	}

}
