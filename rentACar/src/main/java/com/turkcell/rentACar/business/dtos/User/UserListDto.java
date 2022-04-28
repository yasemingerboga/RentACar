package com.turkcell.rentACar.business.dtos.User;

import java.util.Collection;

import com.turkcell.rentACar.entities.concretes.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserListDto {

	private int id;

	private String email;

	private Collection<Role> roles;

}
