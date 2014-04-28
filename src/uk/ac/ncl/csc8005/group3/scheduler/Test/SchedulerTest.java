package uk.ac.ncl.csc8005.group3.scheduler.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import uk.ac.ncl.csc8005.group3.scheduler.Module;
import uk.ac.ncl.csc8005.group3.scheduler.Room;
import uk.ac.ncl.csc8005.group3.scheduler.Scheduler;

public class SchedulerTest {
	ArrayList<Module> modules;
	ArrayList<Room> rooms;
		
	@Test
	public void testGenerateSchedule(){
		addData();
		Scheduler scheduler = new Scheduler();
		scheduler.generateSchedule(modules, rooms, 5);
		System.out.println(modules.get(0));
	}
	
	@Test

	//public void manageDupicateModulesTest(){
	//	addData();
	//	Scheduler scheduler = new Scheduler();
	//	assertEquals("should only be 3 modules now",3, scheduler.manageDupicateModules(modules).size());
	//	assertFalse(modules.size()==scheduler.manageDupicateModules(modules).size());
	//}

	public void addData() {
		modules = new ArrayList<Module>();
		rooms= new ArrayList<Room>();
		ArrayList<String> clashedModules1 = new ArrayList<String>();
		//ArrayList<String> clashedModules2 = new ArrayList<String>();
		//ArrayList<String> clashedModules3 = new ArrayList<String>();
		//ArrayList<String> clashedModules4 = new ArrayList<String>();
		//ArrayList<String> clashedModules5 = new ArrayList<String>();// other
		HashMap<String, Integer> coupledModules1 = new HashMap<String, Integer>();
	//	HashMap<String, Integer> coupledModules2 = new HashMap<String, Integer>();
		//HashMap<String, Integer> coupledModules3 = new HashMap<String, Integer>();
		//HashMap<String, Integer> coupledModules4 = new HashMap<String, Integer>();
		//HashMap<String, Integer> coupledModules5 = new HashMap<String, Integer>();// modules
		//clashedModules1.add("CSC8005");
		//clashedModules5.add("CSC8001");
		//clashedModules2.add("CSC8004");
		//clashedModules4.add("CSC8002");
		//coupledModules1.put("CSC8004", 10);
		//coupledModules3.put("CSC8005", 10);
		modules.add(new Module("CSC8001", clashedModules1, coupledModules1,
				2.00, 15, "CMP"));
		//modules.add(new Module("CSC8002", clashedModules2, coupledModules2,				1.00, 35, "LCT"));
		//modules.add(new Module("CSC8003", clashedModules3, coupledModules3,				3.00, 100, "LCT"));
		//modules.add(new Module("CSC8004", clashedModules4, coupledModules4,				4.00, 21, "ART"));
		//modules.add(new Module("CSC8005", clashedModules5, coupledModules5,				2.00, 35, "CMP"));
		
		rooms.add(new Room("1", "CMP", 13.00, 14.00, 1.00, 200));
		//rooms.add(new Room("2", "CMP", 9.00, 17.00, 1.00, 500));
		//rooms.add(new Room("3", "ART", 9.00, 17.00, 1.00, 100));
		//rooms.add(new Room("4", "LCT", 9.00, 12.00, 0.00, 1000));
		//rooms.add(new Room("5", "LCT", 13.00, 17.00, 0.00, 1000));
	}
	

	
}
