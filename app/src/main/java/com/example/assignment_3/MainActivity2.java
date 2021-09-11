package com.example.assignment_3;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.assignment_3.databinding.ActivityMain2Binding;
import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import adapter.RecyclerViewAdapter;
import entity.PainEntryData;
import viewmodel.PainEntryViewModel;
import worker.UploaderWorker;

public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding;
    private AppBarConfiguration mAppBarConfiguration;
    private String locationgData = "";
    private String userEmail = "";
    private PainEntryViewModel painEntryViewModel;
    private ArrayList<PainEntryData> list = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //setSupportActionBar(binding.appBar.toolbar);
        userEmail= getIntent().getStringExtra("Email");
        String [] listArray = new String[10];


        painEntryViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(this.
                getApplication()).create(PainEntryViewModel.class);

        Log.d("Workman","It started");
        painEntryViewModel.getAllPainEntry().observe(this, new
                Observer<List<PainEntryData>>() {
                    @Override
                    public void onChanged(@Nullable final List<PainEntryData> painEntry) {
                        Log.d("PainEntryData", "Change happened");
                        String allPainEntry = "";
                        for (PainEntryData painEntryData : painEntry) {
                            list.add(painEntryData);
                            //Log.d("PainEntryData", list.get(painEntryData.uid - 1).painLocation);
                            //Log.d("PainEntryData", list.get(painEntryData.uid - 1).painIntensityLevel);
                            Log.d("PainEntry", String.valueOf(painEntryData.uid));
                            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            int id = painEntryData.uid;
                            editor.putInt("ID",painEntryData.uid);
                            editor.apply();
                            Log.d("PainEntryID", String.valueOf(id));

                        }

                        Log.d("Workman","Here it starts");
                        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);

                        boolean CheckExists = sharedPref.contains("ID");
                        if(CheckExists)
                        {


                        int idS = sharedPref.getInt("ID",0);

                        if (idS==1)
                        {
                            idS=0;
                        }
                        else {
                            idS = idS -1;
                        }

                            int id = list.get(idS).uid;
                            listArray[0] = Integer.toString(id);
                            listArray[1] = list.get(idS).painIntensityLevel;
                            listArray[2] = new String (list.get(idS).painLocation);
                            Log.d("Workman",list.get(idS).painLocation);
                            listArray[3] = new String ( list.get(idS).humidity);
                            listArray[4] = new String (list.get(idS).moodLevel);
                            listArray[5] = new String (list.get(idS).stepTaken);
                            listArray[6] = new String (list.get(idS).temperature);
                            listArray[7] = new String (list.get(idS).pressure);
                            listArray[8] = new String (list.get(idS).date);
                            listArray[9] = new String (list.get(idS).userEmail);

/*

                            Calendar rightNow = Calendar.getInstance();
                            int currentHourIn24Format = rightNow.get(Calendar.HOUR_OF_DAY);
                            Log.d("CurrentHour","Curr "+currentHourIn24Format);
                            long diff = 22 - currentHourIn24Format;
                            Log.d("Diff","Diff"+diff);
                            long diffMins = diff*60;
*/


                            Data data = new Data.Builder().putStringArray("DatabaseData",listArray).build();


                                Constraints constraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();


                                    PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(UploaderWorker.class, 1,
                                            TimeUnit.DAYS).setInputData(data).setConstraints(constraints)
                                            .addTag("Periodic").build();
                                    WorkManager.getInstance(MainActivity2.this).enqueue(periodicWorkRequest);



                               /*if (diff == 0){

                                        OneTimeWorkRequest periodicWorkRequest = new OneTimeWorkRequest.Builder(UploaderWorker.class).
                                                setInputData(data).setInitialDelay(diffMins,TimeUnit.MINUTES).setConstraints(constraints)
                                                .addTag("One Time").build();
                                        WorkManager.getInstance(MainActivity2.this).enqueue(periodicWorkRequest);
                                        }*/
                               

                        }


                    }
                });





        //Data data = new Data.

        
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_daily_record,
                R.id.nav_pain_data_entry,
                R.id.nav_maps_options,R.id.nav_reports,R.id.nav_home).setOpenableLayout(binding.drawerLayout)
                .build();

        FragmentManager fragmentManager= getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment)
                fragmentManager.findFragmentById(R.id.nav_host_fragment);

        NavController navController = navHostFragment.getNavController();
        //Sets up a NavigationView for use with a NavController.
        NavigationUI.setupWithNavController(binding.navView, navController);
        //Sets up a Toolbar for use with a NavController.
        NavigationUI.setupWithNavController(binding.appBar.toolbar,navController,
                mAppBarConfiguration);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.overflow_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        View view = binding.getRoot();
        int id = item.getItemId();
        switch (id){
            case R.id.option_1:
                SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.remove("Email");
                editor.apply();
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                intent.putExtra("Logout", true);
                startActivity(intent);
                return true;
            default:
                Toast.makeText(getApplicationContext(),"Item 3 Selected",Toast.LENGTH_LONG).show();
                return super.onOptionsItemSelected(item);
        }
    }
    public String getUserEmail()
    {
        return userEmail;
    }


}