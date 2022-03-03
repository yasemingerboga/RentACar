package com.turkcell.rentACar.business.requests;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarMaintenanceRequest {
	private String description;
	@NotNull
	private Date returnDate;
	@NotNull
	@Min(1)
	private int carId;
}
