package com.turkcell.rentACar.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.dtos.CarListDto;
import com.turkcell.rentACar.business.dtos.GetCarDto;
import com.turkcell.rentACar.business.requests.CreateCarRequest;
import com.turkcell.rentACar.business.requests.UpdateCarRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/cars")
public class CarsController {

	private CarService carService;

	public CarsController(CarService carService) {
		super();
		this.carService = carService;
	}

	@GetMapping("/getAll")
	public DataResult<List<CarListDto>> getAll() {
		return carService.getAll();
	}

	@PostMapping("/add")
	public Result add(@RequestBody CreateCarRequest createCarRequest) {
		return carService.add(createCarRequest);
	}

	@GetMapping("/get/{id}")
	public DataResult<GetCarDto> getById(@RequestParam int id) {
		return carService.getById(id);
	}

	@DeleteMapping("/delete/{id}")
	public Result delete(@RequestParam int id) {
		return carService.delete(id);
	}

	@PutMapping("/update")
	public Result update(@RequestBody UpdateCarRequest updateCarRequest) {
		return this.carService.update(updateCarRequest);
	}

	@GetMapping("/get/{dailyPrice}")
	public DataResult<List<CarListDto>> getByDailyPrice(double dailyPrice) {
		return this.carService.getByDailyPrice(dailyPrice);
	}
	
	@GetMapping("/getallpages")
	public DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize) {
		return carService.getAllPaged(pageNo, pageSize);
	}

	@GetMapping("/getallsorted")
	public DataResult<List<CarListDto>> getAllSorted(String direction) {
		return carService.getAllSorted(direction);
	}
}
