/*
 * LinesFragment.java
 * Copyright (C) 2014 Amin Bandali <me@aminb.org>
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

package org.aminb.mathtools.app.fragment.vector;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import org.aminb.mathtools.app.R;
import org.aminb.mathtools.app.math.VectorHelpers;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LinesFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    public static final String ARG_SECTION_NUMBER = "section_number";

    private static final String STATE_EQ_FORM_TEXT = "eq_form_text";
    private static final String STATE_EQ_FORM_TAG = "eq_form_tag";

    private enum Space{
        space2D, space3D
    }
    private Space space = Space.space2D;

    @InjectView(R.id.rB2D)
    RadioButton rB2D;

    @InjectView(R.id.rB3D)
    RadioButton rB3D;

    @InjectView(R.id.sEqForm)
    TextView eqForm;

    @InjectView(R.id.linesScrollView)
    ScrollView scrollView;

    @InjectView(R.id.linesll1) LinearLayout row1;
    @InjectView(R.id.linestextx1) EditText x1;
    @InjectView(R.id.linestexty1) EditText y1;
    @InjectView(R.id.linestextz1) EditText z1;
    @InjectView(R.id.linestextx2) EditText x2;
    @InjectView(R.id.linestexty2) EditText y2;
    @InjectView(R.id.linestextz2) EditText z2;

    @InjectView(R.id.linesll2) LinearLayout row2;
    @InjectView(R.id.linestextx3) EditText x3;
    @InjectView(R.id.linestexty3) EditText y3;
    @InjectView(R.id.linestextz3) EditText z3;
    @InjectView(R.id.linestextx4) EditText x4;
    @InjectView(R.id.linestexty4) EditText y4;
    @InjectView(R.id.linestextz4) EditText z4;

    @InjectView(R.id.btnlinesclear) Button btnClear;

    @InjectView(R.id.result)
    TextView tVResult;

    public LinesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vectors_lines, container, false);
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

        x3.setHint(Html.fromHtml(getResources().getString(R.string.x3)));
        y3.setHint(Html.fromHtml(getResources().getString(R.string.y3)));
        z3.setHint(Html.fromHtml(getResources().getString(R.string.z3)));

        x4.setHint(Html.fromHtml(getResources().getString(R.string.x4)));
        y4.setHint(Html.fromHtml(getResources().getString(R.string.y4)));
        z4.setHint(Html.fromHtml(getResources().getString(R.string.z4)));
        
        // set up TextWatcher for all EditTexts
        x1.addTextChangedListener(new MTWatcher());
        y1.addTextChangedListener(new MTWatcher());
        z1.addTextChangedListener(new MTWatcher());
        x2.addTextChangedListener(new MTWatcher());
        y2.addTextChangedListener(new MTWatcher());
        z2.addTextChangedListener(new MTWatcher());
        x3.addTextChangedListener(new MTWatcher());
        y3.addTextChangedListener(new MTWatcher());
        z3.addTextChangedListener(new MTWatcher());
        x4.addTextChangedListener(new MTWatcher());
        y4.addTextChangedListener(new MTWatcher());
        z4.addTextChangedListener(new MTWatcher());
    }

    @OnClick(R.id.sEqForm)
    void chooseEqForm(final TextView tV) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setItems(R.array.eq_forms_array, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // The 'which' argument contains the index position
                // of the selected item
                tV.setText(getResources().getStringArray(R.array.eq_forms_array)[which]);
                tV.setTag(which);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @OnClick(R.id.rB2D)
    void chooseR2Space(RadioButton rB) {
        z1.setVisibility(View.GONE);
        z2.setVisibility(View.GONE);
        z3.setVisibility(View.GONE);
        z4.setVisibility(View.GONE);
        y1.setNextFocusDownId(R.id.linestextx2);
        y2.setNextFocusDownId(R.id.linestextx3);
        y3.setNextFocusDownId(R.id.linestextx4);
        y4.setNextFocusDownId(R.id.btnlinesclear);
        row1.setWeightSum(4);
        row2.setWeightSum(4);
        space = Space.space2D;
        inputChanged();
    }
    @OnClick(R.id.rB3D)
    void chooseR3Space(RadioButton rB) {
        z1.setVisibility(View.VISIBLE);
        z2.setVisibility(View.VISIBLE);
        z3.setVisibility(View.VISIBLE);
        z4.setVisibility(View.VISIBLE);
        tVResult.setText("");
        y1.setNextFocusDownId(R.id.linestextz1);
        y2.setNextFocusDownId(R.id.linestextz2);
        y3.setNextFocusDownId(R.id.linestextz3);
        y4.setNextFocusDownId(R.id.linestextz4);
        row1.setWeightSum(6);
        row2.setWeightSum(6);
        space = Space.space3D;
        inputChanged();
    }

    @OnClick(R.id.btnlinesclear)
    void clearAll() {
        x1.setText("");
        x2.setText("");
        y1.setText("");
        y2.setText("");
        z1.setText("");
        z2.setText("");
        tVResult.setText("");
        x1.requestFocus();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(STATE_EQ_FORM_TEXT, eqForm.getText().toString());
        outState.putString(STATE_EQ_FORM_TAG, eqForm.getTag().toString());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            eqForm.setText(savedInstanceState.getString(STATE_EQ_FORM_TEXT));
            eqForm.setTag(savedInstanceState.getString(STATE_EQ_FORM_TAG));
        }
    }

    private boolean allEditTextsFilled() {
        if (space == Space.space2D)
            return x1.getText().length() > 0 && y1.getText().length() > 0 &&
                    x2.getText().length() > 0 && y2.getText().length() > 0 &&
                    x3.getText().length() > 0 && y3.getText().length() > 0 &&
                    x4.getText().length() > 0 && y4.getText().length() > 0;

        // else: space == Space.space3D
        return x1.getText().length() > 0 && y1.getText().length() > 0 && z1.getText().length() > 0 &&
                x2.getText().length() > 0 && y2.getText().length() > 0 && z2.getText().length() > 0 &&
                x3.getText().length() > 0 && y3.getText().length() > 0 && z3.getText().length() > 0 &&
                x4.getText().length() > 0 && y4.getText().length() > 0 && z4.getText().length() > 0;
    }

    private boolean allEditTextsEmpty() {
        if (space == Space.space2D)
            return !(x1.getText().length() > 0 || y1.getText().length() > 0 ||
                    x2.getText().length() > 0 || y2.getText().length() > 0);

        // else: space == Space.space3D
        return !(x1.getText().length() > 0 || y1.getText().length() > 0 || z1.getText().length() > 0 ||
                x2.getText().length() > 0 || y2.getText().length() > 0 || z2.getText().length() > 0);
    }

    private void analyzeInputs(List<Double> in1, List<Double> in2) {

        String result = "x onto y (scalar):\n" +
                new DecimalFormat("###.######").format(VectorHelpers.calcScalarProjection(in1, in2));


        double[] resultVectorProjection = VectorHelpers.calcVectorProjection(in1, in2);

        result += String.format("\n\nx onto y (vector):\n(%s, %s",
                new DecimalFormat("###.######").format(resultVectorProjection[0]),
                new DecimalFormat("###.######").format(resultVectorProjection[1]));

        if (in1.size() == 3)
            result += ", " + new DecimalFormat("###.######").format(resultVectorProjection[2]);

        result += ")";
        tVResult.setText(result);

    }

    public class MTWatcher implements TextWatcher {
        public void afterTextChanged(Editable s) {}

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            inputChanged();
        }
    }

    private void inputChanged() {
        if (allEditTextsFilled()) {
            if (!btnClear.isEnabled())
                btnClear.setEnabled(true);

            try {
                List<Double> one = new ArrayList<Double>(),
                        two = new ArrayList<Double>();

                one.add(Double.parseDouble(x1.getText().toString()));
                one.add(Double.parseDouble(y1.getText().toString()));
                two.add(Double.parseDouble(x2.getText().toString()));
                two.add(Double.parseDouble(y2.getText().toString()));

                if (space == Space.space3D) {
                    one.add(Double.parseDouble(z1.getText().toString()));
                    two.add(Double.parseDouble(z2.getText().toString()));
                }

                analyzeInputs(one, two);

                final View curFocus = getActivity().getCurrentFocus();
                scrollView.post(new Runnable() {

                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                        curFocus.requestFocus();
                    }
                });

            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        else {
            if (!btnClear.isEnabled())
                btnClear.setEnabled(true);
            if (allEditTextsEmpty())
                btnClear.setEnabled(false);
        }
    }

}

