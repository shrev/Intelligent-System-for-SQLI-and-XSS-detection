/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detection;

/**
 *
 * @author Admin
 */
public class stack1 {
    
    accuracy st1 =new accuracy();
     String[] s2 = {"select","*","from","table","drop","*/","and","or","update","into","is null","set","where","null","/*","\'","--","infile","union"};
     int scores[] = {2,1,2,1,1,2,2,1,1,2,1,1,2,1,2,2,3,3,3,3};
     int flag=0;
     String query;
     double score=0;
     double threshold=st1.average_sqli;
     
     public stack1(String s)
     {
         query=s;
     }
    
     
     public double threat_detect()
     {
         for(int i=0;i<s2.length;i++)
         {
             if(query.toLowerCase().contains(s2[i])) score++;
         }
         
         System.out.println("---------------------------------------------------------SQLi--------------------------------------------------------");
         System.out.println("Stack 1\nSQLi Score :"+score);
         System.out.println("Initial Threshold :"+threshold);
         
         return score;
     }
     
     public int decision()
     {
         if(score>threshold) return 1;
         else 
        	 {
        	    if(Integer.parseInt(""+query.charAt(query.length()-1))==1)
        	    {
        	    st1.avg_change(0);
        	    st1.counter_sqli(0);
        	    }
        	    return 0;
        	 }
     }
}
