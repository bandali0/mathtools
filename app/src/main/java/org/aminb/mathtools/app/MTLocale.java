package org.aminb.mathtools.app;

import java.util.Locale;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

public class MTLocale {
    static final String APP_LOCALE_TAG = "appLocale";
    static final String DEFAULT = "default";

    public static String getAppLocale(){
        return getPreferences().getString(APP_LOCALE_TAG, DEFAULT);
    }

    public static void setAppLocale(String language) {
        if (language == null || language.length() == 0 || language.equals(DEFAULT)) {
            language = getDeviceLocale();
        }
        Locale locale;
        if (language.contains("_")) {
            /* handle special language code, in language-country format */
            String array[] = language.split("_");
            locale = new Locale(array[0], array[1]);
        }
        else {
            locale = new Locale(language);
        }
        Locale.setDefault(locale);
        Configuration config = MTApplication.getInstance().getResources().getConfiguration();
        config.locale = locale;
        MTApplication.getInstance().getResources().updateConfiguration(config, MTApplication.getInstance().getResources().getDisplayMetrics());
    }

    public static String getDeviceLocale() {
        return Locale.getDefault().getLanguage();
    }

    private static SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(MTApplication.getInstance());
    }
}
