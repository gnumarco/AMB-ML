/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amb.ml.server.control;

import amb.ml.utils.Message;

/**
 *
 * @author marc
 */
public class SimpleControlServer implements amb.ml.generic.AMBApp {

    @Override
    public Message messageHandler(Message m) {

        Message mes = null;
        System.out.println("Got message: "+m.type);

        if (m.type.equalsIgnoreCase("fitness")) {
            mes = updateFitness(m.value);
        }
        if (m.type.equalsIgnoreCase("sensors")) {
            mes = setSensorsGetActuation(m.value);
        }
        return mes;
    }

    public Message updateFitness(double[] v) {
        Message m = new Message();
        m.type = "fitness";
        m.value = new double[] {1};
        
        return m;
    }

    public Message setSensorsGetActuation(double[] v) {
        Message m =  new Message();
        m.type = "actuation";
        m.value = new double[] {0,0.5};

        return m;
    }

}
