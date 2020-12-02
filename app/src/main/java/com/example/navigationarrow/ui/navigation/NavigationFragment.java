package com.example.navigationarrow.ui.navigation;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.*;
import com.example.navigationarrow.AdventureActivity;
import com.example.navigationarrow.LocationInteraction;
import com.example.navigationarrow.R;
import com.google.android.gms.location.*;

public class NavigationFragment extends Fragment {
    private NavigationViewModel navigationViewModel;

    private LocationInteraction locationInteraction;

    private Location lastLocation;


    TextView gpsTextView;
    Button reset;
    int currentLocationNumber;
    int lastLocationNumber;
    Location currentTarget;
    TextView locationIndex;
    ImageView home;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        navigationViewModel = (NavigationViewModel) obtainFragmentViewModel(getActivity(), NavigationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_navigation, container, false);


        gpsTextView = root.findViewById(R.id.gpsText);
        reset = root.findViewById(R.id.button2);
        locationIndex = root.findViewById(R.id.gpsText2);
        home = root.findViewById(R.id.imageView);



        reset.setOnClickListener(v -> resetButton(v));

        navigationViewModel = ViewModelProviders.of(this).get(NavigationViewModel.class);


        currentLocationNumber = 1;
        lastLocationNumber = navigationViewModel.locations.size();
        currentTarget = navigationViewModel.locations.get(currentLocationNumber - 1);

        //locationInteraction.locIntInit();

        return root;
    }



    public void resetButton(View view){

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Activity activity = (AdventureActivity) getActivity();
        locationInteraction = new LocationInteraction(activity);
        locationInteraction.locIntInit();
        locationInteraction.buildLocationSettingsRequest();
        locationInteraction.checkLocationUpdates();

        //AAAAAAH
        lastLocation = locationInteraction.curLoc.getValue();

        locationInteraction.curLoc.observe(getViewLifecycleOwner(), new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                locationChange(location);
            }
        });

        // Create the observer which updates the UI.

        //navigationViewModel.getOrientationValue().observe(getViewLifecycleOwner(), dataObserver);
    }

    @Override
    public void onResume() {
        super.onResume();
        locationChange(lastLocation);
    }

    protected final <T extends ViewModel> ViewModel obtainFragmentViewModel(@NonNull FragmentActivity fragment, @NonNull Class<T> modelClass) {
        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(fragment.getApplication());
        return new ViewModelProvider(fragment, factory).get(modelClass);
    }


    public void locationChange(Location location){
        String lat = locationInteraction.getLongOrLatitude(locationInteraction.getGPSValue(location,"lat"), "lat");
        String lon = locationInteraction.getLongOrLatitude(locationInteraction.getGPSValue(location,"long"), "long");
        gpsTextView.setText(lat + "\n" + lon);
        /* ᕙ(`▿´)ᕗ if the location has changed, the text should be updated to the corresponding coordinates.
        Currently also features longitude and latitude for control purposes ᕙ(`▿´)ᕗ */

        AdventureActivity activity = (AdventureActivity) getActivity();
        int locationsVisited = 10;
        if(activity != null) {
            locationsVisited = activity.locationsVisited;
        }

        locationIndex.setText(locationsVisited + 1 + "/" + 4);

        int calculateWidth = (int) (activity.getPercentageWalkingDone() * 2);

        ViewGroup.LayoutParams params = (ViewGroup.LayoutParams) home.getLayoutParams();
        params.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,calculateWidth, getResources().getDisplayMetrics());
// existing height is ok as is, no need to edit it
        home.setLayoutParams(params);

        if(calculateWidth > 200){
            navigationViewModel = (NavigationViewModel) obtainFragmentViewModel(getActivity(), NavigationViewModel.class);
        }

        //imageView.setRotation(directionNextCoordinate(location,location2));

    }



}