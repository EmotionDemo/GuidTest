package com.example.amicoli.guiddemo.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.amicoli.guiddemo.R;
import com.example.amicoli.guiddemo.adapters.GuideViewPager;
import com.example.amicoli.guiddemo.utils.CacheUtils;

import java.util.ArrayList;
import java.util.List;

public class GuidActivity extends Activity {


    private ViewPager viewPager;
    private Button button;
    private LinearLayout linearLayout;

    private List<ImageView> guideImage;
    private ImageView imageview,iv_point_red;
    private int [] phoIds;
    private int leftmax; // 两点之间的间距

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guid);
        initViews();
        initViewPager();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CacheUtils.putBoolean(GuidActivity.this,MainActivity.START_MAIN,true);
                Intent intent = new Intent(GuidActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * @author 李丰华
     * @effect 初始化ViwePager
     * @date 2017/4/14
     */
    private void initViewPager() {
        phoIds = new int []{R.drawable.bg_android,R.drawable.bg_js,R.drawable.bg_other};
        guideImage = new ArrayList<>();
        for (int i = 0; i<phoIds.length;i++){
            imageview = new ImageView(this);
            imageview.setImageResource(phoIds[i]);
            guideImage.add(imageview);

            /**
             * 设置点
             * */
            ImageView point = new ImageView(this);
            /**
             * 单位是像素
             * */
            point.setBackgroundResource(R.drawable.point_normal);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(28,28);
            if(i != 0){
                params.leftMargin = 20;
            }
            point.setLayoutParams(params);
            linearLayout.addView(point);
            /**
             * 红点移动 params.leftMargin = 动态值
             * 两点之间移动的距离/间距 = 屏幕滑动的距离/屏幕宽
             * 屏幕滑动的距离/屏幕宽 = 屏幕滑动百分比（已知）
             *
             * 两点之间移动的距离 = 屏幕滑动百分比 * 间距
             *
             * params.leftMargin = 两点之间对应坐标
             *
             *
             * */

            // 根据View的生命周期，当视图执行到OnLayout的时候，获取到视图的高宽以及边距
            iv_point_red.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());
            //得到屏幕滑动百分比
            viewPager.addOnPageChangeListener(new MyOnPageChangeListener());
        }
        viewPager.setAdapter(new GuideViewPager(guideImage));
    }
    /**
     * @author 李丰华
     * @effect 创建viewpager滑动监听得到屏幕百分比
     * @date 2017/4/14
     */
    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{


        /**
         *当页面滑动时回调
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // int leftmargin = (int) (positionOffset * leftmax);

            int leftmargin = (int) (position*leftmax+(positionOffset*leftmax));
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv_point_red.getLayoutParams();
            params.leftMargin = leftmargin;
            iv_point_red.setLayoutParams(params);

        }
        /**
         * 当页面被选中时回调！
         * */
        @Override
        public void onPageSelected(int position) {
            if (position == guideImage.size()-1){
                //最后一个页面
                button.setVisibility(View.VISIBLE);
            }else {
                button.setVisibility(View.GONE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    /**
     * @author 李丰华
     * @effect 创建一个MyOnGlobalLayoutListener监听器
     * @date 2017/4/14
     */
    class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        @Override
        public void onGlobalLayout() {
            //执行很多次
            iv_point_red.getViewTreeObserver().removeOnGlobalLayoutListener(MyOnGlobalLayoutListener.this);
            leftmax = linearLayout.getChildAt(1).getLeft() - linearLayout.getChildAt(0).getLeft();

        }
    }

    /**
     * @author 李丰华
     * @effect 初始化控件们
     * @date 2017/4/14
     */
    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        button = (Button) findViewById(R.id.btn_start_main);
        linearLayout = (LinearLayout) findViewById(R.id.ll_point_group);
        iv_point_red = (ImageView) findViewById(R.id.iv_point_red);
    }
}
