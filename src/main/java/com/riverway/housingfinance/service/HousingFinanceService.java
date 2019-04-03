package com.riverway.housingfinance.service;

import com.riverway.housingfinance.support.HousingFinancePreprocessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

@Slf4j
@Service
public class HousingFinanceService {

    private final HousingFinancePreprocessor preprocessor;
    private final BankService bankService;

    public HousingFinanceService(HousingFinancePreprocessor preprocessor, BankService bankService) {
        this.preprocessor = preprocessor;
        this.bankService = bankService;
    }

    //TODO title 따로 빼와서 연도 월 컬럼을 지우고 Banks를 얻어온후에 title과 같이 이름대로 정렬 하고 MonthlyFinance 객체를 완성한다. 그리고 저장
    public void registerData(InputStream input) {
        List<String> data = preprocessor.readCsv(input);
        String title = data.get(0);
        log.debug("title : {}", title);
//        List<Bank> banks = bankService.findByNames(preprocessor.cleanseData(title));
    }
}
