/*
 * MainActivity.java
 * Copyright (C) 2014 Amin Bandali <me@aminb.org>
 *
 * MATHTools is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MATHTools is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.aminb.mathtools.app.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import org.aminb.mathtools.app.R;
import org.aminb.mathtools.app.fragment.MainFragment;
import org.aminb.mathtools.app.util.Utils;


public class MainActivity extends BaseActivity {
    String prefsTAG = "MATHToolsPrefs";
    String appVersionTAG = "appVersion";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences(prefsTAG, MODE_PRIVATE);
        String appVersionPref = prefs.getString(appVersionTAG, "");
        String appVersion = Utils.GetAppVersion(this);
        if (!appVersionPref.equals(appVersion)) {
            Utils.showChangeLog(this);
            prefs.edit().putString(appVersionTAG, appVersion).apply();
        }

        setContentView(R.layout.activity_main);
        FragmentTransaction tx = getSupportFragmentManager()
                .beginTransaction();
        tx.replace(R.id.main, new MainFragment());
        tx.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_feedback:
                Intent intent_feedback = new Intent(this, FeedbackActivity.class);
                startActivity(intent_feedback);
                return true;
            case R.id.action_about:
                Utils.showAbout(this);
                return true;
            case R.id.action_changelog:
                Utils.showChangeLog(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        // Catch back action and pops from backstack
        // (if you called previously to addToBackStack() in your transaction)
        if (getSupportFragmentManager().getBackStackEntryCount() > 0){
            getSupportFragmentManager().popBackStack();
        }
        // Default action on back pressed
        else super.onBackPressed();
    }
}
