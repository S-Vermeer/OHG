package com.example.navigationarrow;

import android.location.Location;

import java.sql.Time;
import java.util.ArrayList;

public class Adventure {
    private int adventureId;
    private ArrayList<Location> locations;
    private ArrayList<String> storyParts;
    private boolean completed;
    private Time completionTime;

    public Adventure(int id) {
        adventureId = id;
        completed = false;
        setLocations(getLocationsById(id));
        setStoryParts(getPartsById(id));
    }

    public void setLocations(ArrayList<Location> locs) {
        locations = locs;
    }

    public ArrayList<Location> getLocations() {
        return locations;
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

    public void setCompletionTime(Time comTime) {
        completionTime = comTime;
    }

    public Time getCompletionTime() {
        return completionTime;
    }

    public ArrayList<Location> getLocationsById(int id) {
        ArrayList<Location> locationsToGo = new ArrayList<>();
        switch (id) {
            case 0:
                locationsToGo = locationsHomeList();
                break;
            case 1:
                locationsToGo = locationsSchoolList();
                break;
        }
        return locationsToGo;
    }

    public ArrayList<String> getPartsById(int id) {
        ArrayList<String> partsToRead = new ArrayList<>();
        switch (id) {
            case 0:
                partsToRead = getStoryHomeList();
                break;
            case 1:
                partsToRead = getStorySchoolList();
                break;
        }

        return partsToRead;
    }

    public ArrayList<String> getStoryHomeList() {
        ArrayList<String> story = new ArrayList<>();
        story.add("home1");
        story.add("home2");
        story.add("home3");
        story.add("home4");
        return story;
    }

    public ArrayList<String> getStorySchoolList() {
        ArrayList<String> story = new ArrayList<>();
        story.add("schoolSTART");
        story.add("school2");
        story.add("school3");
        story.add("school4");
        story.add("schoolEND");
        return story;
    }


    public ArrayList<Location> locationsHomeList() {
        ArrayList<Location> homeLocations = new ArrayList<>();
        Location loc1 = new Location("");
        Location loc2 = new Location("");
        Location loc3 = new Location("");
        Location loc4 = new Location("");

        loc1.setLatitude(51.5782d);
        loc1.setLongitude(5.0111d);
        homeLocations.add(loc1);

        loc2.setLatitude(51.5805d);
        loc2.setLongitude(5.0118d);
        homeLocations.add(loc2);

        loc3.setLatitude(51.5803d);
        loc3.setLongitude(5.0139d);
        homeLocations.add(loc3);

        loc4.setLatitude(51.5780d);
        loc4.setLongitude(5.0131d);
        homeLocations.add(loc4);

        return homeLocations;
    }

    public ArrayList<Location> locationsSchoolList() {
        ArrayList<Location> schoolLocations = new ArrayList<>();
        Location loc1 = new Location("");
        Location loc2 = new Location("");
        Location loc3 = new Location("");
        Location loc4 = new Location("");


        loc1.setLatitude(51.450936d);
        loc1.setLongitude(5.453675d);
        schoolLocations.add(loc1);

        loc2.setLatitude(51.451087d);
        loc2.setLongitude(5.453784d);
        schoolLocations.add(loc2);

        loc3.setLatitude(51.450738d);
        loc3.setLongitude(5.45363d);
        schoolLocations.add(loc3);

        loc4.setLatitude(51.451087d);
        loc4.setLongitude(5.453784d);
        schoolLocations.add(loc4);

        return schoolLocations;
    }
}
