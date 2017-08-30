package com.example.amicoli.guiddemo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.RelativeLayout;

import com.example.amicoli.guiddemo.R;
import com.example.amicoli.guiddemo.utils.CacheUtils;

public class SplashActivity extends Activity {

    public static final String START_MAIN = "start_main";
    private RelativeLayout rl_splash;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initSplashLayout();
    }

    /**
     * 初始化启动页
     * */
    private void initSplashLayout() {
        rl_splash = (RelativeLayout) findViewById(R.id.rl_splash);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0,1); //设置渐变
        alphaAnimation.setFillAfter(true);
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(alphaAnimation);
        set.setDuration(2000);
        rl_splash.startAnimation(set);
        set.setAnimationListener(new MyAnimationListener());
    }

    /**
     * @author 李丰华
     * @effect 创建动画监听器
     * @date 2017/4/15
     */
    class MyAnimationListener implements Animation.AnimationListener{

        @Override
        public void onAnimationStart(Animation animation) {

        }
        @Override
        public void onAnimationEnd(Animation animation) {
            boolean isStartMain = CacheUtils.getbBoolean(SplashActivity.this,START_MAIN);
            if (isStartMain){
                //进入主界面
                intent = new Intent(SplashActivity.this,MainActivity.class);
            }else {
                intent = new Intent(SplashActivity.this,GuidActivity.class);
            }
            startActivity(intent);
            finish();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
