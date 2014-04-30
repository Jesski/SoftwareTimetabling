package uk.ac.ncl.csc8005.group3.scheduler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: Jessica King 
 * Date: 30/04/2014
 * Class DatabaseIO includes all the database connectivity and querys to read and write to the database. When this class is loaded all modules and rooms are populated 
 * from the database, through the constructor.
 */

public class DatabaseIO {
	private Connection conn = null;
	private Statement stmt = null;

	private ArrayList<Module> modules = new ArrayList<Module>();
	private ArrayList<Integer> students = new ArrayList<Integer>();
	private ArrayList<String> clashed = new ArrayList<String>();
	private ArrayList<Room> rooms = new ArrayList<Room>();

	public DatabaseIO() {
		try {
			openDatabase();
		} catch (Exception e) {
			System.err.println("Cannot connect to database server...");
			System.err.println(e.getMessage());
		} finally {
			if (conn != null) {
				try {
					closeDatabase();
				} catch (Exception e) { /* ignore close errors */
				}
			}
		}
	}

	/*
	 * Return an array list of modules that have been populated.
	 * @return ArrayList of modules
	 */
	public ArrayList<Module> getModules() {
		return modules;
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
	public void openDatabase() throws Exception {
		String userName = "t8005t2"; // t8005t2
		String password = ".oweRaps"; // .oweRaps
		String url = "jdbc:mysql://homepages.cs.ncl.ac.uk"; // jdbc:mysql://localhost:3306"
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		conn = DriverManager.getConnection(url, userName, password);
		System.out.println("Database connection established");
		
		modules = populateModules();
		rooms = populateRooms();
	}	
	
	public void closeDatabase() throws SQLException {
		conn.close();
		System.out.println("Database connection terminated");
	}

	/*
	 * Returns all module titles from the database.
	 */
	public ArrayList<String> getModuletitles() {
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
			System.err.println("Cannot run query");
			System.err.println(e.getMessage());
		}
		return modules;
	}

	/*
	 * Finds the modules that have students taking both modules. Lists all the
	 * other modules that the studentID is also taking.
	 * 
	 * @param ID of the student
	 */
	private ArrayList<String> getClashedModules(String id) {
		ArrayList<String> ClashedModulesAsArray;
		ArrayList<Integer> SID = query2(id);
		Set<String> ClashedModules= new HashSet<String>();
		
		for (int studentid : SID) {
			ArrayList<String> modules = query1(studentid);
			for (String moduleid : modules) {
				ClashedModules.add(moduleid);
			}
		}
		
		ClashedModulesAsArray= new ArrayList<String>(ClashedModules);
		
		return ClashedModulesAsArray;
	}

