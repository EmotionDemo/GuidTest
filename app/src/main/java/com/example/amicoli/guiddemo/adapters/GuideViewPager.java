package com.example.amicoli.guiddemo.adapters;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Amicoli on 2017/8/30.
 * author 李丰华
 * qq:739574055
 * sina:wallamico
 */

public class GuideViewPager extends PagerAdapter {

    private List<ImageView> imageview;
    private ImageView image;

    public GuideViewPager(List<ImageView> imageview) {
        this.imageview = imageview;
    }

    @Override
    public int getCount() {
        return imageview.size();
    }
    /**
     * @author 李丰华
     * @effect 在每次ViewPager需要一个用来显示的Object的时候，该方法都会被ViewPager.addNextItem()调用
     * @date 2017/4/14
     */
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        image = imageview.get(position);
        container.addView(image);
        return image;
    }


    /**
     * 创建当前的师徒
     * 参数为inis返回值结果
     * */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    /**
     * @author 李丰华
     * @effect 销毁的页面的位置
     * @date 2017/4/14
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);  必须注释掉！因为销毁的是view
        container.removeView((View) object);
    }

}
