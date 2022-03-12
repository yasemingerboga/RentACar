package com.turkcell.rentACar.business.dtos.RentalCar;

import java.time.LocalDate;
import java.util.List;

import com.turkcell.rentACar.business.dtos.City.GetCityDto;
import com.turkcell.rentACar.business.dtos.OrderedAdditionalService.OrderedAdditionalServiceListDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalCarListDto {
	private int rentalId;
	private LocalDate startingDate;
	private LocalDate endDate;
	private int carId;
	private Double additionalPrice;
	private GetCityDto rentalCityDto;
	private GetCityDto dropOffCityDto;
	private List<OrderedAdditionalServiceListDto> orderedAdditionalServiceList;
}
