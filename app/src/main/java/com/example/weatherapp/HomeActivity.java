package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.Retrofit.ApiClient;
import com.example.weatherapp.Retrofit.ApiInterface;
import com.example.weatherapp.Retrofit.Example;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    ImageView search;
    TextView tempText, descText, humidityText, mintempText, maxtempText, pressureText;
    EditText textField;



    Button btnLogout;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        search =findViewById(R.id.search);
        tempText =findViewById(R.id.tempText);
        descText =findViewById(R.id.descText);
        humidityText =findViewById(R.id.humidityText);
        mintempText = findViewById(R.id.mintempText);
        maxtempText = findViewById(R.id.maxtempText);
        pressureText = findViewById(R.id.pressureText);
        textField =findViewById(R.id.textField);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call weather API

                getWeatherData(textField.getText().toString().trim());

            }
        });



        btnLogout = findViewById(R.id.logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent inToMain = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(inToMain);
        }
        });
            }
    private void getWeatherData(String name)
    {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<Example> call=apiInterface.getWeatherData(name);

        call.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {

                tempText.setText("Temp:"+" "+response.body().getMain().getTemp()+" °C");
                descText.setText("Feels Like:"+" "+response.body().getMain().getFeels_like()+" °C");
                humidityText.setText("Humidity:"+" "+response.body().getMain().getHumidity()+"%");
                mintempText.setText("Min temp:"+" "+response.body().getMain().getTemp_min()+" °C");
                maxtempText.setText("Max temp:"+" "+response.body().getMain().getTemp_max()+" °C");
                pressureText.setText("Pressure:"+" "+response.body().getMain().getPressure()+" mb");
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {

            }
        });
    }
    }
