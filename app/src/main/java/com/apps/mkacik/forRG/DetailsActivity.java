package com.apps.mkacik.forRG;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.apps.mkacik.forRG.LaunchPadDetails.LaunchPadDetailFragment;
import com.apps.mkacik.forRG.LaunchPadMap.LaunchPadMapFragment;


public class DetailsActivity extends AppCompatActivity {

    public static final String LAUNCHPAD_SITE_ID = "LaunchpadSiteId";
    public static final String LAUNCHPAD_NAME = "LaunchpadName";
    public static final String LAUNCHPAD_LAT = "LaunchpadLat";
    public static final String LAUNCHPAD_LONG = "LaunchpadLong";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        String launchpadSiteId = getIntent().getStringExtra(LAUNCHPAD_SITE_ID);
        String launchpadName = getIntent().getStringExtra(LAUNCHPAD_NAME);
        Double launchpadLat = getIntent().getDoubleExtra(LAUNCHPAD_LAT, 0.0);
        Double launchpadLong = getIntent().getDoubleExtra(LAUNCHPAD_LONG, 0.0);

        setTitle(launchpadName);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_frame_top, LaunchPadDetailFragment.newInstance(launchpadSiteId), LaunchPadDetailFragment.NAME).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_frame_bottom, LaunchPadMapFragment.newInstance(launchpadName, launchpadLat, launchpadLong), LaunchPadMapFragment.NAME).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back_menu: {
                super.onBackPressed();
                return true;
            }
            default:
                return false;
        }
    }

}
