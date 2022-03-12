package com.turkcell.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.rentACar.entities.concretes.AdditionalService;


public interface AdditionalServiceDao extends JpaRepository<AdditionalService, Integer>{

}
