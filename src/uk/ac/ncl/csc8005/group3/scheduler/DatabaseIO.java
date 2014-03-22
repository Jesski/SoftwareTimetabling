package uk.ac.ncl.csc8005.group3.scheduler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

//make a static class. Object factory??

public class DatabaseIO {

	   public static void main (String[] args)
	   {
	       
	       Connection conn = null;
	       ArrayList<String> modules = new ArrayList<String>();
	       ArrayList<Integer> students = new ArrayList<Integer>();
	       ArrayList<String> clashed = new ArrayList<String>();
	       Statement stmt = null;
	       try
	       {
	           String userName = "root"; //t8005t2
	           String password = "root"; //.oweRaps
	           String url = "jdbc:mysql://localhost:3306"; //    jdbc:mysql://homepages.cs.ncl.ac.uk
	           Class.forName ("com.mysql.jdbc.Driver").newInstance ();
	           conn = DriverManager.getConnection (url, userName, password);
	           System.out.println ("Database connection established");	           
	           
	           DatabaseIO connect=new DatabaseIO();
	           modules = connect.query1(1,conn,stmt);
	           System.out.println(modules);
	           students = connect.query2("CSC8001",conn,stmt);
	           System.out.println(students);
	           clashed = connect.getClashedModules("CSC8001",conn,stmt);
	           System.out.println(clashed);
	           System.out.println(connect.getCoupledModules("CSC8001",conn,stmt));
	           System.out.println(connect.populateModules(conn, stmt));
	           System.out.println(connect.populateRooms(conn, stmt));
	       }
	       catch (Exception e)
	       {
	           System.err.println ("Cannot connect to database server...");
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
	   
	  
	   /*
	    * Finds the modules that have students taking both modules.
	    * All the other modules that the students taking id are also taking.
	    */
	   public ArrayList<String> getClashedModules(String id,Connection conn, Statement stmt){
		   ArrayList<String> ClashedModules=new ArrayList<String>();
		   ArrayList<Integer> SID = query2(id, conn, stmt);
		   
		   for(int studentid : SID){   
			   ArrayList<String> modules=query1(studentid, conn, stmt);
			   for(String moduleid : modules){
			   ClashedModules.add(moduleid);
		   		}
		   }
		   return ClashedModules;
	   }
	   
	   public HashMap<String, Integer> getCoupledModules(String id,Connection conn, Statement stmt){
		   //modules that have to be ran on the same day.
		   //String part is module ID that it's clashed with, integer is how many students take that module.
		   String query = "SELECT ForeignID FROM dbInput .coupledModules WHERE moduleID IN (SELECT ID FROM dbInput .modules WHERE name= '" + id + "')";
		   HashMap<String, Integer> coupledModules = new HashMap<String, Integer>();
		   try{
			   stmt = conn.createStatement();
			   ResultSet rs = stmt.executeQuery(query);
			   while(rs.next()){
				   	String foreignID = rs.getString("foreignID");
				   	coupledModules.put(foreignID, 0);
			   }
		   }
	       catch (Exception e)
	       {
	           System.err.println ("Error");
	           System.err.println (e.getMessage ());
	       }
	   return coupledModules;    	
	   }
	 
	   
	   
	   public ArrayList<Module> populateModules(Connection conn, Statement stmt){
		   String query = "SELECT * FROM dbInput .modules";
		   ArrayList<Module> modules = new ArrayList<Module>();
		   try{
			   stmt = conn.createStatement();
			   ResultSet rs = stmt.executeQuery(query);
			   while(rs.next()){
				   	String id = rs.getString("roomNumber");
				   	ArrayList<String> clashedModules = getClashedModules(id,conn, stmt);
				   	HashMap<String, Integer> coupledModules = getCoupledModules(id,conn, stmt);    //hashmap
				   	Double examLength = rs.getDouble("examLength");
				   	int moduleSize = rs.getInt(5);
				   	String type = rs.getString("type");
				   	Module module = new Module(id, clashedModules, coupledModules, examLength, moduleSize, type);
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
  
	   
	     
	   public ArrayList<Room> populateRooms(Connection conn, Statement stmt){
		   String query = "SELECT * FROM dbInput .rooms";
		   ArrayList<Room> rooms = new ArrayList<Room>();
		   try{
			   stmt = conn.createStatement();
			   ResultSet rs = stmt.executeQuery(query);
			   while(rs.next()){
				   	String roomNumber = rs.getString("roomNumber");
				   	String roomType = rs.getString("roomType");
				   	Double roomStart = rs.getDouble("roomStart");
				   	Double roomEnd = rs.getDouble("roomEnd");
				   	Double roomFireBreak = rs.getDouble("roomFireBreak");
				   	int capacity = rs.getInt(6);
				   	Room room = new Room(roomNumber, roomType, roomStart, roomEnd, roomFireBreak, capacity);
				   	rooms.add(room);
			   }
		   }
	       catch (Exception e)
	       {
	           System.err.println ("Cannot connect to database server");
	           System.err.println (e.getMessage ());
	       }
	   return rooms;  		   
	   }
	   
	  /* */
	   //Supply this with a student ID and you will get a list of modules
	   public ArrayList<String> query1(int studentid, Connection conn, Statement stmt){
		   String query = "SELECT name FROM dbInput .modules WHERE ID IN (SELECT ID FROM dbInput .takes WHERE StudentID = " + studentid + ")";
		   ArrayList<String> modules = new ArrayList<String>();
		   try{
			   stmt = conn.createStatement();
			   ResultSet rs = stmt.executeQuery(query);
			   // while there are still results, loop through and get the name, add it to the module array.
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
	   
	   //Supply a module name and get an array of student ids taking that module
	   public ArrayList<Integer> query2(String name, Connection conn, Statement stmt){
		   String query = "SELECT StudentID FROM dbInput .takes WHERE ID IN (SELECT ID FROM dbinput .modules WHERE name = '" + name + "')";
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
