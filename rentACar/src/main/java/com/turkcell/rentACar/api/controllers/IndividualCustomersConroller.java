package com.turkcell.rentACar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACar.business.dtos.IndividualCustomer.GetIndividualCustomerDto;
import com.turkcell.rentACar.business.dtos.IndividualCustomer.IndividualCustomerListDto;
import com.turkcell.rentACar.business.requests.IndividualCustomer.CreateIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.IndividualCustomer.UpdateIndividualCustomerRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/individualCustomers")
public class IndividualCustomersConroller {
	private IndividualCustomerService individualCustomerService;

	@Autowired
	public IndividualCustomersConroller(IndividualCustomerService individualCustomerService) {
		this.individualCustomerService = individualCustomerService;
	}

	@GetMapping("/getAll")
	public DataResult<List<IndividualCustomerListDto>> getAll() {
		return individualCustomerService.getAll();
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateIndividualCustomerRequest createIndividualCustomerRequest) {
		return individualCustomerService.add(createIndividualCustomerRequest);
	}

	@GetMapping("/getById/{id}")
	public DataResult<GetIndividualCustomerDto> getById(@RequestParam int id) {
		return individualCustomerService.getById(id);
	}

	@DeleteMapping("/delete/{id}")
	public Result delete(@RequestParam int id) {
		return individualCustomerService.delete(id);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {
		return this.individualCustomerService.update(updateIndividualCustomerRequest);
	}
}
