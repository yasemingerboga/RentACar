package com.turkcell.rentACar.business.abstracts;

import java.util.List;

import com.turkcell.rentACar.business.dtos.Brand.BrandListDto;
import com.turkcell.rentACar.business.dtos.Brand.GetBrandDto;
import com.turkcell.rentACar.business.requests.Brand.CreateBrandRequest;
import com.turkcell.rentACar.business.requests.Brand.UpdateBrandRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface BrandService {

	DataResult<List<BrandListDto>> getAll();
	Result add(CreateBrandRequest createBrandRequest);
	DataResult<GetBrandDto> getById(int id);
	Result delete(int id);
	Result update(UpdateBrandRequest updateBrandRequest);
}
