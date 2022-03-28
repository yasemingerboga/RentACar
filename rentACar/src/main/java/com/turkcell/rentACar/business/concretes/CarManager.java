package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.Car.CarListDto;
import com.turkcell.rentACar.business.dtos.Car.GetCarDto;
import com.turkcell.rentACar.business.requests.Car.CreateCarRequest;
import com.turkcell.rentACar.business.requests.Car.UpdateCarRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CarDao;
import com.turkcell.rentACar.entities.concretes.Car;

@Service
public class CarManager implements CarService {

	private CarDao carDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CarManager(CarDao carDao, ModelMapperService modelMapperService) {
		super();
		this.carDao = carDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<CarListDto>> getAll() {

		List<Car> result = this.carDao.findAll();
		List<CarListDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, CarListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<CarListDto>>(response, BusinessMessages.CAR_LIST_SUCCESSFULLY);
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) {

		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);

		carDao.save(car);

		return new SuccessResult(BusinessMessages.CAR_SAVE_SUCCESSFULLY);
	}

	@Override
	public DataResult<GetCarDto> getById(int id) {

		checkIfCarExists(id);

		Car car = this.carDao.findById(id);

		GetCarDto response = this.modelMapperService.forDto().map(car, GetCarDto.class);

		return new SuccessDataResult<GetCarDto>(response, BusinessMessages.CAR_GET_SUCCESSFULLY);
	}

	@Override
	public Result delete(int id) {

		checkIfCarExists(id);

		this.carDao.deleteById(id);

		return new SuccessResult(BusinessMessages.CAR_DELETE_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) {

		checkIfCarExists(updateCarRequest.getCarId());

		Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);

		car.setId(updateCarRequest.getCarId());

		carDao.save(car);

		return new SuccessResult(BusinessMessages.CAR_UPDATE_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<CarListDto>> getByDailyPrice(double dailyPrice) {

		List<Car> result = this.carDao.findByDailyPriceLessThanEqual(dailyPrice);
		List<CarListDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, CarListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<CarListDto>>(response, BusinessMessages.CAR_LIST_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize) {

		Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

		List<Car> result = this.carDao.findAll(pageable).getContent();
		List<CarListDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, CarListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<CarListDto>>(response);
	}

	@Override
	public DataResult<List<CarListDto>> getAllSorted(String direction) {

		Sort sort = Sort.by(Sort.Direction.fromString(direction), "dailyPrice");

		List<Car> result = carDao.findAll(sort);
		List<CarListDto> response = result.stream()
				.map(car -> this.modelMapperService.forDto().map(car, CarListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<CarListDto>>(response);
	}

	@Override
	public void checkIfCarExists(int id) {

		if (!carDao.existsById(id)) {
			throw new BusinessException(BusinessMessages.CAR_NOT_FOUND);
		}
	}

	@Override
	public Result updateKilometer(int id, Double kilometer) {

		Car car = carDao.getById(id);

		car.setKilometer(kilometer);

		carDao.save(car);

		return new SuccessResult(BusinessMessages.CAR_UPDATE_KILOMETER);

	}

}
