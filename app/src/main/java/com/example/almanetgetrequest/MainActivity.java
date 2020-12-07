package com.example.almanetgetrequest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
{

    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textViewResult = findViewById(R.id.text_view_result);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://almanetapi.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        AlmaNetAPI almaNetAPI = retrofit.create(AlmaNetAPI.class);

        Call<List<GPS>> call = almaNetAPI.getGPS();

        call.enqueue(new Callback<List<GPS>>()
        {
            @Override
            public void onResponse(Call<List<GPS>> call, Response<List<GPS>> response) {
                // if response failed display error code
                if(!response.isSuccessful()){
                    textViewResult.setText("Code: " + response.code());
                    return;
                }

                // if response succeeded display message body
                List<GPS> gps = response.body();

                // Build content
                for(GPS gps1 : gps){
                    String content = "";
                    content += "Longitude: " + gps1.getLongitude() + "\n";
                    content += "Latitude: " + gps1.getLatitude();

                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<GPS>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });

    }
}