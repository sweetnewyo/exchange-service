package ru.gorbunov.dataTransferObjects;

/**
 * Created by AnGorbunov on 15.09.2017.
 */
public class ApiResponse {
    private String base;
    private RateObject rates;
    private String date;


    public ApiResponse(String base, RateObject rates, String date) {
        this.base = base;
        this.rates = rates;
        this.date = date;
    }


    public String getBase() {
        return base;
    }

    public RateObject getRates() {
        return rates;
    }

    public String getDate() {
        return date;
    }
}
