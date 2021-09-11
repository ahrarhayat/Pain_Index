package fragment;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.assignment_3.MainActivity2;
import com.example.assignment_3.databinding.PainEntryBinding;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import adapter.RecyclerViewAdapter;
import entity.PainEntryData;
import retrofit.RetroFitClient;
import retrofit.RetroFitInterface;
import retrofit.SearchResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import viewmodel.PainEntryViewModel;

public class PainDataEntry extends Fragment {

    private PainEntryBinding binding;
    private PainEntryViewModel painEntryViewModel;
    private String temp = " ";
    private String humid = " ";
    private String pressure = " ";
    private String email = "";
    private String location = "";
    private int ID = 0;


    public PainDataEntry() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = binding.inflate(inflater, container, false);
        createNotificationChannel();

        SharedPreferences sharedPref =  getActivity().getPreferences(Context.MODE_PRIVATE);
        boolean CheckExists = sharedPref.contains("CityCountry");
        if(CheckExists)
        {
            location = sharedPref.getString("CityCountry","Melbourne, AU");
        }
        getWeather(location);
        MainActivity2 activity = (MainActivity2) getActivity();
        email = activity.getUserEmail();
        
        painEntryViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().
                getApplication()).create(PainEntryViewModel.class);


        painEntryViewModel.getAllPainEntry().observe(getViewLifecycleOwner(), new
                Observer<List<PainEntryData>>() {
                    @Override
                    public void onChanged(@Nullable final List<PainEntryData> painEntry) {

                        for (PainEntryData painEntryData : painEntry) {

                            Log.d("Testy", String.valueOf(painEntryData.uid));
                            SharedPreferences sharedPref =  getActivity().getPreferences(Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            if(painEntryData.uid > ID)
                            {
                                ID = painEntryData.uid;
                            }
                            editor.putInt("ID",ID);
                            editor.apply();

                        }

                    }
                });

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String painLevel = binding.painSpinner.getSelectedItem().toString();
                String painLoc = binding.painLoc.getSelectedItem().toString();
                String mood = binding.selectMood.getSelectedItem().toString();
                String numOfSteps = binding.numOfSteps.getText().toString();
                String numOfStepsAsGoal = binding.numOfStepsGoal.getText().toString();
                if (!painLevel.isEmpty() && painLevel!= null && !painLoc.isEmpty() && painLoc!= null &&

                !mood.isEmpty() && mood!= null && !numOfSteps.isEmpty() && numOfSteps!= null)
                {
                    Snackbar.make(v, "Save successful!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    binding.painSpinner.setVisibility(View.INVISIBLE);
                    binding.selectPainT.setText("Pain Level: " + painLevel);
                    binding.painLoc.setVisibility(View.INVISIBLE);
                    binding.selectPainLocT.setText("Pain Location: " + painLoc);
                    binding.selectMood.setVisibility(View.INVISIBLE);
                    binding.selectMoodT.setText("Mood: " + mood);
                    binding.numOfSteps.setVisibility(View.INVISIBLE);
                    binding.numOfStepsT.setText("Number of steps: " + numOfSteps);
                    //binding.numOfStepsGoal.setText("Number of steps as goal:" +numOfStepsAsGoal );
                    binding.editBtn.setVisibility(View.VISIBLE);
                    binding.numOfStepsGoal.setVisibility(View.INVISIBLE);


                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                    String date = df.format(c);
                    Log.d("PainEntryTemp",temp);
                    PainEntryData painEntryData = new PainEntryData(painLevel,painLoc,mood,numOfSteps,date,temp,humid,pressure,email);
                    SharedPreferences sharedPref =  getActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    String fetchedDate = sharedPref.getString("Date of entry","");
                    String prevEmail = sharedPref.getString("EmailLastRecord","");


                    if (fetchedDate.equals(date) && email.equals(prevEmail))
                    {
                        Log.d("EmailCheck", prevEmail);
                        Snackbar.make(v, "You have already made an entry today", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else {
                        editor.putString("EmailLastRecord",email);
                        painEntryViewModel.insert(painEntryData);
                        editor.putString("Date of entry",date);
                        editor.putString("Set Goals",numOfStepsAsGoal);
                        editor.apply();
                        binding.saveBtn.setVisibility(View.INVISIBLE);
                    }



                }

                else {
                    Snackbar.make(v, "Please enter correct values!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }



            }
        });
        binding.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.saveBtn.setVisibility(View.INVISIBLE);
                binding.painSpinner.setVisibility(View.VISIBLE);
                binding.selectPainT.setText("Pain Level: ");
                binding.painLoc.setVisibility(View.VISIBLE);
                binding.selectPainLocT.setText("Pain Location: ");
                binding.selectMood.setVisibility(View.VISIBLE);
                binding.selectMoodT.setText("Mood: ");
                binding.numOfSteps.setVisibility(View.VISIBLE);
                binding.numOfStepsT.setText("Number of steps: ");
                binding.editSaveBtn.setVisibility(View.VISIBLE);
                binding.editBtn.setVisibility(View.INVISIBLE);
                binding.numOfStepsGoals.setText("Number of steps goals: ");
                binding.numOfStepsGoal.setVisibility(View.VISIBLE);


            }
        });

        binding.editSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                boolean CheckExists = sharedPref.contains("ID");
                int id = 0;

                if (CheckExists)
                {
                    id = sharedPref.getInt("ID",0);
                }

                String painLevel = binding.painSpinner.getSelectedItem().toString();
                String painLoc = binding.painLoc.getSelectedItem().toString();
                String mood = binding.selectMood.getSelectedItem().toString();
                String numOfSteps = binding.numOfSteps.getText().toString();
                if (!painLevel.isEmpty() && painLevel!= null && !painLoc.isEmpty() && painLoc!= null &&

                        !mood.isEmpty() && mood!= null && !numOfSteps.isEmpty() && numOfSteps!= null)
                {

                    if (android.os.Build.VERSION.SDK_INT >=
                            android.os.Build.VERSION_CODES.N) {
                        CompletableFuture<PainEntryData> customerCompletableFuture =
                                painEntryViewModel.findByIDFuture(id);
                        customerCompletableFuture.thenApply(painEntryData1 -> {
                            if (painEntryData1 != null) {
                                painEntryData1.painIntensityLevel = painLevel;
                                painEntryData1.painLocation = painLoc;
                                painEntryData1.moodLevel = mood;
                                painEntryData1.stepTaken = numOfSteps;
                                painEntryData1.temperature = temp;
                                painEntryData1.humidity = humid;
                                painEntryData1.pressure = pressure;
                                painEntryData1.userEmail = email;
                                painEntryViewModel.update(painEntryData1);
                                Snackbar.make(v, "Edit successful!" , Snackbar.LENGTH_LONG)
                                        .setAction("Action", null).show();

                            }

                            else {

                            }
                            return painEntryData1;

                        });
                    }
                }

                else {
                    Snackbar.make(v, "Please enter correct values!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                binding.saveBtn.setVisibility(View.VISIBLE);
                binding.editBtn.setVisibility(View.VISIBLE);
                binding.editSaveBtn.setVisibility(View.INVISIBLE);

            }
        });



        binding.setDailyTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.timePicker1.setVisibility(View.VISIBLE);
                binding.setDailyTime.setVisibility(View.INVISIBLE);
                binding.saveDailyTime.setVisibility(View.VISIBLE);



            }
        });

        binding.saveDailyTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour =  binding.timePicker1.getHour();
                int minute = binding.timePicker1.getMinute()-3;
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                binding.timePicker1.getMinute();
                binding.timePicker1.setVisibility(View.INVISIBLE);
                binding.saveDailyTime.setVisibility(View.INVISIBLE);
                binding.setDailyTime.setVisibility(View.VISIBLE);
                //take times values from here
                Intent intent = new Intent(getContext(), NotificationBroadcast.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),1000 * 60 , pendingIntent);
                Snackbar.make(v, "Alarm Set!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });


        View view = binding.getRoot();
        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ahrarChannel";
            String description = "channelForReminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("ahrar", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }


    }

    private void getWeather(String name)
    {
        RetroFitInterface rfi = RetroFitClient.getRetrofitService().create(RetroFitInterface.class);


        Call<SearchResponse> call = rfi.getWeatherInfo(name);

        call.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {

                Log.d("Retrofit Pain",response.body().getItems().getTemp());
                Log.d("Retrofit Pain",response.body().getItems().getHumidity());
                Log.d("Retrofit Pain",response.body().getItems().getPressure());
                temp = response.body().getItems().getTemp();
                humid = response.body().getItems().getHumidity();
                pressure = response.body().getItems().getPressure();

            }


            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Log.d("Retrofit Pain","Fail");

            }
        });




    }

}
