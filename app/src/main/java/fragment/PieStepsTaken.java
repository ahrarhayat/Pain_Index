package fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.assignment_3.R;
import com.example.assignment_3.databinding.PieStepsTakenBinding;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import entity.PainEntryData;
import viewmodel.PainEntryViewModel;

public class PieStepsTaken extends Fragment {

    private PieStepsTakenBinding binding;
    private PainEntryViewModel painEntryViewModel;
    private List<PainEntryData> allData;
    private PieChart pieChart;



    public PieStepsTaken()
    {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = binding.inflate(inflater, container, false);
        pieChart = binding.piePainChart;

        pieChart.setRotationEnabled(true);
        pieChart.setCenterText("Status of completed steps");
        pieChart.setCenterTextSize(10);
        pieChart.setDrawEntryLabels(true);
        Description description = new Description();
        description.setText("The pie chart shows the amount of steps left and the amount completed");
        pieChart.setDescription(description);
//get all the data and put them on a list and only use certain values from those instances there
        painEntryViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().
                getApplication()).create(PainEntryViewModel.class);
        allData = new ArrayList<>();
        painEntryViewModel.getAllPainEntry().observe(getViewLifecycleOwner(), new
                Observer<List<PainEntryData>>() {
                    @Override
                    public void onChanged(@Nullable final List<PainEntryData> painEntry) {
                        for (PainEntryData painEntryData : painEntry) {
                            allData.add(painEntryData);
                            //Log.d("Reps",allData.get(painEntryData.uid-1).getPainLocation());
                        }
                        SharedPreferences sharedPref =  getActivity().getPreferences(Context.MODE_PRIVATE);
                        String targetSteps = sharedPref.getString("Set Goals", String.valueOf(200000));
                        Log.d("Target",targetSteps);
                        float target = Float.parseFloat(targetSteps);
                        boolean CheckExists = sharedPref.contains("ID");

                        int id = 0;

                        if (CheckExists)
                        {
                            id = sharedPref.getInt("ID",1);
                            if (id == 1)
                            {
                                PainEntryData entryData = allData.get(0);
                                String stepsTakenS = entryData.stepTaken;
                                float stepsTaken =  Float.parseFloat(stepsTakenS);
                                addAllData(target,stepsTaken);
                            }
                            else
                            {
                                PainEntryData entryData = allData.get(id-1);
                                String stepsTakenS = entryData.stepTaken;
                                float stepsTaken =  Float.parseFloat(stepsTakenS);
                                addAllData(target,stepsTaken);
                            }

                        }



                    }

                });




        View view = binding.getRoot();
        return view;
    }

    private void addAllData(float target, float taken) {
        ArrayList<String> labels = new ArrayList<>();
        labels.add("Steps Remaining");
        labels.add("Steps Completed");

        ArrayList<Float> yLabels = new ArrayList<>();
        float remaining = target-taken;

        yLabels.add(remaining);
        yLabels.add(taken);

        int i = 0;
        ArrayList<PieEntry> stepEntries = new ArrayList<>();
        for (float x:yLabels) {
            stepEntries.add(i ,new PieEntry(x));
            stepEntries.get(i).setLabel(labels.get(i));
            //painEntries.get(i).setIcon(android.R.drawable.btn_star);
            i = i+1;
        }

        PieDataSet pieDataSet = new PieDataSet(stepEntries, "Steps completion allocation");
        pieDataSet.setValueTextSize(8);
        pieDataSet.setSliceSpace(4);




        //colors
        ArrayList<Integer> colors = new ArrayList<>();


        colors.add(Color.YELLOW);
        colors.add(Color.GREEN);
        pieDataSet.setColors(colors);

        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);

        //create pie data object

        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextSize(20f);
        pieData.setValueTextColor(ContextCompat.getColor(getActivity(), R.color.black));
        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setData(pieData);
        pieChart.invalidate();

    }

    @Override
    public void onDestroyView() {super.onDestroyView();binding = null;}
}
