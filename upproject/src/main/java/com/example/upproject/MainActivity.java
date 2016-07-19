package com.example.upproject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private FragmentPagerAdapter mAdapter;
    private List<Fragment> mDatas;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private int mScreen1_3;
    private ImageView mTabline;
    private int mCurrentPageIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();

        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams
                .FLAG_TRANSLUCENT_STATUS);

        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams
                .FLAG_TRANSLUCENT_NAVIGATION);
        setContentView(R.layout.activity_main);
        initTableLine();
        initView();
    }


    //初始化下划线
    private void initTableLine() {
        //用display获取当前屏幕宽
        mTabline= (ImageView) findViewById(R.id.tabline);
        Display display= getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics=new DisplayMetrics();
        display.getMetrics(outMetrics);
        mScreen1_3=outMetrics.widthPixels/3;
        ViewGroup.LayoutParams lp=  mTabline.getLayoutParams();
        lp.width=mScreen1_3;
        mTabline.setLayoutParams(lp);
    }

    //初始化控件
    public void initView(){

        //初始化数据：单词运行时间/功率/开关/容忍度
        SharedPreferences.Editor editor= getSharedPreferences("DATA_TABLE",MODE_WORLD_WRITEABLE).edit();
        editor.putString("TIME1","1");//五个电器的单次运行时间
        editor.putString("TIME2","1");
        editor.putString("TIME3","1");
        editor.putString("TIME4","1");
        editor.putString("TIME5","1");
        editor.putString("POWER1","1");//五个电器的功率
        editor.putString("POWER2","1");
        editor.putString("POWER3","1");
        editor.putString("POWER4","1");
        editor.putString("POWER5","1");
        editor.putString("STATE1","1");//电器的开关状态
        editor.putString("STATE2","1");
        editor.putString("STATE3","1");
        editor.putString("STATE4","1");
        editor.putString("STATE5","1");
        editor.putString("RONGREN1","1");//电器容忍级别
        editor.putString("RONGREN2","1");
        editor.putString("RONGREN3","1");
        editor.putString("RONGREN4","1");
        editor.putString("RONGREN5","1");
        editor.commit();

        mViewPager= (ViewPager) findViewById(R.id.viewpager);
        textView1= (TextView) findViewById(R.id.textview1);
        textView2= (TextView) findViewById(R.id.textview2);
        textView3= (TextView) findViewById(R.id.textview3);


        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(1);
            }
        });
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(2);
            }
        });

        mDatas=new ArrayList<Fragment>();
        Fragment1 fragment1=new Fragment1();
        Fragment2 fragment2=new Fragment2();
        Fragment3 fragment3=new Fragment3();
        mDatas.add(fragment1);
        mDatas.add(fragment2);
        mDatas.add(fragment3);
        mAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mDatas.get(position);
            }

            @Override
            public int getCount() {
                return mDatas.size();
            }
        };
        mViewPager.setAdapter(mAdapter);


        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            //滑动页面时调用
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                LinearLayout.LayoutParams lp= (LinearLayout.LayoutParams) mTabline.getLayoutParams();
                //从0到1滑动
                if (mCurrentPageIndex==0&&position==0){
                    lp.leftMargin= (int) (positionOffset*mScreen1_3+mCurrentPageIndex*mScreen1_3);
                }
                //从0到1滑动
                else if (mCurrentPageIndex==1&&position==0)
                {
                    lp.leftMargin= (int) (mCurrentPageIndex*mScreen1_3+(positionOffset-1)*mScreen1_3);
                }
                //从1到2滑动
                else if ((mCurrentPageIndex==1&&position==1))
                {
                    lp.leftMargin= (int) (mCurrentPageIndex*mScreen1_3+positionOffset*mScreen1_3);
                }
                //从2到1滑动
                else if (mCurrentPageIndex==2&&position==1)
                {
                    lp.leftMargin= (int) (mCurrentPageIndex*mScreen1_3+(positionOffset-1)*mScreen1_3);
                }
                mTabline.setLayoutParams(lp);
            }
            //切换fragment停留的页面调用
            @Override
            public void onPageSelected(int position) {
                resetTextView();
                switch (position){
                    case 0:
                        textView1.setTextColor(Color.parseColor("#008000"));
                        break;
                    case 1:
                        textView2.setTextColor(Color.parseColor("#008000"));
                        break;
                    case 2:
                        textView3.setTextColor(Color.parseColor("#008000"));
                        break;
                }
                mCurrentPageIndex=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void resetTextView() {
        textView1.setTextColor(Color.BLACK);
        textView2.setTextColor(Color.BLACK);
        textView3.setTextColor(Color.BLACK);
    }
}
