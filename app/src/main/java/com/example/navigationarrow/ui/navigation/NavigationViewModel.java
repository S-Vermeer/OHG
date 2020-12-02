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
    private Location loc4 = new Location("");

    private Date time;
    private double previousDistance = 0;

    public String sensorText;

    ArrayList<Location> locations = new ArrayList<>();
    private int currentLocationNumber;

    private int locationsVisited;

    private boolean completedAdventure = false;

    private Location currentTarget;


    public NavigationViewModel() {
        orientation = new MutableLiveData<>();
        float[] oriVal = new float[]{0,0,0};
        orientation.setValue(oriVal);
        rotationAngle = new MutableLiveData<>();
        Float f = (float) 0;
        rotationAngle.setValue(f);
        setReadingsText();

        time = GregorianCalendar.getInstance().getTime();

        setLocationInfo(5.0120d,51.5777d,loc1);
        setLocationInfo(5.0855d,51.5162d,loc2);
        setLocationInfo(5.4271d,51.6476d,loc3);
        setLocationInfo(5.4541d,51.4515d,loc4);

        addLocationToCollection(loc1);
        addLocationToCollection(loc2);
        addLocationToCollection(loc3);
        addLocationToCollection(loc4);
        currentLocationNumber = 1;
        setCurrentTarget(locations.get(currentLocationNumber - 1));
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

    public void setLocationInfo(double lon, double lat, Location loc){
        loc.setLongitude(lon);
        loc.setLatitude(lat);
    }

    public void updateCurrentLocationNumber(){
        currentLocationNumber++;

    }

    public int getCurrentLocationNumber(){
        return currentLocationNumber;
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

    public void updateLocationsVisited(){
        locationsVisited = locationsVisited + 1;
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

    public void setCurrentTarget(Location location){
        currentTarget = location;
    }


}