package com.riverway.housingfinance.finance.controller;

import com.riverway.housingfinance.bank.domain.Bank;
import com.riverway.housingfinance.bank.service.BankService;
import com.riverway.housingfinance.finance.service.HousingFinanceService;
import com.riverway.housingfinance.finance.support.CsvFilePreprocessor;
import com.riverway.housingfinance.finance.dto.SupplyStatusByYearResponse;
import com.riverway.housingfinance.finance.dto.SupplyStatusData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/housing/finance")
public class HousingFinanceController {

    private final BankService bankService;
    private final HousingFinanceService housingFinanceService;
    private final CsvFilePreprocessor preprocessor;

    public HousingFinanceController(BankService bankService
            , HousingFinanceService housingFinanceService
            , CsvFilePreprocessor preprocessor) {
        this.bankService = bankService;
        this.housingFinanceService = housingFinanceService;
        this.preprocessor = preprocessor;
    }

    @PostMapping("")
    public ResponseEntity<Void> uploadCsvFile(MultipartFile csvFile) {
        log.debug("파일 이름 : {}", csvFile.getOriginalFilename());
        SupplyStatusData supplyStatusData = preprocessor.read(csvFile);
        List<Bank> banks = bankService.findByNames(supplyStatusData.getBankNames());
        housingFinanceService.registerData(supplyStatusData, banks);
        //uri 수정 필요
        URI uri = URI.create("/api/housing/finance");
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/banks/yearly/amount")
    public ResponseEntity<SupplyStatusByYearResponse> getYearlyDataFromBank() {
        SupplyStatusByYearResponse response = housingFinanceService.findSupplyStatus();
        return ResponseEntity.ok().body(response);
    }
}
