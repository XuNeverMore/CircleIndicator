package com.qianyingjiuzhu.circleindicator;

import android.animation.ArgbEvaluator;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private CircleIndicator circleIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewPager = findViewById(R.id.view_pager);
        circleIndicator = findViewById(R.id.circle_indicator);

        ColorAdapter colorAdapter = new ColorAdapter(5);
        mViewPager.setAdapter(colorAdapter);
        circleIndicator.setUpWithPager(mViewPager);

    }

    private class ColorAdapter extends PagerAdapter{

        int pageSize;

        public ColorAdapter(int pageSize) {
            this.pageSize = pageSize;
        }

        @Override
        public int getCount() {
            return pageSize;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            ImageView imageView = new ImageView(container.getContext());

            ArgbEvaluator evaluator = new ArgbEvaluator();
            float fraction = (float)position/pageSize;
            Object evaluate = evaluator.evaluate(fraction, 0xffffffff, 0xff0000ff);
            imageView.setImageDrawable(new ColorDrawable((Integer) evaluate));

            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
