package com.apps.mkacik.forRG.LaunchPadDetails;

import androidx.lifecycle.ViewModel;
import androidx.fragment.app.FragmentManager;

import com.apps.mkacik.forRG.DataModel.LaunchPadModelDetail;

class LaunchPadDetailViewModel extends ViewModel {

    private LaunchPadModelDetail launchPadModelDetail;
    private FragmentManager fragmentManager;

    LaunchPadModelDetail getLaunchPadModelDetail() {
        return launchPadModelDetail;
    }

    void setLaunchPadModelDetail(LaunchPadModelDetail launchPadModelDetail) {
        this.launchPadModelDetail = launchPadModelDetail;
    }

    FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }
}
