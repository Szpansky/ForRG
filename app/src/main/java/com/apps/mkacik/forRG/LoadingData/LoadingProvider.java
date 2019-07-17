package com.apps.mkacik.forRG.LoadingData;

import com.apps.mkacik.forRG.DataModel.LaunchPadModelDetail;
import com.apps.mkacik.forRG.DataModel.LaunchpadModel;

import java.util.List;

public interface LoadingProvider {

    void loadData(ListCallBack listCallBack);

    void loadData(String launchpadSiteId, ItemCallBack itemCallBack);

    interface ListCallBack {
        void onSuccess(List<LaunchpadModel> launchpadList);

        void onFail(Throwable t);

        void onFinally();

        void onStart();
    }


    interface ItemCallBack {
        void onSuccess(LaunchPadModelDetail launchPadModelDetail);

        void onFail(Throwable t);

        void onFinally();

        void onStart();
    }

}
