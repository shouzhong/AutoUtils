package com.wyf.auto;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author wyf
 * 屏幕适配
 * 看了很多大神们的方案，最符合要求的是hongyang大神的方案，但是由于屏幕比例的问题（15：9，16：9，17：9等等），
 * 导致显示会变形，本方案是只取一边长度进行适配，在zhengjingle的AutoUtils上进行修改，
 * 只进行margin，padding，height，width，testSize进行适配，自定义属性请在代码中实现
 * 优点：1.不用考虑状态栏
 *      2.对于不同比例屏幕（16:9,17:9等等）展示时不会变形：如图片等
 *      3.简单，大大减少工作量，与传统适配方案相比，不需要写很多的适配文件，与百分比方案相比没了超大的计算量
 *      4.兼容性强，安卓碎片化严重，而且还在不断增加碎片，新的碎片产生时并不需要去重新适配
 *      5.体积小，就1个类
 * 缺点：1.对于不同比例屏幕（15::9,16:9,17:9等等）展示时屏幕上展示效果不同：如在16：9屏幕布局正好占满屏幕，
 *        但在高比屏幕上底部会留白而低比屏幕则会跑出屏幕，但考虑到大多数页面都是滑动页面，所有影响不是很大，
 *        如果信息不是很多，可以考虑页面居中设计，信息较多可以考虑让页面滑动
 *      2.耦合性强，当设计图纸改变后改的工作量大（例如原来图纸为720*1280，后面变为1080*1920），这个问题可能性还是很小的，
 *        除非人事变动，这时候你就要py交易了，当然你也可以在用到新图纸的地方重新设置设计尺寸或者写适配文件，将所有可能用到
 *        的px值列出来，样式改变时只要在适配文件中进行乘法或除法，不过这样会造成不好维护，建议在一个项目中还是使用统一样式
 *
 * 在Application中调用AutoUtils.setSize(this, 设计宽度或高度, isWidth)，如果发生屏幕旋转的情况，请重新调用;
 * 在Activity的setContent后调用AutoUtils.auto(this);
 * 在用到inflate加载布局的地方调用AutoUtils.auto(view)，如Fragment，ViewHolder中
 * 自定义属性适配：AutoUtils.getDisplayValue(designValue)
 * 特别注意：textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) displayPixels);要加TypedValue.COMPLEX_UNIT_PX属性
 *         或者直接调用AutoUtils.setTextSize()方法进行设置
 *
 * 建议：布局时建议是RelativeLayout，优点在于不管多复杂布局，大多数情况下一层就够了，提高性能
 *
 */
public class AutoUtils {

    private static int displayMaxValue;

    private static int designMaxValue;

    private static double textPixelsRate;

    /**
     * 初始化
     *
     * @param c
     * @param designMaxValue 设计图最大尺度
     * @param isWidth true为自适应规则为宽度，false为自适应规则为高度，考虑到多数app保留系统状态栏，如果自适应规则为高度会造成误差，因此建议自适应规则为宽度
     */
    public static void init(Context c, int designMaxValue, boolean isWidth) {
        if (c == null || designMaxValue < 1) return;

        DisplayMetrics dm = c.getResources().getDisplayMetrics();

        AutoUtils.displayMaxValue = isWidth ? dm.widthPixels : dm.heightPixels;

        AutoUtils.designMaxValue = designMaxValue;

        AutoUtils.textPixelsRate = AutoUtils.displayMaxValue * 1.0 / AutoUtils.designMaxValue;
    }

    /**
     * 对Activity进行布局自适应
     *
     * @param act
     */
    public static void auto(Activity act) {
        if (act == null || displayMaxValue < 1) return;
        View view = act.getWindow().getDecorView();
        auto(view);
    }

    /**
     * 对view进行布局自适应
     *
     * @param view
     */
    public static void auto(View view) {
        if (view == null || displayMaxValue < 1) return;

        AutoUtils.autoTextSize(view);
        AutoUtils.autoSize(view);
        AutoUtils.autoPadding(view);
        AutoUtils.autoMargin(view);

        if (view instanceof ViewGroup) {
            auto((ViewGroup) view);
        }

    }

    private static void auto(ViewGroup viewGroup) {
        int count = viewGroup.getChildCount();

        for (int i = 0; i < count; i++) {

            View child = viewGroup.getChildAt(i);

            if (child != null) {
                auto(child);
            }
        }
    }

    private static void autoMargin(View view) {
        if (!(view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams))
            return;

        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        if (lp == null) return;

        lp.leftMargin = getDisplayValue(lp.leftMargin);
        lp.topMargin = getDisplayValue(lp.topMargin);
        lp.rightMargin = getDisplayValue(lp.rightMargin);
        lp.bottomMargin = getDisplayValue(lp.bottomMargin);

    }

    private static void autoPadding(View view) {
        int l = view.getPaddingLeft();
        int t = view.getPaddingTop();
        int r = view.getPaddingRight();
        int b = view.getPaddingBottom();

        l = getDisplayValue(l);
        t = getDisplayValue(t);
        r = getDisplayValue(r);
        b = getDisplayValue(b);

        view.setPadding(l, t, r, b);
    }

    private static void autoSize(View view) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();

        if (lp == null) return;

        if (lp.width > 0) {
            lp.width = getDisplayValue(lp.width);
        }

        if (lp.height > 0) {
            lp.height = getDisplayValue(lp.height);
        }

    }

    private static void autoTextSize(View view) {
        if (view instanceof TextView) {
            double designPixels = ((TextView) view).getTextSize();
            double displayPixels = textPixelsRate * designPixels;
            ((TextView) view).setIncludeFontPadding(false);
            ((TextView) view).setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) displayPixels);
        }
    }

    /**
     * 设置字体大小
     *
     * @param tv TextView或其子类
     * @param designValue 设置尺度
     */
    public static void setTextSize(TextView tv, int designValue) {
        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float) (designValue * textPixelsRate));
    }

    /**
     * 获取字体大小
     *
     * @param designValue
     * @return
     */
    public static float getTextSize(int designValue) {
        return (float) (designValue * textPixelsRate);
    }

    /**
     * 获取展示的尺度
     *
     * @param designValue 设计尺度
     * @return
     */
    public static int getDisplayValue(int designValue) {
        if (designValue < 2) {
            return designValue;
        }
        return designValue * displayMaxValue / designMaxValue;
    }

}
