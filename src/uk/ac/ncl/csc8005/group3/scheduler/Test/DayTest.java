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
		fail("Not yet implemented");
	}

	
	@Test 
	public void testCheckCoupledModulesNoModule(){
		fail("Not yet implemented");
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
				coupledModules1, 1.00, 15, "CMP");
		
		
		ArrayList<String> clashedModuels2 = new ArrayList<String>();
		clashedModuels1.add("CSC8001");
		clashedModuels1.add("CSC8002");
		
		HashMap<String, Integer> coupledModules2 = new HashMap<String, Integer>();
		coupledModules2.put("CSC8003", 10);

		Module testModule2 = new Module("CSC8004", clashedModuels2,	coupledModules2, 1.00, 10, "CMP");
		
		//create test room
		ArrayList<Room> Rooms= new ArrayList<Room>();
		Rooms.add(new Room("LT1", "CMP", 9.00, 17.00, 100));
		Day testDay = new Day(1, Rooms);
		
		System.out.println(testDay.checkCoupledModules(testModule1, testModule2));
		
	}

	@Test
	public void testRemoveModule() {
		fail("Not yet implemented");
	}

	@Test
	public void testLookFor() {
		fail("Not yet implemented");
	}

}
