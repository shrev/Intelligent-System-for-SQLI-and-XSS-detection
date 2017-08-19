/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static detection.Sqli.readfile;

/**
 *
 * @author Admin
 */
public class xss {
    
     static double xtrain[][],ytrain[][],xtest[][],ytest[][];
     String[] s2 = {"javascript","script",">","%3C","\"<",">\"","?","alert","iframe","src","document","cookie"};
     double[] scores = {2,1,0.5,0.5,3,3,0.5,2,2,1,1,0.5};
     int flag=0;
     double threshold = 6;
     int heuristic = 5;
     accuracy xss_acc=new accuracy();
     
     
   /*  public static void main(String args[])
     {
         xss temp =new 
     }*/
     
     
     public result classify(String s)
     {
         double score =0;
         int prediction=0;
         result res=null;
         double accs=0,accml=0;
          xtest = new double[1][s2.length];
          int len=0;
         for(int i=0;i<s2.length;i++)
         {
             if(s.toLowerCase().contains(s2[i])) 
             {  
                 xtest[0][i] = 1;
                 score = score+scores[i];
             }
             else xtest[0][i] = 0;
         }
         
         if(score>threshold) prediction = 1;
         
        
         
          
    
             ytest = new double[1][1];
             
             ytest[0][0] = Integer.parseInt(""+s.charAt(s.length()-1));
             
                 if(ytest[0][0] ==2)
                     ytest[0][0] =1;
                 else ytest[0][0] =0;
            
              append_file(xtest,ytest,accml); 
//need tochange
             
             if(ytest[0][0]==prediction) accs =1;
         
         if(isavailable())
         {
             try {
               /* 
                 
                 for(int i=0;i<s2.length;i++)
                 {
                     if(s.toLowerCase().contains(s2[i]))
                     {
                         xtest[1][i] = 1;
                     }
                     else  xtest[1][i] = 0;
                 }*/
 
                 svm ml =new svm();
                 res =ml.classify_intelligent(xtrain,ytrain,xtest,ytest);
                 accml = res.getAccuracy();
               
                
                 flag=1;
                 
             } catch (IOException ex) {
                 Logger.getLogger(xss.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
         
         len =xtrain.length;
         System.out.println("----------------------------------------------XSS CLassification-------------------------------------");
         System.out.println("\n\nXSS Score : " +score);
         System.out.println("Total Classified Strings : " +xss_acc.xss_total);
         System.out.println("Accuracy of Score Filter :"+accs);
         System.out.println("Accuracy of classifier :" + accml);
         
         
         if(flag==1)
         {
             if(s.length()<25)  
            	 {
            	 System.out.println("\n Using Score Filter as Primary");
            	 xss_acc.xss_counter(accs,len);
            	 }
             else
             {
             flag=0;
             xss_acc.xss_counter(accml,len); 
             }//trust machine learning more
         }
         else
         {
        	 System.out.println("\n Using Score Filter as Primary");
             xss_acc.xss_counter(accs,len);
         }
         
      
         
         
         return res;
         
         
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
     
     
     public boolean isavailable()
     {
        
         double[][] file = readfile("xss_data.csv");
         set s1 = splitfile(file);
         
         boolean result = s1.x.length>heuristic;
         
         int sum1=0,sum2=0;
         
         for(int i=0;i<s1.y.length;i++)
         {
             if(s1.y[i][0]==1 ) sum1++;
             if(s1.y[i][0]==0) sum2++;
         }
         
         xtrain = s1.x;
         ytrain = s1.y;
         System.out.println("size :"+s1.y.length);
         return result&(sum1>0&&sum2>0);
         
     }
   
     
     public void append_file(double[][] xtest,double[][] ytest,double acc)
     {
         
         BufferedReader CSV = null;
         try {
             File f = new File("xss_data.csv");
             if(!f.exists())
             {
                 try {
                     f.createNewFile();
                 } catch (IOException ex) {
                     Logger.getLogger(xss.class.getName()).log(Level.SEVERE, null, ex);
                 }
             }
             CSV = new BufferedReader(new FileReader(f));
             FileWriter writer = new FileWriter(f,true);
              String temp = Arrays.toString(xtest[0]).replaceAll("\\[|\\]|\\s", "");
              writer.append(temp+","+ytest[0][0]+"\n");
              writer.flush();
              writer.close();
              CSV.close();
             
             
         } catch (FileNotFoundException ex) {
             Logger.getLogger(xss.class.getName()).log(Level.SEVERE, null, ex);
         } catch (IOException ex) {
             Logger.getLogger(xss.class.getName()).log(Level.SEVERE, null, ex);
         } finally {
             try {
                 CSV.close();
          
             } catch (IOException ex) {
                 Logger.getLogger(xss.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
            
            
     }
    
    
   /* public static void main(String args[])
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
                 
                
                       
                 
                 xtrain = new double[max][s2.length];
                 xtest =new double[max][1];
                 
               for(int j=0;j<s2.length;j++)
                 {
                 
                     if(s1.toLowerCase().contains(s2[j]))
                     {
                         xtrain[i][j]=1;
                     }
                     else xtrain[i][j]=0;
                     
                 }
               
                 String temp = Arrays.toString(xtrain[i]).replaceAll("\\[|\\]|\\s", "");
                     System.out.println(temp);
                 //temp = temp.replaceAll("[", "");
                 // temp = temp.replaceAll("]", "");
                 xtest[i][0] = tag;
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
           double[][] x1 = readfile("data0.csv");
           double[][] x2 = readfile("data1.csv");
           double[][] x3 = readfile("data3.csv");
           
           set set1 = splitfile(x1) ;
           set set2 = splitfile(x2);
           set set3 =splitfile(x3);
           
           combine(set1.x,set2.x,set3.x,set1.y,set2.y,set3.y);
           
           System.out.println(""+x2.length +" " +x1.length+" "+x3.length);
           
           
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
           
           ytest[0][0] = Integer.parseInt(""+s.charAt(s.length()-1));
           System.out.println(" "+ytest[0][0] + " " +Arrays.toString(xtest[0]));
           
           
       }
       
       
         
        
        public void combine(double[][] x1,double[][] x2,double[][] x3,double[][] y1,double[][] y2,double[][] y3)
        {
           int sa = x1.length;
           int sb = x2.length;
           int sn =x3.length;
           
           int sc = x1[0].length;
           
           xtrain = new double[sa+sb+sn][sc];
           ytrain = new double[sa+sb+sn][1];
           xtest = new double[1][sc];
           ytest = new double[1][1];
           
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
           
                for(int i=sb+sa;i<sn+sa+sb;i++)
           {
               for(int j=0;j<sc;j++)
               {
                   xtrain[i][j] = x3[i-(sb+sa)][j];
               }
               ytrain[i][0] = y3[i-(sb+sa)][0];
           }
                
               System.out.println(""+ Arrays.toString(xtrain[0]));
           /*System.arraycopy(x1,0, xtrain, 0, sa);
           Arrays.toString(xtrain);
           System.arraycopy(x2,0, xtrain, sa, sa+ sb -1);
           if(sn>0)
           System.arraycopy(x3,0, xtrain, sb, sa+sb+sn - 1);
           
           System.arraycopy(y1,0, xtrain, 0, sa);
           System.arraycopy(y2,0, xtrain, sa, sa+sb -1 );
           if(sn>0)
           System.arraycopy(y3,0, xtrain, sb, sa+sb+sn -1); *//*
                     
        }
        
        
         */
             
    
}
