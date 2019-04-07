package com.riverway.housingfinance.finance.controller;

import com.riverway.housingfinance.bank.BankName;
import com.riverway.housingfinance.finance.dto.BankSupportAmountResponse;
import com.riverway.housingfinance.finance.dto.LargestAmountResponse;
import com.riverway.housingfinance.finance.dto.YearlyTotalAmountsResponse;
import com.riverway.housingfinance.finance.service.HousingFinanceService;
import com.riverway.housingfinance.finance.service.YearlyTotalAmountsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/housing/finance")
public class HousingFinanceController {

    private final HousingFinanceService housingFinanceService;
    private final YearlyTotalAmountsService yearlyTotalAmountsService;

    public HousingFinanceController(HousingFinanceService housingFinanceService, YearlyTotalAmountsService yearlyTotalAmountsService) {
        this.housingFinanceService = housingFinanceService;
        this.yearlyTotalAmountsService = yearlyTotalAmountsService;
    }

    @PostMapping("")
    public ResponseEntity<Void> uploadCsvFile(MultipartFile csvFile) {
        log.debug("파일 이름 : {}", csvFile.getOriginalFilename());
        Integer id = housingFinanceService.registerData(csvFile);
        URI uri = URI.create(String.format("/api/housing/finance/%d", id));
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}/banks/yearly/amount")
    public ResponseEntity<YearlyTotalAmountsResponse> getYearlyTotalAmountEachBank(@PathVariable Integer id) {
        YearlyTotalAmountsResponse response = yearlyTotalAmountsService.tally(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}/banks/yearly/amount/largest")
    public ResponseEntity<LargestAmountResponse> getLargestAmount(@PathVariable Integer id) {
        LargestAmountResponse response = housingFinanceService.findLargestOfAll(id);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}/banks/{bankName}/yearly/amount")
    public ResponseEntity<BankSupportAmountResponse> getLagestAndSmallest(@PathVariable Integer id, @PathVariable String bankName) {
        BankName bank = BankName.valueOf(bankName.toUpperCase());
        BankSupportAmountResponse response = housingFinanceService.findLargestAndSmallest(id, bank.getBankName());
        return ResponseEntity.ok().body(response);
    }
}
