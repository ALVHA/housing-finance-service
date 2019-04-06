package com.riverway.housingfinance.finance.controller;

import com.riverway.housingfinance.bank.domain.Bank;
import com.riverway.housingfinance.bank.service.BankService;
import com.riverway.housingfinance.finance.dto.BankSupportAmountResponse;
import com.riverway.housingfinance.finance.dto.LargestAmountResponse;
import com.riverway.housingfinance.finance.dto.SupplyStatusData;
import com.riverway.housingfinance.finance.dto.YearlyTotalAmountsResponse;
import com.riverway.housingfinance.finance.service.HousingFinanceService;
import com.riverway.housingfinance.finance.service.YearlyTotalAmountsService;
import com.riverway.housingfinance.finance.support.CsvFilePreprocessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/housing/finance")
public class HousingFinanceController {

    private final BankService bankService;
    private final HousingFinanceService housingFinanceService;
    private final YearlyTotalAmountsService yearlyTotalAmountsService;
    private final CsvFilePreprocessor preprocessor;

    public HousingFinanceController(BankService bankService
            , HousingFinanceService housingFinanceService
            , YearlyTotalAmountsService yearlyTotalAmountsService, CsvFilePreprocessor preprocessor) {
        this.bankService = bankService;
        this.housingFinanceService = housingFinanceService;
        this.yearlyTotalAmountsService = yearlyTotalAmountsService;
        this.preprocessor = preprocessor;
    }

    @PostMapping("")
    public ResponseEntity<Void> uploadCsvFile(MultipartFile csvFile) {
        log.debug("파일 이름 : {}", csvFile.getOriginalFilename());
        SupplyStatusData supplyStatusData = preprocessor.read(csvFile);
        List<Bank> banks = bankService.findByNames(supplyStatusData.getBankNames());
        housingFinanceService.registerData(supplyStatusData, banks);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/banks/yearly/amount")
    public ResponseEntity<YearlyTotalAmountsResponse> getYearlyTotalAmountEachBank() {
        YearlyTotalAmountsResponse response = yearlyTotalAmountsService.tally();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/banks/yearly/amount/largest")
    public ResponseEntity<LargestAmountResponse> getLargestAmount() {
        LargestAmountResponse response = housingFinanceService.findLargestOfAll();
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/banks/exchange/yearly/amount")
    public ResponseEntity<BankSupportAmountResponse> getLagestAndSmallest() {
        BankSupportAmountResponse response = housingFinanceService.findLargestAndSmallest();
        return ResponseEntity.ok().body(response);
    }
}
