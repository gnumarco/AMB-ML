/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package amb.ml.server;

/**
 *
 * @author marc
 */
import org.neuroph.core.NeuralNetwork;
import org.neuroph.nnet.Perceptron;
import org.neuroph.core.data.*;
import java.util.Vector;
import java.io.File;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This example trains NaiveBayes incrementally on data obtained from the
 * ArffLoader.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */
public class NNPredict {

    /**
     * Expects an ARFF file as first argument (class attribute is assumed to be
     * the last attribute).
     *
     * @param args the commandline arguments
     * @throws Exception if something goes wrong
     */
    /**
     * This sample shows how to create, train, save and load simple Perceptron
     * neural network
     */
    public static void Train() {

// create training set (logical AND function)
        DataSet trainingSet = new DataSet(2, 1);
        trainingSet.addRow(new DataSetRow(new double[]{0, 0}, new double[]{0}));
        trainingSet.addRow(new DataSetRow(new double[]{0, 1}, new double[]{0}));
        trainingSet.addRow(new DataSetRow(new double[]{1, 0}, new double[]{0}));
        trainingSet.addRow(new DataSetRow(new double[]{1, 1}, new double[]{1}));

// create perceptron neural network
        NeuralNetwork myPerceptron = new Perceptron(2, 1);

// learn the training set
        myPerceptron.learn(trainingSet);

// test perceptron
        System.out.println("Testing trained perceptron");
        testNeuralNetwork(myPerceptron, trainingSet);

// save trained perceptron
        myPerceptron.save("mySamplePerceptron.nnet");

// load saved neural network
        NeuralNetwork loadedPerceptron = NeuralNetwork.load("mySamplePerceptron.nnet");

// test loaded neural network
        System.out.println("Testing loaded perceptron");
        testNeuralNetwork(loadedPerceptron, trainingSet);

    }

    public static void testNeuralNetwork(NeuralNetwork nnet, DataSet tset) {

        for (DataSetRow dataRow : tset.getRows()) {

            nnet.setInput(dataRow.getInput());
            nnet.calculate();
            double[] networkOutput = nnet.getOutput();
            System.out.print("Input: " + Arrays.toString(dataRow.getInput()));
            System.out.println(" Output: " + Arrays.toString(networkOutput));

        }

    }

}
