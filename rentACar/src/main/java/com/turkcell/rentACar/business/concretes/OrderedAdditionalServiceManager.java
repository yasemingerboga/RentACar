package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACar.business.dtos.OrderedAdditionalService.GetOrderedAdditionalServiceDto;
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
				"Ordered additional services has been received by rental car id successfully.");

	}

	@Override
	public Result delete(int id) {

		this.orderedAdditionalServiceDao.deleteById(id);

		return new SuccessResult("Deleted successfully.");
	}

	@Override
	public Result deleteByRentalCarId(int rentalCarId) {

		orderedAdditionalServiceDao.deleteAll(orderedAdditionalServiceDao.findByRentalCar_Id(rentalCarId));

		return new SuccessResult("Ordered additional services deleted successfully");
	}

	@Override
	public DataResult<GetOrderedAdditionalServiceDto> getById(int id) {

		OrderedAdditionalService result = orderedAdditionalServiceDao.getById(id);

		GetOrderedAdditionalServiceDto response = this.modelMapperService.forDto().map(result,
				GetOrderedAdditionalServiceDto.class);

		return new SuccessDataResult<GetOrderedAdditionalServiceDto>(response,
				"Ordered additional service has been received successfully.");
	}

}
