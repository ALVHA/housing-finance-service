package com.riverway.housingfinance.web;

import com.riverway.housingfinance.service.HousingFinanceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
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
    public ResponseEntity<Void> analyzeCsvFile(MultipartFile csvFile) {
        log.debug("파일 이름 : {}");
        log.debug("파일 이름 : {}", csvFile.getOriginalFilename());
        try (InputStream input = csvFile.getInputStream()){
            housingFinanceService.registerData(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        URI uri = URI.create("/api/housing/finance");
        return ResponseEntity.created(uri).build();
    }
}
