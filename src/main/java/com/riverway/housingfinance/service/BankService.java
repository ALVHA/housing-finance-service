package com.riverway.housingfinance.service;

import com.riverway.housingfinance.domain.Bank;
import com.riverway.housingfinance.domain.BankRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BankService {

    private final BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public List<Bank> findByNames(String[] names) {
        return bankRepository.findByInstituteNameIn(names);
    }
}
