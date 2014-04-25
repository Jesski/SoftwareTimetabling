package uk.ac.ncl.csc8005.group3.scheduler;

import java.util.*;

public class Day {
	private ArrayList<Room> rooms;
	private HashSet<Module> scheduledModules;
	private int dayNumber;

	/**
	 * Constructor for objects of type day
	 * @param dayNumber number of day in the schedule
	 * @param rooms all room avilable to hold exams
	 */
	public Day(int dayNumber, ArrayList<Room> rooms) {
		this.rooms = new ArrayList<Room>(rooms);
		scheduledModules = new HashSet<Module>();
		this.dayNumber=dayNumber;
	}

	/**
	 * Attempts to add a module to the day, if module cannot be added returns false.
	 * Does not handle clashed modules - These are handled in scheduler method of program.
	 * @param module the module to be added
	 * @return false if module was not added successfully, true otherwise.
	 */
	public boolean addModule(Module module){
		boolean attemptToSchedule=true;
		boolean coupled = false;
		int count = 0;

		if (scheduledModules.size() != 0){
			for (Module moduleTwo : scheduledModules) {
				if (checkCoupledModules(module, moduleTwo) != 0) {
					//ie cannot be scheduled today, as already scheduled one of its clashed modules
					//can be changed to allow for coupled modules to be scheduled on same day.. will need re-write of room/times class
					return false;
				}
			}
		}

		if (coupled == false) {
			// try to add module to each room.. without clashes. If cannot then
			// throw exception.
			while (attemptToSchedule==true) {
				if (rooms.get(count).addModule(module)==true){
					//successfully scheduled a module
					scheduledModules.add(module);
					module.setDayNumber(dayNumber); // set the day in the module that the module has been scheduled too.
					attemptToSchedule = false;//exit loop
					return true;
				}
					
				//if count become bigger than no of rooms
				if (count >= (rooms.size() - 1)) {
					attemptToSchedule = false;//exit loop
					return false;
				}
				count = count + 1;
			}
		}
		return false; //should never reach here.
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Day [rooms=" + rooms + "]";
	}

	//check this!!!!
	/**
	 * Returns number of pupils in one module that are also in another module.
	 * @param moduleOne first module to be checked
	 * @param moduleTwo second moduel to be checked
	 * @return value of coipling, zero if modules are not coupled.
	 */
	public int checkCoupledModules(Module moduleOne, Module moduleTwo) {
		int coupledValue=0;
		
		if(moduleOne.getCoupledModules()==null){ // ie if module 1 has no coupled modules, then cannot be coupled so return zero.
			return 0;
		}
		
		Map<String, Integer> moduleOneCoupledModules = moduleOne.getCoupledModules(); // what if coupled modules ==null? Throws exception!!
		Iterator<Map.Entry<String, Integer>> it = moduleOneCoupledModules.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry<String, Integer> temp = (Map.Entry<String, Integer>) it.next();

			if (temp.getKey().equals(moduleTwo.getId())) {
				coupledValue=coupledValue+temp.getValue();	
			}
		}
		
		return coupledValue; // return value of coupled modules (ie number of students also taking other modules already scheduled on same day).
	}

	/**
	 * Removes a module from the scheduled modules for the day.
	 * 
	 * @param module module to be removed
	 * @return true if successful, false if not.
	 */
	public boolean removeModule(Module module) {
		for (Room lookForRoom : rooms) {
			if (lookForRoom.removeModule(module) == true) 
			return true;
		}
		return false;
	}
	
	/**
	 * @param module
	 * @return
	 */
	public boolean lookFor(Module module) {
		for (Module lookingforModule : scheduledModules) {
			if (module.getId().equals(lookingforModule.getId())) {
				return true;
			}
		}
		return false;
	}
}
