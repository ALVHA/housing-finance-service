package com.riverway.housingfinance.bank.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankRepository extends JpaRepository<Bank, String> {

    List<Bank> findByInstituteNameIn(String[] bankNames);

    Optional<Bank> findByInstituteName(String bankName);
}
