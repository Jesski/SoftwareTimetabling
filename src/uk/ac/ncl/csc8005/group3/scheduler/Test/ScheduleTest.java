package uk.ac.ncl.csc8005.group3.scheduler.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import uk.ac.ncl.csc8005.group3.scheduler.Module;
import uk.ac.ncl.csc8005.group3.scheduler.Room;
import uk.ac.ncl.csc8005.group3.scheduler.Schedule;

public class ScheduleTest {

	@Test
	public void testScheduleModule() {
		ArrayList<Room> rooms = new ArrayList<Room>();
		
		rooms.add(new Room("LT1", "CMP", 540, 1020, 30, 100));
		rooms.add(new Room("LT2", "CMP", 540, 1020, 30, 75));
		
		
		Schedule schedule = new Schedule(rooms, 5);
		
		ArrayList<String> clashedModuels1 = new ArrayList<String>();
		clashedModuels1.add("CSC8001");
		clashedModuels1.add("CSC8002");
		
		HashMap<String, Integer> coupledModules1 = new HashMap<String, Integer>();
		coupledModules1.put("CSC8003", 10);
		

		Module testModule = new Module("CSC8003", clashedModuels1,coupledModules1, 90, 15, "CMP");
		
		assertTrue(schedule.scheduleModule(testModule));
		
		
		rooms = new ArrayList<Room>();
		rooms.add(new Room("LT1", "CMP", 540, 1020, 30, 100));
		schedule = new Schedule(rooms, 5);
		
		testModule = new Module("CSC8003", clashedModuels1,coupledModules1, 90, 15, "EXE");
		assertFalse(schedule.scheduleModule(testModule));
		
		
		
	}

	@Test
	public void testRemoveLastModule() {
		fail("Not yet implemented");
	}

}
