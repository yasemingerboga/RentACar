package com.turkcell.rentACar.business.dtos.OrderedAdditionalService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetOrderedAdditionalServiceDto {
	private int id;
	private String additionalServiceName;
}
