package com.example.navigationarrow.ui.navigation;


import android.location.Location;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class NavigationViewModel extends ViewModel {
    private MutableLiveData<float[]> orientation;
    private MutableLiveData<Float> rotationAngle;
    private MediatorLiveData<String> readingsText;
    private Location location;

    private Location loc1 = new Location("");
    private Location loc2 = new Location("");
    private Location loc3 = new Location("");

    private Date time;
    private long randomWalkingGoal;
    private long activeWalkingTime;


    private int[][] dotsArray;

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

    public long getActiveWalkingTime(){
        return activeWalkingTime;
    }

    public long setActiveWalkingTime(){
        long active = time.getTime();
        activeWalkingTime = active;
        return active;
    }

    public void setRandomWalkingTimeGoal() {
        Random r = new Random();
        int randomNum = 360000 + r.nextInt((600000 - 360000) + 1);
        long seconds = TimeUnit.SECONDS.toSeconds(randomNum);
        randomWalkingGoal = seconds;
    }

    public long getRandomWalkingTimeGoal() {
        return randomWalkingGoal;
    }

    public void setRandomDots(){
        int[][] array = {};
        Random r = new Random();
        int amount = r.nextInt(16);
        for(int i = 0; i < amount; i++){
            array[i][0] = i;
            array[i][1] = i;
        }
        dotsArray = array;
    }

    public int[][] getRandomDots(){
        return dotsArray;
    }
}