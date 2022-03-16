package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.Car.CarListDto;
import com.turkcell.rentACar.business.dtos.Car.GetCarDto;
import com.turkcell.rentACar.business.requests.Car.CreateCarRequest;
import com.turkcell.rentACar.business.requests.Car.UpdateCarRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface CarService {

	DataResult<List<CarListDto>> getAll();

	Result add(CreateCarRequest createCarRequest);

	DataResult<GetCarDto> getById(int id);

	Result delete(int id);

	Result update(UpdateCarRequest updateCarRequest);

	DataResult<List<CarListDto>> getByDailyPrice(double dailyPrice);

	DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize);

	DataResult<List<CarListDto>> getAllSorted(String direction);

	Result existsById(int id);

	Result updateKilometer(int id, Double kilometer);
}
