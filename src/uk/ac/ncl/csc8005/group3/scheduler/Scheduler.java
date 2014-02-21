package uk.ac.ncl.csc8005.group3.scheduler;

import java.util.*;
import java.io.*;

public class Scheduler {
	private ArrayList<Module> modules;

	private File file;
	private DatabaseIO db;
	private Date examStart;
	private Date examEnd;
	private Schedule schedule = new Schedule(examStart, examEnd);

	public Scheduler(String file) {

		ArrayList<String> temp;

		this.file = new File(file);

		// c db = new DatabaseIO(this.file);

		// c modules=new ArrayList<Module>(db.getModule());
		// c rooms= new ArrayList<Room>(db.getRooms());
		// c days=new ArrayList<Day>(); //<-- add size, remove arraylist

	}

	public void manageDupicateModules() {
		// creating Lookup table for Id
		HashMap<String, Integer> lookUpId;
		lookUpId = new HashMap<String, Integer>();

		int count = 0;
		for (Module module : modules) {
			lookUpId.put(module.getId(), count);
			count++;
		}

		ArrayList<Integer> modulePos = new ArrayList<Integer>();
		ArrayList<Module> clashedModules = new ArrayList<Module>();
		ArrayList<Module> newModules = new ArrayList<Module>();
		ArrayList<Boolean> alreadyRan = new ArrayList<Boolean>();
		ArrayList<String> tempModuleIds = new ArrayList<String>();

		for (int x = 0; x < modules.size(); x++) {
			alreadyRan.add(false);
		}

		for (int x = 0; x < modules.size(); x++) {
			if (alreadyRan.get(x) == false) {
				tempModuleIds.addAll(modules.get(x).getClashedModules());
				if (tempModuleIds.size() > 0) {
					for (String id : tempModuleIds) {
						modulePos.add(lookUpId.get(id));
						alreadyRan.set(lookUpId.get(id), true);
					}
					for (Integer y : modulePos) {
						clashedModules.add(modules.get(y));
					}
					newModules.add(new Module(clashedModules));
				}
			} else {
			}
		}

		Iterator<Module> itr = modules.iterator();

		while (itr.hasNext()) {
			Module ItrModule = itr.next();
			if (ItrModule.getClashedModules().size() > 0) {
				itr.remove();
			}
		}

		modules.addAll(newModules);
	}

	public void addmodule(Stack<Module> unscheduled, Module previouslyScheduled) {
		Module previousModule = null;
		Module module = unscheduled.pop();

		try {
			if (previouslyScheduled != null) {
				unscheduled.push(previouslyScheduled);
			}
			schedule.scheduleModule(module);

		} catch (Exception e) {
			previousModule = schedule.removeLastModule();
			unscheduled.push(module);
		} finally {
			addmodule(unscheduled, previousModule);
		}
	}

}
