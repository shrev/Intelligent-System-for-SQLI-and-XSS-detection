package manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.DBConnector;


public class SignupManager {
	
	public static String login(String password,String uid) throws SQLException
	{
		Connection conn = null;
		Statement stmt = null;
		conn=DBConnector.getConnection();
		stmt = conn.createStatement();
	    
	    String sql = "SELECT uid, pwd from login where pwd='"+password+"' and uid='"+uid+"'";
	    
	    System.out.println("Final sql statement : "+ sql);
	    ResultSet rs = stmt.executeQuery(sql);
	    
	    
	       
	    if(!rs.next())
	    {
	    	
	    	return "0";
	    }
	    
	    else
	    {
	    
	    	sql="SELECT u.uid,account_id,first_name,last_name,account_pwd from user_details u,login l where pwd='"+password+"' and l.uid='"+uid+"' and l.uid = u.uid";
			ResultSet rs1=stmt.executeQuery(sql);
			String uid1 ="",first_name ="",account_pwd ="";
			
			if(rs1.next())
			{
			uid1=rs1.getString("uid");
			
			first_name=rs1.getString("first_name");
			
			account_pwd=rs1.getString("account_pwd");
			System.out.println("Connection Successful");
			}
	    	
			return "Name : "+first_name+" User Id : "+uid+" Password : "+account_pwd;
	    }
}
}