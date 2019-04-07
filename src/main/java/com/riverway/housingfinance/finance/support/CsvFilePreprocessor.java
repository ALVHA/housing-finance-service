package com.riverway.housingfinance.finance.support;

import com.riverway.housingfinance.bank.BankName;
import com.riverway.housingfinance.support.exception.ErrorMessage;
import com.riverway.housingfinance.support.exception.FailedReadCsvFileException;
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

    public HousingFinanceFactory read(MultipartFile file) {
        try (InputStream in = file.getInputStream()) {
            BufferedReader input = new BufferedReader(new InputStreamReader(in, "x-windows-949"));

            List<BankName> bankNames = parseTitle(input.readLine());
            List<String> body = readBody(input);
            log.debug("body: {} ", body);
            return new HousingFinanceFactory(bankNames, body);
        } catch (IOException e) {
            e.printStackTrace();
            log.info("{}", ErrorMessage.READ_FAILED);
            throw new FailedReadCsvFileException(ErrorMessage.READ_FAILED);
        }
    }

//    public List<String> read(MultipartFile file) {
//        try (InputStream in = file.getInputStream()) {
//            BufferedReader input = new BufferedReader(new InputStreamReader(in, "x-windows-949"));
//
//            List<String> housingFinance = new ArrayList<>();
//            housingFinance.add(input.readLine());
//            housingFinance.addAll(readBody(input));
//            return housingFinance;
//        } catch (IOException e) {
//            e.printStackTrace();
//            log.info("{}", ErrorMessage.READ_FAILED);
//            throw new FailedReadCsvFileException(ErrorMessage.READ_FAILED);
//        }
//    }

    public List<BankName> parseTitle(String title) {
        String[] titles = cleanseData(title);
        List<BankName> bankNames = new ArrayList<>();
        for (int i = 2; i < titles.length; i++) {
            bankNames.add(BankName.of(titles[i]));
        }
        return bankNames;
    }

    public String[] cleanseData(String row) {
        String[] parsedData = row.split(",");
        return filterEmptyData(parsedData);
    }

    public String[] filterEmptyData(String[] row) {
        return Arrays.stream(row)
                .filter(data -> data.length() > 0)
                .toArray(String[]::new);
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

    public static int[] filterEmptyDataToInt(String row) {
        return Arrays.stream(row.split(","))
                .filter(data -> data.length() > 0)
                .mapToInt(string -> Integer.parseInt(string))
                .toArray();
    }

}
