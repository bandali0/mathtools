/*
 * ProductsFragment.java
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

package com.aminbandali.mathtools.app.fragment.vector;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.text.TextWatcher;
import android.text.Editable;
import android.widget.Button;

import com.aminbandali.mathtools.app.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ProductsFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";

    private enum Space{
        space2D, space3D
    }
    private Space space = Space.space2D;

    @InjectView(R.id.rB2D)
    RadioButton rB2D;

    @InjectView(R.id.rB3D)
    RadioButton rB3D;

    @InjectView(R.id.productsll1) LinearLayout row1;
    @InjectView(R.id.productstextx1) EditText x1;
    @InjectView(R.id.productstexty1) EditText y1;
    @InjectView(R.id.productstextz1) EditText z1;
    @InjectView(R.id.productstextx2) EditText x2;
    @InjectView(R.id.productstexty2) EditText y2;
    @InjectView(R.id.productstextz2) EditText z2;

    @InjectView(R.id.btnproductsclear) Button btnClear;

    public ProductsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vectors_products, container, false);
        ButterKnife.inject(this, rootView);
        doInit();
        return rootView;
    }

    private void doInit() {
        rB2D.setText(Html.fromHtml(getResources().getString(R.string.r2)));
        rB3D.setText(Html.fromHtml(getResources().getString(R.string.r3)));

        x1.setHint(Html.fromHtml(getResources().getString(R.string.x1)));
        y1.setHint(Html.fromHtml(getResources().getString(R.string.y1)));
        z1.setHint(Html.fromHtml(getResources().getString(R.string.z1)));

        x2.setHint(Html.fromHtml(getResources().getString(R.string.x2)));
        y2.setHint(Html.fromHtml(getResources().getString(R.string.y2)));
        z2.setHint(Html.fromHtml(getResources().getString(R.string.z2)));
        
        // set up TextWatcher for all EditTexts
        x1.addTextChangedListener(new MTWatcher());
        y1.addTextChangedListener(new MTWatcher());
        z1.addTextChangedListener(new MTWatcher());
        x2.addTextChangedListener(new MTWatcher());
        y2.addTextChangedListener(new MTWatcher());
        z2.addTextChangedListener(new MTWatcher());
    }

    @OnClick(R.id.rB2D)
    void chooseR2Space(RadioButton rB) {
        z1.setVisibility(View.GONE);
        z2.setVisibility(View.GONE);
        row1.setWeightSum(4);
        space = Space.space2D;
    }
    @OnClick(R.id.rB3D)
    void chooseR3Space(RadioButton rB) {
        z1.setVisibility(View.VISIBLE);
        z2.setVisibility(View.VISIBLE);
        row1.setWeightSum(6);
        space = Space.space3D;
    }

    private boolean allEditTextsFilled() {
        if (space == Space.space2D)
            return x1.getText().length() > 0 && y1.getText().length() > 0 &&
                    x2.getText().length() > 0 && y2.getText().length() > 0;

        // else: space == Space.space3D
        return x1.getText().length() > 0 && y1.getText().length() > 0 && z1.getText().length() > 0 &&
                x2.getText().length() > 0 && y2.getText().length() > 0 && z2.getText().length() > 0;
    }

    private void analyzeInputs(List<Double> in1, List<Double> in2) {

        // TODO: actually implement it
    }
    
    public class MTWatcher implements TextWatcher {
        public void afterTextChanged(Editable s) {}

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (allEditTextsFilled()) {
                btnClear.setEnabled(true);

                List<Double> one = new ArrayList<Double>(),
                        two = new ArrayList<Double>();

                if (space == Space.space2D) {
                    one.add(Double.parseDouble(x1.getText().toString()));
                    one.add(Double.parseDouble(y1.getText().toString()));
                    two.add(Double.parseDouble(x2.getText().toString()));
                    two.add(Double.parseDouble(y2.getText().toString()));

                    if (space == Space.space3D) {
                        one.add(Double.parseDouble(z1.getText().toString()));
                        two.add(Double.parseDouble(z2.getText().toString()));
                    }

                    analyzeInputs(one, two);
                }
            }
            else
                if (btnClear.isEnabled())
                    btnClear.setEnabled(false);
        }
    }
}
