package uk.ac.ncl.csc8005.group3.scheduler.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import uk.ac.ncl.csc8005.group3.scheduler.Module;
import uk.ac.ncl.csc8005.group3.scheduler.Room;

public class TestRoom {
	@Test
	public void testAddModules(){
		Room testRoom1=new Room("LT1", "CMP", 540, 1020, 100);
		Room testRoom2=new Room("LT2", "CMP", 540, 1020, 75);
		
		Room testRoom3=new Room("LT3", "CMP", 540, 550, 75);
		
		Room testRoom4=new Room("LT4", "CMP", 540, 1020, 300);
		Room testRoom5=new Room("LT5", "CMP", 540, 1020, 1);
		
		
		ArrayList<String> clashedModuels = new ArrayList<String>();
		HashMap<String, Integer> coupledModules = new HashMap<String, Integer>();


		Module testModule = new Module("CSC8003", clashedModuels,coupledModules, 60, 15, "CMP");
		Module testModule2 = new Module("CSC8004", clashedModuels,coupledModules, 90, 15, "EXE");
		
		Module testModule3 = new Module("CSC8005", clashedModuels,coupledModules, 100, 15, "CMP");
		
		Module testModule4 = new Module("CSC8006", clashedModuels,coupledModules, 100, 10, "CMP");

		
		assertTrue("check room type",testRoom1.addModule(testModule));
		assertFalse("check wrong room type", testRoom2.addModule(testModule2));
		
		assertFalse("check not enough time",testRoom3.addModule(testModule3));
		assertTrue("check enough time", testRoom2.addModule(testModule3));
		
		assertTrue("check enough capacity", testRoom4.addModule(testModule4));
		assertFalse("check not enough capacity", testRoom5.addModule(testModule4));
	}
	
	@Test
	public void testTimeLeftInRoom(){
		Room testRoom1=new Room("LT1", "CMP", 540, 1040, 30, 100);
		ArrayList<String> clashedModuels = new ArrayList<String>();
		HashMap<String, Integer> coupledModules = new HashMap<String, Integer>();
		Module testModule = new Module("CSC8003", clashedModuels,coupledModules, 70, 15, "CMP");
		
		testRoom1.addModule(testModule);
		assertTrue("These should be Equal", 400.0==testRoom1.getTimeLeftInRoom());
		
	}
	
	@Test
	public void testRemoveModule(){
		Room testRoom1=new Room("LT1", "CMP", 540, 1040, 30, 100);
		
		ArrayList<String> clashedModuels = new ArrayList<String>();
		HashMap<String, Integer> coupledModules = new HashMap<String, Integer>();
		
		Module testModule = new Module("CSC8003", clashedModuels,coupledModules, 70, 15, "CMP");
		Module testModule2 = new Module("CSC8004", clashedModuels,coupledModules, 70, 15, "CMP");
		
		testRoom1.addModule(testModule);
		testRoom1.addModule(testModule2);
		
		testRoom1.getTimeLeftInRoom();
		
		assertTrue("This should be true", testRoom1.removeModule(testModule));
		assertTrue("These should be equal", 400.0==testRoom1.getTimeLeftInRoom());
		
		assertFalse(testRoom1.removeModule(testModule));
		assertTrue("These should be equal", 400.0==testRoom1.getTimeLeftInRoom());
		
		
	}

}
