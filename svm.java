/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detection;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import libsvm.*;
import static libsvm.svm.*;

/**
 *
 * @author Admin
 */
public class svm {

    public result classify_intelligent(double[][] train, double[][] y_train, double[][] test, double[][] y_test) throws FileNotFoundException, IOException {

    //    System.out.println(train.length);
        double[][] xtrain = train;
        double[][] xtest = test;
        double[][] ytrain = y_train;
        double[][] ytest = y_test;

        svm_model m = svmTrain(xtrain, ytrain);
        double[] ypred = svmPredict(xtest, m);
        LinkedList<double[]> rows = new LinkedList<double[]>();
        LinkedList<Double> wrongpred = new LinkedList<Double>();
        double count = 0;

        for (int i = 0; i < xtest.length; i++) {
            System.out.println("Actual:" + ytest[i][0] + " Prediction:" + ypred[i] + "");
            if (ytest[i][0] == ypred[i]) {
                count += 1;
                rows.addLast(xtest[i]);
                wrongpred.addLast(ytest[i][0]);
            }

        }
        
        
      //  System.out.println("--------------------------------------------------------------------------------------");
        
        
        result res =new result();
        res.setAccuracy(count / xtest.length);
        res.setDrop(ypred[0]);

        return res;
    }
    
       public boolean classify(double[][] train, double[][] y_train, double[][] test) throws FileNotFoundException, IOException {

       // System.out.println(train.length);
        double[][] xtrain = train;
        double[][] xtest = test;
        double[][] ytrain = y_train;
     //   double[][] ytest = y_test;

        svm_model m = svmTrain(xtrain, ytrain);
        double[] ypred = svmPredict(xtest, m);
        LinkedList<double[]> rows = new LinkedList<double[]>();
        LinkedList<Double> wrongpred = new LinkedList<Double>();
        double count = 0;
        
      //  System.out.print(ypred[0]);

    /*    for (int i = 0; i < xtest.length; i++) {
            System.out.println("(Actual:" + ytest[i][0] + " Prediction:" + ypred[i] + ")");
            if (ytest[i][0] == ypred[i]) {
                count += 1;
                rows.addLast(xtest[i]);
                wrongpred.addLast(ytest[i][0]);
            }

        } */

      System.out.println("Prediction :" +ypred[0]);
        
       /* result res =new result();
        res.setAccuracy(count / xtest.length);
        res.setDrop(ypred[0]); */

        return ypred[0]==1;
    }


    /*  public static double[][] readfile(String path)
     {
           
     double[][] csvMatrix =null;
     BufferedReader CSV = 
     null; 
     try {
     CSV = new BufferedReader(new FileReader(path));
     LinkedList<String[]> rows = new LinkedList<String[]>();
     String dataRow = CSV.readLine();
     // Read the number of the lines in .csv file
     // i = row of the .csv file
     int i = 0;
     while ((dataRow = CSV.readLine()) != null){
     i++;
     rows.addLast(dataRow.split(","));
                 
                 
     }   
     String[] k= rows.pop();
     // System.out.println(""+k.length+" "+rows.size());
     csvMatrix= new double[rows.size()][rows.getFirst().length];
     int j=0;
     for(j=0;j<rows.size();j++)
     {
     for(int l=0;l<k.length;l++)
     {
     csvMatrix[j][l]=Double.parseDouble(k[l]);
     }  
     k=rows.pop();
     }
             
     } catch (FileNotFoundException ex) {
     Logger.getLogger(svm.class.getName()).log(Level.SEVERE, null, ex);
     } catch (IOException ex) {
     Logger.getLogger(svm.class.getName()).log(Level.SEVERE, null, ex);
     } finally {
     try {
     CSV.close();
     } catch (IOException ex) {
     Logger.getLogger(svm.class.getName()).log(Level.SEVERE, null, ex);
     }
     }
         
         
     return csvMatrix;
     }*/
    static svm_model svmTrain(double[][] xtrain, double[][] ytrain) {
        svm_problem prob = new svm_problem();
        int recordCount = xtrain.length;
        int featureCount = xtrain[0].length;
        prob.y = new double[recordCount];
        prob.l = recordCount;
        prob.x = new svm_node[recordCount][featureCount];

        for (int i = 0; i < recordCount; i++) {
            double[] features = xtrain[i];
            prob.x[i] = new svm_node[features.length];
            for (int j = 0; j < features.length; j++) {
                svm_node node = new svm_node();
                node.index = j;
                node.value = features[j];
                prob.x[i][j] = node;
            }
            prob.y[i] = ytrain[i][0];
        }

        svm_parameter param = new svm_parameter();
        param.probability = 1;
        param.gamma = 0.5;
        param.nu = 0.5;
        param.C = 100;
        param.svm_type = svm_parameter.C_SVC;
        param.kernel_type = svm_parameter.LINEAR;
        param.cache_size = 20000;
        param.eps = 0.001;

        svm_model model = svm_train(prob, param);

        return model;

    }

    static double[] svmPredict(double[][] xtest, svm_model model) {

        double[] yPred = new double[xtest.length];

        for (int k = 0; k < xtest.length; k++) {

            double[] fVector = xtest[k];

            svm_node[] nodes = new svm_node[fVector.length];
            for (int i = 0; i < fVector.length; i++) {
                svm_node node = new svm_node();
                node.index = i;
                node.value = fVector[i];
                nodes[i] = node;
            }

            int totalClasses = 2;
            int[] labels = new int[totalClasses];
            svm_get_labels(model, labels);

            double[] prob_estimates = new double[totalClasses];
            yPred[k] = svm_predict_probability(model, nodes, prob_estimates);

        }

        return yPred;
    }

}

