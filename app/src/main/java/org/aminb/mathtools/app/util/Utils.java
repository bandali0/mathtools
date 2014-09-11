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
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.aminb.mathtools.app.R;

import it.gmariotti.changelibs.library.view.ChangeLogListView;


public class Utils {

    public static void showAbout(Activity activity) {

        FragmentManager fm = activity.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog_about");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        new AboutDialog().show(ft,"dialog_about");
    }

    public static void showHelp(Activity activity, int stringResId) {

        FragmentManager fm = activity.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog_help");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        HelpDialog h = new HelpDialog();
        h.helpTextResId = stringResId;
        h.show(ft,"dialog_help");
    }

    /**
     * About Dialog
     */
    public static class AboutDialog extends DialogFragment {

        public AboutDialog() {
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Get app version
            String versionName = Utils.GetAppVersion(getActivity());

            // Build the about body view and append the link to see OSS licenses
            //SpannableStringBuilder aboutBody = new SpannableStringBuilder();
            //aboutBody.append(Html.fromHtml(getString(R.string.about_body, versionName)));


            LayoutInflater layoutInflater = getActivity().getLayoutInflater();
            View rootView = layoutInflater.inflate(R.layout.dialog_about, null);
            TextView nameAndVersionView = (TextView) rootView.findViewById(
                    R.id.app_name_and_version);
            nameAndVersionView.setText(Html.fromHtml(
                    getString(R.string.title_about, versionName)));

            TextView aboutBodyView = (TextView) rootView.findViewById(R.id.about_body);
            aboutBodyView.setText(Html.fromHtml(getString(R.string.about_body)));
            aboutBodyView.setMovementMethod(new LinkMovementMethod());

            return new AlertDialog.Builder(getActivity())
                    //.setTitle(R.string.title_about)
                    .setView(rootView)
                    .setPositiveButton(R.string.about_ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }
                            }
                    )
                    .create();
        }
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

    /**
     * Help Dialog
     */
    public static class HelpDialog extends DialogFragment {

        public HelpDialog() {
        }

        public int helpTextResId;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {


            // Build the about body view and append the link to see OSS licenses
            //SpannableStringBuilder aboutBody = new SpannableStringBuilder();
            //aboutBody.append(Html.fromHtml(getString(R.string.about_body, versionName)));


            LayoutInflater layoutInflater = getActivity().getLayoutInflater();
            View rootView = layoutInflater.inflate(R.layout.dialog_about, null);
            TextView nameAndVersionView = (TextView) rootView.findViewById(
                    R.id.app_name_and_version);
            nameAndVersionView.setText(Html.fromHtml(
                    getString(R.string.title_help, getString(R.string.action_help))));

            TextView aboutBodyView = (TextView) rootView.findViewById(R.id.about_body);
            aboutBodyView.setText(Html.fromHtml(getString(helpTextResId)));
            aboutBodyView.setMovementMethod(new LinkMovementMethod());

            return new AlertDialog.Builder(getActivity())
                    //.setTitle(R.string.title_about)
                    .setView(rootView)
                    .setPositiveButton(R.string.about_ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }
                            }
                    )
                    .create();
        }
    }

    public static void showChangeLog(Activity activity) {
        FragmentManager fm = activity.getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("changelog_about");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        new ChangeLogDialog().show(ft,"changeLog_about");
    }

    /**
     * ChangeLogDialog
     */
    public static class ChangeLogDialog extends DialogFragment {
        public ChangeLogDialog() {
        }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            ChangeLogListView chgList=(ChangeLogListView)layoutInflater.inflate(R.layout.dialog_changelog, null);
            return new AlertDialog.Builder(getActivity())
                    .setTitle(R.string.changelog)
                    .setView(chgList)
                    .setPositiveButton(R.string.about_ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialog.dismiss();
                                }
                            }
                    )
                    .create();
        }
    }


    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

}