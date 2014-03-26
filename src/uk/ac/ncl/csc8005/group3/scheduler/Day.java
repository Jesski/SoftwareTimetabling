package uk.ac.ncl.csc8005.group3.scheduler;

import java.util.*;

public class Day {
	private ArrayList<Room> rooms;
	private HashSet<Module> scheduledModules;
	private int dayNumber;

	public Day(int dayNumber, ArrayList<Room> rooms) {
		this.rooms = new ArrayList<Room>(rooms);
		scheduledModules = new HashSet<Module>();
		this.dayNumber=dayNumber;
	}

	public boolean addModule(Module module){
		boolean attemptToSchedule=true;
		boolean coupled = false;
		int count = 0;

		if (scheduledModules.size() != 0){
			for (Module moduleTwo : scheduledModules) {
				if (checkCoupledModules(module, moduleTwo) == -1) {
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

	@Override
	public String toString() {
		return "Day [rooms=" + rooms + "]";
	}

	//check this!!!!
	public int checkCoupledModules(Module moduleOne, Module moduleTwo) {
		boolean coupled = false;
		int coupledValue=0;
		
		if(moduleOne.getCoupledModules()==null){ // ie if module 1 has no coupled modules, then cannot be coupled so return zero.
			return -1;
		}
		
		Map<String, Integer> moduleOneCoupledModules = moduleOne.getCoupledModules(); // what if coupled modules ==null? Throws exception!!
		Iterator<Map.Entry<String, Integer>> it = moduleOneCoupledModules.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry<String, Integer> temp = (Map.Entry<String, Integer>) it.next();

			if (temp.getKey().equals(moduleTwo.getId())) {
				coupledValue=coupledValue+temp.getValue();
				coupled=true;
			}
		}
		
		if(coupled==false){
			return -1; // if not coupled return -1
		}
		return coupledValue; // return number of coupled modules (ie number of students also taking other modules already scheduled on same day).
	}

	public boolean removeModule(Module module) {
		for (Room lookForRoom : rooms) {
			if (lookForRoom.removeModule(module) == true) 
			return true;
		}
		return false;
	}
	
	///  is this needed?
	public boolean lookFor(Module module) {
		for (Module lookingforModule : scheduledModules) {
			if (module.getId().equals(lookingforModule.getId())) {
				return true;
			}
		}
		return false;
	}
}
