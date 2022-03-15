package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.AdditionalService.AdditionalServiceListDto;
import com.turkcell.rentACar.business.dtos.AdditionalService.GetAdditionalServiceDto;
import com.turkcell.rentACar.business.requests.AdditionalService.CreateAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.AdditionalService.UpdateAdditionalServiceRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface AdditionalServiceService {
	DataResult<List<AdditionalServiceListDto>> getAll();

	Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest);

	DataResult<GetAdditionalServiceDto> getById(int id);

	Result delete(int id);

	Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest);

	Result existById(int id);
}
