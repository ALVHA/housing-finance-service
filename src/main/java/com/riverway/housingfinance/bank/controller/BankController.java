package com.riverway.housingfinance.bank.controller;

import com.riverway.housingfinance.bank.domain.Bank;
import com.riverway.housingfinance.bank.service.BankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/housing/finance")
public class BankController {

    private final Logger log = LoggerFactory.getLogger(BankController.class);

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping("/banks")
    public ResponseEntity<List<Bank>> showInstitutions() {
        List<Bank> banks = bankService.findListOfBank();
        log.debug("뱅크 리스트 {}", banks);
        return ResponseEntity.ok().body(banks);
    }
}
