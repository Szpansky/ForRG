package com.apps.mkacik.forRG;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.apps.mkacik.forRG.LaunchPads.LaunchPadsListFragment;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_activity_frame, LaunchPadsListFragment.newInstance(), LaunchPadsListFragment.NAME).commit();
        }
    }
}
