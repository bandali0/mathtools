/*
 * Fragments.java
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

package org.aminb.mathtools.app.util;

import android.app.Fragment;

import org.aminb.mathtools.app.R;
import org.aminb.mathtools.app.fragment.vector.*;
import org.aminb.mathtools.app.fragment.trigonometry.*;

public class Fragments {

    public static final String ARG_SECTION_NUMBER = "section_number";

    public static Fragment getFragment(int titleId, int position) {
        Fragment fragment;

        switch (titleId) {
                case R.string.vectors:
                    switch (position) {
                        case 0:
                            fragment = new ProductsFragment();
                            break;
                        case 1:
                            fragment = new ProjectionsFragment();
                            break;
                        case 2:
                            fragment = new LinesFragment();
                            break;
                        default:
                            fragment = new ProductsFragment();
                    }
                    break;

                case R.string.trigonometry:
                    switch (position) {
                        case 0:
                            fragment = new CalculatorFragment();
                            break;
                        default:
                            fragment = new CalculatorFragment();
                    }
                    break;

                default:
                    return null;
            }
        return fragment;
    }
}
