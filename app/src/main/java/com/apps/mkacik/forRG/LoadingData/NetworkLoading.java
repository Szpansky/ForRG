package com.apps.mkacik.forRG.LoadingData;

import androidx.annotation.NonNull;

import com.apps.mkacik.forRG.Api.SpaceXApi;
import com.apps.mkacik.forRG.App.Constants;
import com.apps.mkacik.forRG.DataModel.LaunchPadModelDetail;
import com.apps.mkacik.forRG.DataModel.LaunchpadModel;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkLoading extends BaseLoading {
    SpaceXApi spaceXApi;

    public NetworkLoading() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.siteURL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        spaceXApi = retrofit.create(SpaceXApi.class);
    }

    @Override
    public void loadData(final ListCallBack listCallBack) {
        super.loadData(listCallBack);

        spaceXApi.allLaunchpadList().enqueue(new Callback<List<LaunchpadModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<LaunchpadModel>> call, @NonNull Response<List<LaunchpadModel>> response) {
                if (response.isSuccessful()) {
                    listCallBack.onSuccess(response.body());
                }
                listCallBack.onFinally();
            }

            @Override
            public void onFailure(@NonNull Call<List<LaunchpadModel>> call, @NonNull Throwable t) {
                listCallBack.onFail(t);
                System.out.println("faill" + t.getMessage());
                listCallBack.onFinally();
            }
        });
    }

    @Override
    public void loadData(String launchpadSiteId, final ItemCallBack itemCallBack) {
        super.loadData(launchpadSiteId, itemCallBack);

        spaceXApi.getLaunchpad(launchpadSiteId).enqueue(new Callback<LaunchPadModelDetail>() {
            @Override
            public void onResponse(@NonNull Call<LaunchPadModelDetail> call, @NonNull Response<LaunchPadModelDetail> response) {
                if (response.isSuccessful()) {
                    itemCallBack.onSuccess(response.body());
                }
                itemCallBack.onFinally();
            }

            @Override
            public void onFailure(@NonNull Call<LaunchPadModelDetail> call, @NonNull Throwable t) {
                itemCallBack.onFail(t);
                System.out.println("faill" + t.getMessage());
                itemCallBack.onFinally();
            }
        });

    }
}
