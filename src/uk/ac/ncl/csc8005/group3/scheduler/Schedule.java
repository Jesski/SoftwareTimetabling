package uk.ac.ncl.csc8005.group3.scheduler;

import java.util.*;


public class Schedule {
	private Stack<Module> scheduleOrder;
	private Day[] days;
	private ArrayList<Room> rooms;

	public Schedule(ArrayList<Room> rooms, int ExamPeriodLength) {
		rooms = new ArrayList<Room>(rooms);
		
		scheduleOrder = new Stack<Module>();
		days = new Day[ExamPeriodLength];

		for (int i = 0; i < 4; i++) {
			days[i] = new Day(i, rooms);
		}
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

	public Module removeLastModule() throws ArrayIndexOutOfBoundsException{
		if (scheduleOrder.size() == 0) {
			throw new ArrayIndexOutOfBoundsException("Schedule is empty!");
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
	
	@Override
	public String toString() {
		return "Schedule [days=" + Arrays.toString(days) + "]";
	}
	
}
