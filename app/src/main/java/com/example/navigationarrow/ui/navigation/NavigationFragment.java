package com.example.navigationarrow.ui.navigation;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.example.navigationarrow.R;

public class NavigationFragment extends Fragment {
    private NavigationViewModel navigationViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        navigationViewModel = (NavigationViewModel) obtainFragmentViewModel(getActivity(), NavigationViewModel.class);
        //navigationViewModel = ViewModelProviders.of(getActivity()).get(NavigationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_navigation, container, false);
        final TextView sensorTextView = root.findViewById(R.id.gpsText2);
        final ImageView arrowImageView = root.findViewById(R.id.imageView);

        navigationViewModel.getOrientationValue().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                sensorTextView.setText(navigationViewModel.getText());
            }
        });
        navigationViewModel.getRotationAngle().observe(getViewLifecycleOwner(), new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                arrowImageView.setRotation(aFloat);
            }
        });

        return root;
    }

    protected final <T extends ViewModel> ViewModel obtainFragmentViewModel(@NonNull FragmentActivity fragment, @NonNull Class<T> modelClass) {
        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(fragment.getApplication());
        return new ViewModelProvider(fragment, factory).get(modelClass);
    }


}