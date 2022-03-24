package com.turkcell.rentACar.business.concretes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turkcell.rentACar.api.controllers.models.Payment.PaymentModel;
import com.turkcell.rentACar.api.controllers.models.RentalCar.CreateRentalModel;
import com.turkcell.rentACar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACar.business.abstracts.InvoiceService;
import com.turkcell.rentACar.business.abstracts.PaymentService;
import com.turkcell.rentACar.business.abstracts.PosService;
import com.turkcell.rentACar.business.abstracts.RentalCarService;
import com.turkcell.rentACar.business.dtos.CorporateCustomer.GetCorporateCustomerDto;
import com.turkcell.rentACar.business.dtos.IndividualCustomer.GetIndividualCustomerDto;
import com.turkcell.rentACar.business.dtos.Payment.PaymentListDto;
import com.turkcell.rentACar.business.requests.Invoice.CreateInvoiceRequest;
import com.turkcell.rentACar.business.requests.Invoice.CreateInvoiceRequestForPayment;
import com.turkcell.rentACar.business.requests.Payment.CreatePaymentRequest;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.ErrorResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.PaymentDao;
import com.turkcell.rentACar.entities.concretes.CorporateCustomer;
import com.turkcell.rentACar.entities.concretes.IndividualCustomer;
import com.turkcell.rentACar.entities.concretes.Invoice;
import com.turkcell.rentACar.entities.concretes.Payment;
import com.turkcell.rentACar.entities.concretes.RentalCar;

@Service
public class PaymentManager implements PaymentService {

	private PaymentDao paymentDao;
	private ModelMapperService modelMapperService;
	private PosService posService;
	private RentalCarService rentalCarService;
	private InvoiceService invoiceService;
	private IndividualCustomerService individualCustomerService;
	private CorporateCustomerService corporateCustomerService;

	@Autowired
	public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService, PosService posService,
			RentalCarService rentalCarService, InvoiceService invoiceService,
			IndividualCustomerService individualCustomerService, CorporateCustomerService corporateCustomerService) {

		this.paymentDao = paymentDao;
		this.modelMapperService = modelMapperService;
		this.posService = posService;
		this.rentalCarService = rentalCarService;
		this.invoiceService = invoiceService;
		this.individualCustomerService = individualCustomerService;
		this.corporateCustomerService = corporateCustomerService;
	}

	@Override
	public Result addForIndividualCustomer(PaymentModel paymentModel) {

		if (posService.pay(paymentModel.getCreatePosServiceRequest())) {

			runPaymentSuccessorForIndividiual(paymentModel);

			return new SuccessResult("Payment successfull.");
		}

		return new ErrorResult("Payment is not successfull.");
	}

	@Override
	public Result addForCorporateCustomer(PaymentModel paymentModel) {

		if (posService.pay(paymentModel.getCreatePosServiceRequest())) {

			runPaymentSuccessorForCorporate(paymentModel);

			return new SuccessResult("Payment successfull.");
		}

		return new ErrorResult("Payment is not successfull.");
	}

	@Transactional
	public void runPaymentSuccessorForIndividiual(PaymentModel paymentModel) {
		RentalCar rentalCar = addRentalForIndividualCustomer(paymentModel.getCreateRentalModel());
		Invoice invoice = addInvoiceForIndividual(paymentModel, rentalCar);
		addPayment(paymentModel, invoice, rentalCar);
	}

	@Transactional
	public void runPaymentSuccessorForCorporate(PaymentModel paymentModel) {
		RentalCar rentalCar = addRentalForCorporateCustomer(paymentModel.getCreateRentalModel());
		Invoice invoice = addInvoiceForCorporate(paymentModel, rentalCar);
		addPayment(paymentModel, invoice, rentalCar);
	}

	@Transactional
	private RentalCar addRentalForCorporateCustomer(CreateRentalModel createRentalModel) {
		return rentalCarService.addForCorporateCustomer(createRentalModel).getData();
	}

	@Transactional
	private RentalCar addRentalForIndividualCustomer(CreateRentalModel createRentalModel) {
		return rentalCarService.addForIndividualCustomer(createRentalModel).getData();
	}

	@Transactional
	private Invoice addInvoiceForIndividual(PaymentModel paymentModel, RentalCar rentalCar) {
		CreateInvoiceRequestForPayment createInvoiceRequestForPayment = paymentModel.getCreateInvoiceRequest();
		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequestForPayment, Invoice.class);

		invoice.setCreationDate(LocalDate.now());
		GetIndividualCustomerDto customer = individualCustomerService
				.getById(paymentModel.getCreateRentalModel().getCreateRentalCarRequest().getCustomerUserId()).getData();
		IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(customer,
				IndividualCustomer.class);

		invoice.setCustomer(individualCustomer);

		invoice.setInvoiceNumber("123");

		invoice.setRentalCar(rentalCar);

		invoice.setTotalPrice(rentalCar.getTotalPrice());
		invoice.setTotalRentDay(rentalCar.getTotalRentDay());
		invoice.setStartDate(rentalCar.getStartingDate());
		invoice.setEndDate(rentalCar.getEndDate());

		CreateInvoiceRequest createInvoiceRequestUpdated = this.modelMapperService.forDto().map(invoice,
				CreateInvoiceRequest.class);
		return invoiceService.add(createInvoiceRequestUpdated).getData();
	}

	@Transactional
	private Invoice addInvoiceForCorporate(PaymentModel paymentModel, RentalCar rentalCar) {
		CreateInvoiceRequestForPayment createInvoiceRequestForPayment = paymentModel.getCreateInvoiceRequest();
		Invoice invoice = this.modelMapperService.forRequest().map(createInvoiceRequestForPayment, Invoice.class);

		invoice.setCreationDate(LocalDate.now());

		GetCorporateCustomerDto customer = corporateCustomerService
				.getById(paymentModel.getCreateRentalModel().getCreateRentalCarRequest().getCustomerUserId()).getData();
		CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(customer,
				CorporateCustomer.class);

		invoice.setCustomer(corporateCustomer);

		invoice.setInvoiceNumber("123");

		invoice.setRentalCar(rentalCar);

		invoice.setTotalPrice(rentalCar.getTotalPrice());
		invoice.setTotalRentDay(rentalCar.getTotalRentDay());
		invoice.setStartDate(rentalCar.getStartingDate());
		invoice.setEndDate(rentalCar.getEndDate());

		CreateInvoiceRequest createInvoiceRequestUpdated = this.modelMapperService.forDto().map(invoice,
				CreateInvoiceRequest.class);
		return invoiceService.add(createInvoiceRequestUpdated).getData();
	}

	@Transactional
	private Payment addPayment(PaymentModel paymentModel, Invoice invoice, RentalCar rentalCar) {

		CreatePaymentRequest createPaymentRequest = paymentModel.getCreatePaymentRequest();
		Payment payment = this.modelMapperService.forRequest().map(createPaymentRequest, Payment.class);
		payment.setInvoice(invoice);
		payment.setRentalCar(rentalCar);
		return paymentDao.save(payment);
	}

	@Override
	public DataResult<List<PaymentListDto>> getAll() {
		List<Payment> result = this.paymentDao.findAll();

		List<PaymentListDto> response = result.stream()
				.map(payment -> this.modelMapperService.forDto().map(payment, PaymentListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<PaymentListDto>>(response, "Payments listed successfully.");
	}

}