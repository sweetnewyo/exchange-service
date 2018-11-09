package ru.gorbunov.services;

import ru.gorbunov.dataTransferObjects.ApiResponse;

import java.util.concurrent.Callable;

/**
 * Created by AnGorbunov on 15.09.2017.
 */
public class ExchangeRateService implements Callable<ApiResponse>{
    private WebService webService;
    private CacheService cacheService;
    private String from;
    private String to;

    public ExchangeRateService(String from, String to, WebService webService, CacheService cacheService) {
        this.webService = webService;
        this.cacheService = cacheService;
        this.from = from;
        this.to = to;
    }


    public ApiResponse getExchangeRate(){
        ApiResponse rateFromFile = cacheService.getExchangeRateFromFile(from, to);
        ApiResponse rate;
        if (rateFromFile != null) {
            System.out.println("from cache");
            rate = rateFromFile;
        } else {
            ApiResponse rateFromInternet = webService.getCurrencyRate(from, to);
            cacheService.writeToFile(rateFromInternet);
            System.out.println("from internet");
            rate = rateFromInternet;
        }
        return rate;
    }

    @Override
    public ApiResponse call() throws Exception {
        return getExchangeRate();
    }
}
