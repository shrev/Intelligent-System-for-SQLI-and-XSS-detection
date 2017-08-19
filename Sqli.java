/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author Admin
 */
public class Sqli {
     static double xtrain[][],ytrain[][],xtest[][],ytest[][];
     String[] s2 = {"select","*","from","table","drop","*/","and","or","update","into","is null","set","where","null","/*","1=1","--","1=1;","\'1=1","--\'"};
     int flag=0;
    
    
    
    public static void main(String args[])
    {
        //read_parse("normalTrafficTraining.txt",0);
    }
    
    public void make_file(String path,int tag,String fname)
    {
         double xtrain[][]=null,xtest[][]=null;
        BufferedReader CSV = 
            null; 
        int i=0;
        int max =400;
         try {
             CSV = new BufferedReader(new FileReader(path));
             FileWriter writer = new FileWriter(fname);

             String pattern = "(GET|POST)(\\s)([^ ]+)(\\s)";
             Pattern r = Pattern.compile(pattern);
             String dataRow = CSV.readLine();
            
             
             while(dataRow!=null)
             {
             Matcher m = r.matcher(dataRow);
             //System.out.println(dataRow.toString());
             
             if(m.find())
             {
                   
                // System.out.print(1);
                //System.out.print(""+m.group(3)+"\n");
                 
                 String s1=m.group(3);
                 
              //   URL x = new URL(s1);
               //  System.out.println(""+URLDecoder.decode(s1, "UTF-16"));
                       
                 
                 xtrain = new double[1][s2.length];
                 xtest =new double[1][1];
                 
               for(int j=0;j<s2.length;j++)
                 {
                 
                     if(s1.toLowerCase().contains(s2[j]))
                     {
                         xtrain[0][j]=1;
                     }
                     else xtrain[0][j]=0;
                     
                 }
               
                 String temp = Arrays.toString(xtrain[0]).replaceAll("\\[|\\]|\\s", "");
                     System.out.println(temp);
                 //temp = temp.replaceAll("[", "");
                 // temp = temp.replaceAll("]", "");
                // ytest[0][0] = tag;
                 writer.append(temp + "," + tag +'\n');
                 i++;
                  
             }
             else ; //System.out.println("no match");;
             
             dataRow = CSV.readLine();
             
             
             if(i==max) {
                 
                 writer.flush();
                 writer.close();
                 break;
             }
             }
             
         } catch (FileNotFoundException ex) {
            Logger.getLogger(Sqli.class.getName()).log(Level.SEVERE, null, ex);
            flag=1;
        } catch (IOException ex) {
            Logger.getLogger(Sqli.class.getName()).log(Level.SEVERE, null, ex);
            flag=1;
        }
    }
    
    
       public void load_files()
       {
    	   System.out.println("------------------------------SQLI-------------------------------------");
           double[][] x1 = readfile("data1.csv");
           double[][] x2 = readfile("data0.csv");
           
           set set1 = splitfile(x1) ;
           set set2 = splitfile(x2);
          // set set3 =splitfile(x3);
           
           combine(set1.x,set2.x,set1.y,set2.y);
           
           System.out.println(""+x2.length +" "+x1.length);
           
           
       }
       
       public void prepare_test(String s)
       {
           for(int j=0;j<s2.length;j++)
                 {
                 
                     if(s.toLowerCase().contains(s2[j]))
                     {
                         xtest[0][j]=1;
                     }
                     else xtest[0][j]=0;
                     
                 }
           
           
           
           
           
       }
       
       
         
        
        public void combine(double[][] x1,double[][] x2,double[][] y1,double[][] y2)
        {
           int sa = x1.length;
           int sb = x2.length;
         //  int sn =x3.length;
           
           int sc = x1[0].length;
           
           xtrain = new double[sa+sb][sc];
           ytrain = new double[sa+sb][1];
           xtest = new double[1][sc];
          // ytest = new double[1][1];
           
           for(int i=0;i<sa;i++)
           {
               for(int j=0;j<sc;j++)
               {
                   xtrain[i][j] = x1[i][j];
               }
               ytrain[i][0] = y1[i][0];
           }
           
             for(int i=sa;i<sa+sb;i++)
           {
               for(int j=0;j<sc;j++)
               {
                   xtrain[i][j] = x2[i-sa][j];
               }
               ytrain[i][0] = y2[i-sa][0];
           }
           
           /*     for(int i=sb+sa;i<sn+sa+sb;i++)
           {
               for(int j=0;j<sc;j++)
               {
                   xtrain[i][j] = x3[i-(sb+sa)][j];
               }
               ytrain[i][0] = y3[i-(sb+sa)][0];
           } */
                
              // System.out.println(""+ Arrays.toString(xtrain[0]));
           /*System.arraycopy(x1,0, xtrain, 0, sa);
           Arrays.toString(xtrain);
           System.arraycopy(x2,0, xtrain, sa, sa+ sb -1);
           if(sn>0)
           System.arraycopy(x3,0, xtrain, sb, sa+sb+sn - 1);
           
           System.arraycopy(y1,0, xtrain, 0, sa);
           System.arraycopy(y2,0, xtrain, sa, sa+sb -1 );
           if(sn>0)
           System.arraycopy(y3,0, xtrain, sb, sa+sb+sn -1); */
                     
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


