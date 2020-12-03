package com.example.almanetgetrequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AlmaNetAPI
{
    // api/values je tu ker dodamo to na koncu BaseUrl
    // Torej ce imamo: https://almanetapi.azurewebsites.net/api/values
    // Potem je base url: https://almanetapi.azurewebsites.net/
    // Dodamo api/values
    // Dobimo: https://almanetapi.azurewebsites.net/api/values
    @GET("api/values")
    Call<List<GPS>> getGPS();
}
