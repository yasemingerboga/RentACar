package com.turkcell.rentACar.business.dtos.City;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCityDto {
	private int id;
	private String name;
}
