package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.CorporateCustomer.CorporateCustomerListDto;
import com.turkcell.rentACar.business.dtos.CorporateCustomer.GetCorporateCustomerDto;
import com.turkcell.rentACar.business.requests.CorporateCustomer.CreateCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.CorporateCustomer.UpdateCorporateCustomerRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CorporateCustomerDao;
import com.turkcell.rentACar.entities.concretes.CorporateCustomer;

@Service
public class CorporateCustomerManager implements CorporateCustomerService {

	private CorporateCustomerDao corporateCustomerDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao, ModelMapperService modelMapperService) {
		super();
		this.corporateCustomerDao = corporateCustomerDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) {

		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(createCorporateCustomerRequest,
				CorporateCustomer.class);

		corporateCustomerDao.save(corporateCustomer);

		return new SuccessResult(BusinessMessages.CORPORATE_SAVE_SUCCESSFULLY);

	}

	@Override
	public Result delete(int id) {

		checkIfCorporateCustomerExists(id);

		this.corporateCustomerDao.deleteById(id);

		return new SuccessResult(BusinessMessages.CORPORATE_DELETE_SUCCESSFULLY);
	}

	@Override
	public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) {

		checkIfCorporateCustomerExists(updateCorporateCustomerRequest.getId());

		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest,
				CorporateCustomer.class);

		corporateCustomerDao.save(corporateCustomer);

		return new SuccessResult(BusinessMessages.CORPORATE_UPDATE_SUCCESSFULLY);
	}

	@Override
	public DataResult<GetCorporateCustomerDto> getById(int id) {

		checkIfCorporateCustomerExists(id);

		CorporateCustomer corporateCustomer = this.corporateCustomerDao.getById(id);
		GetCorporateCustomerDto response = this.modelMapperService.forDto().map(corporateCustomer,
				GetCorporateCustomerDto.class);

		return new SuccessDataResult<GetCorporateCustomerDto>(response, BusinessMessages.CORPORATE_GET_SUCCESSFULLY);
	}

	@Override
	public DataResult<List<CorporateCustomerListDto>> getAll() {

		List<CorporateCustomer> result = this.corporateCustomerDao.findAll();
		List<CorporateCustomerListDto> response = result.stream().map(corporateCustomer -> this.modelMapperService
				.forDto().map(corporateCustomer, CorporateCustomerListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<CorporateCustomerListDto>>(response,
				BusinessMessages.CORPORATE_LIST_SUCCESSFULLY);
	}

	@Override
	public void checkIfCorporateCustomerExists(int id) {

		if (!corporateCustomerDao.existsById(id)) {
			throw new BusinessException(BusinessMessages.CORPORATE_NOT_FOUND);
		}
	}
}
