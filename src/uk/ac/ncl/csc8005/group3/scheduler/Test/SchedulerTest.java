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
				120, 15, "CMP"));
		//modules.add(new Module("CSC8002", clashedModules2, coupledModules2,				60, 35, "LCT"));
		//modules.add(new Module("CSC8003", clashedModules3, coupledModules3,				180, 100, "LCT"));
		//modules.add(new Module("CSC8004", clashedModules4, coupledModules4,				240, 21, "ART"));
		//modules.add(new Module("CSC8005", clashedModules5, coupledModules5,				60, 35, "CMP"));
		
		rooms.add(new Room("1", "CMP", 780, 840, 60, 200));
		//rooms.add(new Room("2", "CMP", 540, 1020, 60, 500));
		//rooms.add(new Room("3", "ART", 540, 1020, 60, 100));
		//rooms.add(new Room("4", "LCT", 540, 720, 0.00, 1000));
		//rooms.add(new Room("5", "LCT", 780, 1020, 0.00, 1000));
	}
	

	
}
