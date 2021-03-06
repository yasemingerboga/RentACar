package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.OrderedAdditionalProductService;
import com.turkcell.rentACar.business.dtos.OrderedAdditionalService.GetOrderedAdditionalServiceDto;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.OrderedAdditionalServiceDao;
import com.turkcell.rentACar.entities.concretes.OrderedAdditionalService;

@Service
public class OrderedAdditionalProductManager implements OrderedAdditionalProductService {
	private OrderedAdditionalServiceDao orderedAdditionalServiceDao;
	private ModelMapperService modelMapperService;

	public OrderedAdditionalProductManager(OrderedAdditionalServiceDao orderedAdditionalServiceDao,
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

}
