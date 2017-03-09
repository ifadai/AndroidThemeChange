package com.fadai.test.ThemeChange;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by fadai on 2016/12/31.
 */
public class BaseActivity extends AppCompatActivity {


    private int theme;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onPreCreate();
    }

    private void onPreCreate() {
        sp= PreferenceManager.getDefaultSharedPreferences(this);
        theme=sp.getInt("theme_change", R.style.Theme7);
        setTheme(theme);

    }

//    当Activity 回调onRestart时（从上一个页面返回），检查当前主题是否已将被更改。
    @Override
    protected void onRestart() {
        super.onRestart();
        int newTheme = sp.getInt("theme_change", theme);
        if (newTheme != theme) {
            recreate();
        }
    }

}
