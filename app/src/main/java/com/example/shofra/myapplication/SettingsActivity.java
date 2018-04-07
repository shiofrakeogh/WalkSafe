package eva.walksafe;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.audiofx.BassBoost;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class SettingsActivity extends AppCompatPreferenceActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        //getListView().setBackgroundColor(Color.BLUE);


        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingScreen()).commit();
    }

    public static class SettingScreen extends PreferenceFragment  {
        @Override
        public void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_screen);
            //PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);

            Preference vibratorsettings = findPreference("vibration");
            vibratorsettings.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                public boolean onPreferenceChange(Preference pref, Object newValue) {
                    if (newValue.toString().equals("true")) {
                        pref.setDefaultValue(true);
                    } else {
                        pref.setDefaultValue(false);
                    }
                    return true;
                }
            });
        }


        @Override
        public void onResume() {
            //getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(listener);
            super.onResume();
            Preference vibratorsettings = findPreference("vibration");
            vibratorsettings.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                public boolean onPreferenceChange(Preference pref, Object newValue) {
                    if (newValue.toString().equals("true")) {
                        pref.setDefaultValue(true);
                    } else {
                        pref.setDefaultValue(false);
                    }
                    return true;
                }
            });
        }

        @Override
        public void onPause() {
            //getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(listener);
            super.onPause();
            Preference vibratorsettings = findPreference("vibration");
            vibratorsettings.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                public boolean onPreferenceChange(Preference pref, Object newValue) {
                    if (newValue.toString().equals("true")) {
                        pref.setDefaultValue(true);
                    } else {
                        pref.setDefaultValue(false);
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStackImmediate();
            //If the last fragment was removed then reset the title of main
            // fragment (if so the previous popBackStack made entries = 0).
            if (getFragmentManager().getBackStackEntryCount() == 0) {
                getSupportActionBar().setTitle(R.string.title_activity_settings);
            }
        } else {
            super.onBackPressed();
        }

        overridePendingTransition(R.anim.down_in, R.anim.down_out);
    }


}





