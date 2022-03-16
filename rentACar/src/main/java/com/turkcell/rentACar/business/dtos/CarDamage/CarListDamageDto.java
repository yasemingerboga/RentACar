package com.turkcell.rentACar.business.dtos.CarDamage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarListDamageDto {

	private int id;

	private String description;

	private int carId;

}
