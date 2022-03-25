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

	private int id;

	private LocalDate startingDate;

	private LocalDate endDate;

	private int carId;

	private Double additionalPrice;

	private GetCityDto rentalCityDto;

	private GetCityDto dropOffCityDto;

	private Long totalRentDay;

	private Double totalPrice;

	private List<OrderedAdditionalServiceListDto> orderedAdditionalServiceList;

	private int customerUserId;

	private Double startingKilometer;

	private Double endingKilometer;

}
