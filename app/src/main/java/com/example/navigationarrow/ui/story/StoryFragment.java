package com.example.navigationarrow.ui.story;

import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.navigationarrow.R;
import com.example.navigationarrow.ui.navigation.NavigationViewModel;

public class StoryFragment extends Fragment {
    private StoryViewModel storyViewmodel;
    protected LocationManager locationManager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        storyViewmodel = ViewModelProviders.of(getActivity()).get(StoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_story, container, false);


        return root;

    }
}
