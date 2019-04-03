package com.riverway.housingfinance.support;

import com.riverway.housingfinance.domain.BankName;
import com.riverway.housingfinance.domain.SupplyStatusData;
import com.riverway.housingfinance.dto.MonthlyFinanceDto;
import com.riverway.housingfinance.exception.FailedReadCsvFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class HousingFinancePreprocessor {

    private static final int BASE_YEAR_2016 = 2016;
    private Pattern pattern = Pattern.compile("\"([\\d,]+?)\"");

    public SupplyStatusData read(InputStream in) {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(in, "x-windows-949"));
            List<BankName> bankNames = readCsvTitle(input);
            List<String> body = readCsvBody(input);
            return new SupplyStatusData(bankNames, body);
        } catch (IOException e) {
            e.printStackTrace();
            log.info("csv파일을 읽는데 실패하였습니다.");
            throw new FailedReadCsvFile();
        }
    }

    public List<BankName> readCsvTitle(BufferedReader bufferedReader) throws IOException {
        String title = bufferedReader.readLine();
        return parseTitle(title);
    }

    //    public List<MonthlyFinanceDto> readCsvBody(BufferedReader input) throws IOException{
//        List<String> lines = new ArrayList<>();
////        input.lines().map(row -> verifyBaseYear(row))
//        String row = "";
//        while ((row = input.readLine()) != null) {
//            log.debug("이전 데이터: {}", row);
//            lines.add(verifyBaseYear(row));
//        }
//        return convertToMonthlyDataOfBank(parseTitle(title), lines);
//    }
    public List<String> readCsvBody(BufferedReader input) throws IOException {
        List<String> lines = new ArrayList<>();
//        input.lines().map(row -> verifyBaseYear(row))
        String row = "";
        while ((row = input.readLine()) != null) {
            log.debug("이전 데이터: {}", row);
            lines.add(verifyBaseYear(row));
        }
        return lines;
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

    public String[] cleanseData(String row) {
        String[] parsedData = row.split(",");
        log.debug("완전한 데이터 : {}", parsedData);
        return filterEmptyData(parsedData);
    }

    public String[] filterEmptyData(String[] row) {
        return Arrays.stream(row)
                .filter(data -> data.length() > 0)
                .toArray(String[]::new);
    }

    public static int[] filterEmptyDataToInt(String row) {
        return Arrays.stream(row.split(","))
                .filter(data -> data.length() > 0)
                .mapToInt(string -> Integer.parseInt(string))
                .toArray();
    }

    public List<BankName> parseTitle(String title) {
        String[] titles = cleanseData(title);
        List<BankName> bankNames = new ArrayList<>();
        for (int i = 2; i < titles.length; i++) {
            bankNames.add(BankName.of(titles[i]));
        }
        return bankNames;
    }
}
