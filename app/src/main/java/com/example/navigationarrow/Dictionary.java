package com.example.navigationarrow;

import android.location.Location;

import java.util.ArrayList;

public class Dictionary {

    private ArrayList<String> StoryHomeList;
    private ArrayList<String> StorySchoolList;
    private ArrayList<Location> LocationsHomeList;
    private ArrayList<Location> LocationsSchoolList;
    private ArrayList<Assignment> AssignmentList;


    public Dictionary(){
        initStoryHomeList();
        initStorySchoolList();
        initLocationsHomeList();
        initLocationsSchoolList();
        initAssignmentList();
    }

    private void initStoryHomeList() {
        ArrayList<String> story = new ArrayList<>();
        story.add("home1");
        story.add("home2");
        story.add("home3");
        story.add("home4");
        StoryHomeList = story;
    }

    private void initStorySchoolList() {
        ArrayList<String> story = new ArrayList<>();
        story.add("schoolSTART");
        story.add("school2");
        story.add("school3");
        story.add("school4");
        story.add("schoolEND");
        StorySchoolList = story;
    }

    public ArrayList<String> getStoryHomeList() {
        return StoryHomeList;
    }


    public ArrayList<String> getStorySchoolList(){
        return StorySchoolList;
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

        LocationsHomeList = homeLocations;
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

        LocationsSchoolList = schoolLocations;
    }

    public ArrayList<Location> getLocationsHomeList() {
        return LocationsHomeList;
    }

    public ArrayList<Location> getLocationsSchoolList(){
        return  LocationsSchoolList;
    }

    private void initAssignmentList(){
        ArrayList<String> multipleAnswerList = new ArrayList<>();
        multipleAnswerList.add("Answer1");
        multipleAnswerList.add("Answer2");
        multipleAnswerList.add("Answer3");
        multipleAnswerList.add("Answer4");
        multipleAnswerList.add("Answer5");

        ArrayList<String> boolAnswerList = new ArrayList<>();
        boolAnswerList.add("True");
        boolAnswerList.add("False");

        ArrayList<Integer> multipleCorrectList = new ArrayList<>();
        multipleCorrectList.add(0);
        multipleCorrectList.add(2);
        multipleCorrectList.add(4);

        AssignmentList.add(new Assignment(0,"Question1",multipleAnswerList,0));
        AssignmentList.add(new Assignment(0,"Question2",multipleAnswerList,1));

        AssignmentList.add(new Assignment(0,"Question3",multipleAnswerList,multipleCorrectList));
        AssignmentList.add(new Assignment(0,"Question4",multipleAnswerList,multipleCorrectList));
        AssignmentList.add(new Assignment(0,"Question5",multipleAnswerList,multipleCorrectList));

        AssignmentList.add(new Assignment(0,"Question6",boolAnswerList,0));
        AssignmentList.add(new Assignment(0,"Question7",boolAnswerList,0));
        AssignmentList.add(new Assignment(0,"Question8",boolAnswerList,0));
        AssignmentList.add(new Assignment(0,"Question9",boolAnswerList,1));
        AssignmentList.add(new Assignment(0,"Question10",boolAnswerList,1));

    }

    public ArrayList<Assignment> getAssignmentList(){
        return AssignmentList;
    }
}
