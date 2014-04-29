package uk.ac.ncl.csc8005.group3.scheduler.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import uk.ac.ncl.csc8005.group3.scheduler.Day;
import uk.ac.ncl.csc8005.group3.scheduler.Module;
import uk.ac.ncl.csc8005.group3.scheduler.Room;

public class DayTest {

	@Test
	public void testAddModule() {
		// create test module
		ArrayList<String> clashedModuels1 = new ArrayList<String>();
		clashedModuels1.add("CSC8001");
		clashedModuels1.add("CSC8002");
		
		HashMap<String, Integer> coupledModules1 = new HashMap<String, Integer>();
		coupledModules1.put("CSC8003", 10);
		

		Module testModule = new Module("CSC8003", clashedModuels1,
				coupledModules1, 60, 15, "CMP");
		
		//create test room
		ArrayList<Room> Rooms= new ArrayList<Room>();
		Rooms.add(new Room("LT1", "CMP", 540, 1020, 100));
		Day testDay = new Day(1, Rooms);
		
		assertEquals("should be true", true ,testDay.addModule(testModule));
		assertEquals("should be false", false ,testDay.addModule(testModule));
	}

	
	@Test 
	public void testCheckCoupledModulesNoModule(){
		//create two test modules which are coupled.
		ArrayList<String> clashedModuels1 = new ArrayList<String>();
		clashedModuels1.add("CSC8001");
		clashedModuels1.add("CSC8002");
		
		HashMap<String, Integer> coupledModules1 = new HashMap<String, Integer>();
		

		Module testModule1 = new Module("CSC8003", clashedModuels1,
				coupledModules1, 60, 15, "CMP");
		
		
		ArrayList<String> clashedModuels2 = new ArrayList<String>();
		clashedModuels1.add("CSC8001");
		clashedModuels1.add("CSC8002");
		
		HashMap<String, Integer> coupledModules2 = new HashMap<String, Integer>();

		Module testModule2 = new Module("CSC8004", clashedModuels2,	coupledModules2, 60, 10, "CMP");
		
		//create test room
		ArrayList<Room> Rooms= new ArrayList<Room>();
		Rooms.add(new Room("LT1", "CMP",540, 1020, 100));
		Day testDay = new Day(1, Rooms);
		
		assertEquals("These should be equal",0,testDay.checkCoupledModules(testModule1, testModule2));
	}
	
	@Test
	public void testCheckCoupledModules() {
		//create two test modules which are coupled.
		ArrayList<String> clashedModuels1 = new ArrayList<String>();
		clashedModuels1.add("CSC8001");
		clashedModuels1.add("CSC8002");
		
		HashMap<String, Integer> coupledModules1 = new HashMap<String, Integer>();
		coupledModules1.put("CSC8004", 10);

		Module testModule1 = new Module("CSC8003", clashedModuels1,
				coupledModules1, 60, 15, "CMP");
		
		
		ArrayList<String> clashedModuels2 = new ArrayList<String>();
		clashedModuels1.add("CSC8001");
		clashedModuels1.add("CSC8002");
		
		HashMap<String, Integer> coupledModules2 = new HashMap<String, Integer>();
		coupledModules2.put("CSC8003", 10);

		Module testModule2 = new Module("CSC8004", clashedModuels2,	coupledModules2, 60, 10, "CMP");
		
		//create test room
		ArrayList<Room> Rooms= new ArrayList<Room>();
		Rooms.add(new Room("LT1", "CMP", 540, 1020, 100));
		Day testDay = new Day(1, Rooms);
		
		assertEquals("These should be equal",10,testDay.checkCoupledModules(testModule1, testModule2));
		
	}

	@Test
	public void testRemoveModule() {
		// create test module
		ArrayList<String> clashedModuels1 = new ArrayList<String>();
		clashedModuels1.add("CSC8001");
		clashedModuels1.add("CSC8002");
		
		HashMap<String, Integer> coupledModules1 = new HashMap<String, Integer>();
		coupledModules1.put("CSC8003", 10);
		

		Module testModule = new Module("CSC8003", clashedModuels1,
				coupledModules1, 60, 15, "CMP");
		
		Module testModule2 = new Module("CSC8005", clashedModuels1,
				coupledModules1, 60, 15, "CMP");
		
		//create test room
		ArrayList<Room> Rooms= new ArrayList<Room>();
		Rooms.add(new Room("LT1", "CMP", 540, 1020, 100));
		Day testDay = new Day(1, Rooms);
		
		testDay.addModule(testModule);
		
		assertTrue("Should be true",testDay.removeModule(testModule));
		assertFalse("Should be false",testDay.removeModule(testModule2));
	}

	@Test
	public void testLookFor() {
		ArrayList<String> clashedModuels1 = new ArrayList<String>();
		clashedModuels1.add("CSC8001");
		clashedModuels1.add("CSC8002");
		
		HashMap<String, Integer> coupledModules1 = new HashMap<String, Integer>();
		coupledModules1.put("CSC8003", 10);
		

		Module testModule = new Module("CSC8003", clashedModuels1,coupledModules1, 60, 15, "CMP");
		
		Module testModule2 = new Module("CSC8005", clashedModuels1,coupledModules1, 60, 15, "CMP");
		
		//create test room
		ArrayList<Room> Rooms= new ArrayList<Room>();
		Rooms.add(new Room("LT1", "CMP",540, 1020, 100));
		Day testDay = new Day(1, Rooms);
		
		testDay.addModule(testModule);	
		
		assertTrue("Should be true",testDay.lookFor(testModule));
		assertFalse("Should be false",testDay.lookFor(testModule2));
		
		
	}
	
	

}
