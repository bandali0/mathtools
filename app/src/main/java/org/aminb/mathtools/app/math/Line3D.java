/*
 * Line3D.java
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

//import android.util.Log;


import org.apache.commons.math3.geometry.euclidean.threed.Line;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.ArrayList;
import java.util.List;

public class Line3D {

	private Vector3D ptA, ptB;
	private Line line;
	private double[] Xparam = new double[2], Yparam = new double[2], Zparam = new double[2],
            Xsymm = new double[2], Ysymm = new double[2], Zsymm = new double[2],
            tmpIntersect = new double[3];

    // calculate a second point on a line, having direction vector and another point
	private double[] calcPtB(double[] pta, double[] dir) {
		double[] ptb = new double[3];
		ptb[0] = pta[0] + 2 * dir[0];
		ptb[1] = pta[1] + 2 * dir[1];
		ptb[2] = pta[2] + 2 * dir[2];
		return ptb;
	}

    // Initialize the main object attributes, given a point and the direction vector
	private void doInit(double[] pointA, double[] dirxnVector) {
		ptA = new Vector3D(pointA);
		double[] pointB = calcPtB(pointA, dirxnVector);
		ptB = new Vector3D(pointB);
		line = new Line(ptA, ptB, .0000001);
	}

    // create two points to build a line object; given X and Y parameters
	private void paramToPt() {
		double[] pta = new double[3];
		double[] ptb = new double[3];
		pta[0] = Xparam[0] + Xparam[1]; // calculate an x value for point A
		pta[1] = Yparam[0] + Yparam[1]; // calculate an y value for point A
		pta[2] = Zparam[0] + Zparam[1]; // calculate an z value for point A
		ptb[0] = Xparam[0] * 3 + Xparam[1]; //x=3t+a    // calculate an x value for point B
		ptb[1] = Yparam[0] * 3 + Yparam[1]; // calculate an y value for point B
		ptb[2] = Zparam[0] * 3 + Zparam[1]; // calculate an z value for point B
		ptA = new Vector3D(pta);
		ptB = new Vector3D(ptb);
		line = new Line(ptA, ptB, .0000001);
	}

    // point and direction vector constructor
	public Line3D (double[] pointA, double[] dirxnVector){
		doInit(pointA, dirxnVector);
	}

    // parametric (or symmetric) constructor
	public Line3D (boolean mode, double[][] ParamOrSymm) {
		// mode true: parametric equation		mode false: symmetric equation
		if (mode) {
			Xparam = ParamOrSymm[0];
			Yparam = ParamOrSymm[1];
			Zparam = ParamOrSymm[2];
            // calculate symmetric coefficients based on parametric equation
			Xsymm[0] = (-1) * Xparam[1];
			Xsymm[1] = Xparam[0];
			Ysymm[0] = (-1) * Yparam[1];
			Ysymm[1] = Yparam[0];
			Zsymm[0] = (-1) * Zparam[1];
			Zsymm[1] = Zparam[0];
		}
		else { // (x+a)/b = (y+c)/d		==>		x=bt-a , y=dt-c
			double[] Xsymm = ParamOrSymm[0];
			double[] Ysymm = ParamOrSymm[1];
			double[] Zsymm = ParamOrSymm[2];
//			String amin = Double.toString(Xsymm[0]);
//			String amin2 = Double.toString(Xsymm[1]);
//			Log.e("inhere", amin + " ++ "+ amin2);
            // calculate parametric coefficients based on symmetric equation
			Xparam[0] = Xsymm[1];
			Xparam[1] = (-1) * Xsymm[0];
			Yparam[0] = Ysymm[1];
			Yparam[1] = (-1) * Ysymm[0];
			Zparam[0] = Zsymm[1];
			Zparam[1] = (-1) * Zsymm[0];
		}
		paramToPt(); // initialize object points and line, having the parametric equation
	}

    // return the point A on the line
	Vector3D getPtA() {
		return ptA;
	}

    // return the direction vector
	double[] getDirxn() {
		double[] tmp = {0, 0,0};
		tmp[0] = ptB.getX() - ptA.getX();
		tmp[1] = ptB.getY() - ptA.getY();
		tmp[2] = ptB.getZ() - ptA.getZ();
		return tmp;
	}

    // return x-equation in parametric form
	double[] getXparam() {
		return Xparam;
	}

    // return y-equation in parametric form
	double[] getYparam() {
		return Yparam;
	}

    // return z-equation in parametric form
	double[] getZparam() {
		return Zparam;
	}

    // return x-equation in symmetric form
	double[] getXsymm() {
		return Xsymm;
	}

    // return y-equation in symmetric form
	double[] getYsymm() {
		return Ysymm;
	}

    // return z-equation in symmetric form
	double[] getZsymm() {
		return Zsymm;
	}

    // return the apache library Line object
	Line getApacheLine() {
		return line;
	}

    // return true if this line is parallel to ln
	public boolean isParallelTo(Line3D ln) {
		double[] lndir = ln.getDirxn();
		if ((lndir[0]/(ptB.getX()-ptA.getX())) == (lndir[1]/(ptB.getY()-ptA.getY())) &&
                ((lndir[1]/(ptB.getY()-ptA.getY())) == (lndir[2]/(ptB.getZ()-ptA.getZ()))))
			return true;
		else
			return false;
	}

    // return the distance of this line from ln
	public double getDistanceFrom(Line3D ln) {
		if (isParallelTo(ln))
			return line.distance(ln.getPtA());
		else if (areSkew(ln)) {
			double[] p1 = ptA.toArray();
			double[] p2 =  ln.getPtA().toArray();
			double[] p1p2 = new double[3];
			p1p2[0] = p2[0] - p1[0];
			p1p2[1] = p2[1] - p1[1];
			p1p2[2] = p2[2] - p1[2];
			double[][] d1xd2 = {{0, 0, 0}, {0, 0, 0}};
			d1xd2[0] = getDirxn();
			d1xd2[1] = ln.getDirxn();
			double[] cross;
            List<Double> list1 = new ArrayList<Double>();
            list1.add(d1xd2[0][0]);
            list1.add(d1xd2[0][1]);
            list1.add(d1xd2[0][2]);
            List<Double> list2 = new ArrayList<Double>();
            list2.add(d1xd2[1][0]);
            list2.add(d1xd2[1][1]);
            list2.add(d1xd2[1][2]);
			cross = VectorHelpers.calcCrossProduct(list1, list2);
			double[][] scalarproj = {{0, 0, 0}, {0, 0, 0}};
			scalarproj[1] = cross;
			scalarproj[0] = p1p2;
            list1 = new ArrayList<Double>();
            list1.add(scalarproj[0][0]);
            list1.add(scalarproj[0][1]);
            list1.add(scalarproj[0][2]);
            list2 = new ArrayList<Double>();
            list2.add(scalarproj[1][0]);
            list2.add(scalarproj[1][1]);
            list2.add(scalarproj[1][2]);
			double result = VectorHelpers.calcScalarProjection(list1, list2);
			return result;
		}
		else
			return -1;
	}

    // return true if this line and ln are skew
	public boolean areSkew(Line3D ln) {
		double[] v1 = ptA.toArray();
		double[] v2 = ln.getPtA().toArray();
		double[][] dot = {{0,0,0},{0,0,0}};
		dot[0][0] = v1[0] - v2[0];
		dot[0][1] = v1[1] - v2[1];
		dot[0][2] = v1[2] - v2[2];
		double[][] cross = {{0,0,0},{0,0,0}};
		cross[0] = getDirxn();
		cross[1] = ln.getDirxn();
        List<Double> list1 = new ArrayList<Double>();
        list1.add(cross[0][0]);
        list1.add(cross[0][1]);
        list1.add(cross[0][2]);
        List<Double> list2 = new ArrayList<Double>();
        list2.add(cross[1][0]);
        list2.add(cross[1][1]);
        list2.add(cross[1][2]);
		dot[1] = VectorHelpers.calcCrossProduct(list1, list2);
        list1 = new ArrayList<Double>();
        list1.add(dot[0][0]);
        list1.add(dot[0][1]);
        list1.add(dot[0][2]);
        list2 = new ArrayList<Double>();
        list2.add(dot[1][0]);
        list2.add(dot[1][1]);
        list2.add(dot[1][2]);
		double result = VectorHelpers.calcDotProduct(list1, list2);
		if (result != 0)
			return true;
		else
			return false;
	}

    // return true if this line and ln intersect
	public boolean ifHasIntersection(Line3D ln) {
		if (!(isParallelTo(ln))) {
			Vector3D a;
			a = line.intersection(ln.getApacheLine());
			tmpIntersect[0] = a.getX();
			tmpIntersect[1] = a.getY();
			tmpIntersect[2] = a.getZ();
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
