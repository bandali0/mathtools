/*
 * vecFunctions.java
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

package com.aminbandali.mathtools.app.util;

public class vecFunctions {
	public static double calcDotProduct (double[][] arrNum) {
    	switch (arrNum.length * arrNum[0].length) {
		case 4:
			return (arrNum[0][0] * arrNum[1][0] + arrNum[0][1] * arrNum[1][1]);
		case 6:
			return (arrNum[0][0] * arrNum[1][0] + arrNum[0][1] * arrNum[1][1] + arrNum[0][2] * arrNum[1][2]);
		default:
			return 0;
		}
    }
    
	public static double[] calcCrossProduct (double[][] arrNum) {
    	double x = arrNum[0][1] * arrNum[1][2] - arrNum[1][1] * arrNum[0][2];
    	double y = arrNum[0][2] * arrNum[1][0] - arrNum[1][2] * arrNum[0][0];
    	double z = arrNum[0][0] * arrNum[1][1] - arrNum[1][0] * arrNum[0][1];
    	double res[] = {x,y,z}; 
    	return res;
    }
    
    public static final double getMagnitude(double a,double b){return Math.sqrt(a*a + b*b);}
    public static final double getMagnitude(double a,double b, double c){return Math.sqrt(a*a + b*b + c*c);}
    
	public static double calcScalarProjection (double[][] arrNum) {
    	double dotProduct, b;
    	dotProduct = calcDotProduct(arrNum);
    	
    	switch (arrNum.length * arrNum[0].length) {
    	case 4:
        	b = getMagnitude(arrNum[1][0], arrNum[1][1]);
      		return Math.abs(dotProduct / b);
    	case 6:
        	b = getMagnitude(arrNum[1][0], arrNum[1][1], arrNum[1][2]);
        	return Math.abs(dotProduct / b);
        default:
        	return 0;
    	}
    }
    
    public static double[] calcVectorProjection (double[][] arrNum) {
    	double b, x, y, scalarProjection;
    	double[] res;
    	scalarProjection = calcScalarProjection(arrNum);
    	
    	switch (arrNum.length * arrNum[0].length) {
    	case 4:
    		b = getMagnitude(arrNum[1][0], arrNum[1][1]);
        	x = (scalarProjection/b) * arrNum[1][0];
        	y = (scalarProjection/b) * arrNum[1][1];
        	res = new double[2];
        	res[0] = x;
        	res[1] = y;
        	return res;
    	case 6:
        	b = getMagnitude(arrNum[1][0], arrNum[1][1], arrNum[1][2]);
    		x = (scalarProjection/b) * arrNum[1][0];
        	y = (scalarProjection/b) * arrNum[1][1];
        	double z = (scalarProjection/b) * arrNum[1][2];
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
