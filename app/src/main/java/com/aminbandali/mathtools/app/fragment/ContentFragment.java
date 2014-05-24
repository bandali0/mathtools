package com.aminbandali.mathtools.app.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aminbandali.mathtools.app.R;
import com.astuetz.PagerSlidingTabStrip;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

/**
 * Created by amin on 5/18/14.
 */
public class ContentFragment extends BaseFragment {

    private int titleId;
    private static String[] titles;

    public ContentFragment() {

    }

    public int getTitleResourceId() {
        return titleId;
    }

    private PagerSlidingTabStrip tabs;
    private ViewPager pager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        titleId = getArguments().getInt("titleId");
        titles = getArguments().getStringArray("titles");

        setRetainInstance(true);
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        setHasOptionsMenu(true);
        tabs.setIndicatorColor(0xFFC74B46);
        pager = (ViewPager) view.findViewById(R.id.pager);
        pager.setAdapter(new ContentPagerAdapter(getActivity().getSupportFragmentManager()));
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
				.getDisplayMetrics());
		pager.setPageMargin(pageMargin);
        tabs.setViewPager(pager);
//		getSherlockActivity().getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.holo_orange_light)));
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.content, menu);
        Drawable aboutDrawable = new IconDrawable(getActivity(), Iconify.IconValue.fa_book)
                .colorRes(R.color.ab_item)
                .actionBarSize();
        menu.findItem(R.id.action_cheat_sheet).setIcon(aboutDrawable);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_cheat_sheet:
                pager.setCurrentItem((titles.length)-1);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    public class ContentPagerAdapter extends FragmentStatePagerAdapter {


        public ContentPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position)
        {
            Bundle args = new Bundle();
            args.putInt("position", position);
            return Fragment.instantiate(getActivity(), VectorsFragment.class.getName(), args);
        }

        @Override
        public int getCount()
        {
            return titles.length;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return titles[position];
        }

    }

    public static class VectorsFragment extends BaseFragment
    {

        public int getTitleResourceId() {
            return R.string.vectors;
        }

        private static final String TAG = "VectorsFragment";
        public VectorsFragment()
        {
            super();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            View view = inflater.inflate(R.layout.page, container, false);
            View text = view.findViewById(android.R.id.text1);
            if(text != null && text instanceof TextView)
            {
                ((TextView)text).setText(titles[getArguments().getInt("position")]);
            }
            return view;
        }

    }
}
