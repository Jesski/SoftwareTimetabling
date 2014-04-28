package uk.ac.ncl.csc8005.group3.scheduler;
import java.util.*;


public class Main {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub	
		DatabaseIO db = new DatabaseIO();
		Scheduler scheduler = new Scheduler();
		ArrayList<Module> modules= new ArrayList<Module>(db.getModules());
		ArrayList<Room> rooms= new ArrayList<Room>(db.getRooms());
		
		scheduler.generateAndReturnSchedule(modules, rooms, examPeriodLength)
	}
	
	

}
	
