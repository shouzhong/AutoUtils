package com.wyf;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.TextView;

import com.wyf.auto.AutoUtils;

/**
 *
 * 你还可能在PopupWindow，Dialog等等中使用，请参考Fragment和ViewHolder的使用
 *
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 在Activity中使用
        AutoUtils.auto(this);
        ViewPager vp = (ViewPager) findViewById(R.id.vp);
        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return new TestFragment();
            }

            @Override
            public int getCount() {
                return 1;
            }
        });
        TextView tv = (TextView) findViewById(R.id.tv3);
        // 模拟自定义属性使用
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, AutoUtils.getTextSize(40));
    }
}
