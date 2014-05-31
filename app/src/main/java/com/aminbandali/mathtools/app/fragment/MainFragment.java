/*
 * MainFragment.java
 * Copyright (C) 2014 Amin Bandali <me@aminbandali.com>
 *
 * MathTools is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MathTools is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.aminbandali.mathtools.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.IconTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aminbandali.mathtools.app.R;
import com.aminbandali.mathtools.app.activity.ContentActivity;
import com.aminbandali.mathtools.app.activity.FeedbackActivity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;


public class MainFragment extends BaseFragment{
    @InjectView(R.id.fragment_main_cardslist)
    ListView listView;

    @Override
    public int getTitleResourceId() {
        return R.string.app_name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initCards();
    }

    private void initCards() {

        // Init an array of Cards
        ArrayList<Card> cards = new ArrayList<Card>();

        cards = addToolsCards(cards);

        CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(),cards);

        if (listView!=null){
            listView.setAdapter(mCardArrayAdapter);
        }
    }

    private ArrayList<Card> addToolsCards(ArrayList<Card> cards) {
        // Vectors
        MainSmallCard card = new MainSmallCard(this.getActivity());
        card.setTitle(getString(R.string.vectors));
        card.count = 1;
        card.setIconTextViewTextResource(R.string.fa_vectors);
        card.setIconTextViewTextColor(0xFFFF4444);
        card.init();
        cards.add(card);

        // Geometry
        card = new MainSmallCard(this.getActivity());
        card.setTitle(getString(R.string.geometry));
        card.count = 2;
        card.setIconTextViewTextResource(R.string.fa_geometry);
        card.setIconTextViewTextColor(0xFF33B5E5);
        card.init();
        cards.add(card);

        // Trigonometry
        card = new MainSmallCard(this.getActivity());
        card.setTitle(getString(R.string.trigonometry));
        card.count = 3;
        card.setIconTextViewTextResource(R.string.fa_trigonometry);
        card.setIconTextViewTextColor(0xFFFFBB33);
        card.init();
        cards.add(card);

        // Calculus
        card = new MainSmallCard(this.getActivity());
        card.setTitle(getString(R.string.calculus));
        card.count = 4;
        card.setIconTextViewTextResource(R.string.fa_calculus);
        card.setIconTextViewTextColor(0xFF99CC00);
        card.init();
        cards.add(card);

        return cards;

    }

    public class MainSmallCard extends Card {

        protected TextView mTitle;
        protected int iconTextViewTextResource;
        protected int iconTextViewTextColor;
        protected int count;

        protected String title;


        public MainSmallCard(Context context) {
            this(context, R.layout.card_inner_content);
        }

        public MainSmallCard(Context context, int innerLayout) {
            super(context, innerLayout);
            //init();
        }

        private void init() {

            switch (count) {
                case 1: // Vectors
                    // Add ClickListener
                    setOnClickListener(new OnCardClickListener() {
                        @Override
                        public void onClick(Card card, View view) {
//                            FragmentTransaction tx = getActivity().getSupportFragmentManager()
//                                    .beginTransaction();
//                            tx.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
//                            Bundle args = new Bundle();
//                            args.putInt("titleId", R.string.vectors);
//                            args.putStringArray("titles", new String[]{"Products", "Projection", "Lines", "Planes", "Cheat Sheet"});
//                            args.putInt("fragmentColor", 0xFFCC0000);
//                            args.putString("className", VectorsFragment.class.getName());
//                            tx.addToBackStack(null);
//                            tx.replace(R.id.main, ContentFragment.instantiate(getContext(), ContentFragment.class.getName(), args));
//                            tx.commit();
                            Intent intent_content = new Intent(mContext, ContentActivity.class);
                            startActivity(intent_content);
                        }
                    });
                    break;

                case 2: // Geometry
                    setOnClickListener(new OnCardClickListener() {
                        @Override
                        public void onClick(Card card, View view) {
                            FragmentTransaction tx = getActivity().getSupportFragmentManager()
                                    .beginTransaction();
                            tx.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                            Bundle args = new Bundle();
                            args.putInt("titleId", R.string.geometry);
                            // TODO: Geometry titles
                            args.putStringArray("titles", new String[]{"Products", "Projection", "Lines", "Planes", "Cheat Sheet"});
                            args.putInt("fragmentColor", 0xFF33B5E5);
                            args.putString("className", VectorsFragment.class.getName()); // TODO: GeometryFragment
                            tx.addToBackStack(null);
                            tx.replace(R.id.main, ContentFragment.instantiate(getContext(), ContentFragment.class.getName(), args));
                            tx.commit();
                        }
                    });
                    break;

                case 3: // Trigonometry
                    setOnClickListener(new OnCardClickListener() {
                        @Override
                        public void onClick(Card card, View view) {
                            FragmentTransaction tx = getActivity().getSupportFragmentManager()
                                    .beginTransaction();
                            tx.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                            Bundle args = new Bundle();
                            args.putInt("titleId", R.string.trigonometry);
                            // TODO: Trigonometry titles
                            args.putStringArray("titles", new String[]{"Products", "Projection", "Lines", "Planes", "Cheat Sheet"});
                            args.putInt("fragmentColor", 0xFFFFBB33);
                            args.putString("className", VectorsFragment.class.getName()); // TODO: TrigonometryFragment
                            tx.addToBackStack(null);
                            tx.replace(R.id.main, ContentFragment.instantiate(getContext(), ContentFragment.class.getName(), args));
                            tx.commit();
                        }
                    });
                    break;

                case 4: // Calculus
                    setOnClickListener(new OnCardClickListener() {
                        @Override
                        public void onClick(Card card, View view) {
                            FragmentTransaction tx = getActivity().getSupportFragmentManager()
                                    .beginTransaction();
                            tx.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                            Bundle args = new Bundle();
                            args.putInt("titleId", R.string.calculus);
                            // TODO: Calculus titles
                            args.putStringArray("titles", new String[]{"Products", "Projection", "Lines", "Planes", "Cheat Sheet"});
                            args.putInt("fragmentColor", 0xFF99CC00);
                            args.putString("className", VectorsFragment.class.getName()); // TODO: CalculusFragment
                            tx.addToBackStack(null);
                            tx.replace(R.id.main, ContentFragment.instantiate(getContext(), ContentFragment.class.getName(), args));
                            tx.commit();
                        }
                    });
                    break;
            }

        }

        @Override
        public void setupInnerViewElements(ViewGroup parent, View view) {

            // Retrieve elements
            mTitle = (TextView) parent.findViewById(R.id.carddemo_myapps_main_inner_title);
            IconTextView iTV = (IconTextView) parent.findViewById(R.id.card_icon);

            if (mTitle != null)
                mTitle.setText(title);

            if (iTV != null) {
                iTV.setText(getText(iconTextViewTextResource));
                iTV.setTextColor(iconTextViewTextColor);
            }

        }


        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setIconTextViewTextResource(int id) {
            iconTextViewTextResource = id;
        }

        public int getIconTextViewTextResource() {
            return iconTextViewTextResource;
        }

        public void setIconTextViewTextColor(int id) {
            iconTextViewTextColor = id;
        }

        public int getIconTextViewTextColor() {
            return iconTextViewTextColor;
        }
    }
}
