package com.example.navigationarrow.ui.story;

import android.location.Location;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class StoryViewModel extends ViewModel {
    private MutableLiveData<Integer> storyIndex;
    private ArrayList<String> storyParts;

    public StoryViewModel() {
        storyIndex = new MutableLiveData<>();
        storyIndex.setValue(0);
    }

    public void setStoryParts(ArrayList<String> parts) {
        storyParts = parts;
    }

    public LiveData<Integer> getStoryIndex() {
        return storyIndex;
    }

    public void updateStoryIndex() {
        int index = storyIndex.getValue();
        storyIndex.setValue(index + 1);
    }

    public String getStory() {
        String story = "";
        for (int i = 0; i <= storyIndex.getValue(); i++) {
            story = story + storyParts.get(i) + "\n\n";
        }

        return story;
    }
}
