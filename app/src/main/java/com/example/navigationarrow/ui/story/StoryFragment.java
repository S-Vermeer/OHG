package com.example.navigationarrow.ui.story;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.navigationarrow.AdventureActivity;
import com.example.navigationarrow.R;
import com.example.navigationarrow.ui.navigation.NavigationViewModel;

import java.util.ArrayList;

public class StoryFragment extends Fragment {
    private StoryViewModel storyViewmodel;
    private int storyIndex;
    TextView story;
    AdventureActivity activity;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        storyViewmodel = ViewModelProviders.of(getActivity()).get(StoryViewModel.class);
        activity = (AdventureActivity) getActivity();
        ArrayList<String> parts = activity.getIntent().getStringArrayListExtra("EXTRA_STORYPARTS");
        storyViewmodel.setStoryParts(parts);

        View root = inflater.inflate(R.layout.fragment_story, container, false);
        story = root.findViewById(R.id.storyView);


        return root;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        storyViewmodel = ViewModelProviders.of(getActivity()).get(StoryViewModel.class);
        storyViewmodel.getStoryIndex().observe(this, index -> story.setText(storyViewmodel.getStory()));
    }
}
