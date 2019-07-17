package com.apps.mkacik.forRG.Api;

import com.apps.mkacik.forRG.DataModel.LaunchPadModelDetail;
import com.apps.mkacik.forRG.DataModel.LaunchpadModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SpaceXApi {

    @GET("v3/launchpads/")
    Call<List<LaunchpadModel>> allLaunchpadList();

    @GET("v3/launchpads/{site_id}")
    Call<LaunchPadModelDetail> getLaunchpad(@Path("site_id") String site_id);
}
