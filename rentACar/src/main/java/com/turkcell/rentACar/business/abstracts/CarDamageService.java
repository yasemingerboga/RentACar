package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.CarDamage.CarListDamageDto;
import com.turkcell.rentACar.business.dtos.CarDamage.GetCarDamageDto;
import com.turkcell.rentACar.business.requests.CarDamage.CreateCarDamageRequest;
import com.turkcell.rentACar.business.requests.CarDamage.UpdateCarDamageRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface CarDamageService {

	DataResult<List<CarListDamageDto>> getAll();

	Result add(CreateCarDamageRequest createCarDamage);

	DataResult<GetCarDamageDto> getById(int id);

	Result delete(int id);

	Result update(UpdateCarDamageRequest updateCarDamageRequest);

}
