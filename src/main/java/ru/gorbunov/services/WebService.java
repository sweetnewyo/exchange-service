package ru.gorbunov.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.gorbunov.dataTransferObjects.ApiResponse;
import ru.gorbunov.dataTransferObjects.RateObject;
import ru.gorbunov.deserializer.RatesDeserializer;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by AnGorbunov on 15.09.2017.
 */
public class WebService {
    private Gson gson = new GsonBuilder().registerTypeAdapter(RateObject.class, new RatesDeserializer()).create();

    public ApiResponse getCurrencyRate(String from, String to) {
        HttpURLConnection urlConnection;
        ApiResponse response = null;
        try {
            URL url = getUrl(from, to);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            response = gson.fromJson(reader, ApiResponse.class);
            in.close();
            reader.close();
        } catch (Exception e) {
            System.out.println("Error reading from internet");
        }
        return response;
    }

    private URL getUrl(String from, String to) throws MalformedURLException {
        String url = "http://api.fixer.io/latest?base=" + from + "&symbols=" + to;
        return new URL(url);
    }


}
