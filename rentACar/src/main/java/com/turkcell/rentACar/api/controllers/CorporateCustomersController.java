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

import com.turkcell.rentACar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACar.business.dtos.CorporateCustomer.CorporateCustomerListDto;
import com.turkcell.rentACar.business.dtos.CorporateCustomer.GetCorporateCustomerDto;
import com.turkcell.rentACar.business.requests.CorporateCustomer.CreateCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.CorporateCustomer.UpdateCorporateCustomerRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/corporateCustomers")
public class CorporateCustomersController {
	private CorporateCustomerService corporateCustomerService;

	@Autowired
	public CorporateCustomersController(CorporateCustomerService corporateCustomerService) {
		this.corporateCustomerService = corporateCustomerService;
	}

	@GetMapping("/getAll")
	public DataResult<List<CorporateCustomerListDto>> getAll() {
		return corporateCustomerService.getAll();
	}

	@PostMapping("/add")
	public Result add(@RequestBody @Valid CreateCorporateCustomerRequest createCorporateCustomerRequest) {
		return corporateCustomerService.add(createCorporateCustomerRequest);
	}

	@GetMapping("/getById/{id}")
	public DataResult<GetCorporateCustomerDto> getById(@RequestParam int id) {
		return corporateCustomerService.getById(id);
	}

	@DeleteMapping("/delete/{id}")
	public Result delete(@RequestParam int id) {
		return corporateCustomerService.delete(id);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateCorporateCustomerRequest updateCorporateCustomerRequest) {
		return this.corporateCustomerService.update(updateCorporateCustomerRequest);
	}
}
