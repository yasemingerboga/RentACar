package com.turkcell.rentACar.business.requests.RentalCar;

import java.time.LocalDate;

import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalCarRequest {

	@Positive
	private int id;

	private LocalDate startingDate;

	private LocalDate endDate;

	@Positive
	private int carId;

	@Positive
	private int rentedCityId;

	@Positive
	private int dropOffCityId;

	@Positive
	private int customerUserId;
}
