package com.fengchi.TimeTravel.Plan.View;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fengchi.TimeTravel.R;
import com.fengchi.TimeTravel.Search.SearchActivity;
import com.fengchi.TimeTravel.Utils.LogUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlanFragment extends Fragment {
    @Bind(R.id.logo)
    ImageView logo;
    @Bind(R.id.tv_search)
    TextView tvSearch;
    @Bind(R.id.plan_search)
    LinearLayout planSearch;
    @Bind(R.id.rb_rb1)
    RadioButton rbRb1;
    @Bind(R.id.cutoff)
    RadioButton cutoff;
    @Bind(R.id.rb_rb2)
    RadioButton rbRb2;
    @Bind(R.id.rg_tab)
    RadioGroup rgTab;
    @Bind(R.id.v_zhong)
    View vZhong;
    ViewPager vpPlan;
    View view;
    Fragment[] fragments;
    DoFragment doFragment;//已确认
    UndoFragment undoFragment;//未确认
    @Bind(R.id.iv_bottom_line)
    ImageView ivBottomLine;
    private ArrayList<Fragment> fragmentsList;
    private int bottomLineWidth;
    private int offset = 0;
    private int currentIndex = 0;
    private int position_one;
    public final static int num = 2 ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plan, container, false);
        ButterKnife.bind(this, view);
        initView();
        InitWidth(view);
        LogUtil.error("position_one",position_one+"");
        LogUtil.error("offset",offset+"");
        TranslateAnimation animation = new TranslateAnimation(position_one, offset, 0, 0);
        animation.setFillAfter(true);
        animation.setDuration(300);
        ivBottomLine.startAnimation(animation);
        return view;
    }

    private void InitWidth(View view) {
        bottomLineWidth = ivBottomLine.getLayoutParams().width;
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        offset = (int) ((screenW / num - bottomLineWidth) / 2);
        int avg = (int) (screenW / num);
        position_one = avg + offset;
    }

    private void initView() {
        vpPlan = (ViewPager) view.findViewById(R.id.vp_plan);
        fragmentsList = new ArrayList<Fragment>();
        doFragment = new DoFragment();
        undoFragment = new UndoFragment();
        fragmentsList.add(undoFragment);
        fragmentsList.add(doFragment);
        vpPlan.setAdapter(new planPageAdapter(getChildFragmentManager(), fragmentsList));
        vpPlan.setCurrentItem(0);
        rbRb1.setChecked(true);
        vpPlan.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Animation animation = null;
                switch (position){
                    case 0:
                //if (currentIndex == 1) {
                    rbRb1.performClick();
                    animation = new TranslateAnimation(position_one, offset, 0, 0);
                //}
                 break;
                    case 1 :
                //if (currentIndex == 0) {
                    rbRb2.performClick();
                    animation = new TranslateAnimation(offset, position_one, 0, 0);
                 // }
                  break;
                }
                currentIndex = position;
                animation.setFillAfter(true);
                animation.setDuration(300);
                ivBottomLine.startAnimation(animation);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //planPageAdapter planpageAdapter = new planPageAdapter();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_search, R.id.rb_rb1, R.id.rb_rb2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.rb_rb1:
                vpPlan.setCurrentItem(0);
                break;
            case R.id.rb_rb2:
                vpPlan.setCurrentItem(1);
                break;
        }

    }


    public class planPageAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragmentsList;

        public planPageAdapter(FragmentManager fm) {
            super(fm);
        }

        public planPageAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);
            this.fragmentsList = fragments;
        }

        @Override
        public int getCount() {
            return fragmentsList.size();
        }

        @Override
        public Fragment getItem(int arg0) {
            return fragmentsList.get(arg0);
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

    }
}
