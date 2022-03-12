package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.api.controllers.models.CreateRentalModel;
import com.turkcell.rentACar.api.controllers.models.UpdateRentalModel;
import com.turkcell.rentACar.business.dtos.RentalCar.GetRentalCarDto;
import com.turkcell.rentACar.business.dtos.RentalCar.RentalCarListDto;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface RentalCarService {

	Result add(CreateRentalModel rentalModel);

	DataResult<GetRentalCarDto> getById(int id);

	DataResult<List<RentalCarListDto>> getAll();

	DataResult<List<RentalCarListDto>> getByCarId(int carId);

	Result update(UpdateRentalModel updateRentalModel);

	Result delete(int id);

}
