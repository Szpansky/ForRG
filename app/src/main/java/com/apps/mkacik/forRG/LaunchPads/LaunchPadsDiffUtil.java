package com.apps.mkacik.forRG.LaunchPads;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.apps.mkacik.forRG.DataModel.LaunchpadModel;

import java.util.List;

public class LaunchPadsDiffUtil extends DiffUtil.Callback {

    private List<LaunchpadModel> newLaunchpadList;
    private List<LaunchpadModel> oldLaunchpadList;

    LaunchPadsDiffUtil(List<LaunchpadModel> oldLaunchpadList, List<LaunchpadModel> newLaunchpadList) {
        this.newLaunchpadList = newLaunchpadList;
        this.oldLaunchpadList = oldLaunchpadList;
    }

    @Override
    public int getOldListSize() {
        return oldLaunchpadList.size();
    }

    @Override
    public int getNewListSize() {
        return newLaunchpadList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldLaunchpadList.get(oldItemPosition).getId() == (newLaunchpadList.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldLaunchpadList.get(oldItemPosition).equals(newLaunchpadList.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
