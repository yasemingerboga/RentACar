package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.OrderedAdditionalService.GetOrderedAdditionalServiceDto;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.OrderedAdditionalServiceDao;
import com.turkcell.rentACar.entities.concretes.OrderedAdditionalService;

@Service
public class OrderedAdditionalServiceManager implements OrderedAdditionalServiceService {

	private OrderedAdditionalServiceDao orderedAdditionalServiceDao;

	private ModelMapperService modelMapperService;

	public OrderedAdditionalServiceManager(OrderedAdditionalServiceDao orderedAdditionalServiceDao,
			ModelMapperService modelMapperService) {
		super();
		this.orderedAdditionalServiceDao = orderedAdditionalServiceDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<GetOrderedAdditionalServiceDto>> getAllByRentalCarId(int id) {

		List<OrderedAdditionalService> result = orderedAdditionalServiceDao.getAllByRentalCarId(id);

		List<GetOrderedAdditionalServiceDto> response = result.stream()
				.map(orderedAdditionalService -> this.modelMapperService.forDto().map(orderedAdditionalService,
						GetOrderedAdditionalServiceDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<GetOrderedAdditionalServiceDto>>(response,
				BusinessMessages.ORDERED_ADDITIONAL_SERVICE_LIST_SUCCESSFULLY);

	}

	@Override
	public Result delete(int id) {

		checkIfOrderedAdditionalServiceExists(id);

		this.orderedAdditionalServiceDao.deleteById(id);

		return new SuccessResult(BusinessMessages.ORDERED_ADDITIONAL_SERVICE_DELETE_SUCCESSFULLY);
	}

	@Override
	public Result deleteByRentalCarId(int rentalCarId) {

		orderedAdditionalServiceDao.deleteAll(orderedAdditionalServiceDao.findByRentalCar_Id(rentalCarId));

		return new SuccessResult(BusinessMessages.ORDERED_ADDITIONAL_SERVICE_DELETE_SUCCESSFULLY);
	}

	@Override
	public DataResult<GetOrderedAdditionalServiceDto> getById(int id) {

		checkIfOrderedAdditionalServiceExists(id);

		OrderedAdditionalService result = orderedAdditionalServiceDao.getById(id);
		GetOrderedAdditionalServiceDto response = this.modelMapperService.forDto().map(result,
				GetOrderedAdditionalServiceDto.class);

		return new SuccessDataResult<GetOrderedAdditionalServiceDto>(response,
				BusinessMessages.ORDERED_ADDITIONAL_SERVICE_GET_SUCCESSFULLY);
	}

	private void checkIfOrderedAdditionalServiceExists(int id) {

		if (!orderedAdditionalServiceDao.existsById(id)) {
			throw new BusinessException(BusinessMessages.ORDERED_ADDITIONAL_SERVICE_NOT_FOUND);
		}
	}

}
