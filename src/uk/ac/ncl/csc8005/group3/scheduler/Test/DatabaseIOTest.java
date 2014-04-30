package uk.ac.ncl.csc8005.group3.scheduler.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;

import org.junit.Test;

import uk.ac.ncl.csc8005.group3.scheduler.DatabaseIO;
import uk.ac.ncl.csc8005.group3.scheduler.Module;
import uk.ac.ncl.csc8005.group3.scheduler.Room;

/**
 * @authors: Jessica King and Luke McMahon 
 * Date: 30/04/2014
 * Class DatabaseIOTest includes tests to chekc the functionallity of the DatabaseIO class.
 */

public class DatabaseIOTest {

	/*
	 * @Test
	 *
	public void testDatabase() {

		DatabaseIO db = new DatabaseIO();

		Calendar cal = Calendar.getInstance();
		java.sql.Date date=new java.sql.Date(cal.getTime().getTime());

		try{
		db.openDatabase();
		}
		catch (Exception e){
		}
		
		db.deleteTable();
		db.writeToDB("moduleID", 5.0, 1000, "room", date);
		db.writeToDB("moduleID2", 5.0, 1000, "room", date);
		
		System.out.println("hello");
	}
	

	
	@Test 
	public void testWriteAllToDB(){
		ArrayList<Module> modules;
		ArrayList<Room> rooms;
		modules = new ArrayList<Module>();
		rooms= new ArrayList<Room>();
		ArrayList<String> clashedModules1 = new ArrayList<String>();
		HashMap<String, Integer> coupledModules1 = new HashMap<String, Integer>();
		modules.add(new Module("CSC8001", clashedModules1, coupledModules1, 2.00, 15, "CMP"));		
		rooms.add(new Room("1", "CMP", 10.00, 17.00, 1.00, 200));
		
		Scheduler scheduler = new Scheduler();
		scheduler.generateSchedule(modules, rooms, 5);
		modules=scheduler.getScheduledModules();
		
		System.out.println(modules.get(0));
		
		DatabaseIO db = new DatabaseIO();
		try{
		db.openDatabase();
		}catch(Exception e){}
		
		Calendar cal = Calendar.getInstance();
		
		System.out.println("check1");
		db.writeAllToDB(modules, cal);
		System.out.println("check2");
	}
	*/

	/*
	 * Tests the getModules method.
	 */
	@Test
	public void testGetModules(){
		DatabaseIO db = new DatabaseIO();
		assert (db.getModules().get(0) instanceof Module);
		assert(db.getModules().get(0) !=null);
	}
	
	/*
	 * Tests the getRooms method.
	 */
	@Test
	public void testGetRooms(){
		DatabaseIO db = new DatabaseIO();
		assert (db.getRooms().get(0) instanceof Room);
		assert(db.getRooms().get(0) !=null);
	}
		
	
	/*
	 * Tests that when provided with a module code, the correct room type is returned.
	 */
	@Test
	public void testRoomType() throws Exception{
		DatabaseIO db = new DatabaseIO();
		db.openDatabase();
		assertEquals(db.roomType("CSC8003"),"CPT");
	}


	
}
