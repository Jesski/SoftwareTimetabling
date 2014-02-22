package uk.ac.ncl.csc8005.group3.scheduler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;



public class Connect {

	   public static void main (String[] args)
	   {
	       Connection conn = null;
	       ArrayList<String> modules = new ArrayList<String>();
	       ArrayList<Integer> students = new ArrayList<Integer>();
	       Statement stmt = null;

	       try
	       {
	           String userName = "root";
	           String password = "root";
	           String url = "jdbc:mysql://localhost:3306";
	           Class.forName ("com.mysql.jdbc.Driver").newInstance ();
	           conn = DriverManager.getConnection (url, userName, password);
	           System.out.println ("Database connection established");
	           
	           Connect connect=new Connect();
	           modules = connect.query1(1,conn,stmt);
	           System.out.println(modules);
	           students = connect.query2("CSC8001",conn,stmt);
	           System.out.println(students);
	        		   
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
	   
	   
	   //Supply this with a student ID and you will get a list of modules
	   public ArrayList<String> query1(int studentid, Connection conn, Statement stmt){
		   String query = "SELECT name FROM dbinput .modules WHERE ID IN (SELECT ID FROM dbinput .takes WHERE StudentID = " + studentid + ")";
		   ArrayList<String> modules = new ArrayList<String>();
		   try{

			   stmt = conn.createStatement();
			   ResultSet rs = stmt.executeQuery(query);
			   while(rs.next()){
				   	String module = rs.getString("name");
				   	modules.add(module);
			   }
		   }
	       catch (Exception e)
	       {
	           System.err.println ("Cannot connect to database server");
	           System.err.println (e.getMessage ());
	       }
	   return modules;    	       
	   }

	   
	   //Supply a module and get an array of student ids taking that module
	   public ArrayList<Integer> query2(String name, Connection conn, Statement stmt){
		   String query = "SELECT StudentID FROM dbinput .takes WHERE ID IN (SELECT ID FROM dbinput .modules WHERE name = '" + name + "')";
		   ArrayList<Integer> students = new ArrayList<Integer>();
		   try{

			   stmt = conn.createStatement();
			   ResultSet rs = stmt.executeQuery(query);
			   while(rs.next()){
				   	int student = rs.getInt("studentID");
				   	students.add(student);
			   }
		   }
	       catch (Exception e)
	       {
	           System.err.println ("Cannot connect to database server");
	           System.err.println (e.getMessage ());
	       }
	   return students;    	       
	   }

}
