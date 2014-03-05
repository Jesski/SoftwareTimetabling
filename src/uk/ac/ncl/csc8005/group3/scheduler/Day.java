package uk.ac.ncl.csc8005.group3.scheduler;

import java.util.*;

public class Day {
	private ArrayList<Room> rooms;
	private HashSet<Module> scheduledModules;

	public Day(ArrayList<Room> rooms) {
		this.rooms = new ArrayList<Room>(rooms);
		scheduledModules = new HashSet<Module>() ;
	}

	
	public boolean addModule(Module module) throws IllegalArgumentException {
		boolean scheduled = false;
		boolean coupled = false;
		int count = 0;
		
		if(scheduledModules.size()==0){
		}else{		
		for (Module moduleTwo : scheduledModules) {
			if (checkCoupledModules(module, moduleTwo) == true) {
				coupled = true;
			}
		}
	}

		if (coupled == false) {
			// try to add module to each room.. without clashes. If cannot then
			// throw exception.
			while (scheduled == false) {
				// needs to take into account room sizes.
				try {
					rooms.get(count).addModule(module);
					scheduledModules.add(module);
					scheduled = true; // update scheduled files.
				} catch (IllegalArgumentException e) {
				}
				if (count > (rooms.size() - 1)) {
					throw new IllegalArgumentException(
							"Not possible to schedule");
				}
				count = count + 1;
			}
		}
		else{
		}
		
		return scheduled;

	}

	public boolean checkCoupledModules(Module moduleOne, Module moduleTwo) {
		// NOTE, boolean can be changed to int, to allow for partially correct
		// schedule.
		boolean coupled = false;

		Map<String, Integer> moduleOneCoupledModules = moduleOne
				.getModuleDetails();

		Iterator it = moduleOneCoupledModules.entrySet().iterator();

		while (it.hasNext()) {
			Map.Entry<String, Integer> temp = (Map.Entry<String, Integer>) it
					.next();

			if (temp.getKey().equals(moduleTwo.getId())) {
				coupled = true;
			}
		}

		return coupled;
	}

	public void removeModule(Module module) {
		for (Room lookForRoom : rooms) {
			if (lookForRoom.findModule(module) == true) {
				lookForRoom.removeModule(module);
			}

		}

	}

	public boolean lookFor(Module module) {
		for (Module lookingforModule : scheduledModules) {
			if (module.getId().equals(lookingforModule.getId())) {
				return true;
			}
		}
		return false;
	}

}
