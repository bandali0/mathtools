package com.aminbandali.mathtools.app.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.aminbandali.mathtools.app.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by amin on 8/26/13.
 */
public class FeedbackFragment extends Fragment
{
    private static final String TAG = "FeedbackFragment";
    public FeedbackFragment()
    {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        final ListView listview = (ListView) view.findViewById(R.id.lVfeedback);
        ArrayList<Map<String, String>> list = buildData();
        String[] from = { "name", "desc" };
        int[] to = { android.R.id.text1, android.R.id.text2 };

        SimpleAdapter adapter = new SimpleAdapter(getActivity(), list,
                android.R.layout.simple_list_item_2, from, to);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        Uri uri = Uri.parse("market://details?id=" + getActivity().getApplicationContext().getPackageName());
                        Intent playintent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(playintent);
                        break;
                    case 1:
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com/+AminBandali")));
                        break;
                    case 2:
                        Intent twttrintent = null;
                        try {
                            // get the Twitter app if possible
                            getActivity().getPackageManager().getPackageInfo("com.twitter.android", 0);
                            twttrintent = new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=1254283670"));
                            twttrintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        } catch (Exception e) {
                            // no Twitter app, revert to browser
                            twttrintent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/aminban"));
                        }
                        startActivity(twttrintent);
                        break;
                    case 3:
                        Intent emailintent = new Intent(Intent.ACTION_SEND);
                        emailintent.setType("message/rfc822");
                        emailintent.putExtra(Intent.EXTRA_EMAIL, new String[]{"me@aminbandali.com"});
                        emailintent.putExtra(Intent.EXTRA_SUBJECT, "Math Tools Feedback");
                        emailintent.putExtra(Intent.EXTRA_TEXT, "Hi Amin, I use Math Tools and here's my feedback:\n\n");
                        try {
                            startActivity(Intent.createChooser(emailintent, "Shoot me an Email!"));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(getActivity(), "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                            Uri mailuri = Uri.parse("mailto://me@aminbandali.com");
                            Intent emailbrowserintent = new Intent(Intent.ACTION_VIEW, mailuri);
                            startActivity(emailbrowserintent);
                            break;
                        }
                    default:
                        break;
                }

            }
        });

        return view;
    }

    private ArrayList<Map<String, String>> buildData() {
        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        list.add(putData(getString(R.string.mt_on_gplay), getString(R.string.mt_on_gplay_desc)));
        list.add(putData(getString(R.string.mygplus), getString(R.string.mygplus_desc)));
        list.add(putData(getString(R.string.mytwitter), getString(R.string.mytwitter_desc)));
        list.add(putData(getString(R.string.sendmeanemail), getString(R.string.sendmeanemail_desc)));
        return list;
    }

    private HashMap<String, String> putData(String name, String desc) {
        HashMap<String, String> item = new HashMap<String, String>();
        item.put("name", name);
        item.put("desc", desc);
        return item;
    }

}