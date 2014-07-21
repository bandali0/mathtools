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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
//        card.setIconTextViewTextColor(0xFF33B5E5);
        card.setIconTextViewTextColor(0xFF777777);
        card.init();
        cards.add(card);

        // Trigonometry
        card = new MainSmallCard(this.getActivity());
        card.setTitle(getString(R.string.trigonometry));
        card.count = 3;
        card.setIconTextViewTextResource(R.string.fa_trigonometry);
//        card.setIconTextViewTextColor(0xFFFFBB33);
        card.setIconTextViewTextColor(0xFF777777);
        card.init();
        cards.add(card);

        // Calculus
        card = new MainSmallCard(this.getActivity());
        card.setTitle(getString(R.string.calculus));
        card.count = 4;
        card.setIconTextViewTextResource(R.string.fa_calculus);
//        card.setIconTextViewTextColor(0xFF99CC00);
        card.setIconTextViewTextColor(0xFF777777);
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
            final Intent intent_content = new Intent(mContext, ContentActivity.class);
            final Bundle args = new Bundle();

            switch (count) {
                case 1: // Vectors
                    // Add ClickListener
                    setOnClickListener(new OnCardClickListener() {
                        @Override
                        public void onClick(Card card, View view) {
                            Bundle args = new Bundle();
                            args.putInt(ContentActivity.ARG_TITLE_ID, R.string.vectors);
                            args.putStringArray(ContentActivity.ARG_TITLES,
                                    new String[]{"Products", "Projections", "Lines", "Cheat Sheet"});
                            intent_content.putExtras(args);
                            startActivity(intent_content);
                        }
                    });
                    break;

//                case 2: // Geometry
//                    // Add ClickListener
//                    setOnClickListener(new OnCardClickListener() {
//                        @Override
//                        public void onClick(Card card, View view) {
//                            args.putInt(ContentActivity.ARG_TITLE_ID, R.string.geometry);
//                            args.putStringArray(ContentActivity.ARG_TITLES,
//                                    new String[]{"Cheat Sheet"});
//                            intent_content.putExtras(args);
//                            startActivity(intent_content);
//                        }
//                    });
//                    break;
//
//                case 3: // Trigonometry
//                    // Add ClickListener
//                    setOnClickListener(new OnCardClickListener() {
//                        @Override
//                        public void onClick(Card card, View view) {
//                            args.putInt(ContentActivity.ARG_TITLE_ID, R.string.trigonometry);
//                            args.putStringArray(ContentActivity.ARG_TITLES,
//                                    new String[]{"Cheat Sheet"});
//                            intent_content.putExtras(args);
//                            startActivity(intent_content);
//                        }
//                    });
//                    break;
//
//                case 4: // Calculus
//                    // Add ClickListener
//                    setOnClickListener(new OnCardClickListener() {
//                        @Override
//                        public void onClick(Card card, View view) {
//                            args.putInt(ContentActivity.ARG_TITLE_ID, R.string.calculus);
//                            args.putStringArray(ContentActivity.ARG_TITLES,
//                                    new String[]{"Cheat Sheet"});
//                            intent_content.putExtras(args);
//                            startActivity(intent_content);
//                        }
//                    });
//                    break;
                default:
                    setOnClickListener(new OnCardClickListener() {
                        @Override
                        public void onClick(Card card, View view) {
                            new AlertDialog.Builder(getContext())
                                    .setTitle("Coming Soon")
                                    .setMessage("Gray sections will be gradually added in future releases.")
                                    .setPositiveButton(R.string.got_it, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // continue with delete
                                        }
                                    })
                                    .show();
                        }
                    });
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
