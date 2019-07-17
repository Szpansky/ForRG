package com.apps.mkacik.forRG.LaunchPadDetails;

import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apps.mkacik.forRG.DataModel.LaunchPadModelDetail;
import com.apps.mkacik.forRG.DetailsActivity;
import com.apps.mkacik.forRG.Dialogs.InfoWithRefresh;
import com.apps.mkacik.forRG.LoadingData.LoadingProvider;
import com.apps.mkacik.forRG.LoadingData.NetworkLoading;
import com.apps.mkacik.forRG.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.apps.mkacik.forRG.DetailsActivity.LAUNCHPAD_SITE_ID;

public class LaunchPadDetailFragment extends Fragment {

    public static final String NAME = LaunchPadDetailFragment.class.getName();

    LoadingProvider loadingProvider = new NetworkLoading();

    private String launchpadSiteId;
    private LaunchPadDetailViewModel viewModel;

    public static LaunchPadDetailFragment newInstance(String launchpadSiteId) {

        Bundle args = new Bundle();
        args.putString(LAUNCHPAD_SITE_ID, launchpadSiteId);
        LaunchPadDetailFragment fragment = new LaunchPadDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.descritpion)
    TextView description;
    @BindView(R.id.latitude)
    TextView latitude;
    @BindView(R.id.longitude)
    TextView longitude;
    @BindView(R.id.progressBar)
    View progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assert getArguments() != null;
        launchpadSiteId = getArguments().getString(DetailsActivity.LAUNCHPAD_SITE_ID);
        viewModel = ViewModelProviders.of(this).get(LaunchPadDetailViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_launchpad_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        viewModel.setFragmentManager(getFragmentManager());
        if (savedInstanceState != null && viewModel.getLaunchPadModelDetail() != null) {
            loadData(viewModel.getLaunchPadModelDetail());
        }else {
            loadData();
        }
    }

    private void loadData() {
        loadingProvider.loadData(launchpadSiteId, new LoadingProvider.ItemCallBack() {
            @Override
            public void onSuccess(LaunchPadModelDetail launchPadModelDetail) {
                viewModel.setLaunchPadModelDetail(launchPadModelDetail);
                name.setText(launchPadModelDetail.getLocation().getName());
                status.setText(launchPadModelDetail.getStatus());
                description.setText(launchPadModelDetail.getDetails());
                latitude.setText(String.valueOf(launchPadModelDetail.getLocation().getLatitude()));
                longitude.setText(String.valueOf(launchPadModelDetail.getLocation().getLongitude()));
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
                infoWithRefresh.show(viewModel.getFragmentManager(), InfoWithRefresh.NAME);
            }

            @Override
            public void onFinally() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onStart() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    private void loadData(LaunchPadModelDetail launchPadModelDetail) {
        name.setText(launchPadModelDetail.getLocation().getName());
        status.setText(launchPadModelDetail.getStatus());
        description.setText(launchPadModelDetail.getDetails());
        latitude.setText(String.valueOf(launchPadModelDetail.getLocation().getLatitude()));
        longitude.setText(String.valueOf(launchPadModelDetail.getLocation().getLongitude()));
    }
}
