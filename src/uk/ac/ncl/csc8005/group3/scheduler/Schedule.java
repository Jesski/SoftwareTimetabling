package uk.ac.ncl.csc8005.group3.scheduler;

import java.util.*;

/**
 * @author:  Denny S Antony & Luke McMahon 
 * Date: 28/04/2014
 */
public class Schedule {
	private Stack<Module> scheduleOrder;
	private Day[] days;
	/**
	 * Constructor for objects of type Schedule
	 * @param rooms arraylist of rooms where exams can be scheduled at
	 * @param ExamPeriodLength the number of days of each exam
	 */
	public Schedule(ArrayList<Room> rooms, int ExamPeriodLength) {
		rooms = new ArrayList<Room>(rooms);
		
		scheduleOrder = new Stack<Module>();
		days = new Day[ExamPeriodLength];

		for (int i = 0; i < ExamPeriodLength; i++) {
			days[i] = new Day(i, rooms);
		}
	}
	/**
	 * Attempts to schedule a module into the schedule order
	 * @param module the module to be added
	 * @return false if module was not added successfully, true otherwise.
	 */

	public boolean scheduleModule(Module module) {
		for (int i = 0; i < (days.length-1); i++) {
			if (days[i].addModule(module) == true) {
				scheduleOrder.push(module);
				return true; // if correctly scheduled, return true.
			}
		}
		return false; // if not correctly scheduled, return true.
	}
	
	/**
	 * removes the last module in the schedule order
	 * @return the last module, throws an exception otherwise
	 */
	public Module removeLastModule() throws ArrayIndexOutOfBoundsException{
		if (scheduleOrder.size() == 0) {
			throw new ArrayIndexOutOfBoundsException("Schedule is empty!");
		} else {
			Module lastModule = scheduleOrder.pop();
			days[findModuleDay(lastModule)].removeModule(lastModule);
			return lastModule;
		}
	}
	
	/**
	 * attempts to find the day which the module is in
	 * @param module to be found
	 * @return the number of the day in the schedule, -1 if not in the schedule
	 */
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
