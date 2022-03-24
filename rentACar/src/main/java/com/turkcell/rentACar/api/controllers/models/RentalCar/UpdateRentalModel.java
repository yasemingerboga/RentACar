package com.turkcell.rentACar.api.controllers.models.RentalCar;

import java.util.List;

import com.turkcell.rentACar.business.requests.OrderedAdditionalService.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.RentalCar.UpdateRentalCarRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRentalModel {
	private UpdateRentalCarRequest updateRentalCarRequest;
	private List<UpdateOrderedAdditionalServiceRequest> orderedAdditionalServiceRequests;
}
