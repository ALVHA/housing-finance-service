package com.riverway.housingfinance.bank.service;

import com.riverway.housingfinance.bank.BankDto;
import com.riverway.housingfinance.bank.BankName;
import com.riverway.housingfinance.bank.domain.BankRepository;
import com.riverway.housingfinance.bank.domain.Bank;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<BankDto> findListOfBank() {
        return bankRepository.findAll()
                .stream()
                .map(bank-> bank.toBankDto())
                .collect(Collectors.toList());
    }
}
