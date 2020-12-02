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

    private int locationsVisited;
    private boolean completedAdventure;
    private Location currentTarget;
    private double previousDistance;


    private Location loc1 = new Location("");
    private Location loc2 = new Location("");
    private Location loc3 = new Location("");
    private Location loc4 = new Location("");

    private Date time;

    public String sensorText;

    public ArrayList<Location> locations = new ArrayList<>();


    public NavigationViewModel() {
        orientation = new MutableLiveData<>();
        float[] oriVal = new float[]{0,0,0};
        orientation.setValue(oriVal);
        rotationAngle = new MutableLiveData<>();
        Float f = (float) 0;
        rotationAngle.setValue(f);
        setReadingsText();

        time = GregorianCalendar.getInstance().getTime();

        setLocationInfo(5.318422d,51.587799d,loc1);
        setLocationInfo(5.318744d,51.587757d,loc2);
        setLocationInfo(5.318932d,51.587824d,loc3);
        setLocationInfo(5.318684d,51.587799d,loc4);

        addLocationToCollection(loc1);
        addLocationToCollection(loc2);
        addLocationToCollection(loc3);
        addLocationToCollection(loc4);
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

    public void setLocationInfo(double lon, double lat, Location loc){
        loc.setLongitude(lon);
        loc.setLatitude(lat);
    }

    public void addLocationToCollection(Location l){
        locations.add(l);
    }

    public long getSpentTime(){
        Date now = GregorianCalendar.getInstance().getTime();
        long spentTime = now.getTime() - time.getTime();
        return spentTime;
    }



    public void updateLocationsVisited(){
        locationsVisited = locationsVisited + 1;
    }

    public void setPreviousDistance(double currentDistance){
        previousDistance = currentDistance;
    }

    public double getPreviousDistance(){
        return previousDistance;
    }

    public void setNewGoal(){
        updateLocationsVisited();
        currentTarget = locations.get(locationsVisited);
    }

    public int getLocationsVisited(){
        return locationsVisited;
    }

    public boolean getCompletedAdventure() {
        return completedAdventure;
    }

    public void setCompletedAdventure(boolean value) {
        completedAdventure = value;
    }

    public Location getCurrentTarget(){
        return currentTarget;
    }

    public void setCurrentTarget(Location location) {
        currentTarget = location;
    }

}