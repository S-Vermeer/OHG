package com.example.navigationarrow.ui.navigation;

import android.hardware.GeomagneticField;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
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
import com.example.navigationarrow.Adventure;
import com.example.navigationarrow.AdventureActivity;
import com.example.navigationarrow.R;
import com.google.android.gms.location.*;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class NavigationFragment extends Fragment {
    private NavigationViewModel navigationViewModel;
    AdventureActivity activity;

    private float azimuth;
    private float turn;

    /* ʕ•́ᴥ•̀ʔっ LOCATION VAR  ʕ•́ᴥ•̀ʔっ*/
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    private SettingsClient mSettingsClient;
    private LocationSettingsRequest mLocationSettingsRequest;

    int currentLocationNumber;
    int lastLocationNumber;
    Location currentTarget;

    /* ʕ•́ᴥ•̀ʔっ LOCATION VAR END ʕ•́ᴥ•̀ʔっ*/

    /* ʕ•́ᴥ•̀ʔっ INTERFACE VAR  ʕ•́ᴥ•̀ʔっ*/
    ImageView arrowImageView;
    TextView gpsTextView;
    Button info;
    TextView locationIndex;
    /* ʕ•́ᴥ•̀ʔっ INTERFACE VAR END  ʕ•́ᴥ•̀ʔっ*/

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

//        navigationViewModel = (NavigationViewModel) obtainFragmentViewModel(getActivity(), NavigationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_navigation, container, false);

        activity = (AdventureActivity) getActivity();

        /* ʕ•́ᴥ•̀ʔっ NAVIGATION INFO DISPLAY ʕ•́ᴥ•̀ʔっ */

        //(•◡•)/ Assign corresponding values (•◡•)/
        arrowImageView = root.findViewById(R.id.imageView);
        gpsTextView = root.findViewById(R.id.gpsText);
        arrowImageView.setRotation(80);
        info = root.findViewById(R.id.checkAnswerButton);
        locationIndex = root.findViewById(R.id.assignmentQuestionText);


        //(•◡•)/ Assign Listeners (•◡•)/
        info.setOnClickListener(v -> infoButton(v));

        navigationViewModel = ViewModelProviders.of(this).get(NavigationViewModel.class);
        Adventure adventure = new Adventure(activity.getIntent().getIntExtra("EXTRA_ID",0));
        navigationViewModel.setLocations(adventure.getLocations());

        //(•◡•)/ See how far you are on the route, how many checkpoints to go (•◡•)/
        currentLocationNumber = 1;
        lastLocationNumber = navigationViewModel.locations.size();
        currentTarget = navigationViewModel.locations.get(currentLocationNumber - 1);


        //(•◡•)/ Location retrieval setup (•◡•)/
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        mSettingsClient = LocationServices.getSettingsClient(activity);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(20 * 1000);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                Location location = locationResult.getLastLocation();
                if (locationResult == null) {
                    return;
                }
                if (location != null) {
                    locationChange(location);

                }
            }
        };

        buildLocationSettingsRequest();


        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(activity, locationSettingsResponse -> {

                    //noinspection MissingPermission
                    fusedLocationClient.requestLocationUpdates(locationRequest,
                            locationCallback, Looper.myLooper());

                });

        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback, Looper.myLooper());
        fusedLocationClient.getLastLocation().addOnSuccessListener(activity, location -> {
            if (location != null) {
                locationChange(location);
            }

        });


        navigationViewModel.setSequenceId(activity.getIntent().getStringExtra("EXTRA_SEQUENCE_ID"));

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        navigationViewModel = ViewModelProviders.of(getActivity()).get(NavigationViewModel.class);
        navigationViewModel.getAzimuth().observe(this, azi -> azimuth = azi);
    }


    public void infoButton(View view){
        navigationViewModel.setSequenceId(navigationViewModel.calculateSequenceId());
        showcaseView();
    }


    protected final <T extends ViewModel> ViewModel obtainFragmentViewModel(@NonNull FragmentActivity fragment, @NonNull Class<T> modelClass) {
        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(fragment.getApplication());
        return new ViewModelProvider(fragment, factory).get(modelClass);
    }

    private void locationChange(Location location){
        String lat = getLongOrLatitude(getGPSValue(location,"lat"), "lat");
        String lon = getLongOrLatitude(getGPSValue(location,"long"), "long");

        lastLocationNumber = navigationViewModel.getLocationsVisited();
        arrowImageView.setRotation(directionNextCoordinate(location, currentTarget));

        int locationsVisited = 10;
        if(activity != null) {
            locationsVisited = activity.locationsVisited;
            currentLocationNumber = locationsVisited + 1;
        }

        locationIndex.setText(locationsVisited + 1 + "/" + 4);
        currentTarget = navigationViewModel.locations.get(currentLocationNumber - 1);

        double distance = calculateDistanceLongLatPoints(location.getLatitude(),currentTarget.getLatitude(), location.getLongitude(), currentTarget.getLongitude());
        gpsTextView.setText(lat + "\n" + lon + "\n" + distance);
        /* ᕙ(`▿´)ᕗ if the location has changed, the text should be updated to the corresponding coordinates. ᕙ(`▿´)ᕗ */

        showcaseView();

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

    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        mLocationSettingsRequest = builder.build();
    }

    public float directionNextCoordinate(Location location1, Location location2) {
        azimuth = navigationViewModel.getAzimuth().getValue();
        azimuth = (float) Math.toDegrees(azimuth);
        GeomagneticField geoField = new GeomagneticField(
                Double.valueOf(location1.getLatitude()).floatValue(),
                Double.valueOf(location1.getLongitude()).floatValue(),
                Double.valueOf(location1.getAltitude()).floatValue(),
                System.currentTimeMillis());

        azimuth -= geoField.getDeclination(); // converts magnetic north to true north
        float bearingTo = location1.bearingTo(location2);
        if (bearingTo < 0) {
            bearingTo = bearingTo + 360;
        }
        float turnAngle = bearingTo - azimuth;
        if(turnAngle < 0){
            turnAngle = turnAngle + 360;
        }
        turn = turnAngle;

        return turnAngle;
    }

    private void showcaseView(){
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(500); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(activity, navigationViewModel.getSequenceId());

        sequence.setConfig(config);

        sequence.addSequenceItem(arrowImageView,
                "This is navigation", "GOT IT");

        sequence.addSequenceItem(locationIndex,
                "This is the index of the target", "GOT IT");

        sequence.addSequenceItem(gpsTextView,
                "This is some location info", "GOT IT");

        sequence.start();
    }



}