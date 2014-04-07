/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/


package amb.ml.GP.ec.app.control;

/* 
 * Sextic.java
 * 
 * Created: Fri Nov 30 21:38:13 EST 2001
 * By: Sean Luke
 */

/**
 * Sextic implements a Symbolic Control problem.
 *
 * <p>The equation to be regressed is y = x^6 - 2x^4 + x^2, {x in [-1,1]}
 * <p>This equation was introduced in J. R. Koza, GP II, 1994.
 *
 */
public class Sextic extends Control
    {
    public double func(double x)
        { return x*x*x*x*x*x - 2.0*x*x*x*x + x*x; }
    }