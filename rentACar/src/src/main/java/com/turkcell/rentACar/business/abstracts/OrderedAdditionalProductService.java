package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.OrderedAdditionalService.GetOrderedAdditionalServiceDto;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface OrderedAdditionalProductService {
	DataResult<List<GetOrderedAdditionalServiceDto>> getAllByRentalCarId(int id);

	Result delete(int id);
}
