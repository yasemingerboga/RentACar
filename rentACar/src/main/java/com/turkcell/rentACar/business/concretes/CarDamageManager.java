package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.CarDamageService;
import com.turkcell.rentACar.business.dtos.CarDamage.CarListDamageDto;
import com.turkcell.rentACar.business.dtos.CarDamage.GetCarDamageDto;
import com.turkcell.rentACar.business.requests.CarDamage.CreateCarDamageRequest;
import com.turkcell.rentACar.business.requests.CarDamage.UpdateCarDamageRequest;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CarDamageDao;
import com.turkcell.rentACar.entities.concretes.CarDamage;

@Service
public class CarDamageManager implements CarDamageService {

	private CarDamageDao carDamageDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CarDamageManager(CarDamageDao carDamageDao, ModelMapperService modelMapperService) {
		super();
		this.carDamageDao = carDamageDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<CarListDamageDto>> getAll() {
		List<CarDamage> result = this.carDamageDao.findAll();
		List<CarListDamageDto> response = result.stream()
				.map(carDamage -> this.modelMapperService.forDto().map(carDamage, CarListDamageDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<CarListDamageDto>>(response, "Car damages listed successfully.");
	}

	@Override
	public Result add(CreateCarDamageRequest createCarDamage) {
		CarDamage carDamage = this.modelMapperService.forRequest().map(createCarDamage, CarDamage.class);
		carDamageDao.save(carDamage);
		return new SuccessResult("Car damage added successfully.");
	}

	@Override
	public DataResult<GetCarDamageDto> getById(int id) {
		CarDamage carDamage = this.carDamageDao.getById(id);
		GetCarDamageDto response = this.modelMapperService.forDto().map(carDamage, GetCarDamageDto.class);
		return new SuccessDataResult<GetCarDamageDto>(response, "Getting car damage by id.");
	}

	@Override
	public Result delete(int id) {
		this.carDamageDao.deleteById(id);
		return new SuccessResult("Car damage deleted successfully.");
	}

	@Override
	public Result update(UpdateCarDamageRequest updateCarDamageRequest) {
		CarDamage carDamage = this.modelMapperService.forRequest().map(updateCarDamageRequest, CarDamage.class);
		carDamageDao.save(carDamage);
		return new SuccessResult("Car damage updated successfully.");
	}

}
