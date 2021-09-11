package fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.assignment_3.databinding.HomeBinding;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit.RetroFitClient;
import retrofit.RetroFitInterface;
import retrofit.SearchResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Fragment {

    private HomeBinding binding;
    private String Location = "";

    public Home() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = binding.inflate(inflater, container, false);

        SharedPreferences sharedPref =  getActivity().getPreferences(Context.MODE_PRIVATE);
        boolean CheckExists = sharedPref.contains("CityCountry");
        Log.d("LocationData", String.valueOf(CheckExists));
        if(CheckExists)
        {
            Location = sharedPref.getString("CityCountry","Melbourne, Au");
            Log.d("LocationData",Location);
        }
        getWeather(Location);
        View view = binding.getRoot();
        return view;

    }

    private void getWeather(String name)
    {
        RetroFitInterface rfi = RetroFitClient.getRetrofitService().create(RetroFitInterface.class);
        Log.d("LocationData",name);

        Call<SearchResponse> call = rfi.getWeatherInfo(name);

        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {

                Log.d("Retrofit",response.body().getItems().getTemp());
                Log.d("Retrofit",response.body().getItems().getHumidity());
                Log.d("Retrofit",response.body().getItems().getPressure());
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                String formattedDate = df.format(c);
                binding.date.setText("Date: "+formattedDate);
                binding.tempoData.setText("Temperature in celsius: "+response.body().getItems().getTemp());
                binding.humidityData.setText("Humidity %: "+response.body().getItems().getHumidity());
                binding.pressureData.setText("Pressure: "+response.body().getItems().getPressure());

            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Log.d("Retrofit","Fail");

            }
        });

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
