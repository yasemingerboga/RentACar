package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.IndividualCustomer.GetIndividualCustomerDto;
import com.turkcell.rentACar.business.dtos.IndividualCustomer.IndividualCustomerListDto;
import com.turkcell.rentACar.business.requests.IndividualCustomer.CreateIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.IndividualCustomer.UpdateIndividualCustomerRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.IndividualCustomerDao;
import com.turkcell.rentACar.entities.concretes.IndividualCustomer;

@Service
public class IndividualCustomerManager implements IndividualCustomerService {
	private IndividualCustomerDao individualCustomerDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao,
			ModelMapperService modelMapperService) {
		super();
		this.individualCustomerDao = individualCustomerDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) {

		IndividualCustomer individualCustomer = this.modelMapperService.forRequest()
				.map(createIndividualCustomerRequest, IndividualCustomer.class);

		individualCustomerDao.save(individualCustomer);

		return new SuccessResult(BusinessMessages.INDIVIDUAL_SAVE_SUCCESSFULLY);

	}

	@Override
	public Result delete(int id) {

		checkIfIndividualCustomerExists(id);

		this.individualCustomerDao.deleteById(id);

		return new SuccessResult(BusinessMessages.INDIVIDUAL_DELETE_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {

		checkIfIndividualCustomerExists(updateIndividualCustomerRequest.getId());

		IndividualCustomer individualCustomer = this.modelMapperService.forRequest()
				.map(updateIndividualCustomerRequest, IndividualCustomer.class);

		individualCustomerDao.save(individualCustomer);

		return new SuccessResult(BusinessMessages.INDIVIDUAL_UPDATE_SUCCESSFULLY);
	}

	@Override
	public DataResult<GetIndividualCustomerDto> getById(int id) {

		checkIfIndividualCustomerExists(id);

		IndividualCustomer individualCustomer = this.individualCustomerDao.getById(id);
		GetIndividualCustomerDto response = this.modelMapperService.forDto().map(individualCustomer,
				GetIndividualCustomerDto.class);

		return new SuccessDataResult<GetIndividualCustomerDto>(response, BusinessMessages.INDIVIDUAL_GET_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<IndividualCustomerListDto>> getAll() {

		List<IndividualCustomer> result = this.individualCustomerDao.findAll();
		List<IndividualCustomerListDto> response = result.stream().map(individualCustomer -> this.modelMapperService
				.forDto().map(individualCustomer, IndividualCustomerListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<IndividualCustomerListDto>>(response,
				BusinessMessages.INDIVIDUAL_LIST_SUCCESSFULLY);
	}

	@Override
	public void checkIfIndividualCustomerExists(int id) {

		if (!individualCustomerDao.existsById(id)) {

			throw new BusinessException(BusinessMessages.INDIVIDUAL_NOT_FOUND);
		}
	}
}
