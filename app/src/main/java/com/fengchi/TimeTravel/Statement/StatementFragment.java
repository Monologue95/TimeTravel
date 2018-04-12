package com.fengchi.TimeTravel.Statement;


import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.fengchi.TimeTravel.MainActivity;
import com.fengchi.TimeTravel.R;
import com.fengchi.TimeTravel.Statement.Activity.InstallStatisticsActivity;
import com.fengchi.TimeTravel.Utils.TitleBarView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StatementFragment extends Fragment {
    @Bind(R.id.statement_titlebar)
    TitleBarView statementTitlebar;
    @Bind(R.id.quantitystatistics)
    RelativeLayout quantitystatistics;
    @Bind(R.id.performanceAccessment)
    RelativeLayout performanceAccessment;
    @Bind(R.id.update)
    RelativeLayout update;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_statement, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        statementTitlebar.setTitle("报表模块");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //ButterKnife.unbind(this);
        //ButterKnife.unbind(this);
    }

    @OnClick({R.id.quantitystatistics, R.id.performanceAccessment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.quantitystatistics:
                Intent intent =new Intent(getActivity(), InstallStatisticsActivity.class);
                getActivity().startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity(), quantitystatistics, "shareNames").toBundle());
                break;
            case R.id.performanceAccessment:
                break;
        }
    }
}
