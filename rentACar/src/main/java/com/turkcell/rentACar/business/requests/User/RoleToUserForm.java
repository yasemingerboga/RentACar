package com.turkcell.rentACar.business.requests.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleToUserForm {
	private int userId;
	private int roleId;
}
