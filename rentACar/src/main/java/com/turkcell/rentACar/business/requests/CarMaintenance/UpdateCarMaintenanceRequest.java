package com.turkcell.rentACar.business.requests.CarMaintenance;

import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarMaintenanceRequest {

	@Positive
	private int carMaintenanceId;
	private String description;
	@NotNull
	private LocalDate returnDate;
	@Positive
	private int carId;
}
