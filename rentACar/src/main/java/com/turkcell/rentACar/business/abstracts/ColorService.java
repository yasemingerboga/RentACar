package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.ColorListDto;
import com.turkcell.rentACar.business.dtos.GetColorDto;
import com.turkcell.rentACar.business.requests.CreateColorRequest;
import com.turkcell.rentACar.business.requests.UpdateColorRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface ColorService {

	DataResult<List<ColorListDto>> getAll();
	Result add(CreateColorRequest createColorRequest) throws Exception;
	DataResult<GetColorDto> getById(int id);
	Result delete(int id);
	Result update(UpdateColorRequest updateColorRequest) throws Exception;
}
