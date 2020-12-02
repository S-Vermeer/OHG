package com.example.navigationarrow.ui.story;

import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.navigationarrow.AdventureActivity;
import com.example.navigationarrow.R;
import com.example.navigationarrow.ui.navigation.NavigationViewModel;
import org.w3c.dom.Text;

public class StoryFragment extends Fragment {
    private StoryViewModel storyModel;
    private TextView storyText;
    protected LocationManager locationManager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AdventureActivity activity = (AdventureActivity) getActivity();
        if(activity == null){
            activity = (AdventureActivity) this.getActivity();
        }
        storyModel = ViewModelProviders.of(activity).get(StoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_story, container, false);
        storyText = root.findViewById(R.id.storyTextView);


        storyText.setText(storyModel.getCurrentStory());

        return root;

    }
}
