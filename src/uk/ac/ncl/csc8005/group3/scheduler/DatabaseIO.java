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

/**
 * @author: Jessica King 
 * Date: 30/04/2014
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
	 */
	public ArrayList<Module> getModules() {
		return modules;
	}

	/*
	 * Return an array list of rooms that have been populated.
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
		ArrayList<String> ClashedModules = new ArrayList<String>();
		ArrayList<Integer> SID = query2(id);

		for (int studentid : SID) {
			ArrayList<String> modules = query1(studentid);
			for (String moduleid : modules) {
				ClashedModules.add(moduleid);
			}
		}
		return ClashedModules;
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
		String query = "SELECT type FROM t8005t2 .takes WHERE name =" + name;
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

	
	
	
	// Populates all modules with data from the database.
	private ArrayList<Module> populateModules() {
		String query = "SELECT * FROM t8005t2 .modules";
		ArrayList<Module> modules = new ArrayList<Module>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				String id = rs.getString("ID");
				ArrayList<String> clashedModules = getClashedModules(id);
				HashMap<String, Integer> coupledModules = getCoupledModules(id); 
				int examLength = rs.getInt("examLength");
				int moduleSize = rs.getInt(4);
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

	// Supply this with a student ID and you will get a list of modules
	private ArrayList<String> query1(int studentid) {
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

	// Supply a module name and get an array of student IDs taking that module
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

	// Delete data in table output.
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

	public void writeAllToDB(ArrayList<Module> schedule,
			final Calendar startDateMaster) {
		deleteTable();
		Calendar startDate = (Calendar) startDateMaster.clone();

		for (Module module : schedule) {
			startDate.add(Calendar.DATE, module.getDayNumber());
			java.sql.Date examDate = new java.sql.Date(startDate.getTime()
					.getTime());
			writeToDB(module.getId(), module.getExamLength(), module.getTime() / 60, module.getRoomName(),
					examDate);
		}
		
		try {
			closeDatabase();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	// Insert statement to write to the output database.
	private void writeToDB(String moduleID, int examLength, int time,
			String room, Date date) {
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

	
	
	// Insert statement to write a new student to the input database
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
	
	

	
	// Update statement to write to the module table
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
	
	
	// Add a room to the database
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
	
	
}
