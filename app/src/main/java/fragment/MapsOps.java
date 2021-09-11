package fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.assignment_3.R;
import com.example.assignment_3.databinding.MapOptionsBinding;
import com.google.android.material.snackbar.Snackbar;

public class MapsOps extends Fragment {


    private MapOptionsBinding binding;
    private String locationData = "";
    private String cityData = "";

    public MapsOps()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = binding.inflate(inflater, container, false);
        binding.findlocbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String add = binding.address.getText().toString();
                String post = binding.postcode.getText().toString();
                String countryCode = binding.countryCode.getText().toString();
                String cityName = binding.city.getText().toString();
                if (!add.isEmpty() && !post.isEmpty() && !countryCode.isEmpty() && !cityName.isEmpty() )
                {


                    if (post.length() != 4)
                    {
                        Snackbar.make(v, "Please enter a valid postcode!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                    else if (countryCode.length() != 2)
                    {
                        Snackbar.make(v, "Please enter a valid country code with 2 letters!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else {
                        String addPost = add + " " + post;
                        String cityCountry = cityName + ", " + countryCode;
                        locationData = addPost;
                        cityData = cityCountry;
                        SharedPreferences sharedPref =  getActivity().getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("Location", locationData);
                        editor.apply();
                        Log.d("Location Added","Location Added");
                        SharedPreferences.Editor editor1 = sharedPref.edit();
                        editor1.putString("CityCountry",cityData);
                        editor1.apply();
                        Snackbar.make(v, "Your Location has been updated!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }
                else {
                    Snackbar.make(v, "You need to enter all the fields above!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }



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
        fragmentTransaction.replace(R.id.fragment_container_view_map, nextFragment);
        //the transaction is committed by this line
        fragmentTransaction.commit();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public String getLocationData() {
        return locationData;
    }
}

