package com.turkcell.rentACar.business.requests.City;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCityRequest {
	private int id;
	private String name;
}
