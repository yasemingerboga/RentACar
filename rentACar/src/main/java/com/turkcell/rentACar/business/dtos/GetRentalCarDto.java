package com.turkcell.rentACar.business.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRentalCarDto {
	private int rentalId;

	private LocalDate startingDate;

	private LocalDate endDate;

	private int carId;
}
