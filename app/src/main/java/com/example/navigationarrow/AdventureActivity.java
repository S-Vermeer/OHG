//ʕ•́ᴥ•̀ʔっ Header ʕ•́ᴥ•̀ʔっ
//(•◡•)/ Sub-header (•◡•)/

package com.example.navigationarrow;

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
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class AdventureActivity extends AppCompatActivity implements LocationListener {

    /* ʕ•́ᴥ•̀ʔっ COMPASS VAR  ʕ•́ᴥ•̀ʔっ*/

    //(•◡•)/ Compass image (•◡•)/
    private ImageView imageView;

    //(•◡•)/ Sensor setup (•◡•)/
    private SensorManager sensorManager;
    private Sensor sensorAccelerometer;
    private Sensor sensorMagneticField;

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
    TextView textView;
    /* ʕ•́ᴥ•̀ʔっ GPS VAR END ʕ•́ᴥ•̀ʔっ */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adventure);

        /* ʕ•́ᴥ•̀ʔっ COMPASS DISPLAY ʕ•́ᴥ•̀ʔっ */

        //(•◡•)/ Assign corresponding values (•◡•)/
        imageView = findViewById(R.id.imageView);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMagneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);

        //(•◡•)/ Accelerometer sensor Get Values (•◡•)/
        SensorEventListener sensorEventListerAccelerometer = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                floatGravity = event.values;

                SensorManager.getRotationMatrix(floatRotationMatrix, null, floatGravity, floatGeoMagnetic);
                SensorManager.getOrientation(floatRotationMatrix, floatOrientation);

                imageView.setRotation((float) (-floatOrientation[0] * 180 / 3.14159));
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        //(•◡•)/ MagneticField Sensor Get Values (•◡•)/
        SensorEventListener sensorEventListenerMagneticField = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                floatGeoMagnetic = event.values;

                SensorManager.getRotationMatrix(floatRotationMatrix, null, floatGravity, floatGeoMagnetic);
                SensorManager.getOrientation(floatRotationMatrix, floatOrientation);

                imageView.setRotation((float) (-floatOrientation[0] * 180 / 3.14159));

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        sensorManager.registerListener(sensorEventListerAccelerometer, sensorAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorEventListenerMagneticField, sensorMagneticField, SensorManager.SENSOR_DELAY_NORMAL);

        /* ʕ•́ᴥ•̀ʔっ COMPASS DISPLAY END ʕ•́ᴥ•̀ʔっ */

        /* ʕ•́ᴥ•̀ʔっ GPS COORDINATES ʕ•́ᴥ•̀ʔっ */
        txtLat = (TextView) findViewById(R.id.gpsText);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        /* ʕ•́ᴥ•̀ʔっ GPS COORDINATES END ʕ•́ᴥ•̀ʔっ */

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
            windDir = 'X';
        }
        return windDir;
    }

    public String getLongOrLatitude(double gpsValue, String longOrLat){
        char windDir = windDir(longOrLat, gpsValue);
        if(gpsValue < 0){
            gpsValue = gpsValue * -1;
        }

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
        double gpsValue;
        if(longOrLat == "lat"){
            longLatValue = location.getLatitude();
        } else if (longOrLat == "long"){
            longLatValue = location.getLongitude();

        } else {
            longLatValue = 69696969;
        }
        gpsValue = longLatValue;

        return gpsValue;
    }

    @Override
    public void onLocationChanged(Location location) {
        txtLat = (TextView) findViewById(R.id.gpsText);
        txtLat.setText("Latitude:" + getLongOrLatitude(getGPSValue(location,"lat"), "lat") + ", \n" + location.getLatitude() + " \n Longitude:" + getLongOrLatitude(getGPSValue(location,"long"), "long") + "\n " + location.getLongitude());
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

    public void ResetButton(View view) {
        imageView.setRotation(180);
    }
}