package uk.ac.ncl.csc8005.group3.scheduler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

//make a static class. Object factory??

public class DatabaseIO {
    Connection conn = null;
    Statement stmt = null;
	
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
	       

	       try
	       {
	    	   openDatabase();
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
	   
	  
	   
	   public void openDatabase() throws Exception{
       	String userName = "t8005t2"; //t8005t2
       	String password = ".oweRaps"; //.oweRaps
       	String url = "jdbc:mysql://homepages.cs.ncl.ac.uk"; //   jdbc:mysql://localhost:3306"
       	Class.forName ("com.mysql.jdbc.Driver").newInstance ();
       	conn = DriverManager.getConnection (url, userName, password);
       	System.out.println ("Database connection established");	           
       
	           modules = populateModules();
	           System.out.println(modules);
       students = query2("CSC8001");
       System.out.println(students);
       clashed = getClashedModules("CSC8001");
       System.out.println(clashed);
       System.out.println(getCoupledModules("CSC8001"));
       System.out.println(populateModules());
       rooms=populateRooms();
	  }
	   
	   
	   
	   //Returns all module titles.
	   public ArrayList<String> getModuletitles(){
		   String query = "SELECT name FROM t8005t2 .modules";
		   ArrayList<String> modules = new ArrayList<String>();
		   try{
			   stmt = conn.createStatement();
			   ResultSet rs = stmt.executeQuery(query);
			   while(rs.next()){
				   	String name = rs.getString("name");
				   	modules.add(name);
			   }
		   }
	       catch (Exception e)
	       {
	           System.err.println ("Cannot connect to database server");
	           System.err.println (e.getMessage ());
	       }
	   return modules;  		   
	   }
	   
	   
	   
	   
	   /*
	    * Finds the modules that have students taking both modules.
	    * All the other modules that the students taking id are also taking.
	    */
	   public ArrayList<String> getClashedModules(String id){
		   ArrayList<String> ClashedModules=new ArrayList<String>();
		   ArrayList<Integer> SID = query2(id);
		   
		   for(int studentid : SID){   
			   ArrayList<String> modules=query1(studentid);
			   for(String moduleid : modules){
			   ClashedModules.add(moduleid);
		   		}
		   }
		   return ClashedModules;
	   }
	   
	   public HashMap<String, Integer> getCoupledModules(String name){
		   //modules that have to be ran on the same day.
		   //String part is module ID that it's clashed with, integer is how many students take that module.
		   String query = "SELECT ForeignID FROM t8005t2 .coupledModules WHERE moduleID IN (SELECT ID FROM t8005t2 .modules WHERE name= '" + name + "')";
		   HashMap<String, Integer> coupledModules = new HashMap<String, Integer>();
		   
		   try{
			   stmt = conn.createStatement();
			   ResultSet rs = stmt.executeQuery(query);
			   while(rs.next()){
				   	String foreignID = rs.getString("foreignID");
				   	int numberOfStudents = numberOfStudents(foreignID);
				   	coupledModules.put(foreignID, numberOfStudents);
			   }
		   }
	       catch (Exception e)
	       {
	           System.err.println ("Error");
	           System.err.println (e.getMessage ());
	       }
	   return coupledModules;    	
	   }
	 
	   
	   
	   public int numberOfStudents(String name){
		   String query = "SELECT COUNT(*) FROM t8005t2 .takes WHERE ID IN (SELECT ID FROM t8005t2 .modules WHERE name= '" + name + "')";
		   int count = 0;
		   try{
			   stmt = conn.createStatement();
			   ResultSet rs = stmt.executeQuery(query);
			   while(rs.next()){
				   count = rs.getInt("COUNT(*)");
				   System.out.println(count);
			   }
		   }
	       catch (Exception e)
	       {
	           System.err.println ("Cannot connect to database server");
	           System.err.println (e.getMessage ());
	       }
	   return count;  		   
	   }
	   
	   
	   
	   
	   //Populates all modules with data from the database.
	   public ArrayList<Module> populateModules(){
		   String query = "SELECT * FROM t8005t2 .modules";
		   ArrayList<Module> modules = new ArrayList<Module>();
		   try{
			   stmt = conn.createStatement();
			   ResultSet rs = stmt.executeQuery(query);
			   while(rs.next()){
				   	String id = rs.getString("ID");
				   	ArrayList<String> clashedModules = getClashedModules(id);
				   	HashMap<String, Integer> coupledModules = getCoupledModules(id);    //hashmap
				   	Double examLength = rs.getDouble("examLength");
				   	int moduleSize = rs.getInt(4);
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
  
	   
	     
	   public ArrayList<Room> populateRooms(){
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
	   
	   //Supply this with a student ID and you will get a list of modules
	   public ArrayList<String> query1(int studentid){
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
	   
	   //Supply a module name and get an array of student IDs taking that module
	   public ArrayList<Integer> query2(String name){
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
	   
	   
	   
	   //Delete data in table output.
	   public void DeleteTable(){
		   String deleteQuery = "DELETE FROM t8005t2 .output";
		   try{
			   stmt = conn.createStatement();
			   int rs = stmt.executeUpdate(deleteQuery);
		   }
	       catch (Exception e)
	       {
	           System.err.println ("Problem executing query");
	           System.err.println (e.getMessage ());
	       }
	   }
	       
	   
	   //Insert statement to write to the output database.
	   public void WriteToDB(String moduleID, double examLength, int time, String room, Date date){
		   String query = "INSERT t8005t2 .output values('" +moduleID + "','" +examLength + "','" + time + "','" + room + "','" + date + "')";
		   try{
			   stmt = conn.createStatement();
			   int rs2 = stmt.executeUpdate(query);
		   }
	       catch (Exception e)
	       {
	           System.err.println ("Problem executing query");
	           System.err.println (e.getMessage ());
	       }
   	   }
	   
}
