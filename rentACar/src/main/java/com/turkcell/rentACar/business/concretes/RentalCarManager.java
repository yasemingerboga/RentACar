package com.turkcell.rentACar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turkcell.rentACar.api.controllers.models.RentalCar.CreateRentalModel;
import com.turkcell.rentACar.api.controllers.models.RentalCar.UpdateRentalModel;
import com.turkcell.rentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACar.business.abstracts.InvoiceService;
import com.turkcell.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACar.business.abstracts.RentalCarService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.CarMaintenance.CarMaintenanceListDto;
import com.turkcell.rentACar.business.dtos.Invoice.InvoiceListDto;
import com.turkcell.rentACar.business.dtos.RentalCar.GetRentalCarDto;
import com.turkcell.rentACar.business.dtos.RentalCar.RentalCarListDto;
import com.turkcell.rentACar.business.requests.RentalCar.EndOfRent;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.ErrorResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.RentalCarDao;
import com.turkcell.rentACar.entities.concretes.OrderedAdditionalService;
import com.turkcell.rentACar.entities.concretes.RentalCar;

@Service
public class RentalCarManager implements RentalCarService {

	private RentalCarDao rentalCarDao;
	private ModelMapperService modelMapperService;
	private CarMaintenanceService carMaintenanceService;
	private OrderedAdditionalServiceService orderedAdditionalServiceService;
	private AdditionalServiceService additionalServiceService;
	private IndividualCustomerService individualCustomerService;
	private CorporateCustomerService corporateCustomerService;
	private CarService carService;
	private InvoiceService invoiceService;

	@Autowired
	public RentalCarManager(RentalCarDao rentalCarDao, ModelMapperService modelMapperService,
			CarMaintenanceService carMaintenanceService,
			OrderedAdditionalServiceService orderedAdditionalServiceService,
			AdditionalServiceService additionalServiceService, IndividualCustomerService individualCustomerService,
			CorporateCustomerService corporateCustomerService, CarService carService, InvoiceService invoiceService) {
		this.rentalCarDao = rentalCarDao;
		this.modelMapperService = modelMapperService;
		this.carMaintenanceService = carMaintenanceService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
		this.additionalServiceService = additionalServiceService;
		this.individualCustomerService = individualCustomerService;
		this.corporateCustomerService = corporateCustomerService;
		this.carService = carService;
		this.invoiceService = invoiceService;
	}

	@Override
	public DataResult<List<RentalCarListDto>> getAll() {

		List<RentalCar> resultList = this.rentalCarDao.findAll();

		List<RentalCarListDto> responseList = resultList.stream()
				.map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<RentalCarListDto>>(responseList, "Rental cars listed successfully.");
	}

	@Override
	public DataResult<GetRentalCarDto> getById(int id) {

		RentalCar result = this.rentalCarDao.getById(id);

		GetRentalCarDto response = this.modelMapperService.forDto().map(result, GetRentalCarDto.class);

		return new SuccessDataResult<GetRentalCarDto>(response, "Rental car has been received successfully.");
	}

