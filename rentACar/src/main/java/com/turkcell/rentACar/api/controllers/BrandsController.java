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

import com.turkcell.rentACar.business.abstracts.BrandService;
import com.turkcell.rentACar.business.dtos.BrandListDto;
import com.turkcell.rentACar.business.dtos.GetBrandDto;
import com.turkcell.rentACar.business.requests.CreateBrandRequest;
import com.turkcell.rentACar.business.requests.UpdateBrandRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/brands")
public class BrandsController {

	private BrandService brandService;

	public BrandsController(BrandService brandService) {
		super();
		this.brandService = brandService;
	}

	@GetMapping("/getall")
	public DataResult<List<BrandListDto>> getAll() {
		return this.brandService.getAll();
	}

	@PostMapping("/add")
	public Result add(@RequestBody CreateBrandRequest createBrandRequest) throws Exception {
		return this.brandService.add(createBrandRequest);
	}

	@GetMapping("/getbyid/{id}")
	public DataResult<GetBrandDto> getById(@RequestParam int id) {
		return this.brandService.getById(id);
	}

	@DeleteMapping("/delete/{id}")
	public Result delete(@RequestParam int id) {
		return this.brandService.delete(id);
	}

	@PutMapping("/update")
	public Result update(@RequestBody UpdateBrandRequest updateBrandRequest) throws Exception {
		return this.brandService.update(updateBrandRequest);
	}

}
