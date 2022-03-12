package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.CarMaintenance.CarMaintenanceListDto;
import com.turkcell.rentACar.business.dtos.CarMaintenance.GetCarMaintenanceDto;
import com.turkcell.rentACar.business.requests.CarMaintenance.CreateCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.CarMaintenance.UpdateCarMaintenanceRequest;
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
