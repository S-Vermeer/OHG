package com.example.navigationarrow.ui.navigation;


import android.location.Location;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.*;

public class NavigationViewModel extends ViewModel {

    private float[] orientation;
    public MutableLiveData<Float> azimuth;

    private int locationsVisited;
    private boolean completedAdventure;
    private Location currentTarget;
    private double previousDistance;

    private Location loc1 = new Location("");
    private Location loc2 = new Location("");
    private Location loc3 = new Location("");
    private Location loc4 = new Location("");

    private Date time;

    public ArrayList<Location> locations = new ArrayList<>();


    public NavigationViewModel() {
        float[] oriVal = new float[]{0,0,0};
        orientation = oriVal;
        azimuth = new MutableLiveData<>();
        azimuth.setValue(orientation[0]);

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

    public void setLocations(ArrayList<Location> locs){
        locations = locs;
    }


    public void setOrientation(float[] value){
        orientation = value;
    }

    public void setAzimuth(){
        azimuth.setValue(orientation[0]);
    }

    public LiveData<Float> getAzimuth(){
        return azimuth;
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

}