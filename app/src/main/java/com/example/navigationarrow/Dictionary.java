package com.example.navigationarrow;

import android.location.Location;

import java.util.ArrayList;

public class Dictionary {

    private ArrayList<String> storyHomeList;
    private ArrayList<String> storySchoolList;
    private ArrayList<Location> locationsHomeList;
    private ArrayList<Location> locationsSchoolList;


    public Dictionary(){
        initStoryHomeList();
        initStorySchoolList();
        initLocationsHomeList();
        initLocationsSchoolList();
    }

    private void initStoryHomeList() {
        ArrayList<String> story = new ArrayList<>();
        story.add("home1");
        story.add("home2");
        story.add("home3");
        story.add("home4");
        storyHomeList = story;
    }

    private void initStorySchoolList() {
        ArrayList<String> story = new ArrayList<>();
        story.add("schoolSTART");
        story.add("school2");
        story.add("school3");
        story.add("school4");
        story.add("schoolEND");
        storySchoolList = story;
    }

    public ArrayList<String> getStoryHomeList() {
        return storyHomeList;
    }


    public ArrayList<String> getStorySchoolList(){
        return storySchoolList;
    }


    private void initLocationsHomeList() {
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

        locationsHomeList = homeLocations;
    }

    private void initLocationsSchoolList() {
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

        locationsSchoolList = schoolLocations;
    }

    public ArrayList<Location> getLocationsHomeList() {
        return locationsHomeList;
    }

    public ArrayList<Location> getLocationsSchoolList(){
        return  locationsSchoolList;
    }
}
