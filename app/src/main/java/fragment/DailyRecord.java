package fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import androidx.recyclerview.widget.RecyclerView;

import com.example.assignment_3.databinding.DailyRecordBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import adapter.RecyclerViewAdapter;
import entity.PainEntryData;
import viewmodel.PainEntryViewModel;

public class DailyRecord extends Fragment {

    private DailyRecordBinding binding;
    private PainEntryViewModel painEntryViewModel;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<PainEntryData> list;
    private RecyclerViewAdapter adapter;



    public DailyRecord()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = binding.inflate(inflater, container, false);
        View view = binding.getRoot();

        list = new ArrayList<PainEntryData>();
        PainEntryData painEntryData = new PainEntryData(0,"Pain Location","Pain Intensity","Mood Level","Steps Taken", "Date","Temperature",
                "Humidity","Pressure","Email");
        list.add(painEntryData);

        painEntryViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().
                getApplication()).create(PainEntryViewModel.class);



        painEntryViewModel.getAllPainEntry().observe(getViewLifecycleOwner(), new
                Observer<List<PainEntryData>>() {
                    @Override
                    public void onChanged(@Nullable final List<PainEntryData> painEntry) {
                        String allPainEntry = "";
                        for (PainEntryData painEntryData : painEntry) {
                            list.add(painEntryData);
                            //Log.d("PainEntryData", list.get(painEntryData.uid - 1).painLocation);
                            //Log.d("PainEntryData", list.get(painEntryData.uid - 1).painIntensityLevel);
                            //Log.d("PainEntry", String.valueOf(painEntryData.uid));
                            /*SharedPreferences sharedPref =  getActivity().getPreferences(Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            int id = painEntryData.uid;
                            editor.putInt("ID",painEntryData.uid);
                            editor.apply();*/
                            //Log.d("PainEntryID", String.valueOf(id));
                        }

                        adapter = new RecyclerViewAdapter(list);
                        binding.recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
                        binding.recyclerView.setAdapter(adapter);
                        layoutManager = new LinearLayoutManager(getContext());
                        binding.recyclerView.setLayoutManager(layoutManager);


                    }
                });


        return view;
    }

    @Override
    public void onDestroyView() {super.onDestroyView();binding = null;}

}
