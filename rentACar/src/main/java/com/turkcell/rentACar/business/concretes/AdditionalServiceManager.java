package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.AdditionalService.AdditionalServiceListDto;
import com.turkcell.rentACar.business.dtos.AdditionalService.GetAdditionalServiceDto;
import com.turkcell.rentACar.business.requests.AdditionalService.CreateAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.AdditionalService.UpdateAdditionalServiceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.AdditionalServiceDao;
import com.turkcell.rentACar.entities.concretes.AdditionalService;

@Service
public class AdditionalServiceManager implements AdditionalServiceService {

	private AdditionalServiceDao additionalServiceDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public AdditionalServiceManager(AdditionalServiceDao additionalProductDao, ModelMapperService modelMapperService) {
		super();
		this.additionalServiceDao = additionalProductDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<AdditionalServiceListDto>> getAll() {

		List<AdditionalService> result = this.additionalServiceDao.findAll();
		List<AdditionalServiceListDto> response = result.stream().map(additionalProduct -> this.modelMapperService
				.forDto().map(additionalProduct, AdditionalServiceListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<AdditionalServiceListDto>>(response,
				BusinessMessages.ADDITIONAL_SERVICE_LIST_SUCCESSFULLY);
	}

	@Override
	public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) {

		AdditionalService additionalProduct = this.modelMapperService.forRequest().map(createAdditionalServiceRequest,
				AdditionalService.class);

		this.additionalServiceDao.save(additionalProduct);

		return new SuccessResult(BusinessMessages.ADDITIONAL_SERVICE_SAVE_SUCCESSFULLY);
	}

	@Override
	public DataResult<GetAdditionalServiceDto> getById(int id) {

		checkIfAdditionalServiceExists(id);

		AdditionalService additionalProduct = additionalServiceDao.getById(id);
		GetAdditionalServiceDto response = this.modelMapperService.forDto().map(additionalProduct,
				GetAdditionalServiceDto.class);

		return new SuccessDataResult<GetAdditionalServiceDto>(response,
				BusinessMessages.ADDITIONAL_SERVICE_GET_SUCCESSFULLY);
	}

	@Override
	public Result delete(int id) {

		checkIfAdditionalServiceExists(id);

		this.additionalServiceDao.deleteById(id);

		return new SuccessResult(BusinessMessages.ADDITIONAL_SERVICE_DELETE_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) {

		checkIfAdditionalServiceExists(updateAdditionalServiceRequest.getId());

		AdditionalService additionalProduct = this.modelMapperService.forRequest().map(updateAdditionalServiceRequest,
				AdditionalService.class);

		this.additionalServiceDao.save(additionalProduct);

		return new SuccessResult(BusinessMessages.ADDITIONAL_SERVICE_UPDATE_SUCCESSFULLY);
	}

	@Override
	public void checkIfAdditionalServiceExists(int id) {

		if (!additionalServiceDao.existsById(id)) {
			throw new BusinessException(BusinessMessages.ADDITIONAL_SERVICE_NOT_FOUND);
		}
	}

}
