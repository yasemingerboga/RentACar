package com.turkcell.rentACar.business.requests.IndividualCustomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateIndividualCustomerRequest {

	private int id;

	private String email;

	private String password;

	private String firstName;

	private String lastName;

	private String nationalIdentity;
}
