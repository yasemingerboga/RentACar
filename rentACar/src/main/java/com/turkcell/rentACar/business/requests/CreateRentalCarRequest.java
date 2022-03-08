package com.turkcell.rentACar.business.requests;

import java.time.LocalDate;

import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalCarRequest {
	private LocalDate startingDate;

	private LocalDate endDate;

	@Positive
	private int carId;
}
