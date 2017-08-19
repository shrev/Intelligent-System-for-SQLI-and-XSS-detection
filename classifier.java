/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import static detection.Sqli.xtrain;

/**
 *
 * @author Admin
 */
public class classifier {
    
    static String packet;
    
    
    
    public classifier(String s)
    {
        packet =s;
    }
    
    public static void main(String args[])
    {
        try {
           /* Sqli fo = new Sqli();
           // String f = "normalTrafficTraining.txt";
           // fo.make_file(f, 0,"data0.csv");
           // f = "anomalousTrafficTest.txt";
           // fo.make_file(f, 1, "data1.csv");     
            fo.load_files();   
            packet = "from  this"; 
            fo.prepare_test(packet); 
            
            svm sv = new svm();
            double x =  sv.classify(fo.xtrain,fo.ytrain, fo.xtest, fo.ytest); */
            
         //   System.out.println(basic_sqli_detect("select * from table"));
            
            
            
           // prepare_train();
            
           /* xss temp =new xss();
            temp.classify("<SCRIPT a=`>`4");
            
            stack2 sqli =new stack2("<username>' OR 1=1--1",23.3);
           // sqli.counter();
            sqli.makefiles();
            sqli.classify();
            */
            
            
        } catch (Exception ex) {
            Logger.getLogger(classifier.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
        
       public static void prepare_train()
       {
            Sqli fo = new Sqli();
            String f = "normalTrafficTraining.txt";
            fo.make_file(f, 0,"data0.csv");
            f = "anomalousTrafficTest.txt";
            fo.make_file(f, 1, "data1.csv"); 
       }
       
       public static boolean basic_xss_detect(String query)
       {
    	   xss_detect  detector =new xss_detect();
    	   return detector.classify(query);
    	   
    	   
       }
    
    
        public static boolean basic_sqli_detect(String query)
        {
             boolean x=false;
        try {
            Sqli detector = new Sqli();
            detector.load_files(); 
            detector.prepare_test(query);
            
            svm sv = new svm();
            x =  sv.classify(detector.xtrain,detector.ytrain, detector.xtest); 
            
            
        } catch (IOException ex) {
            Logger.getLogger(classifier.class.getName()).log(Level.SEVERE, null, ex);
        }
            
         return x;
            
        }
        
        public static boolean learn_xss(String query)
        {
        	xss detector =new xss();
        	result res = detector.classify(query);
        	return res.getDrop()==1;
        }
        
        public static boolean learn_sqli(String query,ArrayList<String> tables)
        {
        	boolean res =false;
        	stack1 scores =new stack1(query);
        	double score=scores.threat_detect();
        	int decision = scores.decision();
        	if(decision==0)
        	{
        		
        		System.out.println("Score Not greater than Threshold.Skipping Stack 2 and 3\nFinal Verdict: Not SQLi ");
        		return false;
        	}
        	else 
        	{
        		stack2 classify = new stack2(query,score);
        		boolean r = classify.classify();
        		if(!r)
        		{
        			stack3 match = new stack3();
        			boolean final_r = match.schema_matcher(tables, query);
        			if(final_r)
        			{
        				System.out.println("Final_Verdict : SQLi");
        				res = true;
        			}
        			else 
        			{
        				System.out.println("Final_Verdict : Not SQLi");
        				
        			}
        		}
        		else {
        			System.out.println("Skipping Stack 3\n Final_Verdict : SQLi");
        			res = true;
        		}
        		
        	}
        	
        	return res;
        }
        
        
 
    
}
