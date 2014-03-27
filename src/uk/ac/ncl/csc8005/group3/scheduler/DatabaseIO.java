package uk.ac.ncl.csc8005.group3.scheduler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

//make a static class. Object factory??

public class DatabaseIO {
    
	
	public ArrayList<Module> getModules() {
		return modules;
	}

	ArrayList<Module> modules = new ArrayList<Module>();
    ArrayList<Integer> students = new ArrayList<Integer>();
    ArrayList<String> clashed = new ArrayList<String>();
    public ArrayList<Room> getRooms() {
		return rooms;
	}

	ArrayList<Room> rooms = new ArrayList<Room>();
    
    
    
	   public DatabaseIO()
	   {
	       
	       Connection conn = null;
	   
	       Statement stmt = null;
	       try
	       {
	           String userName = "t8005t2"; //t8005t2
	           String password = ".oweRaps"; //.oweRaps
	           String url = "jdbc:mysql://homepages.cs.ncl.ac.uk"; //   jdbc:mysql://localhost:3306"
	           Class.forName ("com.mysql.jdbc.Driver").newInstance ();
	           conn = DriverManager.getConnection (url, userName, password);
	           System.out.println ("Database connection established");	           
	           
	 	           modules = populateModules(conn,stmt);
	           System.out.println(modules);
	           students = query2("CSC8001",conn,stmt);
	           System.out.println(students);
	           clashed = getClashedModules("CSC8001",conn,stmt);
	           System.out.println(clashed);
	           System.out.println(getCoupledModules("CSC8001",conn,stmt));
	           System.out.println(populateModules(conn, stmt));
	           rooms=populateRooms(conn, stmt);
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
		   String query = "SELECT ForeignID FROM t8005t2 .coupledModules WHERE moduleID IN (SELECT ID FROM t8005t2 .modules WHERE name= '" + id + "')";
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
		   String query = "SELECT * FROM t8005t2 .modules";
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
		   String query = "SELECT * FROM t8005t2 .rooms";
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
		   String query = "SELECT name FROM t8005t2 .modules WHERE ID IN (SELECT ID FROM t8005t2 .takes WHERE StudentID = " + studentid + ")";
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
		   String query = "SELECT StudentID FROM t8005t2 .takes WHERE ID IN (SELECT ID FROM t8005t2 .modules WHERE name = '" + name + "')";
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
