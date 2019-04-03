package com.riverway.housingfinance.web;

import com.riverway.housingfinance.dto.BankDto;
import com.riverway.housingfinance.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/housing/finance")
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping("/banks")
    public ResponseEntity<List<BankDto>> showInstitutions() {
        List<BankDto> banks = bankService.findListOfBank();
        log.debug("뱅크 리스트 {}", banks);
        return ResponseEntity.ok().body(banks);
    }
}
