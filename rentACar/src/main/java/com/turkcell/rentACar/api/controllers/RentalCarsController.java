package com.turkcell.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.business.abstracts.RentalCarService;
import com.turkcell.rentACar.business.dtos.GetRentalCarDto;
import com.turkcell.rentACar.business.dtos.RentalCarListDto;
import com.turkcell.rentACar.business.requests.CreateRentalCarRequest;
import com.turkcell.rentACar.business.requests.UpdateRentalCarRequest;
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

	@PostMapping("/add")
	Result add(@RequestBody @Valid CreateRentalCarRequest createRentalCarRequest) {
		return this.rentalCarService.add(createRentalCarRequest);
	}

	@GetMapping("/getByRentalId/{rentalId}")
	DataResult<GetRentalCarDto> getByRentalId(@RequestParam("rentalId") int rentalId) {
		return this.rentalCarService.getByRentalId(rentalId);
	}

	@GetMapping("/getAll")
	DataResult<List<RentalCarListDto>> getAll() {
		return this.rentalCarService.getAll();
	}

	@GetMapping("/getAllPaged")
	DataResult<List<RentalCarListDto>> getAllPaged(@RequestParam("pageNo") int pageNo,
			@RequestParam("pageSize") int pageSize) {
		return this.rentalCarService.getAllPaged(pageNo, pageSize);
	}

	@GetMapping("/getAllSorted")
	DataResult<List<RentalCarListDto>> getAllSorted(@RequestParam("direction") Sort.Direction direction) {
		return this.rentalCarService.getAllSorted(direction);
	}

	@PutMapping("/update")
	Result update(@RequestParam("rentalId") int rentalId,
			@RequestBody @Valid UpdateRentalCarRequest updateRentalCarRequest) {
		return this.rentalCarService.update(rentalId, updateRentalCarRequest);
	}

	@DeleteMapping("/delete")
	Result delete(int id) {
		return this.rentalCarService.delete(id);
	}

}
