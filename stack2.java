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

/**
 *
 * @author Admin
 */
public class stack2 {
    
    accuracy sqli =new accuracy();
    
    public String query;
     static double xtrain[][],ytrain[][],xtest[][],ytest[][];
      int flag=0;
    String[]  punctuation = {"[","]","(",")","\'","\"",";"};
    String  slc[] ={"--"},mlc[] = {"/*","*/"};
    String[] operators = {"<",">","<","=",">=","=","==","!=","<<",">>","|","&","-","+","%","^"};
    String[] lops = {"NOT","AND","OR","&&","||"};
    String[] keys = {"SELECT","UPDATE","INSERT","CREATE","DROP","ALTER","RENAME","admin"}; 
    String[] phrases = {"1=1","1=1;","\'1=1","--\'","admin--","or 1=1"};
    Object[] tokens =  {punctuation,mlc,operators,lops,keys,phrases,slc};
    double[] counts = new double[tokens.length];
    double score=0;
    
    
            
    
    public stack2(String s,double score)
    {
        query = s;
        this.score=score;
    }
    
    
    public void makefiles()
    {
        //this method is called only once at the beginning.
        String f = "normalTrafficTraining.txt";
        create_file(f, 0,"data_normal.csv");
        f = "anomalousTrafficTest.txt";
        create_file(f, 1, "data_sqli.csv");     
      
    }
    
    
    public boolean classify()
    {
        try {
              load_files(); 
              counter();
            svm sq = new svm();
            result res = sq.classify_intelligent(xtrain, ytrain, xtest, ytest); 
            double acc = res.getAccuracy();
            System.out.println("----------------------------------------------------------------------------------------");
            System.out.println("Accuracy of classifier :"+acc);
            if(ytest[0][0]==0||ytest[0][0]==2)
            {
               sqli.avg_change(score);
            }
            append_file();     
            sqli.counter_sqli(acc);
            
            return res.drop==1;
            
        } catch (IOException ex) {
            Logger.getLogger(stack2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        
    }
    
   
    
    
    public void counter()
    {
        xtest = new double[1][tokens.length];
        ytest =new double[1][1];
        for(int i=0;i<tokens.length;i++)
        {
            String[] t = (String[])tokens[i];
           // System.out.println("i :"+i);
            counts[i]=0;
            for(int j=0;j<t.length;j++)
            {  
                if(query.toLowerCase().contains(t[j].toLowerCase()))
                {
                    counts[i] +=1;
                }
            }
                
        }
        xtest[0] = counts;
    //    System.out.println(""+query.toLowerCase()+" "+Arrays.toString(counts));
        ytest[0][0] = Integer.parseInt(""+query.charAt(query.length()-1));
        
       
    }
    
     public void append_file()
     {
         
         BufferedReader CSV = null;
         try {
             File f = new File("data_new.csv");
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
    
    
    public void create_file(String path,int tag,String fname)
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
             xtrain = new double[1][tokens.length];
             xtest =new double[1][1];

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
                       
                 
                 
                 
               for(int j=0;j<tokens.length;j++)
                 {
                 
                     String[] t = (String[])tokens[j];
                  //   System.out.println("i :"+j);
                     xtrain[0][j]=0;
                     for(int k=0;k<t.length;k++)
                     {
                        
                         if(s1.toLowerCase().contains(t[k].toLowerCase()))
                            {
                                xtrain[0][j] +=1;
                            }
                    }
                     
                    /* if(s1.toLowerCase().contains(s2[j]))
                     {
                         xtrain[i][j]=1;
                     }
                     else xtrain[i][j]=0;*/
                     
                 }
               
                 String temp = Arrays.toString(xtrain[0]).replaceAll("\\[|\\]|\\s", "");
                     System.out.println(temp);
                 //temp = temp.replaceAll("[", "");
                 // temp = temp.replaceAll("]", "");
                 xtest[0][0] = tag;
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
           
           //  System.out.println("\n"+sn+" "+sb+" "+sa);
           for(int i=sb+sa;i<sn+sa+sb;i++)
           {
               for(int j=0;j<sc;j++)
               {
                   //System.out.println("i:"+i+"j:"+j);
                   xtrain[i][j] = x3[i-(sb+sa)][j];
               }
               ytrain[i][0] = y3[i-(sb+sa)][0];
           }
                
            //   System.out.println(""+ Arrays.toString(xtrain[0]));
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
    
    
    public void load_files()
       {
           double[][] x1 = readfile("data_sqli.csv");
           double[][] x2 = readfile("data_normal.csv");
           double[][] x3 = readfile("data_new.csv");
           
           set set1 = splitfile(x1) ;
           set set2 = splitfile(x2);
           set set3 =splitfile(x3);
           
           combine(set1.x,set2.x,set3.x,set1.y,set2.y,set3.y);
           
           System.out.println(""+x2.length +" " +x1.length+" "+x3.length);
           
           
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
