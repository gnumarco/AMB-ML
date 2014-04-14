/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amb.ml.server.control;

import amb.ml.utils.Message;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marc
 */
public class GPControlServerTest {
    
    public GPControlServerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of messageHandler method, of class GPControlServer.
     */
    @Test
    public void testMessageHandler() {
        System.out.println("messageHandler");
        Message m = null;
        GPControlServer instance = new GPControlServer();
        Message expResult = null;
        Message result = instance.messageHandler(m);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateFitness method, of class GPControlServer.
     */
    @Test
    public void testUpdateFitness() {
        System.out.println("updateFitness");
        double[] fit = null;
        GPControlServer instance = new GPControlServer();
        Message expResult = null;
        Message result = instance.updateFitness(fit);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateSensors method, of class GPControlServer.
     */
    @Test
    public void testUpdateSensors() {
        System.out.println("updateSensors");
        double[] sens = null;
        GPControlServer instance = new GPControlServer();
        Message expResult = null;
        Message result = instance.updateSensors(sens);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
