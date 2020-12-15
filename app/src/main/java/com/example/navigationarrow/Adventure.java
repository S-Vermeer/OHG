package com.example.navigationarrow;

import android.location.Location;

import java.sql.Time;
import java.util.ArrayList;

public class Adventure {
    private int adventureId;
    private ArrayList<Location> locations;
    private ArrayList<String> storyParts;
    private boolean completed;
    private long completionTime;
    private Dictionary dictionary;

    public Adventure(int id) {
        adventureId = id;
        completed = false;
        dictionary = new Dictionary();
        setLocations(getLocationsById(id));
        setStoryParts(getPartsById(id));
    }

    public void setLocations(ArrayList<Location> locs) {
        locations = locs;
    }

    public ArrayList<Location> getLocations() {
        return locations;
    }

    public int getAdventureId() {
        return adventureId;
    }

    public void setStoryParts(ArrayList<String> story) {
        storyParts = story;
    }

    public ArrayList<String> getStoryParts() {
        return storyParts;
    }

    public void setCompleted() {
        completed = !completed;
    }

    public boolean getCompleted() {
        return completed;
    }

    public void setCompletionTime(long comTime) {
        completionTime = comTime;
    }

    public long getCompletionTime() {
        return completionTime;
    }

    public ArrayList<Location> getLocationsById(int id) {
        ArrayList<Location> locationsToGo = new ArrayList<>();
        switch (id) {
            case 0:
                locationsToGo = dictionary.getLocationsHomeList();
                break;
            case 1:
                locationsToGo = dictionary.getLocationsSchoolList();
                break;
        }
        return locationsToGo;
    }

    public ArrayList<String> getPartsById(int id) {
        ArrayList<String> partsToRead = new ArrayList<>();
        switch (id) {
            case 0:
                partsToRead = dictionary.getStoryHomeList();
                break;
            case 1:
                partsToRead = dictionary.getStorySchoolList();
                break;
        }

        return partsToRead;
    }


}
