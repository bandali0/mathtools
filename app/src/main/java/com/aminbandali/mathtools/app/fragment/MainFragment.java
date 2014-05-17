package com.aminbandali.mathtools.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.aminbandali.mathtools.app.R;

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardThumbnail;
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

        //Init an array of Cards
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
        card.setResourceIdThumbnail(R.drawable.ic_vectors);
        card.init();
        cards.add(card);

        // Geometry
        card = new MainSmallCard(this.getActivity());
        card.setTitle(getString(R.string.geometry));
        card.count = 2;
        card.setResourceIdThumbnail(R.drawable.ic_geometry);
        card.init();
        cards.add(card);

        // Trigonometry
        card = new MainSmallCard(this.getActivity());
        card.setTitle(getString(R.string.trigonometry));
        card.count = 3;
        card.setResourceIdThumbnail(R.drawable.ic_trigonometry);
        card.init();
        cards.add(card);

        // Calculus
        card = new MainSmallCard(this.getActivity());
        card.setTitle(getString(R.string.calculus));
        card.count = 4;
        card.setResourceIdThumbnail(R.drawable.ic_calculus);
        card.init();
        cards.add(card);

        return cards;

    }

    public class MainSmallCard extends Card {

        protected TextView mTitle;
        protected int resourceIdThumbnail;
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

            // Add thumbnail
            CardThumbnail cardThumbnail = new CardThumbnail(mContext);

            if (resourceIdThumbnail==0)
                cardThumbnail.setDrawableResource(R.drawable.ic_launcher);
            else{
                cardThumbnail.setDrawableResource(resourceIdThumbnail);
            }

            addCardThumbnail(cardThumbnail);

            switch (count) {
                case 1: // Vectors

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

            if (mTitle != null)
                mTitle.setText(title);

        }


        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }


        public int getResourceIdThumbnail() {
            return resourceIdThumbnail;
        }

        public void setResourceIdThumbnail(int resourceIdThumbnail) {
            this.resourceIdThumbnail = resourceIdThumbnail;
        }
    }
}
