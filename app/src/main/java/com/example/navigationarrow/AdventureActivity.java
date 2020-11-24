//ʕ•́ᴥ•̀ʔっ Header ʕ•́ᴥ•̀ʔっ
//(•◡•)/ Sub-header (•◡•)/
//ᕙ(`▿´)ᕗ Explanation of code ᕙ(`▿´)ᕗ
//≧◉◡◉≦ TOFIX  ≧◉◡◉≦

package com.example.navigationarrow;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.navigationarrow.ui.navigation.NavigationViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.concurrent.TimeUnit;

import static java.lang.Math.floor;

public class AdventureActivity extends AppCompatActivity implements LocationListener {

    /* ʕ•́ᴥ•̀ʔっ COMPASS VAR  ʕ•́ᴥ•̀ʔっ*/

    private NavigationViewModel navModel;

    //(•◡•)/ Sensor setup (•◡•)/

    private SensorManager sensorManager;
    private Sensor sensorRotationVector;

    //(•◡•)/ Accelerometer (orientation) variables (•◡•)/

    private float[] floatOrientation = new float[3];
    private float[] floatRotationMatrix = new float[9];

    /* ʕ•́ᴥ•̀ʔっ COMPASS VAR END ʕ•́ᴥ•̀ʔっ */

    /* ʕ•́ᴥ•̀ʔっ GPS VAR ʕ•́ᴥ•̀ʔっ */

    protected LocationManager locationManager;

    /* ʕ•́ᴥ•̀ʔっ GPS VAR END ʕ•́ᴥ•̀ʔっ */

    //(•◡•)/ View variables setup (•◡•)/
    TextView txtLat;
    TextView txtCheck;
    //TextView txtSensor;
    TextView timeText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //ᕙ(`▿´)ᕗ Mandatory in onCreate ᕙ(`▿´)ᕗ
        super.onCreate(savedInstanceState);
        navModel = (NavigationViewModel) obtainViewModel(this, NavigationViewModel.class);
        DataBindingUtil.setContentView(this, R.layout.activity_adventure);

        /* ʕ•́ᴥ•̀ʔっ COMPASS DISPLAY ʕ•́ᴥ•̀ʔっ */

        //(•◡•)/ Assign corresponding values (•◡•)/


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensorRotationVector = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);

        //txtSensor = (TextView) findViewById(R.id.gpsText2);

        SensorEventListener sensorEventListenerRotationVector = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
                    // Convert the rotation-vector to a 4x4 matrix..
                    SensorManager.getRotationMatrixFromVector(floatRotationMatrix,
                            event.values);
                    SensorManager
                            .remapCoordinateSystem(floatRotationMatrix,
                                    SensorManager.AXIS_X, SensorManager.AXIS_Z,
                                    floatRotationMatrix);
                    SensorManager.getOrientation(floatRotationMatrix, floatOrientation);

                    // Optionally convert the result from radians to degrees
                    floatOrientation[0] = (float) Math.toDegrees(floatOrientation[0]);
                    floatOrientation[1] = (float) Math.toDegrees(floatOrientation[1]);
                    floatOrientation[2] = (float) Math.toDegrees(floatOrientation[2]);

                    navModel.setOrientation(floatOrientation);
                    navModel.setSensorText(navModel.getOrientationValue().getValue());

                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        sensorManager.registerListener(sensorEventListenerRotationVector, sensorRotationVector, SensorManager.SENSOR_DELAY_NORMAL);
        /* ʕ•́ᴥ•̀ʔっ COMPASS DISPLAY END ʕ•́ᴥ•̀ʔっ */

        /* ʕ•́ᴥ•̀ʔっ GPS COORDINATES ʕ•́ᴥ•̀ʔっ */
        txtLat = (TextView) findViewById(R.id.gpsText);
        txtCheck = (TextView) findViewById(R.id.textView2);
        timeText = (TextView) findViewById(R.id.timeWalked);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        /* ʕ•́ᴥ•̀ʔっ GPS COORDINATES END ʕ•́ᴥ•̀ʔっ */

        BottomNavigationView navView = findViewById(R.id.nav_view_adventure);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_compass, R.id.navigation_story)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_adventure);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    // ʕ•́ᴥ•̀ʔっ Making GPS coordinates visible in DMS notation ʕ•́ᴥ•̀ʔっ

    //(•◡•)/ Get wind direction letter from long- and latitude (•◡•)/
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
        double gpsHours = floor(gpsValue);
        String hourStr = String.format("%.0f", gpsHours);
        double gpsMin = floor((gpsValue - gpsHours) * 60);
        String minStr = String.format("%.0f", gpsMin);
        double gpsSec = (gpsValue - gpsHours - (gpsMin / 60)) * 3600;
        String secStr = String.format("%.3f", gpsSec);
        String gps = windDir + hourStr + "° " + minStr + "' " + secStr + "\"";
        return gps;
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

    //ʕ•́ᴥ•̀ʔっ MAKING  GPS COORDINATES VISIBLE IN DMS NOTATION END ʕ•́ᴥ•̀ʔっ


    @Override
    public void onLocationChanged(Location location) {

        /* ᕙ(`▿´)ᕗ if the location has changed, the text should be updated to the corresponding coordinates.
        Currently also features longitude and latitude for control purposes ᕙ(`▿´)ᕗ */

        double distance = calculateDistanceLongLatPoints(location.getLatitude(), location.getLatitude() + 0.5, location.getLongitude(), location.getLongitude() + 0.5);

        txtLat = (TextView) findViewById(R.id.gpsText);
        //txtLat.setText("Latitude:" + getLongOrLatitude(getGPSValue(location, "lat"), "lat") + ", \n" + location.getLatitude() + " \n Longitude:" + getLongOrLatitude(getGPSValue(location, "long"), "long") + "\n " + location.getLongitude() + "\n " + distance);

        Location location2 = new Location("");
        location2.setLatitude(51.5162d);
        location2.setLongitude(5.0855d);

        double dist = calculateDistanceLongLatPoints(location.getLatitude(), location2.getLatitude(), location.getLongitude(), location2.getLongitude());
        txtCheck.setText(location.toString());
        if (dist < 100) {
            txtCheck.setText("smol " + dist);
        } else if (dist > 100) {
            txtCheck.setText("big" + dist);
        } else {
            txtCheck.setText("aaaaah");
        }

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
    public float directionNextCoordinate(Location location1, Location location2) {
        float direction = location1.bearingTo(location2);

        float phoneOrientation = (float) (-floatOrientation[0] * 180 / 3.14159);
        float turnAngle = direction - phoneOrientation;

        return turnAngle;

        //≧◉◡◉≦ TOFIX Enhance turnangle so phone rotation + bearing vs true north next location = direction arrow  ≧◉◡◉≦

    }

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

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }


}