/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detection;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class accuracy implements Serializable{
    
    double accuracy_sqli=0 ;
    double average_sqli = 0;
    double avg_count_sqli;
    double accuracy_xss ;
    double accuracy_sqli_flag =0;
    double accuracy_xss_flag =0;
    double sqli_count;
    double xss_count;   
    double xss_total;
    double sqli_total;
    double change_threshold = 5;
    
   public accuracy()
   {
       load();
   }
    
    
public void write_to_file()
{
  FileOutputStream fout = null;
        try {
            File f = new File("accuracy.txt");
            if(!f.exists())
            {
                f.createNewFile();
            }
            fout = new FileOutputStream("accuracy.txt");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(this);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(accuracy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(accuracy.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fout.close();
            } catch (IOException ex) {
                Logger.getLogger(accuracy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
}


public void load()
{
    FileInputStream fis = null;
        try {
            fis = new FileInputStream("accuracy.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            
            accuracy temp = (accuracy) ois.readObject();
            
            this.accuracy_sqli = temp.accuracy_sqli;
            this.accuracy_sqli_flag =temp.accuracy_sqli_flag;
            this.accuracy_xss = temp.accuracy_xss;
            this.accuracy_xss_flag =temp.accuracy_xss_flag;
            this.sqli_count =temp.sqli_count;
            this.xss_count =temp.xss_count;
            this.xss_total =temp.xss_total;
            this.sqli_total =temp.sqli_total;
            this.average_sqli = temp.average_sqli;
            this.avg_count_sqli = temp.avg_count_sqli;
            
            
        } catch (FileNotFoundException ex) {
          // Logger.getLogger(accuracy.class.getName()).log(Level.SEVERE, null, ex);
            write_to_file();
            load();
        } catch (IOException ex) {
            Logger.getLogger(accuracy.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(accuracy.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fis.close();
            } catch (Exception ex) {
             //  Logger.getLogger(accuracy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
}

public void xss_counter(double acc,int len)
{
      load();
     double num = accuracy_xss*xss_total;
     xss_total += 1;  
     double prev = accuracy_xss;
     
    
        accuracy_xss = (num+acc)/xss_total;
                
        if(prev>accuracy_xss)
        {
        if(accuracy_xss_flag==0)
        {
           xss_count += 1;
           check_xss(len);
        }
        else {
            accuracy_xss_flag=0;
             xss_count = 1;
        }  
        }
        else
        {
           if(accuracy_xss_flag==0)
           {
                              
            accuracy_xss_flag=1;
     
           }
           xss_count = 1;

        }
        
        System.out.println("Accuracy of System :"+accuracy_xss);
          System.out.println("Descesion Count:"+xss_count);
        //  System.out.println("----------------------------------------------------------------------------");
        write_to_file();
    
    
    
}

public void avg_change(double avg)
{
	load();
   average_sqli = (average_sqli*this.avg_count_sqli+avg)/(++this.avg_count_sqli);
   System.out.println("New Threshold :" + average_sqli);
   this.write_to_file();
}


public void check_xss(int len)
{
    if(xss_count>change_threshold&&len>5)
    {
         boolean success = (new File
         ("xss_data.csv")).delete();
         
         if(success) System.out.println("\nFile has been deleted due to low accuracy\n");
         xss_count=0;
         accuracy_xss_flag=1;
    }
}

public void counter_sqli(double acc)
{
	load();
    this.accuracy_sqli = (this.accuracy_sqli*this.sqli_total+acc)/(++this.sqli_total);
    System.out.println("Total no of Classified Strings :"+this.sqli_total);
    System.out.println("Accuracy of System :"+accuracy_sqli);    
    write_to_file();
}


}





