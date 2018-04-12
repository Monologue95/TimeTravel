package com.fengchi.TimeTravel;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.fengchi.TimeTravel.ActualProgress.ActualProcessFragment;
import com.fengchi.TimeTravel.Mine.MineFragment;
import com.fengchi.TimeTravel.Plan.View.PlanFragment;
import com.fengchi.TimeTravel.Statement.StatementFragment;
import com.fengchi.TimeTravel.Utils.StatusBarCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    int i=4;
    private long exitTime = 0;
    Fragment[] fragments;
    ActualProcessFragment actualProcessFragment;//计划周期
    PlanFragment planFragment;//进度反馈
    StatementFragment statementFragment;//报表
    MineFragment mineFragment ;//个人中心
    //按钮的数组，一开始第一个按钮被选中
    RadioButton[] tabs;
    int oldIndex;//用户看到的item
    int newIndex;//用户即将看到的item
    public static MainActivity instance=null;
    boolean intentFlag=false;
    private List<Map<String,String>> companyRegionList =new ArrayList<>();
    Map<String,String> companyRegionMap =new HashMap<>();
    //String a =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarCompat.compat(this, Color.parseColor("#538fe6"));
        instance = this;
       // Log.e("测试bugly",a);
//        companyRegionMap.put("1","1");
//   //     companyRegionList.add(companyRegionMap);
//        companyRegionMap.put("2","2");
////        companyRegionList.add(companyRegionMap);
//        companyRegionMap.put("3","3");
//        Set keys1 = companyRegionMap.keySet();
//        //companyRegionList.add(companyRegionMap);
//        LogUtil.error("companyRegionMap",companyRegionMap.toString());
//        LogUtil.error("companyRegionMap",keys1.toString());
//        List list1 = new ArrayList(keys1);
//        LogUtil.error("companyRegionMap",list1.get(0).toString());
//        LogUtil.error("companyRegionMap",companyRegionMap.get(list1.get(0).toString()));
        //初始化fragment
        actualProcessFragment=new ActualProcessFragment();
        planFragment =new PlanFragment();
        statementFragment=new StatementFragment();
        mineFragment=new MineFragment();
        //所有fragment的数组
        fragments=new Fragment[]{planFragment,actualProcessFragment,statementFragment,mineFragment};

        //设置按钮的数组
        tabs=new RadioButton[4];
        tabs[0]=(RadioButton) findViewById(R.id.rb_rb1);
        tabs[1]=(RadioButton) findViewById(R.id.rb_rb2);
        tabs[2]=(RadioButton) findViewById(R.id.rb_rb3);
        tabs[3]=(RadioButton) findViewById(R.id.rb_rb4);
        //界面初始显示第一个fragment;添加第一个fragment
        getSupportFragmentManager().beginTransaction().add(R.id.f1_content, fragments[0]).commit();
        //初始时，按钮1选中
        tabs[0].setChecked(true);



    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//      if(resultCode==103){
//          this.getSupportFragmentManager().beginTransaction().replace(R.id.f1_content, fragments[0]).commit();
//          tabs[0].setSelected(true);
//      }
//    }

    protected void onNewIntent(Intent intent) {
        //每次重新到前台就主动更新intent并保存，之后就能获取到最新的intent
        setIntent(intent);
        super.onNewIntent(intent);
        System.out.println("onNewIntent");
        intentFlag=true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    //按钮的点击事件:选中不同的按钮，不同的fragment显示
    public void onTabClicked(View view) {

        //点击按钮时，表示选中不同的项
        switch(view.getId()){
            case R.id.rb_rb1:
                newIndex=0;//选中第一项
                break;
            case R.id.rb_rb2:
                newIndex=1;//选中第二项
                break;
            case R.id.rb_rb3:
                newIndex=2;//选中第三项
                break;
            case R.id.rb_rb4:
                newIndex=3;//选中第四项
                break;
        }
        addshow(newIndex);
    }

    public void addshow(int newIndex){
        FragmentTransaction transaction;

        //如果选择的项不是当前选中项，则替换；否则，不做操作
        if(newIndex!=oldIndex){
            transaction=getSupportFragmentManager().beginTransaction();
            transaction.hide(fragments[oldIndex]);//隐藏当前显示项


            //如果选中项没有加过，则添加
            if(!fragments[newIndex].isAdded()){
                //添加fragment
                transaction.add(R.id.f1_content,fragments[newIndex]);
            }
            //显示当前选择项
            transaction.show(fragments[newIndex]).commit();
        }


        //之前选中的项，取消选中
        tabs[oldIndex].setSelected(false);
        //当前选择项，按钮被选中
        tabs[newIndex].setSelected(true);


        //当前选择项变为选中项
        oldIndex=newIndex;

    }



    //再按一次退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
        }
        return super.onTouchEvent(event);
    }
}
