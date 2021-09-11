package fragment;

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
import com.example.assignment_3.databinding.ReportPieChartBinding;
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

public class PieReportPain extends Fragment {

    private ReportPieChartBinding binding;
    private PainEntryViewModel painEntryViewModel;
    private ArrayList<PainEntryData> allData ;
    private String [] painLocations = {"Back","Neck","Head","Knee", "Hip","Abdomen","Elbow","Shoulder","Shin","Jaw","Wrist","Facial"};
    private PieChart pieChart;
    private ArrayList<Float> nums = new ArrayList<>();


    public PieReportPain()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        Log.d("Pie", "Got here");
        binding = binding.inflate(inflater, container, false);
        pieChart = binding.piePainChart;
        pieChart.setRotationEnabled(true);
        pieChart.setCenterText("Allocation of pain location");
        pieChart.setCenterTextSize(10);
        pieChart.setDrawEntryLabels(true);
        Description description = new Description();
        description.setText("The pie chart shows the pain locations and their percentages");
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
                        if (!allData.isEmpty())
                        {
                            nums.add(getBackCount(allData));
                            nums.add(getNeckCount(allData));
                            nums.add(getHeadCount(allData));
                            nums.add(getKneeCount(allData));
                            nums.add(getHipCount(allData));
                            nums.add(getAbdomenCount(allData));
                            nums.add(getElbowCount(allData));
                            nums.add(getShoulderCount(allData));
                            nums.add(getShinCount(allData));
                            nums.add(getJawCount(allData));
                            nums.add(getWristCount(allData));
                            nums.add(getFacial(allData));
                            addAllData();
                        }



                    }

                });


        View view = binding.getRoot();
        return view;
    }

    private void addAllData() {
        ArrayList<PieEntry> painEntries = new ArrayList<>();
        ArrayList<PieEntry> painEntriesFiltered = new ArrayList<>();
        ArrayList<String> pains = new ArrayList<>();

        Log.d("Add", "Got here");
        for (int i = 0; i<painLocations.length;i++)
        {
            pains.add(painLocations[i]);
        }

       /* for (int i = 1; i<painLocations.length;i++)
        {
            painEntries.add(new PieEntry(nums.get(i),pains));
        }*/

        int i = 0;
      for (float x:nums)
        {

            painEntries.add(i ,new PieEntry(x));
            painEntries.get(i).setLabel(pains.get(i));
            i = i+1;
        }

        //create dataset

        for (PieEntry entry: painEntries)
        {
            if (entry.getValue()>0)
            {
                painEntriesFiltered.add(entry);
            }
        }
        PieDataSet pieDataSet = new PieDataSet(painEntriesFiltered, "Pain location allocation");

        pieDataSet.setValueTextSize(8);
        pieDataSet.setSliceSpace(4);




        //colors
        ArrayList<Integer> colors = new ArrayList<>();

        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
        colors.add(Color.CYAN);
        colors.add(Color.MAGENTA);
        colors.add(Color.BLUE);
        pieDataSet.setColors(colors);



        //legend

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

    public float getBackCount(ArrayList<PainEntryData> painEntryData)
    {
        int total = painEntryData.size();
        float count = 0;
        for (PainEntryData painEntryData1: painEntryData)
        {
            if (painEntryData1.painLocation.contains("Back"))
            {
                count ++;
            }
            Log.d("Back", String.valueOf(count));
        }
        float percent = (count/total)*100;
        return percent;
    }
    public float getNeckCount(ArrayList<PainEntryData> painEntryData)
    {

        int total = painEntryData.size();
        float count = 0;
        for (PainEntryData painEntryData1: painEntryData)
        {
            if (painEntryData1.painLocation.contains("Neck"))
            {
                count ++;
            }

        }
        float percent = (count/total)*100;
        return percent;
    }

    public float getHeadCount(ArrayList<PainEntryData> painEntryData)
    {

        int total = painEntryData.size();
        float count = 0;
        for (PainEntryData painEntryData1: painEntryData)
        {
            if (painEntryData1.painLocation.contains("Head"))
            {
                count ++;
            }

        }
        float percent = (count/total)*100;
        return percent;
    }

    public float getKneeCount(ArrayList<PainEntryData> painEntryData)
    {

        int total = painEntryData.size();
        float count = 0;
        for (PainEntryData painEntryData1: painEntryData)
        {
            if (painEntryData1.painLocation.contains("knee"))
            {
                count ++;
            }

        }
        float percent = (count/total)*100;
        return percent;
    }

    public float getHipCount(ArrayList<PainEntryData> painEntryData)
    {

        int total = painEntryData.size();
        float count = 0;
        for (PainEntryData painEntryData1: painEntryData)
        {
            if (painEntryData1.painLocation.contains("Hip"))
            {
                count ++;
            }

        }
        float percent = (count/total)*100;
        return percent;
    }

    public float getAbdomenCount(ArrayList<PainEntryData> painEntryData)
    {

        int total = painEntryData.size();
        float count = 0;
        for (PainEntryData painEntryData1: painEntryData)
        {
            if (painEntryData1.painLocation.contains("Abdomen"))
            {
                count ++;
            }

        }
        float percent = (count/total)*100;
        return percent;
    }

    public float getElbowCount(ArrayList<PainEntryData> painEntryData)
    {

        int total = painEntryData.size();
        float count = 0;
        for (PainEntryData painEntryData1: painEntryData)
        {
            if (painEntryData1.painLocation.contains("elbow"))
            {
                count ++;
            }

        }
        float percent = (count/total)*100;
        return percent;
    }

    public float getShoulderCount(ArrayList<PainEntryData> painEntryData)
    {

        int total = painEntryData.size();
        float count = 0;
        for (PainEntryData painEntryData1: painEntryData)
        {
            if (painEntryData1.painLocation.contains("shoulder"))
            {
                count ++;
            }

        }
        float percent = (count/total)*100;
        return percent;
    }

    public float getShinCount(ArrayList<PainEntryData> painEntryData)
    {

        int total = painEntryData.size();
        float count = 0;
        for (PainEntryData painEntryData1: painEntryData)
        {
            if (painEntryData1.painLocation.contains("Shin"))
            {
                count ++;
            }

        }
        float percent = (count/total)*100;
        return percent;
    }

    public float getJawCount(ArrayList<PainEntryData> painEntryData)
    {

        int total = painEntryData.size();
        float count = 0;
        for (PainEntryData painEntryData1: painEntryData)
        {
            if (painEntryData1.painLocation.contains("Jaw"))
            {
                count ++;
            }

        }
        float percent = (count/total)*100;
        return percent;
    }

    public float getWristCount(ArrayList<PainEntryData> painEntryData)
    {

        int total = painEntryData.size();
        float count = 0;
        for (PainEntryData painEntryData1: painEntryData)
        {
            if (painEntryData1.painLocation.contains("wrist"))
            {
                count ++;
            }

        }
        float percent = (count/total)*100;
        return percent;
    }


    public float getFacial(ArrayList<PainEntryData> painEntryData)
    {

        int total = painEntryData.size();
        float count = 0;
        for (PainEntryData painEntryData1: painEntryData)
        {
            if (painEntryData1.painLocation.contains("Facial"))
            {
                count ++;
            }

        }
        float percent = (count/total)*100;
        return percent;
    }

    @Override
    public void onDestroyView() {super.onDestroyView();binding = null;}

}
