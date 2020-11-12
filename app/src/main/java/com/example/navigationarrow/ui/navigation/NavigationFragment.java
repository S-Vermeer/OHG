package com.example.navigationarrow.ui.navigation;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.navigationarrow.R;

public class NavigationFragment extends Fragment {
    private NavigationViewModel navigationViewModel;
    protected LocationManager locationManager;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        navigationViewModel = ViewModelProviders.of(this).get(NavigationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_navigation, container, false);
        final TextView textView = root.findViewById(R.id.text_navigation);
        navigationViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        return root;
    }


}