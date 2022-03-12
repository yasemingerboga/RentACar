package com.turkcell.rentACar.business.requests.RentalCar;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.Positive;

import com.turkcell.rentACar.entities.concretes.OrderedAdditionalService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalCarRequest {
	@Positive
	private int rentalId;
	private LocalDate startingDate;
	private LocalDate endDate;
	@Positive
	private int carId;
	@Positive
	private int rentedCityId;
	@Positive
	private int dropOffCityId;
	private List<OrderedAdditionalService> orderedAdditionalServices;
}
