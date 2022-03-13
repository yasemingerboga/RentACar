package com.turkcell.rentACar.business.dtos.CorporateCustomer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCorporateCustomerDto {
	private int id;

	private String email;

	private String password;

	private String companyName;

	private String taxNumber;
}
