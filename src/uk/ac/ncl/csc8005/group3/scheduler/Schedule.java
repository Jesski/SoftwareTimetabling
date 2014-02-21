package uk.ac.ncl.csc8005.group3.scheduler;

import java.text.SimpleDateFormat;
import java.util.*;

///TODO: miss Sundays?

public class Schedule {
	private Stack<Module> scheduleOrder;
	private Day[] days;
	private Date examStart;
	private Date examEnd;
	private static final int MILLISECONDS_IN_A_DAY = 1000 * 60 * 60 * 24;

	public Schedule(Date examStart, Date examEnd) {

		// this.examStart=new SimpleDateFormat("d MMMM, yyyy",
		// Locale.ENGLISH).parse(examStart);
		// this.examEnd=new SimpleDateFormat("d MMMM, yyyy",
		// Locale.ENGLISH).parse(examEnd);

		scheduleOrder = new Stack<Module>();
		days = new Day[calcNumberOfDays()];
	}

	private int calcNumberOfDays() {
		int numberOfDays;
		numberOfDays = (int) ((this.examStart.getTime() - this.examEnd
				.getTime()) / MILLISECONDS_IN_A_DAY); // <---check
		return numberOfDays;
	}

	public void scheduleModule(Module module) {
		boolean scheduledModule = false;

		for (int i = 0; i < days.length; i++) {
			try {
				if (days[i].addModule(module) == true) {
					scheduledModule = true;
					scheduleOrder.push(module);
					break;
				}
			} catch (IllegalArgumentException e) {
			}
		}
		if (scheduledModule == false) {
			throw new IllegalArgumentException();
		}

	}

	public Module removeLastModule() {
		Module lastModule = scheduleOrder.pop();
		days[findModuleDay(lastModule)].removeModule(lastModule);
		return lastModule;
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
