package com.fengchi.TimeTravel.Plan.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fengchi.TimeTravel.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DoFragment extends Fragment {

    @Bind(R.id.tv_confirm)
    TextView tvConfirm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_do, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.tv_confirm)
    public void onViewClicked() {
    }


}
