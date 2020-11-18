package com.example.navigationarrow.ui.navigation;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NavigationViewModel extends ViewModel {
    private MutableLiveData<float[]> orientation;
    private MutableLiveData<Float> rotationAngle;
    private MediatorLiveData<String> readingsText;
    public NavigationViewModel() {
        orientation = new MutableLiveData<>();
        float[] oriVal = new float[]{0,0,0};
        orientation.setValue(oriVal);
        rotationAngle = new MutableLiveData<>();
        Float f = (float) 0;
        rotationAngle.setValue(f);
        setReadingsText();
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


    public String getText() {
        return readingsText.getValue();
    }

    public MutableLiveData<String> getOrientationValue() {
        return readingsText;
    }
}