package com.apps.mkacik.forRG.Dialogs;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.apps.mkacik.forRG.LaunchPads.LaunchPadsListFragment;
import com.apps.mkacik.forRG.R;

import java.io.Serializable;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class InfoWithRefresh extends DialogFragment {

    public static final String CALL_BACK = "CALL_BACK";
    public static final String NAME = LaunchPadsListFragment.class.getName();
    private CallBack callBack;

    public static InfoWithRefresh newInstance(CallBack callBack) {
        InfoWithRefresh infoWithRefresh = new InfoWithRefresh();
        infoWithRefresh.setStyle(STYLE_NO_TITLE, 0);
        Bundle args = new Bundle();
        args.putSerializable(CALL_BACK, callBack);
        infoWithRefresh.setArguments(args);
        return infoWithRefresh;
    }

    public interface CallBack extends Serializable {
        void onButtonClick();
    }

    @OnClick(R.id.refresh)
    void onRefreshClick() {
        callBack.onButtonClick();
        dismiss();
    }

    @OnClick(R.id.cancel)
    void onCancelClick() {
        dismiss();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.dialog_info_refresh, container, false);
        ButterKnife.bind(this, view);
        assert getArguments() != null;
        callBack = (CallBack) getArguments().getSerializable(CALL_BACK);
        return view;
    }
}
