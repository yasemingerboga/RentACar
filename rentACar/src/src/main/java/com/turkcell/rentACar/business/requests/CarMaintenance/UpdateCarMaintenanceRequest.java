package com.turkcell.rentACar.business.requests.CarMaintenance;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarMaintenanceRequest {

	@NotNull
	@Min(1)
	@NotBlank
	private int carMaintenanceId;
	private String description;
	@NotNull
	private LocalDate returnDate;
	@NotNull
	@Min(1)
	private int carId;
}
