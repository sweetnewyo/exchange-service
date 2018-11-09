package ru.gorbunov.services;

import ru.gorbunov.dataTransferObjects.ApiResponse;
import ru.gorbunov.dataTransferObjects.RateObject;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Created by AnGorbunov on 15.09.2017.
 */
public class CacheService {

    private String path;

    public CacheService(String path) throws IOException {
        File file = new File(path);
        file.createNewFile();
        this.path = path;
    }

    public void writeToFile(ApiResponse rate) {
        String rateString = rate.getBase() + " " + rate.getRates().getName() + " " + rate.getDate() + " " + rate.getRates().getRate() + "\n";
        try {
            Files.write(Paths.get(path), rateString.getBytes(), StandardOpenOption.APPEND);
        } catch (Exception e) {
            System.out.println("Can't write rate to file");
        }
    }


    public ApiResponse getExchangeRateFromFile(String from, String to) {
        List<String> lines;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        ApiResponse rate;
        LocalDate now = LocalDate.now();

        try {
            if(Files.size(Paths.get(path))==0){
                return null;
            }
            lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
            for (String line : lines) {
                String[] s = line.split(" ");
                LocalDate rateDate = LocalDate.parse(s[2], formatter);
                Long daysPeriod = ChronoUnit.DAYS.between(rateDate, now);
                if (s[0].equals(from) && s[1].equals(to) && daysPeriod.equals(0L)) {
                    rate = new ApiResponse(s[0], new RateObject(s[1], Double.parseDouble(s[3])), s[2]);
                    return rate;
                }
            }
        } catch (Exception e) {
            System.out.println("Can't get rate from file");
        }
        return null;
    }
}
