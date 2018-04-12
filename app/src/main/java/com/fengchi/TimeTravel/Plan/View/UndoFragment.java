package com.fengchi.TimeTravel.Plan.View;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.fengchi.TimeTravel.R;
import com.scwang.smartrefresh.header.DeliveryHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.R.layout.simple_list_item_2;

public class UndoFragment extends Fragment {


//    @Bind(R.id.recyclerview)
//    RecyclerView recyclerview;
    @Bind(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    View view;
    private BaseRecyclerAdapter<Void> mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_undo, container, false);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        ListView listView = (ListView) view.findViewById(R.id.listview);
        listView.setAdapter(mAdapter = new BaseRecyclerAdapter<Void>(simple_list_item_2) {
            @Override
            protected void onBindViewHolder(SmartViewHolder holder, Void model, int position) {
                holder.text(android.R.id.text1, String.format(Locale.CHINA, "第%02d条数据", position));
                holder.text(android.R.id.text2, String.format(Locale.CHINA, "这是测试的第%02d条数据", position));
                holder.textColorId(android.R.id.text2, R.color.colorTextAssistant);
            }
        });
        refreshLayout.setRefreshHeader(new DeliveryHeader(getActivity()));
        refreshLayout.setEnableAutoLoadmore(true);//开启自动加载功能（非必须）
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.refresh(initData());
                        refreshlayout.finishRefresh();
                        refreshlayout.setLoadmoreFinished(false);
                    }
                }, 2000);
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(final RefreshLayout refreshlayout) {
                refreshlayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.loadmore(initData());
                        refreshlayout.finishLoadmore();
                        if (mAdapter.getItemCount() > 60) {
                            Toast.makeText(getActivity(), "数据全部加载完毕", Toast.LENGTH_SHORT).show();
                            refreshlayout.setLoadmoreFinished(true);//将不会再次触发加载更多事件
                        }
                    }
                }, 2000);
            }
        });

        //触发自动刷新
        refreshLayout.autoRefresh();

//        refreshLayout.setEnableAutoLoadmore(true);
//        //refreshLayout.setRefreshHeader(new MaterialHeader(getActivity()).setShowBezierWave(true));
//        //设置 Footer 为 球脉冲
//        refreshLayout.setRefreshHeader(new CircleHeader(getActivity()));
//        //refreshLayout.setRefreshFooter(new BallPulseFooter(getActivity()).setSpinnerStyle(SpinnerStyle.Scale));
//        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(RefreshLayout refreshlayout) {
//                refreshlayout.finishRefresh(2000);
//            }
//        });
//        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
//            @Override
//            public void onLoadmore(RefreshLayout refreshlayout) {
//                refreshlayout.finishLoadmore(2000);
//            }
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    private Collection<Void> initData() {
        return Arrays.asList(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null);
    }
}
