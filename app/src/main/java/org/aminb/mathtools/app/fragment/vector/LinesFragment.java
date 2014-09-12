/*
 * LinesFragment.java
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

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import org.aminb.mathtools.app.R;
import org.aminb.mathtools.app.math.Line2D;
import org.aminb.mathtools.app.math.Line3D;
import org.aminb.mathtools.app.util.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LinesFragment extends Fragment {

    private static final String STATE_EQ_FORM_TEXT = "eq_form_text";
    private static final String STATE_EQ_FORM_TAG = "eq_form_tag";

    private enum Space{
        space2D, space3D
    }
    private Space space = Space.space2D;

    private enum EqForm{
        vector, standard
    }
    private EqForm equationForm = EqForm.vector;

    @InjectView(R.id.rB2D)
    RadioButton rB2D;

    @InjectView(R.id.rB3D)
    RadioButton rB3D;

    @InjectView(R.id.sEqForm)
    TextView eqForm;

    @InjectView(R.id.linesScrollView)
    ScrollView scrollView;

    @InjectView(R.id.linesll1) LinearLayout row1;
    @InjectView(R.id.linestextx0) EditText x0;
    @InjectView(R.id.linestexty0) EditText y0;
    @InjectView(R.id.linestextz0) EditText z0;
    @InjectView(R.id.linestextdx) EditText dx;
    @InjectView(R.id.linestextdy) EditText dy;
    @InjectView(R.id.linestextdz) EditText dz;

    @InjectView(R.id.linesll2) LinearLayout row2;
    @InjectView(R.id.linestextxp0) EditText xp0;
    @InjectView(R.id.linestextyp0) EditText yp0;
    @InjectView(R.id.linestextzp0) EditText zp0;
    @InjectView(R.id.linestextdpx) EditText dpx;
    @InjectView(R.id.linestextdpy) EditText dpy;
    @InjectView(R.id.linestextdpz) EditText dpz;

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

        x0.setHint(Html.fromHtml(getResources().getString(R.string.x0)));
        y0.setHint(Html.fromHtml(getResources().getString(R.string.y0)));
        z0.setHint(Html.fromHtml(getResources().getString(R.string.z0)));

        dx.setHint(Html.fromHtml(getResources().getString(R.string.dx)));
        dy.setHint(Html.fromHtml(getResources().getString(R.string.dy)));
        dz.setHint(Html.fromHtml(getResources().getString(R.string.dz)));

        xp0.setHint(Html.fromHtml(getResources().getString(R.string.xp0)));
        yp0.setHint(Html.fromHtml(getResources().getString(R.string.yp0)));
        zp0.setHint(Html.fromHtml(getResources().getString(R.string.zp0)));

        dpx.setHint(Html.fromHtml(getResources().getString(R.string.dpx)));
        dpy.setHint(Html.fromHtml(getResources().getString(R.string.dpy)));
        dpz.setHint(Html.fromHtml(getResources().getString(R.string.dpz)));
        
        // set up TextWatcher for all EditTexts
        x0.addTextChangedListener(new MTWatcher());
        y0.addTextChangedListener(new MTWatcher());
        z0.addTextChangedListener(new MTWatcher());
        dx.addTextChangedListener(new MTWatcher());
        dy.addTextChangedListener(new MTWatcher());
        dz.addTextChangedListener(new MTWatcher());
        xp0.addTextChangedListener(new MTWatcher());
        yp0.addTextChangedListener(new MTWatcher());
        zp0.addTextChangedListener(new MTWatcher());
        dpx.addTextChangedListener(new MTWatcher());
        dpy.addTextChangedListener(new MTWatcher());
        dpz.addTextChangedListener(new MTWatcher());
    }

    private void setVectorMode() {
        clearAll();

        x0.setHint(Html.fromHtml(getResources().getString(R.string.x0)));
        y0.setHint(Html.fromHtml(getResources().getString(R.string.y0)));
        z0.setHint(Html.fromHtml(getResources().getString(R.string.z0)));
        xp0.setHint(Html.fromHtml(getResources().getString(R.string.xp0)));
        yp0.setHint(Html.fromHtml(getResources().getString(R.string.yp0)));
        zp0.setHint(Html.fromHtml(getResources().getString(R.string.zp0)));

        z0.setVisibility(View.GONE);
        zp0.setVisibility(View.GONE);
        dx.setVisibility(View.VISIBLE);
        dy.setVisibility(View.VISIBLE);
        dz.setVisibility(View.VISIBLE);
        dpx.setVisibility(View.VISIBLE);
        dpy.setVisibility(View.VISIBLE);
        dpz.setVisibility(View.VISIBLE);

        y0.setNextFocusDownId(R.id.linestextdx);
        dy.setNextFocusDownId(R.id.linestextxp0);
        yp0.setNextFocusDownId(R.id.linestextdpx);
        dpy.setNextFocusDownId(R.id.btnlinesclear);

        equationForm = EqForm.vector;
    }

    private void setStandardMode() {
        clearAll();

        x0.setHint("A");
        y0.setHint("B");
        z0.setHint("C");
        xp0.setHint("A\'");
        yp0.setHint("B\'");
        zp0.setHint("C\'");

        z0.setVisibility(View.VISIBLE);
        zp0.setVisibility(View.VISIBLE);
        dx.setVisibility(View.GONE);
        dy.setVisibility(View.GONE);
        dz.setVisibility(View.GONE);
        dpx.setVisibility(View.GONE);
        dpy.setVisibility(View.GONE);
        dpz.setVisibility(View.GONE);

        y0.setNextFocusDownId(R.id.linestextz0);
        z0.setNextFocusDownId(R.id.linestextxp0);
        yp0.setNextFocusDownId(R.id.linestextzp0);
        zp0.setNextFocusDownId(R.id.btnlinesclear);

        equationForm = EqForm.standard;
    }

    @OnClick(R.id.sEqForm)
    void chooseEqForm(final TextView tV) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setItems(R.array.equation_forms_array, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // The 'which' argument contains the index position
                // of the selected item
                tV.setText(getResources().getStringArray(R.array.equation_forms_array)[which]);
                tV.setTag(which);

                if (which == 0) // 0 == Vector
                    setVectorMode();
                else // 1 == Standard
                    setStandardMode();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @OnClick(R.id.rB2D)
    void chooseR2Space() {
        z0.setVisibility(View.GONE);
        dz.setVisibility(View.GONE);
        zp0.setVisibility(View.GONE);
        dpz.setVisibility(View.GONE);
        y0.setNextFocusDownId(R.id.linestextdx);
        dy.setNextFocusDownId(R.id.linestextxp0);
        yp0.setNextFocusDownId(R.id.linestextdpx);
        dpy.setNextFocusDownId(R.id.btnlinesclear);
        row1.setWeightSum(4);
        row2.setWeightSum(4);
        eqForm.setEnabled(true);
        space = Space.space2D;
        inputChanged();
    }
    @OnClick(R.id.rB3D)
    void chooseR3Space() {
        setVectorMode();
        z0.setVisibility(View.VISIBLE);
        dz.setVisibility(View.VISIBLE);
        zp0.setVisibility(View.VISIBLE);
        dpz.setVisibility(View.VISIBLE);
        tVResult.setText("");
        y0.setNextFocusDownId(R.id.linestextz0);
        z0.setNextFocusDownId(R.id.linestextdx);
        dy.setNextFocusDownId(R.id.linestextdz);
        yp0.setNextFocusDownId(R.id.linestextzp0);
        zp0.setNextFocusDownId(R.id.linestextdpx);
        dpy.setNextFocusDownId(R.id.linestextdpz);
        row1.setWeightSum(6);
        row2.setWeightSum(6);
        eqForm.setText(R.string.vector);
        eqForm.setTag(0);
        eqForm.setEnabled(false);
        space = Space.space3D;
        inputChanged();
    }

    @OnClick(R.id.btnlinesclear)
    void clearAll() {
        x0.setText("");
        dx.setText("");
        xp0.setText("");
        dpx.setText("");
        y0.setText("");
        dy.setText("");
        yp0.setText("");
        dpy.setText("");
        z0.setText("");
        dz.setText("");
        zp0.setText("");
        dpz.setText("");
        tVResult.setText("");
        x0.requestFocus();
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
        if (equationForm == EqForm.vector) {
            if (space == Space.space2D)
                return x0.getText().length() > 0 && y0.getText().length() > 0 &&
                        dx.getText().length() > 0 && dy.getText().length() > 0 &&
                        xp0.getText().length() > 0 && yp0.getText().length() > 0 &&
                        dpx.getText().length() > 0 && dpy.getText().length() > 0;

            // else: space == Space.space3D
            return x0.getText().length() > 0 && y0.getText().length() > 0 && z0.getText().length() > 0 &&
                    dx.getText().length() > 0 && dy.getText().length() > 0 && dz.getText().length() > 0 &&
                    xp0.getText().length() > 0 && yp0.getText().length() > 0 && zp0.getText().length() > 0 &&
                    dpx.getText().length() > 0 && dpy.getText().length() > 0 && dpz.getText().length() > 0;
        }
        else
            return x0.getText().length() > 0 && y0.getText().length() > 0 && z0.getText().length() > 0 &&
                    xp0.getText().length() > 0 && yp0.getText().length() > 0 && zp0.getText().length() > 0;
    }

    private boolean allEditTextsEmpty() {
        if (space == Space.space2D)
            return !(x0.getText().length() > 0 || y0.getText().length() > 0 ||
                    dx.getText().length() > 0 || dy.getText().length() > 0);

        // else: space == Space.space3D
        return !(x0.getText().length() > 0 || y0.getText().length() > 0 || z0.getText().length() > 0 ||
                dx.getText().length() > 0 || dy.getText().length() > 0 || dz.getText().length() > 0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.content, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_help:
                Utils.showHelp(getActivity(), R.string.lines_help_body);
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    private void analyzeInputs(List<Double> in1, List<Double> in2, List<Double> in3, List<Double> in4) {

        String result = "";

        if (space == Space.space2D)
            if (equationForm == EqForm.vector) {
                Line2D line1 = new Line2D(new double[]{in1.get(0), in1.get(1)},
                        new double[] {in2.get(0), in2.get(1)});
                Line2D line2 = new Line2D(new double[]{in3.get(0), in3.get(1)},
                        new double[] {in4.get(0), in4.get(1)});

                if (line1.ifHasIntersection(line2))
                    result += getString(R.string.lines_intersecting) +
                            new DecimalFormat(getString(R.string.precision_pattern)).format(line1.getTmpIntersect()[0]) + ", " +
                            new DecimalFormat(getString(R.string.precision_pattern)).format(line1.getTmpIntersect()[1]) + ") ";
                else
                    result += getString(R.string.lines_parallel) +
                        new DecimalFormat(getString(R.string.precision_pattern)).format(line1.getDistanceFrom(line2)) +
                        getString(R.string.units);
            }

            else { // equationForm == EqForm.standard
                Line2D line1 = new Line2D(new double[]{in1.get(0), in1.get(1), in1.get(2)});
                Line2D line2 = new Line2D(new double[]{in3.get(0), in3.get(1), in3.get(2)});

                if (line1.ifHasIntersection(line2))
                    result += getString(R.string.lines_intersecting) +
                            new DecimalFormat(getString(R.string.precision_pattern)).format(line1.getTmpIntersect()[0]) + ", " +
                            new DecimalFormat(getString(R.string.precision_pattern)).format(line1.getTmpIntersect()[1]) + ") ";
                else {
                    double dist = line1.getDistanceFrom(line2);
                    String distString = new DecimalFormat(getString(R.string.precision_pattern)).format(dist);

                    if (distString.equals("0"))
                        result += getString(R.string.lines_identical);
                    else
                        result += getString(R.string.lines_parallel) +
                                distString + getString(R.string.units);
                }
            }

        else {
            Line3D line1 = new Line3D(new double[]{in1.get(0), in1.get(1), in1.get(2)},
                    new double[] {in2.get(0), in2.get(1), in2.get(2)});
            Line3D line2 = new Line3D(new double[]{in3.get(0), in3.get(1), in3.get(2)},
                    new double[] {in4.get(0), in4.get(1), in4.get(2)});

            // Analyzing part
            if (line1.isParallelTo(line2)) {
                double dist = line1.getDistanceFrom(line2);
                String distString = new DecimalFormat(getString(R.string.precision_pattern)).format(dist);
                if (distString.equals("0"))
                    result += getString(R.string.lines_identical);
                else
                    result += getString(R.string.lines_parallel) +
                        distString + getString(R.string.units);
            }
            else if (line1.areSkew(line2)) {
                double dist = line1.getDistanceFrom(line2);
                result += getString(R.string.lines_skew) +
                        new DecimalFormat(getString(R.string.precision_pattern)).format(dist) + getString(R.string.units);
            }
            else { // intersecting
                if (line1.ifHasIntersection(line2)) {
                    double[] intersectpt = line1.getTmpIntersect();
                    result += getString(R.string.lines_intersecting_r3) +
                            new DecimalFormat(getString(R.string.precision_pattern)).format(intersectpt[0]) + ", " +
                            new DecimalFormat(getString(R.string.precision_pattern)).format(intersectpt[1]) + ", " +
                            new DecimalFormat(getString(R.string.precision_pattern)).format(intersectpt[2]) + ").";
                }
            }

        }

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
                        two = new ArrayList<Double>(),
                        three = new ArrayList<Double>(),
                        four = new ArrayList<Double>();

                if (equationForm == EqForm.vector) {
                    // x and d (line 1)
                    one.add(Double.parseDouble(x0.getText().toString()));
                    one.add(Double.parseDouble(y0.getText().toString()));
                    two.add(Double.parseDouble(dx.getText().toString()));
                    two.add(Double.parseDouble(dy.getText().toString()));

                    // x' and d' (line 2)
                    three.add(Double.parseDouble(xp0.getText().toString()));
                    three.add(Double.parseDouble(yp0.getText().toString()));
                    four.add(Double.parseDouble(dpx.getText().toString()));
                    four.add(Double.parseDouble(dpy.getText().toString()));

                    if (space == Space.space3D) {
                        one.add(Double.parseDouble(z0.getText().toString()));
                        two.add(Double.parseDouble(dz.getText().toString()));
                        three.add(Double.parseDouble(zp0.getText().toString()));
                        four.add(Double.parseDouble(dpz.getText().toString()));
                    }

                    analyzeInputs(one, two, three, four);
                }
                else { // equationForm == EqForm.standard
                    // line 1
                    one.add(Double.parseDouble(x0.getText().toString()));
                    one.add(Double.parseDouble(y0.getText().toString()));
                    one.add(Double.parseDouble(z0.getText().toString()));

                    // line 2
                    three.add(Double.parseDouble(xp0.getText().toString()));
                    three.add(Double.parseDouble(yp0.getText().toString()));
                    three.add(Double.parseDouble(zp0.getText().toString()));

                    analyzeInputs(one, null, three, null);
                }

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

