package com.riverway.housingfinance.finance.service;

import com.riverway.housingfinance.bank.domain.Bank;
import com.riverway.housingfinance.bank.service.BankService;
import com.riverway.housingfinance.finance.domain.HousingFinanceFile;
import com.riverway.housingfinance.finance.domain.YearlyFinanceSupply;
import com.riverway.housingfinance.finance.domain.repository.HousingFinanceFileRepository;
import com.riverway.housingfinance.finance.dto.BankSupportAmountResponse;
import com.riverway.housingfinance.finance.dto.LargestAmountResponse;
import com.riverway.housingfinance.finance.support.CsvFilePreprocessor;
import com.riverway.housingfinance.finance.support.HousingFinanceFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Service
public class HousingFinanceService {

    private final BankService bankService;
    private final HousingFinanceFileRepository fileRepository;
    private final YearlyFinanceService yearlyFinanceService;
    private final CsvFilePreprocessor preprocessor;

    public HousingFinanceService(BankService bankService, HousingFinanceFileRepository fileRepository, YearlyFinanceService yearlyFinanceService, CsvFilePreprocessor preprocessor) {
        this.bankService = bankService;
        this.fileRepository = fileRepository;
        this.yearlyFinanceService = yearlyFinanceService;
        this.preprocessor = preprocessor;
    }

    @Transactional
    public Integer registerData(MultipartFile file) {
        HousingFinanceFactory housingFinanceFactory = preprocessor.read(file);
        List<Bank> banks = bankService.findByNames(housingFinanceFactory.getBankNames());
        List<YearlyFinanceSupply> housingFinanceData = housingFinanceFactory.parse(banks);
        HousingFinanceFile housingFinanceFile = fileRepository.save(HousingFinanceFile.of(file, housingFinanceData));
        return housingFinanceFile.getId();
    }

    @Transactional(readOnly = true)
    public LargestAmountResponse findLargestOfAll(Integer Id) {
        return yearlyFinanceService.findLargestOfAll(Id).toLargestAmount();
    }

    @Transactional(readOnly = true)
    public BankSupportAmountResponse findLargestAndSmallest(Integer id, String bankName) {
        String bankId = bankService.findByName(bankName).getInstituteCode();
        return yearlyFinanceService.findLargestAndSmallest(id, bankId);
    }
}
