package com.example.navigationarrow.ui.navigation;


import android.location.Location;
import android.view.View;
import android.widget.TextView;
import androidx.databinding.*;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Observable;

public class NavigationViewModel extends ViewModel {
    private MutableLiveData<float[]> orientation;
    private MutableLiveData<Float> rotationAngle;
    private MediatorLiveData<String> readingsText;
    private Location location;

    public String sensorText;


    public NavigationViewModel() {
        orientation = new MutableLiveData<>();
        float[] oriVal = new float[]{0,0,0};
        orientation.setValue(oriVal);
        rotationAngle = new MutableLiveData<>();
        Float f = (float) 0;
        rotationAngle.setValue(f);
        setReadingsText();
    }

    public String getSensorText() {
        return sensorText;
    }

    @InverseBindingAdapter(attribute = "android:text")
    public String setSensorText(String text) {;
        sensorText = text;
        return sensorText;
    }


    public void setReadingsText() {
        float[] currentOrientation = orientation.getValue();
        readingsText = new MediatorLiveData<>();
        readingsText.setValue(" Yaw: " + currentOrientation[0] + "\n Pitch: "
                + currentOrientation[1] + "\n Roll (not used): "
                + currentOrientation[2]);
    }



    public void setRotationAngle(float rotate){
        rotationAngle.setValue(rotate);
    }

    public MutableLiveData<Float> getRotationAngle(){
        return rotationAngle;
    }

    public void setOrientation(float[] value){
        orientation.setValue(value);
        setReadingsText();
    }


    public LiveData<String> getOrientationValue(){
        return readingsText;
    }

    public Location getLocation(){
        return location;
    }


    public void setLocation(Location location) {
        this.location = location;
        //update observer
    }
}