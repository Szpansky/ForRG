package com.apps.mkacik.forRG.LaunchPads;

import androidx.lifecycle.ViewModel;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.mkacik.forRG.DataModel.LaunchpadModel;

import java.util.ArrayList;
import java.util.List;

class LaunchPadsViewModel extends ViewModel {

    private List<LaunchpadModel> launchpadList = new ArrayList<>();
    private RecyclerView.Adapter adapter = new LaunchPadsAdapter(getLaunchpadList());
    private FragmentManager fragmentManager;

    public LaunchPadsViewModel() {
        this.launchpadList.clear();
    }

    void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    FragmentManager getFragmentManager() {
        return fragmentManager;
    }

    RecyclerView.Adapter getAdapter() {
        return adapter;
    }

    List<LaunchpadModel> getLaunchpadList() {
        return launchpadList;
    }

    void updateList(List<LaunchpadModel> newList) {
        ((LaunchPadsAdapter) adapter).updateLaunchpadsList(newList);
    }
}
