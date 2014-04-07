/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/


package amb.ml.GP.ec.select;
import amb.ml.GP.ec.util.*;
import amb.ml.GP.ec.*;

/* 
 * FitProportionateSelection.java
 * 
 * Created: Thu Feb 10 16:31:24 2000
 * By: Sean Luke
 */

/**
 * Picks individuals in a population in direct proportion to their
 * fitnesses as returned by their fitness() methods.  This is expensive to
 * set up and bring down, so it's not appropriate for steady-state evolution.
 * If you're not familiar with the relative advantages of 
 * selection methods and just want a good one,
 * use TournamentSelection instead.   Not appropriate for
 * multiobjective fitnesses.
 *
 * <p><b><font color=red>
 * Note: Fitnesses must be non-negative.  0 is assumed to be the worst fitness.
 * </font></b>

 <p><b>Typical Number of Individuals Produced Per <tt>produce(...)</tt> call</b><br>
 Always 1.

 <p><b>Default Base</b><br>
 select.fitness-proportionate

 *
 * @author Sean Luke
 * @version 1.0 
 */

public class FitProportionateSelection extends SelectionMethod
    {
    /** Default base */
    public static final String P_FITNESSPROPORTIONATE = "fitness-proportionate";
    /** Normalized, totalized fitnesses for the population */
    public float[] fitnesses;

    public Parameter defaultBase()
        {
        return SelectDefaults.base().push(P_FITNESSPROPORTIONATE);
        }

    // don't need clone etc. 

    public void prepareToProduce(final EvolutionState s,
        final int subpopulation,
        final int thread)
        {
        // load fitnesses
        fitnesses = new float[s.population.subpops[subpopulation].individuals.length];
        for(int x=0;x<fitnesses.length;x++)
            {
            fitnesses[x] = ((Individual)(s.population.subpops[subpopulation].individuals[x])).fitness.fitness();
            if (fitnesses[x] < 0) // uh oh
                s.output.fatal("Discovered a negative fitness value.  FitProportionateSelection requires that all fitness values be non-negative(offending subpopulation #" + subpopulation + ")");
            }
        
        // organize the distribution.  All zeros in fitness is fine
        RandomChoice.organizeDistribution(fitnesses, true);
        }

    public int produce(final int subpopulation,
        final EvolutionState state,
        final int thread)
        {
        // Pick and return an individual from the population
        return RandomChoice.pickFromDistribution(
            fitnesses,state.random[thread].nextFloat());
        }
    
    public void finishProducing(final EvolutionState s,
        final int subpopulation,
        final int thread)
        {
        // release the distributions so we can quickly 
        // garbage-collect them if necessary
        fitnesses = null;
        }
    }