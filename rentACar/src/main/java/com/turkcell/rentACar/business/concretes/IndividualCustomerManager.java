package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACar.business.dtos.IndividualCustomer.GetIndividualCustomerDto;
import com.turkcell.rentACar.business.dtos.IndividualCustomer.IndividualCustomerListDto;
import com.turkcell.rentACar.business.requests.IndividualCustomer.CreateIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.IndividualCustomer.UpdateIndividualCustomerRequest;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.ErrorResult;
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
		return new SuccessResult("Individual customer added successfully.");

	}

	@Override
	public Result delete(int id) {
		this.individualCustomerDao.deleteById(id);
		return new SuccessResult("Individual customer deleted successfully.");
	}

	@Override
	public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) {
		IndividualCustomer individualCustomer = this.modelMapperService.forRequest()
				.map(updateIndividualCustomerRequest, IndividualCustomer.class);
		individualCustomerDao.save(individualCustomer);
		return new SuccessResult("Individual customer updated successfully.");
	}

	@Override
	public DataResult<GetIndividualCustomerDto> getById(int id) {
		IndividualCustomer individualCustomer = this.individualCustomerDao.getById(id);
		GetIndividualCustomerDto response = this.modelMapperService.forDto().map(individualCustomer,
				GetIndividualCustomerDto.class);
		return new SuccessDataResult<GetIndividualCustomerDto>(response, "Getting individual customer by id");
	}

	@Override
	public DataResult<List<IndividualCustomerListDto>> getAll() {
		List<IndividualCustomer> result = this.individualCustomerDao.findAll();
		List<IndividualCustomerListDto> response = result.stream().map(individualCustomer -> this.modelMapperService
				.forDto().map(individualCustomer, IndividualCustomerListDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<IndividualCustomerListDto>>(response,
				"Individual customers listed successfully.");
	}

	@Override
	public Result existById(int id) {

		if (!individualCustomerDao.existsById(id)) {

			return new ErrorResult("There is no individual customer found with specified id.");
		}

		return new SuccessResult("Getting individual user successfully.");
	}
}
