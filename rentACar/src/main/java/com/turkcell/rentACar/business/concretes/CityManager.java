package com.turkcell.rentACar.business.concretes;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.CityService;
import com.turkcell.rentACar.business.dtos.City.CityListDto;
import com.turkcell.rentACar.business.dtos.City.GetCityDto;
import com.turkcell.rentACar.business.requests.City.CreateCityRequest;
import com.turkcell.rentACar.business.requests.City.UpdateCityRequest;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.dataAccess.abstracts.CityDao;
import com.turkcell.rentACar.entities.concretes.City;

@Service
public class CityManager implements CityService {
	private CityDao cityDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CityManager(CityDao cityDao, ModelMapperService modelMapperService) {
		super();
		this.cityDao = cityDao;
		this.modelMapperService = modelMapperService;
	}

	public DataResult<GetCityDto> getById(int id) {
		City city = cityDao.getById(id);
		GetCityDto response = this.modelMapperService.forDto().map(city, GetCityDto.class);
		return new SuccessDataResult<GetCityDto>(response, "Getting color by id");
	}

	@Override
	public DataResult<List<CityListDto>> getByAllId(List<Integer> idList) {
		List<City> cities = cityDao.findAllById(idList);
		List<CityListDto> response = new ArrayList<>();
		cities.forEach(city -> {
			response.add(this.modelMapperService.forDto().map(cities, CityListDto.class));
		});
		return new SuccessDataResult<List<CityListDto>>(response, "Getting cities by id");
	}

	@Override
	public Result add(CreateCityRequest createCityRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result update(UpdateCityRequest updateCityRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataResult<List<CityListDto>> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result delete(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
