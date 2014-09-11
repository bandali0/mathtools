/*
 * Line2D.java
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

import org.apache.commons.math3.geometry.euclidean.twod.Line;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class Line2D {
	
	private Vector2D ptA, ptB, normal;
	private Line line;
	private double[] Xparam = new double[2], Yparam = new double[2], Xsymm = new double[2], Ysymm = new double[2], Standard = new double[2], tmpIntersect = new double[2];

    // calculate a second point on a line, having direction vector and another point
	private double[] calcPtB(double[] pta, double[] dir) {
		double[] ptb = {0,0};
		ptb[0] = pta[0] + 2 * dir[0];
		ptb[1] = pta[1] + 2 * dir[1];
		return ptb;
	}

    // Initialize the main object attributes, given a point and the direction vector
	private void doInit(double[] pointA, double[] dirxnVector) {
		ptA = new Vector2D(pointA);
		double[] pointB = calcPtB(pointA, dirxnVector);
		ptB = new Vector2D(pointB);
		line = new Line(ptA, ptB, .0000001);
	}

    // create two points to build a line object; given X and Y parameters
	private void paramToPt() {
		double[] pta = {0,0};
		double[] ptb = {0,0};
		pta[0] = Xparam[0] + Xparam[1]; // calculate an x value for point A
		pta[1] = Yparam[0] + Yparam[1]; // calculate an y value for point A
		ptb[0] = Xparam[0] * 3 + Xparam[1]; // calculate an x value for point B
		ptb[1] = Yparam[0] * 3 + Yparam[1]; // calculate an y value for point B
		ptA = new Vector2D(pta);
		ptB = new Vector2D(ptb);
		line = new Line(ptA, ptB, .0000001);
	}

    // point and direction vector constructor
	public Line2D (double[] pointA, double[] dirxnVector){
		doInit(pointA, dirxnVector);
	}

    // parametric (or symmetric) constructor
	public Line2D (boolean mode, double[][] ParamOrSymm) {
		// mode true: parametric equation		mode false: symmetric equation
		if (mode) {
			Xparam = ParamOrSymm[0];
			Yparam = ParamOrSymm[1];
            // calculate symmetric coefficients based on parametric equation
			Xsymm[0] = (-1) * Xparam[1];
			Xsymm[1] = Xparam[0];
			Ysymm[0] = (-1) * Yparam[1];
			Ysymm[1] = Yparam[0];
		}
		else { // (x+a)/b = (y+c)/d		==>		x=bt-a , y=dt-c  
			Xsymm = ParamOrSymm[0];
			Ysymm = ParamOrSymm[1];
            // calculate parametric coefficients based on symmetric equation
			Xparam[0] = Xsymm[1];
			Xparam[1] = (-1) * Xsymm[0];
			Yparam[0] = Ysymm[1];
			Yparam[1] = (-1) * Ysymm[0];
		}
		paramToPt(); // initialize object points and line, having the parametric equation
	}

    // create a line, given the standard equation
	public Line2D (double[] standard) {
		Standard = standard;
		normal = new Vector2D(standard[0], standard[1]);
		double[] dirxn = {0,0};
		dirxn[0] = standard[1];
		dirxn[1] = (-1)*standard[0];
		double[] pta = {0,0};
        if(standard[0] != 0) { //check if the coefficient of x is not 0 to avoid errors
            pta[0] = (standard[2] - 2 * standard[1]) / standard[0];
            pta[1] = 2;
        }
        else if(standard[1] != 0) { //check if the coefficient of y is not 0 to avoid errors
            pta[0] = 2;
            pta[1] = (standard[2] - 2*standard[0]) / standard[1];
        }
        //else x and y are both 0, show that the equation is invalid
		doInit(pta, dirxn);
	}

    // return the point A on the line
	Vector2D getPtA() {
		return ptA;
	}

    // return normal vector
	double[] getNormal() {
		return normal.toArray();
	}

    // return x-equation in parametric form
	double[] getXparam() {
		return Xparam;
	}

    // return y-equation in parametric form
	double[] getYparam() {
		return Yparam;
	}

    // return x-equation in symmetric form
	double[] getXsymm() {
		return Xsymm;
	}

    // return y-equation in symmetric form
	double[] getYsymm() {
		return Ysymm;
	}

    // return the coefficients of the equation for standard form
	double[] getStandard() {
		return Standard;
	}

    // return the apache library Line object
	Line getApacheLine() {
		return line;
	}

    // return true if this line is parallel to ln
	public boolean isParallelTo(Line2D ln) {
		if (line.isParallelTo(ln.getApacheLine()))
			return true;
		else
			return false;
	}

    // return the distance of this line from ln
	public double getDistanceFrom(Line2D ln) {
		if (line.isParallelTo(ln.getApacheLine()))
			return line.distance(ln.getPtA());
		else
			return -1;
	}

    // return true if this line and ln intersect
	public boolean ifHasIntersection(Line2D ln) {
		if (!(line.isParallelTo(ln.getApacheLine()))) {
			Vector2D a = line.intersection(ln.getApacheLine());
			tmpIntersect = a.toArray();
			return true;
		}
		else 
			return false;
	}

    // return the intersection point
	public double[] getTmpIntersect() {
		return tmpIntersect;
	}
	
}
