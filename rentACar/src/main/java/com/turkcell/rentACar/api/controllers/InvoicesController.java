package com.turkcell.rentACar.api.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.business.abstracts.InvoiceService;
import com.turkcell.rentACar.business.dtos.Invoice.GetInvoiceDto;
import com.turkcell.rentACar.business.dtos.Invoice.InvoiceListDto;
import com.turkcell.rentACar.business.requests.Invoice.UpdateInvoiceRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/invoices")
public class InvoicesController {

	private InvoiceService invoiceService;

	public InvoicesController(InvoiceService invoiceService) {
		this.invoiceService = invoiceService;
	}

	@GetMapping("/getall")
	public DataResult<List<InvoiceListDto>> getAll() {
		return invoiceService.getAll();
	}

	@GetMapping("/getbyid/{id}")
	public DataResult<GetInvoiceDto> getById(@RequestParam int id) {
		return this.invoiceService.getById(id);
	}

	@DeleteMapping("/delete/{id}")
	public Result delete(@RequestParam int id) {
		return this.invoiceService.delete(id);
	}

	@PutMapping("/update")
	public Result update(@RequestBody @Valid UpdateInvoiceRequest updateColorRequest) {
		return this.invoiceService.update(updateColorRequest);
	}

	@GetMapping("/getByCustomerId/{id}")
	public DataResult<List<InvoiceListDto>> getByCustomerId(@RequestParam int id) {
		return this.invoiceService.getByCustomerId(id);
	}

	@GetMapping("/getAllByBetweenStartDateAndEndDate")
	public DataResult<List<InvoiceListDto>> getAllByBetweenStartDateAndEndDate(
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") @RequestParam("startDate") LocalDate startDate,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd") @RequestParam("endDate") LocalDate endDate) {
		return this.invoiceService.getAllByBetweenStartDateAndEndDate(startDate, endDate);
	}

}
