package uk.ac.ncl.csc8005.group3.scheduler;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


/**
 * @author: Jessica King 
 * Date: 01/05/2014
 * Class DatabaseIO includes all the database connectivity and querys to read and write to the database. When this class is loaded all modules and rooms are populated 
 * from the database, through the constructor.
 */

public class DatabaseIO {
	private Connection conn = null;
	private Statement stmt = null;

	private ArrayList<Module> modules = new ArrayList<Module>();
	//private ArrayList<Integer> students = new ArrayList<Integer>();
	//private ArrayList<String> clashed = new ArrayList<String>();
	private ArrayList<Room> rooms = new ArrayList<Room>();

	private boolean invalidModules =false;
	
	public DatabaseIO() {
		populateProgram();
	}

	/*
	 * Return an array list of modules that have been populated.
	 * @return ArrayList of modules
	 */
	public ArrayList<Module> getModules() {
		if (invalidModules==false){
			return modules;
		}else{
			throw new IllegalArgumentException("A module has no pupils!");
		}
	}

	/*
	 * Return an array list of rooms that have been populated.
	 * @return ArrayList of rooms.
	 */
	public ArrayList<Room> getRooms() {
		return rooms;
	}

	/*
	 * Opens the connection to the database. This needs to be executed before
	 * any query is ran.
	 */
	
	public void populateProgram(){
		modules = populateModules();
		rooms = populateRooms();
	}
	
