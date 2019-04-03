package com.riverway.housingfinance.support;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Component
public class HousingFinancePreprocessor {

    private Pattern pattern = Pattern.compile("\"([\\d,]+?)\"");
    private static int BASE_YEAR_2016 = 2016;

    public List<String> readCsv(InputStream in) {
        List<String> lines = new ArrayList<>();
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(in, "x-windows-949"));
            String row = input.readLine();
            lines.add(row);
            while ((row = input.readLine()) != null) {
                log.debug("이전 데이터: {}", row);
                lines.add(verifyBaseYear(row));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lines;
    }

    public int[] cleanseData(String row) {
        String[] parsedData = row.split(",");
        log.debug("완전한 데이터 : {}", parsedData);
        return filterEmptyData(parsedData);
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

    public int[] filterEmptyData(String[] row) {
        return Arrays.stream(row)
                .filter(data -> data.length() > 0)
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
