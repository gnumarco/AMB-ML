/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amb.ml.server.control;

import amb.ml.utils.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import amb.ml.GP.ec.Evolve;

/**
 *
 * @author marc
 */
public class GPControlServer implements amb.ml.generic.AMBApp {
    
    protected Memory m = new Memory();
    protected Evolve e = new Evolve();
    
    
    GPControlServer(){
        e.config(m,new String[]{"-file","control.params"});
        e.start();
    }

    @Override
    public Message messageHandler(Message m) {
        
        Message mes = null;
        
        if (m.type.equalsIgnoreCase("fitness")) {
            mes = updateFitness(m.value);
        }
        if (m.type.equalsIgnoreCase("sensors")) {
            System.out.println("Sensors: "+m.value[0]);
            mes = updateSensors(m.value);
        }
        return mes;
    }

    public Message updateFitness(double[] fit) {
        m.indivFinished=true;
        m.fit  = fit[0];
        Message mes = new Message();
        mes.type = "fitness";
        mes.value = new double[]{0};
        return mes;

    }

    public Message updateSensors(double[] sens) {
        // write the sensors to memory
        System.out.println("writing sensors to shared memory...");
        m.sens = sens;
        m.newSens = true;
        System.out.println("done");
        // here wait to read the actuation from memory
        while(!m.newAct)
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(GPControlServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        Message mes = new Message();
        mes.type = "actuation";
        mes.value = m.act;
        m.newAct = false;
        return mes;
    }

}
