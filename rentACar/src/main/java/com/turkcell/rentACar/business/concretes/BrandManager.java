package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.BrandService;
import com.turkcell.rentACar.business.dtos.BrandListDto;
import com.turkcell.rentACar.business.dtos.GetBrandDto;
import com.turkcell.rentACar.business.requests.CreateBrandRequest;
import com.turkcell.rentACar.business.requests.UpdateBrandRequest;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.BrandDao;
import com.turkcell.rentACar.entities.concretes.Brand;

@Service
public class BrandManager implements BrandService {

	private BrandDao brandDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public BrandManager(BrandDao brandDao, ModelMapperService modelMapperService) {
		super();
		this.brandDao = brandDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<BrandListDto>> getAll() {

		List<Brand> result = this.brandDao.findAll();
		List<BrandListDto> response = result.stream()
				.map(brand -> this.modelMapperService.forDto().map(brand, BrandListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<BrandListDto>>(response, "All brands are listed.");
	}

	@Override
	public Result add(CreateBrandRequest createBrandRequest) throws Exception {
		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
		checkIfBrandExists(createBrandRequest.getName());
		this.brandDao.save(brand);
		return new SuccessResult("Brand added successfully.");

	}

	@Override
	public DataResult<GetBrandDto> getById(int id) {
		Brand brand = brandDao.findById(id);
		GetBrandDto response = this.modelMapperService.forDto().map(brand, GetBrandDto.class);
		return new SuccessDataResult<GetBrandDto>(response,"Getting brand by id");
	}

	private void checkIfBrandExists(String name) throws Exception {
		if (this.brandDao.existsByBrandName(name)) {
			throw new Exception("Aynı isimde marka eklenemez");
		}
	}

	private void checkIfBrandNameExists(Brand brand) throws Exception {

		Brand ifExsistsBrand = this.brandDao.findByBrandName(brand.getBrandName());

		if (ifExsistsBrand != null && ifExsistsBrand.getBrandId() != brand.getBrandId()) {
			throw new Exception("Aynı isimde marka eklenemez");
		}
	}

	@Override
	public Result delete(int id) {
		this.brandDao.deleteById(id);
		return new SuccessResult("Brand deleted successfully.");

	}

	@Override
	public Result update(UpdateBrandRequest updateBrandRequest) throws Exception {
		Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
		checkIfBrandNameExists(brand);
		this.brandDao.save(brand);
		return new SuccessResult("Brand updated successfully.");
	}

}
