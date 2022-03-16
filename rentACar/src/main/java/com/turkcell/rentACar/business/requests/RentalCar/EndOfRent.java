package com.turkcell.rentACar.business.requests.RentalCar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndOfRent {

	private int id;

	private Double endingKilometer;
}
