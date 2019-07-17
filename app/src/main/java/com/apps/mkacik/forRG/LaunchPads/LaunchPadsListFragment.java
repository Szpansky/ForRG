package com.apps.mkacik.forRG.LaunchPads;


import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.mkacik.forRG.DataModel.LaunchpadModel;
import com.apps.mkacik.forRG.DetailsActivity;
import com.apps.mkacik.forRG.Dialogs.InfoWithRefresh;
import com.apps.mkacik.forRG.LoadingData.LoadingProvider;
import com.apps.mkacik.forRG.LoadingData.NetworkLoading;
import com.apps.mkacik.forRG.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.apps.mkacik.forRG.DetailsActivity.LAUNCHPAD_LAT;
import static com.apps.mkacik.forRG.DetailsActivity.LAUNCHPAD_LONG;
import static com.apps.mkacik.forRG.DetailsActivity.LAUNCHPAD_NAME;
import static com.apps.mkacik.forRG.DetailsActivity.LAUNCHPAD_SITE_ID;

public class LaunchPadsListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, LaunchPadsAdapter.ClicksCallBack {

    public static final String NAME = LaunchPadsListFragment.class.getName();

    LaunchPadsViewModel launchPadsViewModel;

    private static final int LIST_SPAN_COUNT = 2;
    private static final int LIST_ORIENTATION = StaggeredGridLayoutManager.VERTICAL;
    StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(LIST_SPAN_COUNT, LIST_ORIENTATION);


    private LoadingProvider loadingProvider = new NetworkLoading();

    public static LaunchPadsListFragment newInstance() {

        Bundle args = new Bundle();

        LaunchPadsListFragment fragment = new LaunchPadsListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.item_list_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    View progressBar;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        launchPadsViewModel = ViewModelProviders.of(this).get(LaunchPadsViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_launchpads_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        if (savedInstanceState == null) loadData();

        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(launchPadsViewModel.getAdapter());
        swipeRefresh.setOnRefreshListener(this);
        ((LaunchPadsAdapter) launchPadsViewModel.getAdapter()).setOnClicksListener(this);

        launchPadsViewModel.setFragmentManager(getFragmentManager());
    }

    private void loadData() {
        loadingProvider.loadData(new LoadingProvider.ListCallBack() {
            @Override
            public void onSuccess(List<LaunchpadModel> launchpadList) {
                launchPadsViewModel.updateList(launchpadList);
            }

            @Override
            public void onFail(Throwable t) {
                t.printStackTrace();
                InfoWithRefresh infoWithRefresh = InfoWithRefresh.newInstance(new InfoWithRefresh.CallBack() {
                    @Override
                    public void onButtonClick() {
                        loadData();
                    }
                });
                infoWithRefresh.show(launchPadsViewModel.getFragmentManager(), InfoWithRefresh.NAME);
            }

            @Override
            public void onFinally() {
                progressBar.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);
            }

            @Override
            public void onStart() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onRefresh() {
        loadData();
    }

    @Override
    public void onItemClick(LaunchpadModel launchpadModel) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra(LAUNCHPAD_SITE_ID, launchpadModel.getSite_id());
        intent.putExtra(LAUNCHPAD_NAME, launchpadModel.getLocation().getName());
        intent.putExtra(LAUNCHPAD_LAT, launchpadModel.getLocation().getLatitude());
        intent.putExtra(LAUNCHPAD_LONG, launchpadModel.getLocation().getLongitude());

        startActivity(intent);
    }
}
