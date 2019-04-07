package com.riverway.housingfinance.bank.service;

import com.riverway.housingfinance.bank.BankName;
import com.riverway.housingfinance.bank.domain.Bank;
import com.riverway.housingfinance.bank.domain.BankRepository;
import com.riverway.housingfinance.support.exception.ErrorMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
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

    public List<Bank> findListOfBank() {
        return bankRepository.findAll();
    }

    public Bank findByName(String bankName) {
        return bankRepository
                .findByInstituteName(bankName)
                .orElseThrow(() -> new EntityNotFoundException(ErrorMessage.NOT_EXIST_BANK));
    }
}