	public void openDatabase() throws Exception{
			String userName = "t8005t2"; // t8005t2
			String password = ".oweRaps"; // .oweRaps
			String url = "jdbc:mysql://homepages.cs.ncl.ac.uk"; // jdbc:mysql://localhost:3306"
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, userName, password);
			System.out.println("Database connection established");
		

	}
	
	
	
	public void closeDatabase() throws SQLException {
		conn.close();
		System.out.println("Database connection terminated");
	}

	/*
	 * Returns all module titles from the database.
	 */
	public ArrayList<String> getModuletitles() {
		try{
        	openDatabase();
		}catch(Exception e){}
		
		String query = "SELECT name FROM t8005t2 .modules";
		ArrayList<String> modules = new ArrayList<String>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String name = rs.getString("name");
				modules.add(name);
			}
		} catch (Exception e) {
			System.err.println("Problem executing getmoduletitles query");
			System.err.println(e.getMessage());
		}
		
		try{
        	closeDatabase();
		}catch(Exception e){}
		
		return modules;
	}

	/*
	 * Finds the modules that have students taking both modules. Lists all the
	 * other modules that the studentID is also taking.
	 * 
	 * @param ID of the student
	 */
	private HashMap<String, Integer> getcoupledModules(String id) {

		ArrayList<Integer> SID = query2(id);
		HashMap<String, Integer> coupledMap = new HashMap<String, Integer>(); 
		Set<String> coupledModules= new HashSet<String>();
		
		for (int studentid : SID) {
			ArrayList<String> modules = query1(studentid);
			for (String moduleid : modules) {
				coupledModules.add(moduleid);
			}
		}
		
		for(String var : coupledModules){
			coupledMap.put(var,numberOfStudents(var));
		}
		

		
		return coupledMap;
	}

	/*
	 * Finds the modules that have to be ran on the same day as the module
	 * given.
	 * 
	 * @param name of module.
	 */
	private ArrayList<String> getclashedModules(String name) {
		// String part is module ID that it's clashed with, integer is how many
		// students take that module.
		try{
        	openDatabase();
		}catch(Exception e){}
		String query = "SELECT name FROM t8005t2 .modules WHERE ID IN (SELECT ForeignID FROM t8005t2 .clashedModules WHERE moduleID IN (SELECT ID FROM t8005t2 .modules WHERE name= '"
				+ name + "'))";
		ArrayList<String> clashedModules = new ArrayList<String>();

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String foreignID = rs.getString("name");
				clashedModules.add(foreignID);
			}
		} catch (Exception e) {
			System.err.println("Problem executing getclashedModules query");
			System.err.println(e.getMessage());
		}
		try{
        	closeDatabase();
		}catch(Exception e){}
		return clashedModules;
	}

	/*
	 * Returns the number of students taking a particular module.
	 */
	public int numberOfStudents(String name) {
		try{
	        	openDatabase();
	    }catch(Exception e){}
		
		String query = "SELECT COUNT(*) FROM t8005t2 .takes WHERE ID IN (SELECT ID FROM t8005t2 .modules WHERE name= '"
				+ name + "')";
		int count = 0;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				count = rs.getInt("COUNT(*)");
			}
		} catch (Exception e) {
			System.err.println("Problem executing numberOfStudents query");
			System.err.println(e.getMessage());
		}
		try{
			closeDatabase();
		}catch(Exception e){}
		
		return count;
	}

	
	/*
	 * Returns the number of students taking a particular module.
	 */
	public String roomType(String name) {
		try{
        	openDatabase();
		}catch(Exception e){}
		
		String query = "SELECT type FROM t8005t2 .modules WHERE name= '" + name + "'";
		String type = null;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				type = rs.getString("type");
			}
		} catch (Exception e) {
			System.err.println("Problem executing roomType query");
			System.err.println(e.getMessage());
		}
		
		try{
        	closeDatabase();
		}catch(Exception e){}
		return type;
	}

	
	
	
	/*
	 * Populates all modules with data from the database.
	 * @Return ArrayList of modules
	 * @throws IllegalArgumentException
	 */
	private ArrayList<Module> populateModules() throws IllegalArgumentException {
		try{
        	openDatabase();
		}catch(Exception e){}
		String query = "SELECT * FROM t8005t2 .modules";
		ArrayList<Module> modules = new ArrayList<Module>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String id = rs.getString("name");
				ArrayList<String> clashedModules = getclashedModules(id);
				HashMap<String,Integer> coupledModules = getcoupledModules(id); 
				int examLength = rs.getInt("examLength");
				int moduleSize = numberOfStudents(id);
				String type = rs.getString("type");
				try{
					Module module = new Module(id, clashedModules, coupledModules, examLength, moduleSize, type);
					modules.add(module);
				}catch(IllegalArgumentException e){
					invalidModules=true;
					throw new IllegalArgumentException(e.getMessage());
				}
				
			}
		} catch (Exception e) {
			System.out.print(e);
			System.err.println("Problem executing populateModules query");
			System.err.println(e.getMessage());
		}
		
		try{
        	closeDatabase();
		}catch(Exception e){}
		return modules;
	}

	
	/*
	 * Populates all rooms with data from the database.
	 * @Return ArrayList of rooms
	 */
	private ArrayList<Room> populateRooms() {
		try{
        	openDatabase();
		}catch(Exception e){}
		String query = "SELECT * FROM t8005t2 .rooms";
		ArrayList<Room> rooms = new ArrayList<Room>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String roomNumber = rs.getString("roomNumber");
				String roomType = rs.getString("roomType");
				int roomStart = rs.getInt("roomStart");
				int roomEnd = rs.getInt("roomEnd");
				int roomFireBreak = rs.getInt("roomFireBreak");
				int capacity = rs.getInt("capacity");
				Room room = new Room(roomNumber, roomType, roomStart, roomEnd,
						roomFireBreak, capacity);
				rooms.add(room);
			}
		} catch (Exception e) {
			System.err.println("Problem executing populateRooms query");
			System.err.println(e.getMessage());
		}
		try{
        	closeDatabase();
		}catch(Exception e){}
		return rooms;
	}


	/*
	 * Supply this with a student ID and you will get a list of modules that the student is taking
	 * 
	 * @param StudentID
	 * @return ArrayList of modules  
	 */

	public ArrayList<String> query1(int studentid) {
		try{
        	openDatabase();
		}catch(Exception e){}
		String query = "SELECT name FROM t8005t2 .modules WHERE ID IN (SELECT ID FROM t8005t2 .takes WHERE StudentID = "
				+ studentid + ")";
		ArrayList<String> modules = new ArrayList<String>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// while there are still results, loop through and get the name, add
			// it to the module array.
			while (rs.next()) {
				String module = rs.getString("name");
				modules.add(module);
			}
		} catch (Exception e) {
			System.err.println("Problem executing query1");
			System.err.println(e.getMessage());
		}
		
		try{
        	closeDatabase();
		}catch(Exception e){}
		return modules;
	}

	/*
	 * Supply a module code and get an array of student IDs taking that module
	 * 
	 * @param The code of the module.
	 * @return An arrayList of Student IDs.
	 */
	private ArrayList<Integer> query2(String name) {
		try{
        	openDatabase();
		}catch(Exception e){}
		
		String query = "SELECT StudentID FROM t8005t2 .takes WHERE ID IN (SELECT ID FROM t8005t2 .modules WHERE name = '"
				+ name + "')";
		ArrayList<Integer> students = new ArrayList<Integer>();
		try {

			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				int student = rs.getInt("studentID");
				students.add(student);
			}
		} catch (Exception e) {
			System.err.println("Problem executing 'query2'");
			System.err.println(e.getMessage());
		}
		
		try{
        	closeDatabase();
		}catch(Exception e){}
		return students;
	}

	/*
	 * Delete all data in table output.
	 */
	private void deleteTable() {
		try{
        	openDatabase();
		}catch(Exception e){}		
		String deleteQuery = "DELETE FROM t8005t2 .output";
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(deleteQuery);
		} catch (Exception e) {
			System.err.println("Problem executing deleteTable query");
			System.err.println(e.getMessage());
		}
		try{
        	closeDatabase();
		}catch(Exception e){}
	}

	/*
	 * CLears the output table of data, and then writes new data to the Output database.
	 * @param ArrayList of modules
	 * @param startDate Start date of exam period.
	 */
	public boolean writeAllToDB(ArrayList<Module> schedule, final Calendar startDateMaster) {
		deleteTable();
		Calendar startDate = (Calendar) startDateMaster.clone();

		//Loop through all modules in the schedule and write to database.
		for (Module module : schedule) {
			startDate.add(Calendar.DATE, module.getDayNumber());
			java.sql.Date examDate = new java.sql.Date(startDate.getTime().getTime());   //Convert to SQL date
			writeToDB(module.getId(), module.getExamLength(), module.getTime(), module.getRoomName(), examDate);
		}
		
		return true;

	}

	/*
	 * Insert query to write data to the output database.
	 * 
	 * @param ModuleID. The ID of the module
	 * @param examLength. The length that the exam will take
	 * @param time. The time that the exam is scheduled for.
	 * @param room. The room that the exam is scheduled in.
	 * @param date. The date that the exam is scheduled on.
	 * 
	 */
	private void writeToDB(String moduleID, int examLength, int time, String room, Date date) {
		try{
        	openDatabase();
		}catch(Exception e){}
		String query = "INSERT t8005t2 .output values('" + moduleID + "','"
				+ examLength + "','" + time + "','" + room + "','" + date
				+ "')";
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
		} catch (Exception e) {
			System.err.println("Problem executing output query");
			System.err.println(e.getMessage());
		}
		try{
        	closeDatabase();
		}catch(Exception e){}
	}

	
	
	/*
	 * Insert statement to write a new student to the input database
	 * 
	 * @param StudentID. ID of the student
	 */
	public void writeToInputDB(String studentID) {
		try{
        	openDatabase();
		}catch(Exception e){}
		String query = "INSERT t8005t2 .student values("+studentID+")";
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
		} catch (Exception e) {
			System.err.println("Problem executing writetoInputDB query");
			System.err.println(e.getMessage());
		}
		try{
        	closeDatabase();
		}catch(Exception e){}
	}
	
	

	
	/*
	 * Update statement to write to the module table
	 * 
	 * @param moduleID. ID of the module
	 * @param examLength. The length of the exam
	 * @param moduleSize. The amount of students taking the module.
	 * @param type. The room type that the module requires.
	 * @return boolean.  
	 */
	public boolean writeToModuleTable(String moduleID, int examLength, String type, String department) {
		try{
        	openDatabase();
		}catch(Exception e){}
		
		String query = "UPDATE t8005t2 .modules SET examLength='" +examLength  + "', type='" +type + "', department='" +department+ "' WHERE name= '" + moduleID + "'";
		
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			return true;
		} catch (Exception e) {
			System.err.println("Problem executing writeToModule query");
			System.err.println(e.getMessage());
			
		}
		try{
        	closeDatabase();
		}catch(Exception e){}
		
		return false;
	}

	
	
	/*
	 * Adds a room to the database (rooms table)
	 * 
	 * @param roomNumber. The Number of the room
	 * @param roomType. The type of the room (eg lab).
	 * @param roomStart. The start time that the room can be booked.
	 * @param roomFireBreak. The amount of time required for a FireBreak.
	 * @param capacity. Capacity the room can hold.
	 * @param roomEnd. The end time the room is booked for.
	 */
	public boolean addRoom(String roomNumber, String roomType, int roomStart,int roomFireBreak, int capacity, int roomEnd) {
		try{
        	openDatabase();
		}catch(Exception e){}
		//String query = "INSERT t8005t2 .rooms values('" + roomNumber + "','"+ roomType + "','" + roomStart + "','" + roomFireBreak + "','" + capacity+  "','" + roomEnd + "')";
		String query = "INSERT t8005t2 .rooms (roomNumber, roomType, roomStart,roomFireBreak, capacity, roomEnd) values('" + roomNumber + "','"+ roomType + "','" + roomStart + "','" + roomFireBreak + "','" + capacity+  "','" + roomEnd + "')";
		System.out.println(query);
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			try{
	        	closeDatabase();
			}catch(Exception e){}
			
			return true;
		} catch (Exception e) {
			System.err.println("Problem executing addRoom query");
			System.err.println(e.getMessage());
			
			try{
	        	closeDatabase();
			}catch(Exception q){}
			return false;
		}
		
	}
	
	
	/*
	 * Returns data from the output database.
	 * @Return ArrayList of rooms
	 */
	public ArrayList<String> returnOutput() {
		try{
        	openDatabase();
		}catch(Exception e){}
		String query = "SELECT * FROM t8005t2 .output";
		ArrayList<String> output = new ArrayList<String>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String moduleID = rs.getString("moduleID");
				int examLength = rs.getInt("examLength");
				int time = rs.getInt("time");
				String room = rs.getString("room");
				String date = rs.getString("date");
				output.add(moduleID);
				output.add(""+examLength);
				output.add(""+time);
				output.add(room);
				output.add(date);
			}
		} catch (Exception e) {
			System.err.println("Problem executing returnOutput query");
			System.err.println(e.getMessage());
		}
		try{
        	closeDatabase();
		}catch(Exception e){}
		return output;
	}
	
	
}
