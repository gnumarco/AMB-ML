/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/


package amb.ml.GP.ec.app.control.func;
import amb.ml.GP.ec.*;
import amb.ml.GP.ec.app.control.*;
import amb.ml.GP.ec.gp.*;
import amb.ml.GP.ec.util.*;

/* 
 * X.java
 * 
 * Created: Wed Nov  3 18:26:37 1999
 * By: Sean Luke
 */

/**
 * @author Sean Luke
 * @version 1.0 
 */

public class Xn extends GPNode
    {
    private int n = 0;
    private int numInput = -1;
    public String toString() { return "S"+ numInput; }

/*
  public void checkConstraints(final EvolutionState state,
  final int tree,
  final GPIndividual typicalIndividual,
  final Parameter individualBase)
  {
  super.checkConstraints(state,tree,typicalIndividual,individualBase);
  if (children.length!=0)
  state.output.error("Incorrect number of children for node " + 
  toStringForError() + " at " +
  individualBase);
  }
  
*/
    public void resetNode(final EvolutionState state, final int thread)
        { n = Math.abs(state.random[thread].nextInt());
        //state.output.message("Resetting "+n);
        Parameter p;
        p = new Parameter("eval.problem");
        numInput=n%(state.parameters.getInt(p.push("sensors"), null)*state.parameters.getInt(p.push("groups"), null));
        //state.output.message("Resetting "+numInput);
        }
    
    public int expectedChildren() { return 0; }

    public void eval(final EvolutionState state,
        final int thread,
        final GPData input,
        final ADFStack stack,
        final GPIndividual individual,
        final Problem problem)
        {
        ControlData rd = ((ControlData)(input));      
        rd.x = ((Control)problem).currentValue[numInput];
        }
    
    public String toStringForHumans()
        { return "S" + numInput; }
    }



