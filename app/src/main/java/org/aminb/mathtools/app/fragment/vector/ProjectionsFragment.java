/*
 * ProjectionsFragment.java
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

package org.aminb.mathtools.app.fragment.vector;

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
import android.widget.TextView;

import org.aminb.mathtools.app.R;
import org.aminb.mathtools.app.math.VectorHelpers;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class ProjectionsFragment extends Fragment {

    private enum Space{
        space2D, space3D
    }
    private Space space = Space.space2D;

    @InjectView(R.id.rB2D)
    RadioButton rB2D;

    @InjectView(R.id.rB3D)
    RadioButton rB3D;

    @InjectView(R.id.result)
    TextView tVResult;

    @InjectView(R.id.projectionsll1) LinearLayout row1;
    @InjectView(R.id.projectionstextx1) EditText x1;
    @InjectView(R.id.projectionstexty1) EditText y1;
    @InjectView(R.id.projectionstextz1) EditText z1;
    @InjectView(R.id.projectionstextx2) EditText x2;
    @InjectView(R.id.projectionstexty2) EditText y2;
    @InjectView(R.id.projectionstextz2) EditText z2;

    @InjectView(R.id.btnprojectionsclear) Button btnClear;

    public ProjectionsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vectors_projections, container, false);
        ButterKnife.inject(this, rootView);
        doInit();
        return rootView;
    }

    private void doInit() {
        rB2D.setText(Html.fromHtml(getResources().getString(R.string.r2)));
        rB3D.setText(Html.fromHtml(getResources().getString(R.string.r3)));

        x1.setHint(Html.fromHtml(getResources().getString(R.string.ax)));
        y1.setHint(Html.fromHtml(getResources().getString(R.string.ay)));
        z1.setHint(Html.fromHtml(getResources().getString(R.string.az)));

        x2.setHint(Html.fromHtml(getResources().getString(R.string.bx)));
        y2.setHint(Html.fromHtml(getResources().getString(R.string.by)));
        z2.setHint(Html.fromHtml(getResources().getString(R.string.bz)));

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
        y1.setNextFocusDownId(R.id.projectionstextx2);
        y2.setNextFocusDownId(R.id.btnprojectionsclear);
        row1.setWeightSum(4);
        space = Space.space2D;
        inputChanged();
    }
    @OnClick(R.id.rB3D)
    void chooseR3Space(RadioButton rB) {
        z1.setVisibility(View.VISIBLE);
        z2.setVisibility(View.VISIBLE);
        tVResult.setText("");
        y1.setNextFocusDownId(R.id.projectionstextz1);
        y2.setNextFocusDownId(R.id.projectionstextz2);
        row1.setWeightSum(6);
        space = Space.space3D;
        inputChanged();
    }

    @OnClick(R.id.btnprojectionsclear)
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

    private boolean allEditTextsFilled() {
        if (space == Space.space2D)
            return x1.getText().length() > 0 && y1.getText().length() > 0 &&
                    x2.getText().length() > 0 && y2.getText().length() > 0;

        // else: space == Space.space3D
        return x1.getText().length() > 0 && y1.getText().length() > 0 && z1.getText().length() > 0 &&
                x2.getText().length() > 0 && y2.getText().length() > 0 && z2.getText().length() > 0;
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

        String result = getString(R.string.a_on_b_scalar) +
                new DecimalFormat(getString(R.string.precision_pattern)).format(VectorHelpers.calcScalarProjection(in1, in2));


        double[] resultVectorProjection = VectorHelpers.calcVectorProjection(in1, in2);

        result += String.format(getString(R.string.a_on_b_vector),
                new DecimalFormat(getString(R.string.precision_pattern)).format(resultVectorProjection[0]),
                new DecimalFormat(getString(R.string.precision_pattern)).format(resultVectorProjection[1]));

        if (in1.size() == 3)
            result += ", " + new DecimalFormat(getString(R.string.precision_pattern)).format(resultVectorProjection[2]);

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

