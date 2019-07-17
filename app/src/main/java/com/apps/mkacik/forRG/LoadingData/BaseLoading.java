package com.apps.mkacik.forRG.LoadingData;

import androidx.annotation.CallSuper;

public abstract class BaseLoading implements LoadingProvider {

    @CallSuper
    public void loadData(ListCallBack listCallBack) {
        listCallBack.onStart();
    }

    @CallSuper
    public void loadData(String launchpadSiteId, ItemCallBack itemCallBack) {
        itemCallBack.onStart();
    }
}
