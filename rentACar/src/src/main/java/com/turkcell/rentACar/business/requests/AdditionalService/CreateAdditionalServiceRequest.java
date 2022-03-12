package com.turkcell.rentACar.business.requests.AdditionalService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdditionalServiceRequest {
	private String additionalServiceName;
	private Double additionalServicePrice;
}
