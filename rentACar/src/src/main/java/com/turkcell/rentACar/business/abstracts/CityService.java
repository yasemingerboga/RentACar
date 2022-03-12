package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.City.CityListDto;
import com.turkcell.rentACar.business.dtos.City.GetCityDto;
import com.turkcell.rentACar.business.requests.City.CreateCityRequest;
import com.turkcell.rentACar.business.requests.City.UpdateCityRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface CityService {
	DataResult<List<CityListDto>> getAll();
	Result add(CreateCityRequest createCityRequest);
	DataResult<GetCityDto> getById(int id);
	Result delete(int id);
	Result update(UpdateCityRequest updateCityRequest);
	DataResult<List<CityListDto>> getByAllId(List<Integer> idList);
}
