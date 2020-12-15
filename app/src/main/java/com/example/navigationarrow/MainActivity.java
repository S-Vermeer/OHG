//≧◉◡◉≦ TOFIX  ≧◉◡◉≦

package com.example.navigationarrow;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public boolean gotPermissions;
    private ArrayList<Location> locations;
    private ArrayList<String> parts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // call the super class onCreate to complete the creation of activity like
        // the view hierarchy
        super.onCreate(savedInstanceState);

        // recovering the instance state
        if (savedInstanceState != null) {
            //≧◉◡◉≦ TOFIX uncomment when game_state_key is found  ≧◉◡◉≦

            //gameState = savedInstanceState.getString(GAME_STATE_KEY);
        }

        // set the user interface layout for this activity
        // the layout file is defined in the project res/layout/main_activity.xml file
        setContentView(R.layout.activity_main);

    }

    //ᕙ(`▿´)ᕗ Go to different activity, as to not crowd the main ᕙ(`▿´)ᕗ

    public void toHomeAdventureActivity(View view){
        toAdventureActivity(view, 0);
    }

    public void toSettingsActivity(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void toSchoolAdventureActivity(View view){
        toAdventureActivity(view,1);
    }


    public void toAdventureActivity(View view, int id) {
        checkPermission();

        if(gotPermissions){
            Intent intent = new Intent(this, AdventureActivity.class);
            intent.putExtra("EXTRA_ID", id);
            startActivity(intent);
        } else {
            //≧◉◡◉≦ TOFIX Thrown exception (Exits app)  ≧◉◡◉≦
            //toAdventureActivity(view,id);
        }

    }



    public void checkPermission() {
        //ᕙ(`▿´)ᕗ Check whether the phone has permissions to access location ... ᕙ(`▿´)ᕗ
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
                // Check whether the phone has permission to access the internet... ᕙ(`▿´)ᕗ
                if (checkSelfPermission(Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
                    gotPermissions = true;
                } else {
                    //ᕙ(`▿´)ᕗ ... or request internet permission ᕙ(`▿´)ᕗ
                    gotPermissions = false;
                    ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.INTERNET,}, 2);
                }
            } else {
                // ᕙ(`▿´)ᕗ ... or request location permission ᕙ(`▿´)ᕗ
                gotPermissions = false;
                ActivityCompat.requestPermissions(this, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,}, 1);
            }

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Thanks for enabling the permission", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this,AdventureActivity.class);
                intent.putExtra("EXTRA_LOCATIONS", locations);
                startActivity(intent);

                //do something permission is allowed here....

            } else {
                Toast.makeText(this, "Please allow the Permission", Toast.LENGTH_SHORT).show();

            }

    }

    //≧◉◡◉≦ TOFIX GAME_STATE_KEY etc. How to access keys  ≧◉◡◉≦
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        //textView.setText(savedInstanceState.getString(TEXT_VIEW_KEY));
    }

    // invoked when the activity may be temporarily destroyed, save the instance state here
    @Override
    public void onSaveInstanceState(Bundle outState) {
        //outState.putString(GAME_STATE_KEY, gameState);
        //outState.putString(TEXT_VIEW_KEY, textView.getText());

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(outState);
    }


}