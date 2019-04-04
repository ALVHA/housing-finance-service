package com.riverway.housingfinance.bank.domain;

import com.riverway.housingfinance.bank.domain.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BankRepository extends JpaRepository<Bank, String> {

    List<Bank> findByInstituteNameIn(String[] bankNames);
}
