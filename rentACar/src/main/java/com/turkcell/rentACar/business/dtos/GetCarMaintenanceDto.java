package com.turkcell.rentACar.business.dtos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCarMaintenanceDto {
	private int carMaintenanceId;
	
	private String description;
	
	private Date returnDate;
	
}
