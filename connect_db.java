/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class connect_db {
    
    public void connect()
    {
         String url ="jdbc:mysql://localhost:3306";
		String user="root";
		String password="shrev95";
                Statement stt;
                
    		try{
			//to identify MySQL driver
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection con=DriverManager.getConnection(url, user, password);
			//can only run 1 statement at a time, can't separate statements using ';'
			stt=con.createStatement();
			//create and select DB
                        stt.execute("use bank;");
  			
			
              	}
		catch(ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e){
		Logger.getLogger(connect_db.class.getName()).log(Level.SEVERE, null, e);
                }
    }
    
    
    
}
