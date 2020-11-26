package com.example.navigationarrow.ui.navigation;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.*;
import com.example.navigationarrow.R;

import java.lang.reflect.Array;
import java.util.List;

import static androidx.core.content.ContextCompat.getSystemService;

public class NavigationFragment extends Fragment implements LocationListener {
    private NavigationViewModel navigationViewModel;

    //TextView sensorTextView;
    LocationManager locationManager;
    TextView gpsTextView;
    Button reset;
    int currentLocationNumber;
    int lastLocationNumber;
    Location currentTarget;
    TextView locationIndex;
    ImageView home;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        navigationViewModel = (NavigationViewModel) obtainFragmentViewModel(getActivity(), NavigationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_navigation, container, false);

        locationManager= (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, (android.location.LocationListener) this);


        //sensorTextView = root.findViewById(R.id.gpsText2);
        gpsTextView = root.findViewById(R.id.gpsText);
        reset = root.findViewById(R.id.button2);
        locationIndex = root.findViewById(R.id.gpsText2);
        home = root.findViewById(R.id.imageView);

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetButton(v);
            }
        });

        final Observer<String> dataObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String newData) {
                // Update the UI, in this case, a TextView.
                //sensorTextView.setText(newData);
            }
        };


        navigationViewModel = ViewModelProviders.of(this).get(NavigationViewModel.class);
        ViewModelProviders.of(getActivity()).get(NavigationViewModel.class).getOrientationValue().observe(this, dataObserver);


        currentLocationNumber = 1;
        lastLocationNumber = navigationViewModel.locations.size();
        currentTarget = navigationViewModel.locations.get(currentLocationNumber - 1);



        return root;
    }

    public void resetButton(View view){

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Create the observer which updates the UI.

        //navigationViewModel.getOrientationValue().observe(getViewLifecycleOwner(), dataObserver);
    }

    @Override
    public void onResume() {
        super.onResume();

        navigationViewModel.getRotationAngle().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
            }
        });
    }

    protected final <T extends ViewModel> ViewModel obtainFragmentViewModel(@NonNull FragmentActivity fragment, @NonNull Class<T> modelClass) {
        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(fragment.getApplication());
        return new ViewModelProvider(fragment, factory).get(modelClass);
    }

    @Override
    public void onLocationChanged(Location location) {

        String lat = getLongOrLatitude(getGPSValue(location,"lat"), "lat");
        String lon = getLongOrLatitude(getGPSValue(location,"long"), "long");
        double distance = calculateDistanceLongLatPoints(location.getLatitude(),currentTarget.getLatitude(), location.getLongitude(), currentTarget.getLongitude());
        gpsTextView.setText(lat + "\n" + lon + "\n" + distance);
        /* ᕙ(`▿´)ᕗ if the location has changed, the text should be updated to the corresponding coordinates.
        Currently also features longitude and latitude for control purposes ᕙ(`▿´)ᕗ */
        Location location2 = new Location("");
        location2.setLatitude(51.5162d);
        location2.setLongitude(5.0855d);

        float randomRot = (float) (Math.random() * 100);

        if(distance < 100){
            //sensorTextView.setText("/");
        }

        locationIndex.setText(navigationViewModel.getLocationsVisited() + "/" + 4);

        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) home.getLayoutParams();
        params.width = params.width + 10;
// existing height is ok as is, no need to edit it
        home.setLayoutParams(params);

        //imageView.setRotation(directionNextCoordinate(location,location2));
    }

    public double getGPSValue(Location location, String longOrLat) {
        double longLatValue;
        if (longOrLat == "lat") {
            longLatValue = location.getLatitude();
        } else if (longOrLat == "long") {
            longLatValue = location.getLongitude();

        } else {
            longLatValue = 69696969; //ᕙ(`▿´)ᕗ Mock value in case something goes wrong with the connection to the sensors ᕙ(`▿´)ᕗ
        }

        return longLatValue;
    }

    public char windDir(String longOrLat, double value) {
        char windDir;
        if (longOrLat == "lat" && value >= 0) {
            windDir = 'N';
        } else if (longOrLat == "lat" && value < 0) {
            windDir = 'S';
        } else if (longOrLat == "long" && value >= 0) {
            windDir = 'E';
        } else if (longOrLat == "long" && value < 0) {
            windDir = 'W';
        } else {
            windDir = 'X'; //ᕙ(`▿´)ᕗ In case something goes wrong, still initialisation of windDir ᕙ(`▿´)ᕗ
        }
        return windDir;
    }

    public String getLongOrLatitude(double gpsValue, String longOrLat) {
        char windDir = windDir(longOrLat, gpsValue);
        if (gpsValue < 0) {
            gpsValue = gpsValue * -1;
        }

        /* ᕙ(`▿´)ᕗ DMS notation is both for longitude and latitude the full value (so no decimals), then get the
        remaining value * 60 and the full value from that and repeat once more. Its the most commonly used formatting of GPS coordinates ᕙ(`▿´)ᕗ */
        double gpsHours = Math.floor(gpsValue);
        String hourStr = String.format("%.0f", gpsHours);
        double gpsMin = Math.floor((gpsValue - gpsHours) * 60);
        String minStr = String.format("%.0f", gpsMin);
        double gpsSec = (gpsValue - gpsHours - (gpsMin / 60)) * 3600;
        String secStr = String.format("%.3f", gpsSec);
        String gps = windDir + hourStr + "° " + minStr + "' " + secStr + "\"";
        return gps;
    }

    public double calculateDistanceLongLatPoints(double lat1, double lat2, double long1, double long2) {
        double radiusEarth = 6371e3;

        double radLat1 = lat1 * Math.PI / 180;
        double radLat2 = lat2 * Math.PI / 180;
        double radLong1 = long1 * Math.PI / 180;
        double radLong2 = long2 * Math.PI / 180;


        double sineSquaredDifLatitudes = Math.pow(Math.sin((radLat2 - radLat1) / 2), 2);
        double cosineLat1 = Math.cos(radLat1);
        double cosineLat2 = Math.cos(radLat2);
        double sineSquaredDifLongitudes = Math.pow(Math.sin((radLong2 - radLong1) / 2), 2);

        double squareRoot = Math.sqrt(sineSquaredDifLatitudes + (cosineLat1 * cosineLat2 * sineSquaredDifLongitudes));
        double arcsine = Math.asin(squareRoot);

        double haversine = (2 * radiusEarth) * arcsine;

        return haversine;
    }


}