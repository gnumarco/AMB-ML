/*
  Copyright 2006 by Sean Luke
  Licensed under the Academic Free License version 3.0
  See the file "LICENSE" for more information
*/


package amb.ml.GP.ec.select;
import amb.ml.GP.ec.util.Parameter;
import amb.ml.GP.ec.*;

/* 
 * SelectDefaults.java
 * 
 * Created: Thu Jan 20 17:14:40 2000
 * By: Sean Luke
 */

/**
 * @author Sean Luke
 * @version 1.0 
 */

public final class SelectDefaults implements DefaultsForm
    {
    public static final String P_SELECT = "select";

    /** Returns the default base. */
    public static final Parameter base()
        {
        return new Parameter(P_SELECT);
        }

    }
