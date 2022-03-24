package com.turkcell.rentACar.business.requests.Payment;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePosServiceRequest {

	@NotNull
	private String cardNo;

	@NotNull
	@Size(min = 4)
	private String cardHolder;

	@NotNull
	private String month;
	
	@NotNull
	private String year;

	@NotNull
	@Size(min = 3, max = 3)
	private String cvv;

}
