//ʕ•́ᴥ•̀ʔっ Header ʕ•́ᴥ•̀ʔっ
//(•◡•)/ Sub-header (•◡•)/
//ᕙ(`▿´)ᕗ Explanation of code ᕙ(`▿´)ᕗ
//≧◉◡◉≦ TOFIX  ≧◉◡◉≦

package com.example.navigationarrow;

import android.hardware.*;
import android.location.Location;
import android.os.Looper;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.navigationarrow.ui.navigation.NavigationViewModel;
import com.google.android.gms.location.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.floor;

public class AdventureActivity extends AppCompatActivity {

    private NavigationViewModel navModel;

    /* ʕ•́ᴥ•̀ʔっ LOCATION VAR  ʕ•́ᴥ•̀ʔっ*/
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    private SettingsClient mSettingsClient;
    private LocationSettingsRequest mLocationSettingsRequest;


    private Location targetLocation;
    public int locationsVisited;
    private float azimuth;
    /* ʕ•́ᴥ•̀ʔっ COMPASS VAR END ʕ•́ᴥ•̀ʔっ */

    /* ʕ•́ᴥ•̀ʔっ INTERFACE VAR ʕ•́ᴥ•̀ʔっ */
    private Timer timer;

    /* ʕ•́ᴥ•̀ʔっ INTERFACE VAR END ʕ•́ᴥ•̀ʔっ */

    //(•◡•)/ View variables setup (•◡•)/
    TextView txtLat;
    TextView timeText;
    TextView distanceWalkedText;


    //(•◡•)/ Notification Bar variables setup (•◡•)/
    private Snackbar sb;
    private Snackbar complete;


    /* ʕ•́ᴥ•̀ʔっ SENSOR VAR ʕ•́ᴥ•̀ʔっ */

    //(•◡•)/ Sensor setup (•◡•)/

    private SensorManager sensorManager;
    private Sensor sensorRotationVector;
    private final float[] accelerometerReading = new float[3];
    private final float[] magnetometerReading = new float[3];


    //(•◡•)/ Accelerometer (orientation) variables (•◡•)/

    private float[] floatOrientation = new float[3];
    private float[] floatRotationMatrix = new float[9];

    /* ʕ•́ᴥ•̀ʔっ SENSOR VAR END ʕ•́ᴥ•̀ʔっ */



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //ᕙ(`▿´)ᕗ Mandatory in onCreate ᕙ(`▿´)ᕗ
        super.onCreate(savedInstanceState);
        navModel = ViewModelProviders.of(this).get(NavigationViewModel.class);
//        navModel = (NavigationViewModel) obtainViewModel(this, NavigationViewModel.class);
//        pagerAgentViewModel.init();
        ArrayList<Location> locs = getIntent().getParcelableArrayListExtra("EXTRA_LOCATIONS");
        navModel.setLocations(locs);
        DataBindingUtil.setContentView(this, R.layout.activity_adventure);

        //(•◡•)/ Notification Bar variables assignment (•◡•)/
        sb = Snackbar.make(findViewById(R.id.constraintLayoutAdventure), "Bestemming behaald", 3000);
        complete = Snackbar.make(findViewById(R.id.constraintLayoutAdventure), "Op laatste bestemming gekomen", 15000);

        /* ʕ•́ᴥ•̀ʔっ ARROW DISPLAY ʕ•́ᴥ•̀ʔっ */

        //(•◡•)/ Assign corresponding values (•◡•)/

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensorRotationVector = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        //(•◡•)/ Sensor event listener setup (•◡•)/
        SensorEventListener sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

                // Rotation matrix based on current readings from accelerometer and magnetometer.
                if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                    System.arraycopy(event.values, 0, accelerometerReading,
                            0, accelerometerReading.length);
                } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                    System.arraycopy(event.values, 0, magnetometerReading,
                            0, magnetometerReading.length);
                }

                updateOrientationAngles();


                // Rotation matrix based on current readings from accelerometer and magnetometer.
                final float[] rotationMatrix = new float[9];
                SensorManager.getRotationMatrix(rotationMatrix, null,
                        accelerometerReading, magnetometerReading);

