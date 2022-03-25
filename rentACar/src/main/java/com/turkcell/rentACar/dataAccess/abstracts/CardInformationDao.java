package com.turkcell.rentACar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.rentACar.entities.concretes.CardInformation;

public interface CardInformationDao extends JpaRepository<CardInformation, Integer> {

}
