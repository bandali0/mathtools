/*
 * VectorHelpers.java
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

package org.aminb.mathtools.app.math;

import java.util.List;

public class VectorHelpers {
	public static double calcDotProduct (List<Double> in1, List<Double> in2) {
        Double dotProduct = 0d;

        for(int i=0; i < in1.size(); i++) {
            dotProduct += in1.get(i) * in2.get(i);
        }

        return dotProduct;
    }
    
	public static double[] calcCrossProduct (List<Double> in1, List<Double> in2) {
    	double x = in1.get(1) * in2.get(2) - in2.get(1) * in1.get(2);
    	double y = in1.get(2) * in2.get(0) - in2.get(2) * in1.get(0);
    	double z = in1.get(0) * in2.get(1) - in2.get(0) * in1.get(1);
    	return new double[] {x, y, z};
    }
    
    public static final double getMagnitude(double a,double b){return Math.sqrt(a*a + b*b);}
    public static final double getMagnitude(double a,double b, double c){return Math.sqrt(a*a + b*b + c*c);}
    
	public static double calcScalarProjection (List<Double> in1, List<Double> in2) {
    	double dotProduct, b;
    	dotProduct = calcDotProduct(in1, in2);
    	
    	switch (in1.size()) {
    	case 2:
        	b = getMagnitude(in2.get(0), in2.get(1));
      		return Math.abs(dotProduct / b);
    	case 3:
        	b = getMagnitude(in2.get(0), in2.get(1), in2.get(2));
        	return Math.abs(dotProduct / b);
        default:
        	return 0;
    	}
    }
    
    public static double[] calcVectorProjection (List<Double> in1, List<Double> in2) {
    	double b, x, y, scalarProjection;
    	double[] res;
    	scalarProjection = calcScalarProjection(in1, in2);
    	
    	switch (in1.size()) {
    	case 2:
    		b = getMagnitude(in2.get(0), in2.get(1));
        	x = (scalarProjection/b) * in2.get(0);
        	y = (scalarProjection/b) * in2.get(1);
        	res = new double[2];
        	res[0] = x;
        	res[1] = y;
        	return res;
    	case 3:
        	b = getMagnitude(in2.get(0), in2.get(1), in2.get(2));
    		x = (scalarProjection/b) * in2.get(0);
        	y = (scalarProjection/b) * in2.get(1);
        	double z = (scalarProjection/b) * in2.get(2);
        	res = new double[3];
        	res[0] = x;
        	res[1] = y;
        	res[2] = z;
        	return res;
        default:
        	double[] no = {0,0};
        	return no;
    	}
    }

}
