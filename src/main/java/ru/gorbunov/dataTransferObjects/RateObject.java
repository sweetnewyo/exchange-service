package ru.gorbunov.dataTransferObjects;

/**
 * Created by AnGorbunov on 15.09.2017.
 */
public class RateObject {
    private String name;
    private double rate;

    public RateObject(String name, double rate){
        this.name = name;
        this.rate = rate;
    }

    public String getName() {
        return name;
    }

    public double getRate() {
        return rate;
    }
}
