package com.turkcell.rentACar.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.business.abstracts.AdditionalProductService;
import com.turkcell.rentACar.business.dtos.AdditionalService.AdditionalServiceListDto;
import com.turkcell.rentACar.business.dtos.AdditionalService.GetAdditionalServiceDto;
import com.turkcell.rentACar.business.requests.AdditionalService.CreateAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.AdditionalService.UpdateAdditionalServiceRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/additionalProducts")
public class AdditionalProductsController {
	private AdditionalProductService additionalProductService;

	@Autowired
	public AdditionalProductsController(AdditionalProductService additionalProductService) {
		super();
		this.additionalProductService = additionalProductService;
	}

	@GetMapping("/getall")
	DataResult<List<AdditionalServiceListDto>> getAll() {
		return additionalProductService.getAll();
	}

	@PostMapping("/add")
	Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) {
		return additionalProductService.add(createAdditionalServiceRequest);
	}

	@GetMapping("/getById")
	DataResult<GetAdditionalServiceDto> getById(int id) {
		return additionalProductService.getById(id);
	}

	@PostMapping("/delete")
	Result delete(int id) {
		return additionalProductService.delete(id);
	}

	@PutMapping("/update")
	Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) {
		return additionalProductService.update(updateAdditionalServiceRequest);
	}
}
