package com.turkcell.rentACar.api.controllers.models.RentalCar;

import java.util.List;

import com.turkcell.rentACar.business.requests.OrderedAdditionalService.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.RentalCar.CreateRentalCarRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRentalModel {
	private CreateRentalCarRequest createRentalCarRequest;
	private List<CreateOrderedAdditionalServiceRequest> createOrderedAdditionalServiceRequest;
}
