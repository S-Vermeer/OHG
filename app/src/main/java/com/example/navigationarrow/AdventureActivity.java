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

public class AdventureActivity extends AppCompatActivity implements LocationListener {

    /* ʕ•́ᴥ•̀ʔっ COMPASS VAR  ʕ•́ᴥ•̀ʔっ*/

    private NavigationViewModel navModel;

    //(•◡•)/ Compass image (•◡•)/
    private ImageView imageView;

    //(•◡•)/ Sensor setup (•◡•)/
    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;
    private Sensor sensorMagneticField;
    private Sensor sensorRotationVector;

    //(•◡•)/ Magnetic field variables (•◡•)/
    private float[] floatGravity = new float[3];
    private float[] floatGeoMagnetic = new float[3];

    //(•◡•)/ Accelerometer (orientation) variables (•◡•)/
    private float[] floatOrientation = new float[3];
    private float[] floatRotationMatrix = new float[9];
    /* ʕ•́ᴥ•̀ʔっ COMPASS VAR END ʕ•́ᴥ•̀ʔっ */

    /* ʕ•́ᴥ•̀ʔっ GPS VAR ʕ•́ᴥ•̀ʔっ */
    protected LocationManager locationManager;
    TextView txtLat;
    TextView txtCheck;
    String magnet = "";
    String accel = "";
    TextView txtSensor;
    /* ʕ•́ᴥ•̀ʔっ GPS VAR END ʕ•́ᴥ•̀ʔっ */

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //ᕙ(`▿´)ᕗ Mandatory in onCreate ᕙ(`▿´)ᕗ
        super.onCreate(savedInstanceState);
        navModel = (NavigationViewModel) obtainViewModel(this, NavigationViewModel.class);
        DataBindingUtil.setContentView(this, R.layout.activity_adventure);

        /* ʕ•́ᴥ•̀ʔっ COMPASS DISPLAY ʕ•́ᴥ•̀ʔっ */

        //(•◡•)/ Assign corresponding values (•◡•)/


        imageView = findViewById(R.id.imageView);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorRotationVector = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
        sensorMagneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        //(•◡•)/ Accelerometer sensor Get Values (•◡•)/
 /*       SensorEventListener sensorEventListerAccelerometer = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                floatOrientation = event.values;

                SensorManager.getRotationMatrix(floatRotationMatrix, null, floatGravity, floatGeoMagnetic);
                SensorManager.getOrientation(floatRotationMatrix, floatOrientation);

                //ᕙ(`▿´)ᕗ Change direction of compass based on sensor data ᕙ(`▿´)ᕗ
                navModel.setRotationAngle((float) (-floatOrientation[0] * 180 / 3.14159));
                //imageView.setRotation((float) (-floatOrientation[0] * 180 / 3.14159));

                accel = floatOrientation[0]+  " " + floatOrientation[1] + " " + floatOrientation[2];
                txtSensor = (TextView) findViewById(R.id.gpsText2);
                navModel.setAccelSensorText(floatOrientation[0]);
                navModel.setReadingsText();
                //txtSensor.setText("Magnet: "+ magnet + "\n Accel: " + accel + "\n Rotation: " + (-floatOrientation[0] * 180 / 3.14159));

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };*/

        SensorEventListener sensorEventListenerRotationVector = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
                    // Convert the rotation-vector to a 4x4 matrix.
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
                    txtSensor = (TextView) findViewById(R.id.gpsText2);


                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        //(•◡•)/ MagneticField Sensor Get Values (•◡•)/
   /*     SensorEventListener sensorEventListenerMagneticField = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                floatRotationMatrix = event.values;

                SensorManager.getRotationMatrix(floatRotationMatrix, null, floatGravity, floatGeoMagnetic);
                SensorManager.getOrientation(floatRotationMatrix, floatOrientation);

                //ᕙ(`▿´)ᕗ Change direction of compass based on sensor data ᕙ(`▿´)ᕗ
                navModel.setRotationAngle((float) (-floatOrientation[0] * 180 / 3.14159));
                //imageView.setRotation((float) (-floatOrientation[0] * 180 / 3.14159));

                magnet = floatGeoMagnetic[0] + " " + floatGeoMagnetic[1] + " " +  floatGeoMagnetic[2] ;
                txtSensor = (TextView) findViewById(R.id.gpsText2);
                //txtSensor.setText("Magnet: "+ magnet + "\n Accel: " + accel + "\n Rotation: " + (-floatOrientation[0] * 180 / 3.14159));

                navModel.setMagnetSensorText(floatGeoMagnetic[0]);
                navModel.setReadingsText();
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        sensorManager.registerListener(sensorEventListerAccelerometer, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListenerMagneticField, sensorMagneticField, SensorManager.SENSOR_DELAY_NORMAL);
*/
        sensorManager.registerListener(sensorEventListenerRotationVector, sensorRotationVector, SensorManager.SENSOR_DELAY_NORMAL);
        /* ʕ•́ᴥ•̀ʔっ COMPASS DISPLAY END ʕ•́ᴥ•̀ʔっ */

        /* ʕ•́ᴥ•̀ʔっ GPS COORDINATES ʕ•́ᴥ•̀ʔっ */
        txtLat = (TextView) findViewById(R.id.gpsText);
        txtCheck = (TextView) findViewById(R.id.textView2);

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
        double gpsHours = Math.floor(gpsValue);
        String hourStr = String.format("%.0f", gpsHours);
        double gpsMin = Math.floor((gpsValue - gpsHours) * 60);
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

        //imageView.setRotation(directionNextCoordinate(location,location2));
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