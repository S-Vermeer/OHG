package com.example.navigationarrow.ui.story;

import androidx.lifecycle.ViewModel;

public class StoryViewModel extends ViewModel {
    private int storyPartIndex;
    private String[] storyParts = {"a", "bb", "ccc", "dddd", "eeeee", "ffffff", "ggggggg"};

    public StoryViewModel() {
        storyPartIndex = 0;
    }

    public void updateStoryPartIndex() {
        storyPartIndex += 1;
    }

    public String getCurrentStory(){
        String story = "";
        for(int i = 0; i <= storyPartIndex; i++){
            story = story + storyParts[i] + "\n\n";
        }
        return story;
    }


}
