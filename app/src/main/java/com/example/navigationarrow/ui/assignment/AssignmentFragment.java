package com.example.navigationarrow.ui.assignment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.navigationarrow.R;

public class AssignmentFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_assignment, container, false);

        return root;
    }

    public View getViewWithCheckboxes(View view){
        View newView = view;



        return newView;
    }
}