	@Override
	public DataResult<List<RentalCarListDto>> getByCarId(int carId) {

		List<RentalCar> result = this.rentalCarDao.getByCar_Id(carId);

		List<RentalCarListDto> response = result.stream()
				.map(rentalCar -> this.modelMapperService.forDto().map(rentalCar, RentalCarListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<RentalCarListDto>>(response, "Rental cars listed successfully.");
	}

	@Override
	@Transactional
	public DataResult<RentalCar> addForCorporateCustomer(CreateRentalModel rentalModel) {

		checkIfCorporateCustomerExists(rentalModel.getCreateRentalCarRequest().getCustomerUserId());

		checkIfCarExists(rentalModel.getCreateRentalCarRequest().getCarId());

		RentalCar rentalCar = this.modelMapperService.forRequest().map(rentalModel.getCreateRentalCarRequest(),
				RentalCar.class);

		checkIfInMaintenance(rentalCar);
		checkIfInRent(rentalCar);

		List<OrderedAdditionalService> orderedAdditionalServices = rentalModel
				.getCreateOrderedAdditionalServiceRequest().stream().map(additionalService -> this.modelMapperService
						.forRequest().map(additionalService, OrderedAdditionalService.class))
				.collect(Collectors.toList());

		rentalCar.setOrderedAdditionalServices(mappingOrderedAdditionalService(orderedAdditionalServices, rentalCar));

		rentalCar.setOrderedAdditionalServices(orderedAdditionalServices);

		rentalCar.equals(checkIfAdditionalPrice(rentalCar));
		rentalCar.equals(checkIfOrderedAdditionalServicesExists(rentalCar));

		rentalCar.setTotalRentDay(calculateTotalRentDay(rentalCar.getStartingDate(), rentalCar.getEndDate()));

		rentalCar.setTotalPrice(calculateTotalPrice(rentalCar.getCar().getId(), rentalCar.getTotalRentDay(),
				rentalCar.getAdditionalPrice()));

		rentalCar.setStartingKilometer(carService.getById(rentalCar.getCar().getId()).getData().getKilometer());

		RentalCar savedRentalCar = this.rentalCarDao.save(rentalCar);

		return new SuccessDataResult<RentalCar>(savedRentalCar, "Rental car saved successfully.");
	}

	public Double calculateTotalPrice(int carId, Long totalRentDay, Double additionalPrice) {
		Double dailyPrice = carService.getById(carId).getData().getDailyPrice();
		return additionalPrice + (dailyPrice * totalRentDay);
	}

	@Override
	@Transactional
	public DataResult<RentalCar> addForIndividualCustomer(CreateRentalModel rentalModel) {

		checkIfIndividualCustomerExists(rentalModel.getCreateRentalCarRequest().getCustomerUserId());

		checkIfCarExists(rentalModel.getCreateRentalCarRequest().getCarId());

		RentalCar rentalCar = this.modelMapperService.forRequest().map(rentalModel.getCreateRentalCarRequest(),
				RentalCar.class);

		checkIfInMaintenance(rentalCar);
		checkIfInRent(rentalCar);

		List<OrderedAdditionalService> orderedAdditionalServices = rentalModel
				.getCreateOrderedAdditionalServiceRequest().stream().map(additionalService -> this.modelMapperService
						.forRequest().map(additionalService, OrderedAdditionalService.class))
				.collect(Collectors.toList());

		rentalCar.setOrderedAdditionalServices(mappingOrderedAdditionalService(orderedAdditionalServices, rentalCar));

		rentalCar.equals(checkIfAdditionalPrice(rentalCar));
		rentalCar.equals(checkIfOrderedAdditionalServicesExists(rentalCar));

		rentalCar.setTotalRentDay(calculateTotalRentDay(rentalCar.getStartingDate(), rentalCar.getEndDate()));

		rentalCar.setTotalPrice(calculateTotalPrice(rentalCar.getCar().getId(), rentalCar.getTotalRentDay(),
				rentalCar.getAdditionalPrice()));

		rentalCar.setStartingKilometer(carService.getById(rentalCar.getCar().getId()).getData().getKilometer());

		RentalCar savedRentalCar = this.rentalCarDao.save(rentalCar);

		return new SuccessDataResult<RentalCar>(savedRentalCar, "Rental car saved successfully.");
	}

	private void checkIfIndividualCustomerExists(int id) {
		if (!individualCustomerService.existById(id).isSuccess()) {
			throw new BusinessException("There is no individual customer with the specified id.");
		}
	}

	private void checkIfCorporateCustomerExists(int id) {

		if (!corporateCustomerService.existById(id).isSuccess()) {

			throw new BusinessException(BusinessMessages.CORPORATE_NOT_FOUND);
		}
	}

	private List<OrderedAdditionalService> mappingOrderedAdditionalService(
			List<OrderedAdditionalService> orderedAdditionalServices, RentalCar rentalCar) {

		checkIfAdditionalServiceExists(orderedAdditionalServices);

		for (int i = 0; i < orderedAdditionalServices.size(); i++) {
			orderedAdditionalServices.get(i).setId(0);
			orderedAdditionalServices.get(i).setRentalCar(rentalCar);
		}
		return orderedAdditionalServices;
	}

	private void checkIfAdditionalServiceExists(List<OrderedAdditionalService> orderedAdditionalServices) {

		for (OrderedAdditionalService orderedAdditionalService : orderedAdditionalServices) {

			additionalServiceService.existById(orderedAdditionalService.getAdditionalService().getId());
		}
	}

	private RentalCar checkIfAdditionalPrice(RentalCar rentalCar) {

		if (rentalCar.getRentedCity().getId() != rentalCar.getDropOffCity().getId()) {

			rentalCar.setAdditionalPrice(750.0);
		}
		return rentalCar;
	}

	private RentalCar checkIfOrderedAdditionalServicesExists(RentalCar rentalCar) {

		if (!rentalCar.getOrderedAdditionalServices().isEmpty()) {

			rentalCar.getOrderedAdditionalServices().forEach(orderedAdditionalService -> {
				Double additionalServicePrice = additionalServiceService
						.getById(orderedAdditionalService.getAdditionalService().getId()).getData().getPrice();
				rentalCar.setAdditionalPrice(rentalCar.getAdditionalPrice() + additionalServicePrice);
				orderedAdditionalService.setRentalCar(rentalCar);
			});
		}

		return rentalCar;
	}

	private void checkIfInMaintenance(RentalCar rentalCar) {

		List<CarMaintenanceListDto> result = this.carMaintenanceService.getByCarId(rentalCar.getCar().getId())
				.getData();

		if (result == null) {
			return;
		}

		for (CarMaintenanceListDto carMaintenanceDto : result) {

			if ((carMaintenanceDto.getReturnDate() != null)
					&& (rentalCar.getStartingDate().isBefore(carMaintenanceDto.getReturnDate())
							|| rentalCar.getEndDate().isBefore(carMaintenanceDto.getReturnDate()))) {

				throw new BusinessException("This car cannot be rented as it is under maintenance.");
			}
			if (carMaintenanceDto.getReturnDate() == null) {

				throw new BusinessException(
						"This car cannot be rented as it is under maintenance / return date equals null.");
			}
		}
	}

	private void checkIfInRent(RentalCar rentalCar) {

		List<RentalCar> rentalCarList = this.rentalCarDao.getByCar_Id(rentalCar.getCar().getId());

		if (rentalCarList == null) {
			return;
		}
		for (RentalCar rental : rentalCarList) {
			if (rentalCar.getId() != rental.getId()) {
				if (rentalCar.getStartingDate().isBefore(rental.getEndDate())
						&& rentalCar.getStartingDate().isAfter(rental.getStartingDate())) {
					throw new BusinessException("This car is rented already.");
				}
			}

		}

	}

	@Override
	public Result update(UpdateRentalModel updateRentalModel) {

		orderedAdditionalServiceService.deleteByRentalCarId(updateRentalModel.getUpdateRentalCarRequest().getId());

		RentalCar updatedRentalCar = this.modelMapperService.forRequest()
				.map(updateRentalModel.getUpdateRentalCarRequest(), RentalCar.class);

		checkIfInMaintenance(updatedRentalCar);
		checkIfInRent(updatedRentalCar);

		List<OrderedAdditionalService> orderedAdditionalServices = updateRentalModel
				.getOrderedAdditionalServiceRequests().stream().map(additionalService -> this.modelMapperService
						.forRequest().map(additionalService, OrderedAdditionalService.class))
				.collect(Collectors.toList());

		updatedRentalCar.setOrderedAdditionalServices(
				mappingOrderedAdditionalService(orderedAdditionalServices, updatedRentalCar));

		updatedRentalCar.equals(checkIfAdditionalPrice(updatedRentalCar));

		updatedRentalCar.setOrderedAdditionalServices(orderedAdditionalServices);
		updatedRentalCar.equals(checkIfOrderedAdditionalServicesExists(updatedRentalCar));

		updatedRentalCar.setTotalRentDay(
				calculateTotalRentDay(updatedRentalCar.getStartingDate(), updatedRentalCar.getEndDate()));

		updatedRentalCar.setTotalPrice(calculateTotalPrice(updatedRentalCar.getCar().getId(),
				updatedRentalCar.getTotalRentDay(), updatedRentalCar.getAdditionalPrice()));
		this.rentalCarDao.save(updatedRentalCar);

		return new SuccessResult("Rental car is updated.");
	}

	public void checkIfCarExists(int carId) {

		if (!carService.existsById(carId).isSuccess()) {

			throw new BusinessException("There is no car with the specified id.");
		}
	}

	@Override
	public Result delete(int id) {

		checkIfRentalCarExists(id);

		this.rentalCarDao.deleteById(id);

		return new SuccessResult("Deleted successfully.");
	}

	public void checkIfRentalCarExists(int rentalCarId) {

		if (!rentalCarDao.existsById(rentalCarId)) {

			throw new BusinessException("There is no rental car with the specified id.");

		}
	}

	public Long calculateTotalRentDay(LocalDate startingDate, LocalDate endDay) {
		return ChronoUnit.DAYS.between(startingDate, endDay) + 1;
	}

	@Override
	public Result endOfRent(EndOfRent endOfRent) {

		checkIfRentalCarExists(endOfRent.getId());

		RentalCar rentalCar = rentalCarDao.getById(endOfRent.getId());

		rentalCar.setEndingKilometer(endOfRent.getEndingKilometer());

		carService.updateKilometer(rentalCar.getCar().getId(), rentalCar.getEndingKilometer());

		rentalCarDao.save(rentalCar);

		if (!checkIfIsRightTime(rentalCar)) {
			List<InvoiceListDto> invoices = invoiceService.getbyRentalCarId(rentalCar.getId()).getData();

			Double totalInvoicePrice = 0.0;

			for (InvoiceListDto invoice : invoices) {
				totalInvoicePrice += invoice.getTotalPrice();
			}
			Double difference = calculateTotalPrice(rentalCar.getCar().getId(),
					calculateTotalRentDay(rentalCar.getStartingDate(), LocalDate.now()), rentalCar.getAdditionalPrice())
					- totalInvoicePrice;
			return new ErrorResult("You should pay extra " + difference.toString() + " liras.");
		}

		return new SuccessResult("Rent ends.");

	}

	private boolean checkIfIsRightTime(RentalCar rentalCar) {
		if (!rentalCar.getEndDate().equals(LocalDate.now())) {
			return false;
		}
		return true;
	}

	@Override
	public boolean saveNewRentalCarAfterPayingExtra(RentalCar rentalCar) {
		rentalCar.setEndDate(LocalDate.now());
		rentalCar.setTotalRentDay(calculateTotalRentDay(rentalCar.getStartingDate(), rentalCar.getEndDate()));
		rentalCar.setTotalPrice(calculateTotalPrice(rentalCar.getCar().getId(), rentalCar.getTotalRentDay(),
				rentalCar.getAdditionalPrice()));
		rentalCarDao.save(rentalCar);
		return true;
	}
}
