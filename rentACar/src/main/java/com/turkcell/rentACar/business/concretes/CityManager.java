package com.turkcell.rentACar.business.concretes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.CityService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.City.CityListDto;
import com.turkcell.rentACar.business.dtos.City.GetCityDto;
import com.turkcell.rentACar.business.requests.City.CreateCityRequest;
import com.turkcell.rentACar.business.requests.City.UpdateCityRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
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

		checkIfCityExists(id);

		City city = cityDao.getById(id);

		GetCityDto response = this.modelMapperService.forDto().map(city, GetCityDto.class);

		return new SuccessDataResult<GetCityDto>(response, BusinessMessages.CITY_GET_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<CityListDto>> getByAllId(List<Integer> idList) {

		List<City> cities = cityDao.findAllById(idList);
		List<CityListDto> response = new ArrayList<>();

		cities.forEach(city -> {
			response.add(this.modelMapperService.forDto().map(cities, CityListDto.class));
		});

		return new SuccessDataResult<List<CityListDto>>(response, BusinessMessages.CITY_LIST_SUCCESSFULLY);
	}

	@Override
	public Result add(CreateCityRequest createCityRequest) {
		City city = this.modelMapperService.forRequest().map(createCityRequest, City.class);

		cityDao.save(city);

		return new SuccessResult(BusinessMessages.CITY_SAVE_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateCityRequest updateCityRequest) {

		checkIfCityExists(updateCityRequest.getId());

		City city = this.modelMapperService.forRequest().map(updateCityRequest, City.class);

		cityDao.save(city);

		return new SuccessResult(BusinessMessages.CITY_UPDATE_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<CityListDto>> getAll() {

		List<City> result = this.cityDao.findAll();
		List<CityListDto> response = result.stream()
				.map(city -> this.modelMapperService.forDto().map(city, CityListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<CityListDto>>(response, BusinessMessages.CITY_LIST_SUCCESSFULLY);
	}

	@Override
	public Result delete(int id) {
		checkIfCityExists(id);

		this.cityDao.deleteById(id);

		return new SuccessResult(BusinessMessages.CITY_DELETE_SUCCESSFULLY);
	}

	private void checkIfCityExists(int id) {

		if (!cityDao.existsById(id)) {
			throw new BusinessException(BusinessMessages.CITY_NOT_FOUND);
		}
	}

}
