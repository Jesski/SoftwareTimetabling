package uk.ac.ncl.csc8005.group3.scheduler;

import java.util.*;

import uk.ac.ncl.csc8005.group3.scheduler.Module;
import uk.ac.ncl.csc8005.group3.scheduler.Schedule;
import uk.ac.ncl.csc8005.group3.scheduler.Utils.SortedArrayList;


/**
 * @author:  Denny S Antony & Luke McMahon 
 * Date: 28/04/2014
 */
public class Scheduler {	
	boolean generatedSuccessfully=false;// stores boolean, changed when schedule successfully generated, global due to recursion.	
	private Schedule schedule;  //stores the schedule.
	private ArrayList<Module> scheduledModules;
	
	public Scheduler() {}

	/**
	 * algorithm of the program
	 * @param modules Arraylist of all modules to be scheduled
	 * @param rooms Arraylist of all rooms to be scheduled
	 * @param examPeriodLength the numberOfdays which the exams can be schedule in
	 * @return 
	 */
	public boolean generateSchedule(ArrayList<Module> modules, ArrayList<Room> rooms, int examPeriodLength) {
		schedule= new Schedule(rooms, examPeriodLength);
		beginScheduler(manageDupicateModules(modules));
		
		if (generatedSuccessfully=true){
			scheduledModules=modules;
		}
		return generatedSuccessfully;
	}
	
	public ArrayList<Module> getScheduledModules(){
		return scheduledModules;
	}
	
	
	/**
	 * bundles clashed modules together, to allow them to be ran at same time.
	 * @param modules Arraylist of all modules to be scheduled
	 * @return sorted ArrayList of all modules, with clashed modules organised together as Super modules
	 */
	public ArrayList<Module> manageDupicateModules(ArrayList<Module> modules){
		ArrayList<Module> managedModules = new ArrayList<Module>(); // array of now non repeated, coupled modules.
		ArrayList<Module> modulesWithClashes = new ArrayList<Module>(); // array ofModuels that need to be clashed
		Module tempModule;
		ArrayList<String> tempClashed = new ArrayList<String>(); // array of moduels that are clashed with current module
		boolean finishedClashing =false;
	
		managedModules.addAll(modules);
		
		//build list of all clashed modules
		for (Module module:modules){
			if (module.getClashedModules().size() > 0) {
				modulesWithClashes.add(module);
				managedModules.remove(module); // create list of none clashed modules
			}
		}
		
		if (modulesWithClashes.size()==0){
			finishedClashing=true;
		}
		
		
		while(!finishedClashing){ // while not finished clashing
			tempModule= modulesWithClashes.get(0); // Get module on top of list
			tempClashed= tempModule.getClashedModules(); // Get this modules clashed modules
			ArrayList<Module> modulesToBeClashedTogether = new ArrayList<Module>();
			
			boolean finishedSingleClash =false;
			int count =0;
			while(!finishedSingleClash){ //cycle through the entire temp clashed list, finding and removing from modules with clashes
				String moduleTofind= tempClashed.get(count);
				
				boolean found=false;
				int count2=0;
				modulesToBeClashedTogether.clear(); // intalise before creating list of modules that need to be clashed together.
				while(!found){ //cycle through modules with clashes list looking for moduletoFind &remove
					if (modulesWithClashes.get(count2).getId().equals(moduleTofind)){
						modulesToBeClashedTogether.add(modulesWithClashes.get(count2));//save found modules in one array list,as can be used to create the new module
						modulesWithClashes.remove(count2);
						found=true;
					}
					count2++;
				}
				
				if (tempClashed.size()==count+1){ // if ran for each module in modules list of clashed modules, then finished this module so exit loop.
					finishedSingleClash=true;
				}				
				count++;
			}
			modulesToBeClashedTogether.add(tempModule);
			managedModules.add(new SuperModule(modulesToBeClashedTogether)); //create the new super module from old
			modulesWithClashes.remove(tempModule); //remove module from to be clashed list as now clashed
			
			if (modulesWithClashes.size()==0){ // if ran for each module in modules list of clashed modules, then finished this module so exit loop.
				finishedClashing=true;
			}				
		}
		
		return managedModules;
	}
				
		private void beginScheduler(ArrayList<Module> managedModules){
			SortedArrayList<Module> unscheduledModules = new SortedArrayList<Module>();
			
			
			for (Module module: managedModules){
				unscheduledModules.add(module);
			}
			
			for (Module module: unscheduledModules){
				System.out.println(module.toString());
			}
			
			addmodules(unscheduledModules, null, false, 0);
			
			System.out.println("Generated schedule succesfully? "+ generatedSuccessfully);
			System.out.println(schedule);
			
		}
		
		/*
		 * Attempts to schedule a module to the schedule(part of the algorithm)
		 * @param unscheduledModules the list of modules that need to be schedule 
		 * @param previouslyScheduled the module which was last scheduled in the schedule 
		 * @param looping used to create a loop when self calling
		 * @param count
		 * @return true if nothing left to schedule and stops the algorithm, false otehrwise
		 */	
		private boolean addmodules(SortedArrayList<Module> unscheduledModules, Module previouslyScheduled, boolean looping, int count) {
			int attemptedModuleCount =count;
			Module moduleToSchedule;
			
			if (previouslyScheduled==null && looping==false){
				// if null, means successfully scheduled last module.. therefore need to being going through arraylist again.
				attemptedModuleCount=0;
			}
			
			if (previouslyScheduled!=null && looping==false){
				// if not null, means unsuccessfully scheduled last module.. therefore need to continue going through Arraylist at from +1 after last previously scheduled module.
				unscheduledModules.add(previouslyScheduled);
				attemptedModuleCount=unscheduledModules.indexOf(previouslyScheduled)+1;
			}
			
			if (unscheduledModules.isEmpty()){ 
				generatedSuccessfully=true;
				return true; // if nothing left to schedule, stop
				
			}else{
				moduleToSchedule = unscheduledModules.get(attemptedModuleCount); //get biggest none attempted scheduled module in array
				attemptedModuleCount++; // increment counter for next time
			}
			
			if (schedule.scheduleModule(moduleToSchedule)==false){//attempt to schedule the module, if false try to schedule next module
				if (attemptedModuleCount>unscheduledModules.size()){
					//if attempted every possible branch of this node & cannot schedule, remove last scheduled module & continue from where left off.
					try{
						addmodules(unscheduledModules, schedule.removeLastModule(),false,count);
					}catch(ArrayIndexOutOfBoundsException e){ //if this error thrown, then schedule is empty, and attempts at scheduling first module have been exhausted
						return false;
					}
					addmodules(unscheduledModules, null, true,count); //call self with null and looping (acts like while loop)
				}
			}else{
				unscheduledModules.remove(moduleToSchedule); //removed now scheduled module from unscheduled arrayList;
				addmodules(unscheduledModules, null, false,count); //call self with new unscheduled list
			}
			return false;				
	}	
}
