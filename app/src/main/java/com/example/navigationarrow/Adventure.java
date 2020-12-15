import android.location.Location;

import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;

public class Adventure {
    private int adventureId;
    private ArrayList<Location> locations;
    private ArrayList<String> storyParts;
    private boolean completed;
    private Time completionTime;

    public Adventure(int id){
        adventureId = id;
        completed = false;
    }

    public void setLocations(ArrayList<Location> locs){
        locations = locs;
    }

    public ArrayList<Location> getLocations(){
        return locations;
    }

    public void setStoryParts(ArrayList<String> story) {
        storyParts = story;
    }

    public void setCompleted(){
        completed = !completed;
    }

    public boolean getCompleted(){
        return completed;
    }

    public void setCompletionTime(Time comTime) {
        completionTime = comTime;
    }

    public Time getCompletionTime(){
        return completionTime;
    }
}
