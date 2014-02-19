package uk.ac.ncl.csc8005.group3.scheduler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

public class Connect {

	   public static void main (String[] args)
	   {
	       Connection conn = null;
// 128.240.148.134
	       try
	       {
	           String userName = "t8005t2";
	           String password = ".oweRaps";
	           String url = "jdbc:mysql://128.240.148.134/t8005t2:3306";
	           Class.forName ("com.mysql.jdbc.Driver").newInstance ();
	           conn = DriverManager.getConnection (url, userName, password);
	           System.out.println ("Database connection established");
	       }
	       catch (Exception e)
	       {
	           System.err.println ("Cannot connect to database server");
	           System.err.println (e.getMessage ());
	       }
	       finally
	       {
	           if (conn != null)
	           {
	               try
	               {
	                   conn.close ();
	                   System.out.println ("Database connection terminated");
	               }
	               catch (Exception e) { /* ignore close errors */ }
	           }
	       }
	   }	

}
