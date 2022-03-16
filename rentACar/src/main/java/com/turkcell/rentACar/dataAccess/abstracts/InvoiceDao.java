package com.turkcell.rentACar.dataAccess.abstracts;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.entities.concretes.Invoice;

@Repository
public interface InvoiceDao extends JpaRepository<Invoice, Integer> {

	List<Invoice> getByCustomer_Id(int id);

	@Query("SELECT i FROM Invoice i WHERE i.creationDate BETWEEN :startDate AND :endDate")
	List<Invoice> getAllByBetweenStartDateAndEndDate(LocalDate startDate, LocalDate endDate);
}
