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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.example.assignment_3.R;
import com.example.assignment_3.SignUp;
import com.example.assignment_3.databinding.ReportBinding;

import java.util.ArrayList;
import java.util.List;

import entity.PainEntryData;
import viewmodel.PainEntryViewModel;

public class Reports extends Fragment {

   private ReportBinding binding;


   public Reports()
   {

   }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = binding.inflate(inflater, container, false);
        binding.piePain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //binding.pieSteps.setVisibility(View.INVISIBLE);
                //binding.piePain.setVisibility(View.INVISIBLE);
                replaceFragment(new PieReportPain());
            }
        });

        binding.pieSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //binding.pieSteps.setVisibility(View.INVISIBLE);
                //binding.piePain.setVisibility(View.INVISIBLE);
                replaceFragment(new PieStepsTaken());
            }
        });



        View view = binding.getRoot();
        return view;

    }

    private void replaceFragment(Fragment nextFragment) {
        //this creates a fragment manager
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        //this creates a new transaction for the manager to begin
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //this replaces the previous one with a new fragment, which is passed as a parameter to this method
        fragmentTransaction.replace(R.id.fragment_container_view, nextFragment);
        //the transaction is committed by this line
        fragmentTransaction.commit();
    }
    @Override
    public void onDestroyView() {super.onDestroyView();binding = null;}

}
