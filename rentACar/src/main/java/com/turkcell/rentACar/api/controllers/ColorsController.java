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

import com.turkcell.rentACar.business.abstracts.ColorService;
import com.turkcell.rentACar.business.dtos.ColorListDto;
import com.turkcell.rentACar.business.dtos.GetColorDto;
import com.turkcell.rentACar.business.requests.CreateColorRequest;
import com.turkcell.rentACar.business.requests.UpdateColorRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/colors")
public class ColorsController {

	private final ColorService colorService;

	public ColorsController(ColorService colorService) {
		super();
		this.colorService = colorService;
	}

	@GetMapping("/getall")
	public DataResult<List<ColorListDto>> getAll() {
		return colorService.getAll();
	}

	@PostMapping("/add")
	public Result add(@RequestBody CreateColorRequest createColorRequest) throws Exception {
		return this.colorService.add(createColorRequest);
	}

	@GetMapping("/getbyid/{id}")
	public DataResult<GetColorDto> getById(@RequestParam int id) {
		return this.colorService.getById(id);
	}

	@DeleteMapping("/delete/{id}")
	public Result delete(@RequestParam int id) {
		return this.colorService.delete(id);
	}

	@PutMapping("/update")
	public Result update(@RequestBody UpdateColorRequest updateColorRequest) throws Exception {
		return this.colorService.update(updateColorRequest);
	}

}
