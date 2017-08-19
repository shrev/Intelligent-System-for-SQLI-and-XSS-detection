/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detection;

import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class stack3 {
    
    public boolean schema_matcher(ArrayList<String> list,String s)
    {
       boolean res =false;
       
       for(int i=0;i<list.size();i++)
       {
           if(s.toLowerCase().contains(list.get(i).toLowerCase()))
           {
               res =true;
               System.out.println("Stack 3\n Table name '"+list.get(i)+"' detected\n");
               break;
           }
           
       }
       
       if(res==false)
       {
    	   System.out.println("Stack 3\n No Table detected");
       }
       
       
        
        return res;
    }
    
}
