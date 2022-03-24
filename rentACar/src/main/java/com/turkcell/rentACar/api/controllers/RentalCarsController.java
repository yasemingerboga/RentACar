package com.turkcell.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.api.controllers.models.RentalCar.UpdateRentalModel;
import com.turkcell.rentACar.business.abstracts.RentalCarService;
import com.turkcell.rentACar.business.dtos.RentalCar.GetRentalCarDto;
import com.turkcell.rentACar.business.dtos.RentalCar.RentalCarListDto;
import com.turkcell.rentACar.business.requests.RentalCar.EndOfRent;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/rentalCars")
public class RentalCarsController {

	private RentalCarService rentalCarService;

	@Autowired
	public RentalCarsController(RentalCarService rentalCarService) {
		super();
		this.rentalCarService = rentalCarService;
	}

	@GetMapping("/getByRentalId/{rentalId}")
	DataResult<GetRentalCarDto> getByRentalId(@RequestParam("rentalId") int rentalId) {
		return this.rentalCarService.getById(rentalId);
	}

	@GetMapping("/getAll")
	DataResult<List<RentalCarListDto>> getAll() {
		return this.rentalCarService.getAll();
	}

	@PutMapping("/update")
	Result update(@RequestBody @Valid UpdateRentalModel updateRentalModel) {
		return this.rentalCarService.update(updateRentalModel);
	}

	@DeleteMapping("/delete")
	Result delete(int id) {
		return this.rentalCarService.delete(id);
	}

	@PutMapping("/endOfRent")
	public Result endOfRent(@RequestBody @Valid EndOfRent endOfRent) {
		return this.rentalCarService.endOfRent(endOfRent);
	}

}
