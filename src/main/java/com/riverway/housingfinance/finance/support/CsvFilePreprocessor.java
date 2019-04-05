package com.riverway.housingfinance.finance.support;

import com.riverway.housingfinance.bank.BankName;
import com.riverway.housingfinance.finance.FailedReadCsvFileException;
import com.riverway.housingfinance.finance.dto.SupplyStatusData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CsvFilePreprocessor {

    private static final int BASE_YEAR_2016 = 2016;
    private Pattern pattern = Pattern.compile("\"([\\d,]+?)\"");

    public SupplyStatusData read(MultipartFile file) {
        try (InputStream in = file.getInputStream()) {
            BufferedReader input = new BufferedReader(new InputStreamReader(in, "x-windows-949"));

            String title = input.readLine();
            List<String> body = readBody(input);
            return new SupplyStatusData(title, body);
        } catch (IOException e) {
            e.printStackTrace();
            log.info("파일을 읽는데 실패하였습니다.");
            throw new FailedReadCsvFileException();
        }
    }

    public List<String> readBody(BufferedReader input) {
        return input.lines()
                .map(row -> verifyBaseYear(row))
                .collect(Collectors.toList());
    }

    public String verifyBaseYear(String data) {
        String year = data.substring(0, data.indexOf(","));
        if (Integer.valueOf(year) >= BASE_YEAR_2016) {
            return removeCommaBetweenDoubleQuotes(data);
        }
        return data;
    }

    public String removeCommaBetweenDoubleQuotes(String rawData) {
        StringBuilder parsedData = new StringBuilder();
        Matcher matcher = pattern.matcher(rawData);
        int beforeEndIndex = 0;
        while (matcher.find()) {
            String data = matcher.group(1)
                    .replaceAll(",", "");
            parsedData.append(rawData.substring(beforeEndIndex, matcher.start()));
            parsedData.append(data);
            beforeEndIndex = matcher.end();
        }
        parsedData.append(rawData.substring(beforeEndIndex));
        return parsedData.toString();
    }
}
