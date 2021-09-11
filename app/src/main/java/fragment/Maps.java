package fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.assignment_3.databinding.MapBinding;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;


import java.io.IOException;
import java.security.Permission;
import java.util.List;
import java.util.Locale;

public class Maps extends Fragment  {


    private MapBinding binding;
    private MapView mapView;


    public  Maps()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String token = "pk.eyJ1IjoiYWhyYXJoYXlhdCIsImEiOiJja28yaHpsM2EwdXE1MnB0ZGY0MHM0czV6In0.K2ic9tJMfO9XpBHZDHBnxA";
        Mapbox.getInstance(getContext(), token);

        binding = binding.inflate(inflater, container, false);

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);

        boolean CheckExists = sharedPref.contains("CityCountry");
        String address = "Deakin Hall 3168";

        if (CheckExists)
        {
             address = sharedPref.getString("Location","Deakin Hall 3168");
             Log.d("Location",address);
        }


        LatLng latLng = getLocationFromAddress(getContext(),address);
        //latLng = new LatLng(-37.915989,145.138824);
        mapView = binding.mapView;
        mapView.onCreate(savedInstanceState);
        LatLng finalLatLng = latLng;
        mapView.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        CameraPosition position = new CameraPosition.Builder().target(finalLatLng).zoom(15).build();
                        mapboxMap.setCameraPosition(position);
                       mapboxMap.addMarker(new MarkerOptions()
                               .position(finalLatLng)
                             .title("Current Location"));

                    }
                });
            }
        });


        View view = binding.getRoot();
        return view;

    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context, Locale.getDefault());
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 125);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    @Override
    public void onStart() {
        super.onStart(); mapView.onStart();
    }
    @Override
    public void onResume() {
        super.onResume(); mapView.onResume(); }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause(); }

    @Override public void onStop() { super.onStop();
        mapView.onStop(); }

    @Override public void onSaveInstanceState(Bundle outState) { super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState); }
    @Override public void onLowMemory() { super.onLowMemory(); mapView.onLowMemory(); }
    @Override public void onDestroy() { super.onDestroy(); mapView.onDestroy(); }


}
