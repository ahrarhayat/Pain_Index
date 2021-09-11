package worker;

import android.app.Application;
import android.content.Context;
import android.nfc.Tag;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.DialogTitle;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mapbox.mapboxsdk.log.LoggerDefinition;

import java.util.HashMap;

import repository.PainEntryRepository;
import viewmodel.PainEntryViewModel;

public class UploaderWorker extends Worker {



    private static final String TAG = "Upload Worker";
    public UploaderWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }




    @NonNull
    @Override
    public Result doWork() {

        //DO firebase stuff here

        Data inputData = getInputData();

        int number = inputData.getInt("number", -1);
        Log.d(TAG,"do work: number: "+number);

        String [] entries = new String[10];

        entries = inputData.getStringArray("DatabaseData");

        Log.d("WorkManager","Work Started");

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference().child("Pain Entries");
            String id = entries[0];
            HashMap<String,String> map = new HashMap<>();
            map.put("UID",entries[0]);
            map.put("Pain Intensity",entries[1]);
            map.put("Pain Location",entries[2]);
            map.put("Humidity",entries[3]);
            map.put("Mood Level",entries[4]);
            map.put("Steps Taken",entries[5]);
            map.put("Temperature",entries[6]);
            map.put("Pressure",entries[7]);
            map.put("Date",entries[8]);
            map.put("UserEmail", entries[9]);
            myRef.child("ID: "+ id).setValue(map);
            Log.d("WorkManager","All values added to firebase");
            Log.d("WorkManager","UID" + entries[0]);
            Log.d("WorkManager","Pain Intensity" + entries[1]);
            Log.d("WorkManager","Pain Location" + entries[2]);
            Log.d("WorkManager","Humidity" + entries[3]);
            Log.d("WorkManager","Mood Level" + entries[4]);
            Log.d("WorkManager","Steps Taken" + entries[5]);
            Log.d("WorkManager","Temperature" + entries[6]);
            Log.d("WorkManager","Pressure" + entries[7]);
            Log.d("WorkManager","Date" + entries[8]);
            Log.d("WorkManager","UserEmail" + entries[9]);
            Log.d("WorkManager","Work Ended");

        return Result.success();
    }
}
