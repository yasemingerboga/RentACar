package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.RentalCar.GetRentalCarDto;
import com.turkcell.rentACar.business.dtos.RentalCar.RentalCarListDto;
import com.turkcell.rentACar.business.requests.RentalCar.CreateRentalCarRequest;
import com.turkcell.rentACar.business.requests.RentalCar.UpdateRentalCarRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface RentalCarService {

	Result add(CreateRentalCarRequest createRentalCarRequest);

	DataResult<GetRentalCarDto> getById(int id);

	DataResult<List<RentalCarListDto>> getAll();

	DataResult<List<RentalCarListDto>> getByCarId(int carId);

	Result update(UpdateRentalCarRequest updateRentalCarRequest);

	Result delete(int id);

}
