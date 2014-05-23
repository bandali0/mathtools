package com.aminbandali.mathtools.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.IconTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.aminbandali.mathtools.app.R;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.view.CardListView;

/**
 * Created by amin on 5/16/14.
 */
public class MainFragment extends BaseFragment{
    @Override
    public int getTitleResourceId() {
        return R.string.app_name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
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

        CardListView listView = (CardListView) getActivity().findViewById(R.id.fragment_main_cardslist);
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
                            FragmentTransaction tx = getActivity().getSupportFragmentManager()
                                    .beginTransaction();
                            tx.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
                            Bundle args = new Bundle();
                            args.putInt("titleId", R.string.vectors);
                            args.putStringArray("titles", new String[] {"Products", "Projection", "Lines", "Planes", "Cheat Sheet"});
                            tx.addToBackStack(null);
                            tx.replace(R.id.main, ContentFragment.instantiate(getContext(), ContentFragment.class.getName(), args));
                            tx.commit();
                        }
                    });
                    break;

                case 2: // Geometry
                    // Add ClickListener
                    setOnClickListener(new OnCardClickListener() {
                        @Override
                        public void onClick(Card card, View view) {
                            Toast.makeText(getContext(), "card=" + title, Toast.LENGTH_SHORT).show();
                        }
                    });
                    break;

                case 3: // Trigonometry
                    break;

                case 4: // Calculus
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
