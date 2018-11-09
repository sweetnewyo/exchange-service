package ru.gorbunov;

import ru.gorbunov.dataTransferObjects.ApiResponse;
import ru.gorbunov.services.CacheService;
import ru.gorbunov.services.ExchangeRateService;
import ru.gorbunov.services.WebService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by AnGorbunov on 15.09.2017.
 */
public class MainApp {
    public static void main(String[] args) throws IOException {

        ArrayList<String> currency = new ArrayList<>(Arrays.asList("AUD", "GBP", "KRW", "SEK", "BGN", "HKD", "MXN", "SGD", "BRL", "HRK",
                "MYR", "THB", "CAD", "HUF", "NOK", "TRY", "CHF", "IDR", "HZD", "USD", "CNY",
                "ILS", "PHP", "ZAR", "CZK", "INR", "PLN", "EUR", "DKK", "JPY", "RON", "RUB"));

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter from currency");
        String from = reader.readLine();

        while (!currency.contains(from)) {
            System.out.println("Try again, this currency is not valid");
            System.out.println("Enter from currency");
            from = reader.readLine();
        }

        System.out.println("Enter to currency");
        String to = reader.readLine();

        while (!currency.contains(to)) {
            System.out.println("Try again, this currency is not valid");
            System.out.println("Enter to currency");
            to = reader.readLine();
        }

        WebService webService = new WebService();
        CacheService cacheService = new CacheService("Rates.txt");

        try {
            ExchangeRateService exchangeRateService = new ExchangeRateService(from, to, webService, cacheService);

            ExecutorService service = Executors.newCachedThreadPool();

            Future<ApiResponse> rate = service.submit(exchangeRateService);
            ApiResponse response = rate.get();
            System.out.println(response.getBase() + " => " + response.getRates().getName() + " : "
                    + response.getRates().getRate());
            service.shutdown();
            reader.close();
        } catch (Exception e) {
            System.out.println("Error please try again later");
        }
    }

}