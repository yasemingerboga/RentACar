package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentACar.business.dtos.AdditionalService.AdditionalServiceListDto;
import com.turkcell.rentACar.business.dtos.AdditionalService.GetAdditionalServiceDto;
import com.turkcell.rentACar.business.requests.AdditionalService.CreateAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.AdditionalService.UpdateAdditionalServiceRequest;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.AdditionalServiceDao;
import com.turkcell.rentACar.entities.concretes.AdditionalService;

@Service
public class AdditionalServiceManager implements AdditionalServiceService {

	private AdditionalServiceDao additionalProductDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public AdditionalServiceManager(AdditionalServiceDao additionalProductDao, ModelMapperService modelMapperService) {
		super();
		this.additionalProductDao = additionalProductDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<AdditionalServiceListDto>> getAll() {
		List<AdditionalService> result = this.additionalProductDao.findAll();
		List<AdditionalServiceListDto> response = result.stream().map(additionalProduct -> this.modelMapperService
				.forDto().map(additionalProduct, AdditionalServiceListDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<AdditionalServiceListDto>>(response,
				"All additional products/services are listed.");
	}

	@Override
	public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) {
		AdditionalService additionalProduct = this.modelMapperService.forRequest().map(createAdditionalServiceRequest,
				AdditionalService.class);
		this.additionalProductDao.save(additionalProduct);
		return new SuccessResult("Additional product added successfully.");
	}

	@Override
	public DataResult<GetAdditionalServiceDto> getById(int id) {
		AdditionalService additionalProduct = additionalProductDao.getById(id);
		GetAdditionalServiceDto response = this.modelMapperService.forDto().map(additionalProduct,
				GetAdditionalServiceDto.class);
		return new SuccessDataResult<GetAdditionalServiceDto>(response, "Getting additional by id");
	}

	@Override
	public Result delete(int id) {
		this.additionalProductDao.deleteById(id);
		return new SuccessResult("Additional product deleted successfully.");
	}

	@Override
	public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) {
		AdditionalService additionalProduct = this.modelMapperService.forRequest().map(updateAdditionalServiceRequest,
				AdditionalService.class);
		this.additionalProductDao.save(additionalProduct);
		return new SuccessResult("Additional product updated successfully.");
	}

}