	/*
	 * Finds the modules that have to be ran on the same day as the module
	 * given.
	 * 
	 * @param name of module.
	 */
	private HashMap<String, Integer> getCoupledModules(String name) {
		// String part is module ID that it's clashed with, integer is how many
		// students take that module.
		String query = "SELECT ForeignID FROM t8005t2 .coupledModules WHERE moduleID IN (SELECT ID FROM t8005t2 .modules WHERE name= '"
				+ name + "')";
		HashMap<String, Integer> coupledModules = new HashMap<String, Integer>();

		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String foreignID = rs.getString("foreignID");
				int numberOfStudents = numberOfStudents(foreignID);
				coupledModules.put(foreignID, numberOfStudents);
			}
		} catch (Exception e) {
			System.err.println("Error");
			System.err.println(e.getMessage());
		}
		return coupledModules;
	}

	/*
	 * Returns the number of students taking a particular module.
	 */
	public int numberOfStudents(String name) {
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
			System.err.println("Cannot connect to database server");
			System.err.println(e.getMessage());
		}
		return count;
	}

	
	/*
	 * Returns the number of students taking a particular module.
	 */
	public String roomType(String name) {
		String query = "SELECT type FROM t8005t2 .modules WHERE name= '" + name + "'";
		String type = null;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				type = rs.getString("type");
			}
		} catch (Exception e) {
			System.err.println("Cannot connect to database server");
			System.err.println(e.getMessage());
		}
		return type;
	}

	
	
	
	/*
	 * Populates all modules with data from the database.
	 * @Return ArrayList of modules
	 */
	private ArrayList<Module> populateModules() {
		String query = "SELECT * FROM t8005t2 .modules";
		ArrayList<Module> modules = new ArrayList<Module>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String id = rs.getString("name");
				ArrayList<String> clashedModules = getClashedModules(id);
				HashMap<String, Integer> coupledModules = getCoupledModules(id); 
				int examLength = rs.getInt("examLength");
				int moduleSize = numberOfStudents(id);
				String type = rs.getString("type");
				Module module = new Module(id, clashedModules, coupledModules,
						examLength, moduleSize, type);
				modules.add(module);
			}
		} catch (Exception e) {
			System.err.println("Cannot connect to database server");
			System.err.println(e.getMessage());
		}
		return modules;
	}

	
	/*
	 * Populates all rooms with data from the database.
	 * @Return ArrayList of rooms
	 */
	private ArrayList<Room> populateRooms() {
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
				int capacity = rs.getInt(6);
				Room room = new Room(roomNumber, roomType, roomStart, roomEnd,
						roomFireBreak, capacity);
				rooms.add(room);
			}
		} catch (Exception e) {
			System.err.println("Cannot connect to database server");
			System.err.println(e.getMessage());
		}
		return rooms;
	}


	/*
	 * Supply this with a student ID and you will get a list of modules that the student is taking
	 * 
	 * @param StudentID
	 * @return ArrayList of modules  
	 */

	public ArrayList<String> query1(int studentid) {
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
			System.err.println("Cannot connect to database server");
			System.err.println(e.getMessage());
		}
		return modules;
	}

	/*
	 * Supply a module code and get an array of student IDs taking that module
	 * 
	 * @param The code of the module.
	 * @return An arrayList of Student IDs.
	 */
	private ArrayList<Integer> query2(String name) {
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
			System.err.println("Cannot connect to database server");
			System.err.println(e.getMessage());
		}
		return students;
	}

	/*
	 * Delete all data in table output.
	 */
	private void deleteTable() {
		String deleteQuery = "DELETE FROM t8005t2 .output";
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(deleteQuery);
		} catch (Exception e) {
			System.err.println("Problem executing query");
			System.err.println(e.getMessage());
		}
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
			writeToDB(module.getId(), module.getExamLength(), module.getTime() / 60, module.getRoomName(), examDate);
		}
		
		try {
			closeDatabase();
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
			return false;
		}
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
	private void writeToDB(String moduleID, int examLength, int time,String room, Date date) {
		String query = "INSERT t8005t2 .output values('" + moduleID + "','"
				+ examLength + "','" + time + "','" + room + "','" + date
				+ "')";
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
		} catch (Exception e) {
			System.err.println("Problem executing query");
			System.err.println(e.getMessage());
		}
	}

	
	
	/*
	 * Insert statement to write a new student to the input database
	 * 
	 * @param StudentID. ID of the student
	 */
	public void writeToInputDB(String studentID) {
		String query = "INSERT t8005t2 .student values("+studentID+")";
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
		} catch (Exception e) {
			System.err.println("Problem executing query");
			System.err.println(e.getMessage());
		}
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
	public boolean writeToModuleTable(String moduleID, double examLength, int moduleSize,String type) {
		String query = "UPDATE t8005t2 .modules SET examLength='" +examLength + "', moduleSize='" +moduleSize + "', type='" +type + "' WHERE name= '" + moduleID + "'";


		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			return true;
		} catch (Exception e) {
			System.err.println("Problem executing query");
			System.err.println(e.getMessage());
			return false;
		}
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
	public boolean addRoom(int roomNumber, String roomType, int roomStart,int roomFireBreak, int capacity, int roomEnd) {
		String query = "INSERT t8005t2 .rooms values('" + roomNumber + "','"+ roomType + "','" + roomStart + "','" + roomFireBreak + "','" + capacity+  "','" + roomEnd + "')";
		System.out.println(query);
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(query);
			return true;
		} catch (Exception e) {
			System.err.println("Problem executing query");
			System.err.println(e.getMessage());
			return false;
		}
	}
	
	
	/*
	 * Returns data from the output database.
	 * @Return ArrayList of rooms
	 */
	public ArrayList<String> returnOutput() {
		String query = "SELECT * FROM t8005t2 .output";
		ArrayList<String> output = new ArrayList<String>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String moduleID = rs.getString("moduleID");
				String examLength = rs.getString("examLength");
				String time = rs.getString("time");
				String room = rs.getString("room");
				String date = rs.getString("date");
				output.add(moduleID);
				output.add(examLength);
				output.add(time);
				output.add(room);
				output.add(date);
			}
		} catch (Exception e) {
			System.err.println("Cannot connect to database server");
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	
}
