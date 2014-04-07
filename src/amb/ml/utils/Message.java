/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amb.ml.utils;

/**
 *
 * @author marc
 */
public class Message {
    public String type;
    public double[] value;
    
    public void Message(){
        type = null;
        value = null;
    }
    
    public void message(String t, double[] v){
        type = t;
        value = v;
    }
    
}
