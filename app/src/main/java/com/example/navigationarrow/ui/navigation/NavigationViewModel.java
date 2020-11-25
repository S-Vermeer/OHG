package com.example.navigationarrow.ui.navigation;


import android.location.Location;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.*;

public class NavigationViewModel extends ViewModel {
    private MutableLiveData<float[]> orientation;
    private MutableLiveData<Float> rotationAngle;
    private MediatorLiveData<String> readingsText;
    private Location location;

    private Location loc1 = new Location("");
    private Location loc2 = new Location("");
    private Location loc3 = new Location("");

    private Date time;
    public double previousDistance = 0;

    public String sensorText;

    ArrayList<Location> locations = new ArrayList<>();


    public NavigationViewModel() {
        orientation = new MutableLiveData<>();
        float[] oriVal = new float[]{0,0,0};
        orientation.setValue(oriVal);
        rotationAngle = new MutableLiveData<>();
        Float f = (float) 0;
        rotationAngle.setValue(f);
        setReadingsText();

        time = GregorianCalendar.getInstance().getTime();


        loc1.setLongitude(5.0855d);
        loc1.setLatitude(51.4162d);
        loc2.setLongitude(5.1855d);
        loc2.setLatitude(51.5162d);
        loc3.setLongitude(5.2855d);
        loc3.setLongitude(51.6162d);
        addLocationToCollection(loc1);
        addLocationToCollection(loc2);
        addLocationToCollection(loc3);
    }

    public String getSensorText() {
        return sensorText;
    }

    public String setSensorText(String text) {;
        sensorText = text;
        return sensorText;
    }


    public void setReadingsText() {
        float[] currentOrientation = orientation.getValue();
        readingsText = new MediatorLiveData<>();
        readingsText.setValue(" Yaw: " + currentOrientation[0] + "\n Pitch: "
                + currentOrientation[1] + "\n Roll (not used): "
                + currentOrientation[2]);
    }



    public void setRotationAngle(float rotate){
        rotationAngle.setValue(rotate);
    }

    public MutableLiveData<Float> getRotationAngle(){
        return rotationAngle;
    }

    public void setOrientation(float[] value){
        orientation.setValue(value);
        setReadingsText();
    }


    public LiveData<String> getOrientationValue(){
        return readingsText;
    }

    public Location getLocation(){
        return location;
    }


    public void setLocation(Location location) {
        this.location = location;
        //update observer
    }

    public void addLocationToCollection(Location l){
        locations.add(l);
    }

    public long getSpentTime(){
        Date now = GregorianCalendar.getInstance().getTime();
        long spentTime = now.getTime() - time.getTime();
        return spentTime;
    }

    public void setDirection(){

    }

    public int getDirection(){
        int dir = 0;
        return dir;
    }


}