// Express the updated rotation matrix as three orientation angles.
                final float[] orientationAngles = new float[3];
                floatOrientation = SensorManager.getOrientation(rotationMatrix, orientationAngles);

                navModel.setOrientation(floatOrientation);
                navModel.setAzimuth();
                }


            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            sensorManager.registerListener(sensorEventListener, accelerometer,
                    SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }
        Sensor magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (magneticField != null) {
            sensorManager.registerListener(sensorEventListener, magneticField,
                    SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }

        /* ʕ•́ᴥ•̀ʔっ ARROW DISPLAY END ʕ•́ᴥ•̀ʔっ */

        /* ʕ•́ᴥ•̀ʔっ LOCATION RETRIEVAL ʕ•́ᴥ•̀ʔっ */
        targetLocation = navModel.locations.get(locationsVisited);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mSettingsClient = LocationServices.getSettingsClient(this);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(5000);

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Long spentTime = navModel.getSpentTime();

                runOnUiThread(() -> timeText.setText(TimeString(spentTime)));

            }
        },0, 5);


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
                .addOnSuccessListener(this, locationSettingsResponse -> {

                    //noinspection MissingPermission
                    fusedLocationClient.requestLocationUpdates(locationRequest,
                            locationCallback, Looper.myLooper());

                });

        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback, Looper.myLooper());

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                locationChange(location);
            }
        });

        /* ʕ•́ᴥ•̀ʔっ LOCATION RETRIEVAL END ʕ•́ᴥ•̀ʔっ */

        timeText = (TextView) findViewById(R.id.timeWalked);
        distanceWalkedText = (TextView) findViewById(R.id.distanceWalked);


        BottomNavigationView navView = findViewById(R.id.nav_view_adventure);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_compass, R.id.navigation_story)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_adventure);
        NavigationUI.setupWithNavController(navView, navController);
    }

    public void locationChange(Location location){
        /* ᕙ(`▿´)ᕗ if the location has changed, the text should be updated to the corresponding coordinates.
        Currently also features longitude and latitude for control purposes ᕙ(`▿´)ᕗ */

        txtLat = (TextView) findViewById(R.id.gpsText);


        double dist = calculateDistanceLongLatPoints(location.getLatitude(), targetLocation.getLatitude(), location.getLongitude(), targetLocation.getLongitude());


        if(dist < 5 && navModel.getPreviousDistance() >= 5){
            if(navModel.getLocationsVisited() == 3 && navModel.getCompletedAdventure() != true){
                complete.show();
                navModel.setCompletedAdventure(true);
                //storyModel.updateStoryPartIndex();
            } else if(navModel.getCompletedAdventure() != true) {
                sb.show();
                navModel.setNewGoal();
                targetLocation = navModel.getCurrentTarget();
                locationsVisited = navModel.getLocationsVisited();
                //storyModel.updateStoryPartIndex();
            }
        }
        navModel.setPreviousDistance(dist);

        long timeSpent = navModel.getSpentTime();

        timeText.setText(TimeString(timeSpent));
    }


    /* ʕ•́ᴥ•̀ʔっ NAVIGATION TO NEXT COORDINATE ʕ•́ᴥ•̀ʔっ */

    /* ᕙ(`▿´)ᕗ HAVERSINE FORMULE:
    distance between two coordinates = 2 * radiusEarth * (arcsin ( root of (sine^2(difference between latitudes / 2)
    + cosine(latitude 1) * cosine(latitude 2) * sine^2(difference between longitudes / 2))))   ᕙ(`▿´)ᕗ */

    //(•◡•)/ distance (meters) between two coordinates (give long and latitude of two points)  (•◡•)/
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

    //(•◡•)/ direction of an arrow (degrees to turn) to the next coordinate  (•◡•)/


    public String TimeString(long timeSpent){
        long totalSeconds = TimeUnit.MILLISECONDS.toSeconds(timeSpent);
        double minutes = floor(TimeUnit.MILLISECONDS.toMinutes(timeSpent));

        double currentSeconds = totalSeconds;
        if(totalSeconds >= 60){
            currentSeconds = totalSeconds - 60 * minutes;
        }
        String addedZero = "";
        if(currentSeconds < 10){
            addedZero = "0";
        }

        String time = String.format("%.0f", minutes) + ":" + addedZero + String.format("%.0f", currentSeconds);
        return time;
    }


    /* ʕ•́ᴥ•̀ʔっ NAVIGATION TO NEXT COORDINATE END ʕ•́ᴥ•̀ʔっ */

    protected final <T extends ViewModel> ViewModel obtainViewModel(@NonNull AppCompatActivity activity, @NonNull Class<T> modelClass) {
        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, factory).get(modelClass);
    }

    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        mLocationSettingsRequest = builder.build();
    }

    private void updateOrientationAngles(){
        // Update rotation matrix, which is needed to update orientation angles.
        SensorManager.getRotationMatrix(floatRotationMatrix, null,
                accelerometerReading, magnetometerReading);

        // "rotationMatrix" now has up-to-date information.

        SensorManager.getOrientation(floatRotationMatrix, floatOrientation);

    }


}