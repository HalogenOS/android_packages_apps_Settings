/*
 * Copyright (C) 2017 The halogenOS Project
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
package com.android.settings.deviceinfo;

import android.content.Context;
import android.os.SystemProperties;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.text.TextUtils;

import com.android.settings.R;
import com.android.settings.core.PreferenceController;

public class RevisionsPreferenceController extends PreferenceController {

    private static final String PROPERTY_MOD_REVISIONS = "ro.mod.revisons";
    private static final String KEY_MOD_REVISIONS = "revisions";

    public RevisionsPreferenceController(Context context) {
        super(context);
    }

    @Override
    public boolean isAvailable() {
        return !TextUtils.isEmpty(SystemProperties.get(PROPERTY_MOD_REVISIONS));
    }

    @Override
    public String getPreferenceKey() {
        return KEY_MOD_REVISIONS;
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
        final Preference pref = screen.findPreference(KEY_MOD_REVISIONS);
        if (pref == null) return;
        String revisionsString = SystemProperties.get(PROPERTY_MOD_REVISIONS);
        String cafRevision = "";
        String droidRevision = "";
        StringBuilder valueSummary = new StringBuilder();
        if (revisionsString.contains("caf")) {
            cafRevision = revisionsString.split(",")[0].split("=")[1];
        }
        if (revisionsString.contains("droid")) {
            droidRevision = revisionsString.split(",")[1].split("=")[1];
        }
        valueSummary.append(TextUtils.isEmpty(cafRevision) ? "" : "CAF: " + cafRevision + "\n");
        valueSummary.append(TextUtils.isEmpty(droidRevision) ? "" : "AOSP: " + droidRevision);
        pref.setSummary(valueSummary.toString());
    }
}

