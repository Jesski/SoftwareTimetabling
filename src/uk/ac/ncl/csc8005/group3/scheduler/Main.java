package uk.ac.ncl.csc8005.group3.scheduler;

import java.util.ArrayList;
import java.util.Stack;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		 ArrayList<Room> rooms = new ArrayList<Room>();
		 rooms.add(new Room("1",  "CMP",  1.00,  5.00,  1.00,  2));
		 rooms.add(new Room("2",  "CMP",  9.00,  5.00,  1.00,  5));
		 rooms.add(new Room("3",  "ART",  9.00,  12.00,  1.00,  10));
		 rooms.add(	new Room("4",  "LCT",  9.00,  12.00,  0.00,  1));
		 rooms.add(new Room("5",  "LCT",  1.00,  5.00,  0.00,  1));
		 Scheduler schedule = new Scheduler();
		 schudule.addmodule(Stack<Module> unscheduled, Module previouslyScheduled)
	}

}
