/*
 * Utils.java
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

package org.aminb.mathtools.app.util;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;

public class Utils {

    public static void showAbout(Activity activity) {

        FragmentManager fm = activity.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog_about");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        new Dialogs.AboutDialog().show(ft,"dialog_about");
    }

    public static void showHelp(Activity activity, int stringResId) {

        FragmentManager fm = activity.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog_help");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        Dialogs.HelpDialog h = new Dialogs.HelpDialog();
        h.helpTextResId = stringResId;
        h.show(ft,"dialog_help");
    }

    public static void showChangeLog(Activity activity) {
        FragmentManager fm = activity.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("changelog_about");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        new Dialogs.ChangeLogDialog().show(ft,"changeLog_about");
    }

    private static final String VERSION_UNAVAILABLE = "N/A";
    public static String GetAppVersion(Activity activity) {
        // Get app version
        PackageManager pm = activity.getPackageManager();
        String packageName = activity.getPackageName();
        String versionName;

        try {
            PackageInfo info = pm.getPackageInfo(packageName, 0);
            versionName = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            versionName = VERSION_UNAVAILABLE;
        }

        return versionName;
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

}