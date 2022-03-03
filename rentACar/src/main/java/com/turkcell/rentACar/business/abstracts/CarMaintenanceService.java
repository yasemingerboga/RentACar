package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentACar.business.dtos.GetCarMaintenanceDto;
import com.turkcell.rentACar.business.requests.CreateCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.UpdateCarMaintenanceRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;


public interface CarMaintenanceService {

	DataResult<List<CarMaintenanceListDto>> getAll();
	Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest);
	DataResult<GetCarMaintenanceDto> getById(int id);
	Result delete(int id);
	Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest);
	DataResult<List<CarMaintenanceListDto>> getByCarId(int carId);
}
