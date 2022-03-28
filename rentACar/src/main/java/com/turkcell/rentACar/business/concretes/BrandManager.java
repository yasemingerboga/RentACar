package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.BrandService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.Brand.BrandListDto;
import com.turkcell.rentACar.business.dtos.Brand.GetBrandDto;
import com.turkcell.rentACar.business.requests.Brand.CreateBrandRequest;
import com.turkcell.rentACar.business.requests.Brand.UpdateBrandRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
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

		return new SuccessDataResult<List<BrandListDto>>(response, BusinessMessages.BRAND_LIST_SUCCESSFULLY);
	}

	@Override
	public Result add(CreateBrandRequest createBrandRequest) {

		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);

		checkIfBrandExists(createBrandRequest.getName());

		this.brandDao.save(brand);

		return new SuccessResult(BusinessMessages.BRAND_SAVE_SUCCESSFULLY);

	}

	@Override
	public DataResult<GetBrandDto> getById(int id) {

		checkIfBrandExistsById(id);

		Brand brand = brandDao.findById(id);
		GetBrandDto response = this.modelMapperService.forDto().map(brand, GetBrandDto.class);

		return new SuccessDataResult<GetBrandDto>(response, BusinessMessages.BRAND_GET_SUCCESSFULLY);
	}

	private void checkIfBrandExists(String name) {

		if (this.brandDao.existsByBrandName(name)) {
			throw new BusinessException(BusinessMessages.BRAND_HAS_SAME_NAME);
		}
	}

	private void checkIfBrandNameExists(Brand brand) {

		Brand ifExsistsBrand = this.brandDao.findByBrandName(brand.getBrandName());

		if (ifExsistsBrand != null && ifExsistsBrand.getId() != brand.getId()) {
			throw new BusinessException(BusinessMessages.BRAND_HAS_SAME_NAME);
		}
	}

	@Override
	public Result delete(int id) {
		checkIfBrandExistsById(id);

		this.brandDao.deleteById(id);

		return new SuccessResult(BusinessMessages.BRAND_DELETE_SUCCESSFULLY);

	}

	@Override
	public Result update(UpdateBrandRequest updateBrandRequest) {

		checkIfBrandExistsById(updateBrandRequest.getBrandId());

		Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);

		checkIfBrandNameExists(brand);

		this.brandDao.save(brand);

		return new SuccessResult(BusinessMessages.BRAND_UPDATE_SUCCESSFULLY);
	}

	private void checkIfBrandExistsById(int id) {

		if (!brandDao.existsById(id)) {
			throw new BusinessException(BusinessMessages.BRAND_NOT_FOUND);
		}
	}

}
