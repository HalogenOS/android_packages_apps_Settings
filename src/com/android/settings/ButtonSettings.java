/*
 * Copyright (C) 2010 The Android Open Source Project
 * Copyright (C) 2016 halogenOS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.UiModeManager;
import android.app.WallpaperManager;
import android.app.admin.DevicePolicyManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.SearchIndexableResource;
import android.provider.Settings;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.DropDownPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.MetricsProto.MetricsEvent;
import com.android.internal.view.RotationPolicy;
import com.android.settings.accessibility.ToggleFontSizePreferenceFragment;
import com.android.settings.dashboard.SummaryLoader;
import com.android.settings.search.BaseSearchIndexProvider;
import com.android.settings.search.Indexable;

import java.util.ArrayList;
import java.util.List;


public class ButtonSettings extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener,
        Preference.OnPreferenceClickListener,
        Indexable {
    private static final String TAG = ButtonSettings.class.getSimpleName();

    private static final String
        KEY_LONG_PRESS_HOME_BUTTON_ASSIST =
                    "long_press_home_button_assist";

    private SwitchPreference
        mLongPressHomeButtonAssistPreference
        ;

    @Override
    protected int getMetricsCategory() {
        return MetricsEvent.DISPLAY;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.button_settings);

        mLongPressHomeButtonAssistPreference = (SwitchPreference)
                findPreference(KEY_LONG_PRESS_HOME_BUTTON_ASSIST);
        mLongPressHomeButtonAssistPreference.setOnPreferenceChangeListener(this);
        
        
        updateState();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateState();
    }

    private void updateState() {
        updateState(KEY_LONG_PRESS_HOME_BUTTON_ASSIST);
    }

    private void updateState(String key) {
        switch(key) {
            case KEY_LONG_PRESS_HOME_BUTTON_ASSIST:
                mLongPressHomeButtonAssistPreference.setChecked(
                    Settings.System.getInt(getContentResolver(),
                        Settings.System.LONG_PRESS_HOME_BUTTON_BEHAVIOR, 0) == 2);
                break;
            default: break;
        }
    }
    
    @Override
    public boolean onPreferenceClick(Preference preference) {
        return onPreferenceChange(preference, null);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object objValue) {
        switch(preference.getKey()) {
            case KEY_LONG_PRESS_HOME_BUTTON_ASSIST:
                Settings.System.putInt(getContentResolver(),
                    Settings.System.LONG_PRESS_HOME_BUTTON_BEHAVIOR,
                        ((Boolean)objValue) ? 2 : 0);
                break;
            default: break;
        }
        updateState(preference.getKey());
        return true;
    }

    @Override
    protected int getHelpResource() {
        return R.string.help_uri_buttons;
    }

}
