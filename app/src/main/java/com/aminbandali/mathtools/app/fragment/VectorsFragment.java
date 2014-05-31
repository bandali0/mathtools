/*
 * VectorsFragment.java
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

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aminbandali.mathtools.app.R;

public class VectorsFragment extends BaseFragment
{

    public int getTitleResourceId() {
        if (getArguments().getInt("titleId") != 0)
            return getArguments().getInt("titleId");
        else
            return 0;
    }

    public VectorsFragment()
    {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_vectors_lines, container, false);
//        View text = view.findViewById(android.R.id.text1);
//        if(text != null && text instanceof TextView)
//        {
//            ((TextView)text).setText("Fragment " + getArguments().getInt("position"));
//        }
        return view;
    }

}
