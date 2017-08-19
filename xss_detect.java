package detection;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import detection.xss.set;

public class xss_detect {
	
	
	 static double xtrain[][],ytrain[][],xtest[][],ytest[][];
     String[] s2 = {"javascript","script",">","%3C","\"<",">\"","?","alert","iframe","src","document","cookie"};
     double[] scores = {2,1,0.5,0.5,3,3,0.5,2,2,1,1,0.5};
     int flag=0;
     
     
     
     public boolean classify(String query)
     {
    	 double[][] file = readfile("xss_data_copy.csv");
         set s1 = splitfile(file);
         
         xtrain = s1.x;
         ytrain = s1.y;
         
      
         boolean prediction =false;
          
          xtest = new double[1][s2.length];
          int len=0;
         for(int i=0;i<s2.length;i++)
         {
             if(query.toLowerCase().contains(s2[i])) 
             {  
                 xtest[0][i] = 1;
               //  score = score+scores[i];
             }
             else xtest[0][i] = 0;
         }
         
         System.out.println(""+Arrays.toString(xtest));
         
         svm ml =new svm();
         try {
			prediction= ml.classify(xtrain, ytrain, xtest);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
                  
         return prediction;
                
     }
     
     
     
     
     public static double[][] readfile(String path)
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
            // Logger.getLogger(svm.class.getName()).log(Level.SEVERE, null, ex);
             return new double[0][0];
         } catch (Exception ex) {
            // Logger.getLogger(svm.class.getName()).log(Level.SEVERE, null, ex);
             return new double[0][0];
         } finally {
             try {
                 CSV.close();
             } catch (Exception ex) {
                // Logger.getLogger(svm.class.getName()).log(Level.SEVERE, null, ex);
                 
             }
         }
         
         
         return csvMatrix;
     }
         
     public set splitfile(double[][] x){
    	 
    	 System.out.println("------------------------------XSS-------------------------------------");
         
       try
       {
         set train =new set(x.length,x[0].length-1);
       
       for(int i=0;i<x.length;i++)
       {
           int j=0;
           for(j=0;j<x[0].length-1;j++)
           {
               train.x[i][j] = x[i][j]; 
           }
           train.y[i][0]=x[i][j];
       }
         return train;  
       }
       catch(Exception e)
       {
           return new set(0,0);
       }
         
         
     
     }
             
             
             
       class set{
           double[][] x,y;
           
           public set(int sizerow,int sizecolumn)
           {
               x = new double[sizerow][sizecolumn];
               y =new double[sizerow][1]; 
           }
                  
       } 
     
     
     

}
