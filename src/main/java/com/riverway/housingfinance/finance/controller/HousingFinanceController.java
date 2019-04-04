package com.riverway.housingfinance.finance.controller;

import com.riverway.housingfinance.finance.service.HousingFinanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/housing/finance")
public class HousingFinanceController {

    private final HousingFinanceService housingFinanceService;

    public HousingFinanceController(HousingFinanceService housingFinanceService) {
        this.housingFinanceService = housingFinanceService;
    }

    @PostMapping("")
    public ResponseEntity<Void> uploadCsvFile(MultipartFile csvFile) {
        log.debug("파일 이름 : {}", csvFile.getOriginalFilename());
        housingFinanceService.registerData(csvFile);
        //uri 수정 필요
        URI uri = URI.create("/api/housing/finance");
        return ResponseEntity.created(uri).build();
    }
}
