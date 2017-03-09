package com.fadai.test.ThemeChange;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.fadai.test.ThemeChange.Util.ThemeUtils;

/**
 * Created by miaoyongyong on 2016/12/25.
 */

public class SettingActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("设置");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getFragmentManager().beginTransaction().replace(R.id.frame_content,new SettingPreferenceFragment()).commit();
        ThemeUtils.initStatusBarColor(SettingActivity.this,ThemeUtils.getPrimaryDarkColor(SettingActivity.this));
    }

//    当前页面，主题切换后，需要手动进行颜色修改。下面只修改了状态栏和ToolBar的颜色。
    public void setColor(){
        ThemeUtils.setToolbarColor(SettingActivity.this,ThemeUtils.getPrimaryColor(SettingActivity.this));
        ThemeUtils.setWindowStatusBarColor(SettingActivity.this,ThemeUtils.getPrimaryDarkColor(SettingActivity.this));

    }

    public static class SettingPreferenceFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{

        private int theme;
        private SharedPreferences sp;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_settings);
            sp= PreferenceManager.getDefaultSharedPreferences(getActivity());
            theme=sp.getInt("theme_change",R.style.Theme7);
        }



        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onDestroy() {
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
            super.onDestroy();

        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if(key.equals("theme_change")){
                int newTheme = sp.getInt("theme_change", theme);
                if (newTheme != theme && getActivity()!=null) {
                     SettingActivity settingActivity=(SettingActivity)getActivity();
                    settingActivity.setTheme(newTheme);
                    settingActivity.setColor();
                    this.onCreate(null);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);

    }
}
