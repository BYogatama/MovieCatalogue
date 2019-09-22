/*
 * Created by Bagus Yogatama on 7/14/19 2:49 PM
 * Copyright (c) 2019 . All rights reserved.
 * Last modified 7/14/19 2:49 PM
 */

package com.lonedev.moviecatalogue.ui.main.settings

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.lonedev.moviecatalogue.R
import com.lonedev.moviecatalogue.utils.Preferences

class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener,
    Preference.OnPreferenceClickListener {

    companion object {
        internal const val DAILY_REMINDER_KEY = "daily_reminder"
        internal const val LANGUAGE_KEY = "language"
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        val preferenceKey = preference?.key
        val isPreferenceOn = newValue as Boolean

        if (preferenceKey.equals(DAILY_REMINDER_KEY)) {
            if (isPreferenceOn) {
                Preferences.setupDailyReminder(activity)
                Toast.makeText(context, "Daily Reminder On", Toast.LENGTH_SHORT).show()
            } else {
                Preferences.disableDailyReminder(activity)
                Toast.makeText(context, "Daily Reminder Off", Toast.LENGTH_SHORT).show()
            }
        }

        return true

    }

    override fun onPreferenceClick(preference: Preference?): Boolean {
        val preferenceKey = preference?.key

        if (preferenceKey.equals(LANGUAGE_KEY)) {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }

        return true
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)

        val dailyReminder = findPreference<SwitchPreference>(DAILY_REMINDER_KEY)
        dailyReminder?.onPreferenceChangeListener = this

        val languageSettings = findPreference<Preference>(LANGUAGE_KEY)
        languageSettings?.onPreferenceClickListener = this

    }

}