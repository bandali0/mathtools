/*
 * FeedbackFragment.java
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

package org.aminb.mathtools.app.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.aminb.mathtools.app.R;
import org.aminb.mathtools.app.util.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;


public class FeedbackFragment extends BaseFragment
{
    @InjectView(R.id.lVfeedback) ListView listView;

    private static final String TAG = "FeedbackFragment";
    public FeedbackFragment()
    {
        super();
    }

    @Override
    public int getTitleResourceId() {
        return R.string.action_feedback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        ButterKnife.inject(this, view);

        ArrayList<Map<String, String>> list = buildData();
        String[] from = { "name", "desc" };
        int[] to = { android.R.id.text1, android.R.id.text2 };

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), list,
                android.R.layout.simple_list_item_2, from, to);
        listView.setAdapter(adapter);

        return view;
    }

    @OnItemClick(R.id.lVfeedback)
    void onItemClick(AdapterView<?> parent, View view,
                         int position, long id) {
        switch (position) {
            case 0:
                Uri uri = Uri.parse("market://details?id=" +
                        getActivity().getApplicationContext().getPackageName());
                Intent playintent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(playintent);
                break;
            case 1:
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://google.com/" + getString(R.string.my_google_plus))));
                break;
            case 2:
                Intent twttrintent = null;
                try {
                    // get the Twitter app if possible
                    getActivity().getPackageManager().getPackageInfo("com.twitter.android", 0);
                    twttrintent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("twitter://user?user_id=1254283670"));
                    twttrintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                } catch (Exception e) {
                    // no Twitter app, revert to browser
                    twttrintent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://twitter.com/" + getString(R.string.my_twitter)));
                }
                startActivity(twttrintent);
                break;
            case 3:
                Intent emailintent = new Intent(Intent.ACTION_SEND);
                emailintent.setType("message/rfc822");
                emailintent.putExtra(Intent.EXTRA_EMAIL, new String[]{getString(R.string.my_email)});
                emailintent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject));
                emailintent.putExtra(Intent.EXTRA_TEXT, getString(R.string.email_body,
                                                        Utils.GetAppVersion(getActivity())));
                try {
                    startActivity(Intent.createChooser(emailintent,
                                                        getString(R.string.shoot_me_an_email)));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), getString(R.string.no_email_apps_installed),
                                                                Toast.LENGTH_SHORT).show();
                    Uri mailuri = Uri.parse("mailto://" + getString(R.string.my_email));
                    Intent emailbrowserintent = new Intent(Intent.ACTION_VIEW, mailuri);
                    startActivity(emailbrowserintent);
                    break;
                }
            default:
                break;
        }
    }

    private ArrayList<Map<String, String>> buildData() {
        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        list.add(putData(getString(R.string.google_play), getString(R.string.leave_a_review)));
        list.add(putData(getString(R.string.google_plus), getString(R.string.my_google_plus)));
        list.add(putData(getString(R.string.twitter), getString(R.string.my_twitter)));
        list.add(putData(getString(R.string.email), getString(R.string.my_email)));
        return list;
    }

    private HashMap<String, String> putData(String name, String desc) {
        HashMap<String, String> item = new HashMap<String, String>();
        item.put("name", name);
        item.put("desc", desc);
        return item;
    }

}
