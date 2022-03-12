package com.turkcell.rentACar.business.dtos.AdditionalService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalServiceListDto {
	private int id;
	private String name;
	private Double price;
}
