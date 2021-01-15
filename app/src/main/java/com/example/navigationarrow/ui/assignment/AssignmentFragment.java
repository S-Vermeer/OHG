package com.example.navigationarrow.ui.assignment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.navigationarrow.*;


public class AssignmentFragment extends Fragment {
    AdventureActivity activity;
    CheckBox cb1;
    CheckBox cb2;
    CheckBox cb3;
    CheckBox cb4;
    CheckBox cb5;
    CheckBox cb6;
    CheckBox cb7;
    CheckBox cb8;
    Assignment currentAssignment;
    Adventure adventure;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_assignment, container, false);

        activity = (AdventureActivity) getActivity();
        adventure = activity.adventure;

        cb1 = root.findViewById(R.id.cbOptie1);
        cb2 = root.findViewById(R.id.cbOptie2);
        cb3 = root.findViewById(R.id.cbOptie3);
        cb4 = root.findViewById(R.id.cbOptie4);
        cb5 = root.findViewById(R.id.cbOptie5);
        cb6 = root.findViewById(R.id.cbOptie6);
        cb7 = root.findViewById(R.id.cbOptie7);
        cb8 = root.findViewById(R.id.cbOptie8);

        currentAssignment = getAssignment(0);


        return root;
    }

    public Assignment getAssignment(int id){
        Assignment a = (Assignment) adventure.getAssignmentsById(id);
        return a;
    }
}
