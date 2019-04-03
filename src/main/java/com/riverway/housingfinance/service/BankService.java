package com.riverway.housingfinance.service;

import com.riverway.housingfinance.domain.Bank;
import com.riverway.housingfinance.domain.BankName;
import com.riverway.housingfinance.domain.BankRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BankService {

    private final BankRepository bankRepository;

    public BankService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public List<Bank> findByNames(List<BankName> names) {
        String[] bankNames = convertToString(names);
        return bankRepository.findByInstituteNameIn(bankNames);
    }

    public String[] convertToString(List<BankName> names) {
        return names.stream()
                .map(bank -> bank.getBankName())
                .toArray(String[]::new);
    }
}
