package uk.ac.ncl.csc8005.group3.scheduler;

import java.text.SimpleDateFormat;
import java.util.*;

///TODO: miss Sundays?

public class Schedule {
	private Stack<Module> scheduleOrder;
	private Day[] days;
	// private Date examStart = new Date("21/01/2014");
	// private Date examEnd = new Date("25/01/2014");
	private static final int MILLISECONDS_IN_A_DAY = 1000 * 60 * 60 * 24;

	public Schedule() {
		ArrayList<Room> rooms = new ArrayList<Room>();

		rooms.add(new Room("1", "CMP", 13.00, 17.00, 1.00, 200));
		rooms.add(new Room("2", "CMP", 9.00, 17.00, 1.00, 500));
		rooms.add(new Room("3", "ART", 9.00, 17.00, 1.00, 100));
		rooms.add(new Room("4", "LCT", 9.00, 12.00, 0.00, 1000));
		rooms.add(new Room("5", "LCT", 13.00, 17.00, 0.00, 1000));

		// public Schedule(Date examStart, Date examEnd) {

		// this.examStart=new SimpleDateFormat("d MMMM, yyyy",
		// Locale.ENGLISH).parse(examStart);
		// this.examEnd=new SimpleDateFormat("d MMMM, yyyy",
		// Locale.ENGLISH).parse(examEnd);

		scheduleOrder = new Stack<Module>();
		days = new Day[4];

		for (int i = 0; i < 4; i++) {
			days[i] = new Day(rooms);
		}
	}

	private int calcNumberOfDays() {
		// int numberOfDays;
		// numberOfDays = (int) ((this.examEnd.getTime() - this.examStart
		// .getTime()) / MILLISECONDS_IN_A_DAY); // <---check
		return 5;
	}

	public boolean scheduleModule(Module module) {
		for (int i = 0; i < days.length; i++) {
			if (days[i].addModule(module) == true) {
				scheduleOrder.push(module);
				return true; // if correctly scheduled, return true.
			}
		}
		
		return false; // if not correctly scheduled, return true.
	}

	public Module removeLastModule() {
		if (scheduleOrder.size() == 0) {
			return null;
		} else {
			Module lastModule = scheduleOrder.pop();
			days[findModuleDay(lastModule)].removeModule(lastModule);
			return lastModule;
		}

	}

	private int findModuleDay(Module module) {
		for (int i = 0; i < days.length; i++) {
			if (days[i].lookFor(module) == true) {
				return i;
			}
		}
		return -1;
	}
}
