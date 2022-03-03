package com.turkcell.rentACar.business.requests;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRequest {
	
	@NotNull
	@Min(0)
	private double dailyPrice;
	@NotNull
	@Min(1980)
	@Max(2022)
	private int modelYear;
	private String description;
	@NotNull
	@Min(1)
	private int brandId;
	@NotNull
	@Min(1)
	private int colorId;
}
