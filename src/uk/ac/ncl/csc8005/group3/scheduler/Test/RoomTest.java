package uk.ac.ncl.csc8005.group3.scheduler.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import uk.ac.ncl.csc8005.group3.scheduler.Module;
import uk.ac.ncl.csc8005.group3.scheduler.Room;

public class RoomTest {

	@Test
	public void testAddModule() {
		ArrayList<String> clashedModuels1 = new ArrayList<String>();
		clashedModuels1.add("CSC8001");
		clashedModuels1.add("CSC8002");
		HashMap<String, Integer> coupledModules1 = new HashMap<String, Integer>();
		coupledModules1.put("CSC8004", 10);

		Module testModule1 = new Module("CSC8004", clashedModuels1,
				coupledModules1, 1.00, 15, "CMP");
		Module testModule2 = new Module("CSC8004", clashedModuels1,
				coupledModules1, 1.00, 15, "LOL");
		Module testModule3 = new Module("CSC8004", clashedModuels1,
				coupledModules1, 1.00, 15, "CMP");

		Room testRoom = new Room("LT1", "CMP", 9.00, 17.00, 100);
		Room testRoom2 = new Room("LT2", "CMP", 9.00, 17.00, 100);
		Room testRoom3 = new Room("LT2", "CMP", 9.00, 17.00, 10);
		Room testRoom4 = new Room("LT2", "CMP", 9.00, 11.00, 100);

		assertTrue(testRoom.addModule(testModule1));
		assertFalse(testRoom2.addModule(testModule2));
		assertFalse(testRoom3.addModule(testModule3));

		testRoom4.addModule(testModule1);
		assertFalse(testRoom4.addModule(testModule1));
	}

	@Test
	public void testRemoveModule() {
		ArrayList<String> clashedModuels1 = new ArrayList<String>();
		clashedModuels1.add("CSC8001");
		clashedModuels1.add("CSC8002");
		HashMap<String, Integer> coupledModules1 = new HashMap<String, Integer>();
		coupledModules1.put("CSC8004", 10);

		Module testModule1 = new Module("CSC8004", clashedModuels1,
				coupledModules1, 1.00, 15, "CMP");

		Room testRoom = new Room("LT1", "CMP", 9.00, 17.00, 100);
		double timeleft = testRoom.getTimeLeftInRoom();
		System.out.println(timeleft);

		testRoom.addModule(testModule1);
		System.out.println(testRoom.getTimeLeftInRoom());
		testRoom.removeModule(testModule1);

		System.out.println(testRoom.getTimeLeftInRoom());
		assertTrue(timeleft == testRoom.getTimeLeftInRoom());

	}

}
