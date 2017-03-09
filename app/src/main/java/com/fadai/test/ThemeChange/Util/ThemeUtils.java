package com.fadai.test.ThemeChange.Util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.fadai.test.ThemeChange.R;

/**
 * Created by miaoyongyong on 2017/1/1.
 */

public class ThemeUtils {

    /**
     * 当前页面设置状态栏颜色
     *
     * */
    public static void setWindowStatusBarColor(Activity activity, int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            View view=contentView.getChildAt(1);
            view.setBackgroundColor(color);
        } else {
            Window window=activity.getWindow();
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            状态栏
                window.setStatusBarColor(color);
//            底部导航栏
                window.setNavigationBarColor(color);
            }
        }


    }

//    Toolbar颜色
    public static void setToolbarColor(AppCompatActivity activity, int color){
        ActionBar actionBar=activity.getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(color));
    }


    public static Drawable getThemeColor(Context context, int attrRes) {
        TypedArray typedArray = context.obtainStyledAttributes(new int[]{attrRes});
        Drawable color = typedArray.getDrawable(0);
        typedArray.recycle();
        return color;
    }

    /**
     * android 4.4  设置状态栏颜色
     * 该方法是将View设置paddingTop值为状态栏高度，View延伸到顶部，造成状态栏颜色与toolbar统一的效果。
     * @params  Activity 实例
     * @params 要延伸到状态栏的View 一般为Toolbar,该View的layout_height属性建议为wrap_content，状态栏的颜色为该View的颜色
     * * */
    public static void initImmerseLayout(Activity context, View view) {
        if (context == null || view == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP) {
            Window window = context.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            int statusBarHeight = getStatusBarHeight(context.getBaseContext());
            view.setPadding(0, statusBarHeight, 0, 0);
        }
    }

    /**
     * 用于获取状态栏的高度。 使用Resource对象获取（推荐这种方式）
     * 国产定制系统一般不一样，所以动态获取。
     * @return 返回状态栏高度的像素值。
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * android 4.4  设置状态栏颜色
     * 该方法要是将界面总布局设置：android:fitsSystemWindows="true"。
     * 然后在toolbar上面放了一个状态栏高度的View，并设置颜色为参数的颜色
     * @params  Activity 实例
     * @params 设置的颜色
     * * */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void initStatusBarColor(Activity activity, int statusColor) {

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
//        {
//            if (statusColor != INVALID_VAL)
//            {
//                activity.getWindow().setStatusBarColor(statusColor);
//            }
//            return;
//        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
            View statusBarView = new View(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight(activity));
            statusBarView.setBackgroundColor(statusColor);
            contentView.addView(statusBarView, lp);
        }
    }

    public static int getPrimaryColor(Context context){
        TypedValue primaryValue=new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimary,primaryValue,true);
        return primaryValue.data;

    }
    public static int getPrimaryDarkColor(Context context){
        TypedValue primaryDarkValue=new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorPrimaryDark,primaryDarkValue,true);
        return primaryDarkValue.data;
    }
}
