package uk.ac.ncl.csc8005.group3.scheduler;

import java.util.*;

import uk.ac.ncl.csc8005.group3.scheduler.Module;
import uk.ac.ncl.csc8005.group3.scheduler.Schedule;
import uk.ac.ncl.csc8005.group3.scheduler.Utils.SortedArrayList;


/**
 * @authors:  Denny S Antony & Luke McMahon 
 * Date: 28/04/2014
 */
public class Scheduler {	
	boolean generatedSuccessfully=false;// stores boolean, changed when schedule successfully generated, global due to recursion.	
	private Schedule schedule;  //stores the schedule.
	private ArrayList<Module> scheduledModules;
	
	public Scheduler() {}

	/** checks if it is possible to generate a schedule from the modules, room and length specified.
	 * @param modules arraylist of modules to be scheduled
	 * @param rooms arraylist of rooms to be used
	 * @param examPeriodLength length of exam period
	 * @throws IllegalArgumentException exception containing reason schedule cannot be made 
	 */
	private void checkValidityOfData(ArrayList<Module> modules, ArrayList<Room> rooms, int examPeriodLength) throws IllegalArgumentException{
		if (checkAllRoomTypes(modules,rooms )==false){
			throw new IllegalArgumentException("Not all modules type in the database have a room type");
		}
		
		if (checkEnoughTime(modules,rooms,examPeriodLength)==false){
			throw new IllegalArgumentException("Not enough time in the specified examination time with the rooms in the database to schedule all the exams");
		}
		
		checkAdvancedTime(modules,rooms,examPeriodLength);
		
		if (checkRoomCapacity(modules,rooms)==false){
			throw new IllegalArgumentException("The largest room is smaller than the largest exam");
		}
	}
	
	/** checks if all module room types are in rooms specified.
	 * @param modules arraylist of modules to be scheduled
	 * @param rooms arraylist of rooms to be used
	 * @return false if not, true otherwise
	 */
	private boolean checkAllRoomTypes(ArrayList<Module> modules, ArrayList<Room> rooms){
		Set<String> roomTypes = new HashSet<String>();
		Set<String> moduleTypes = new HashSet<String>();
		
		for(Module module:modules){
			moduleTypes.add(module.getType());
		}
		
		for(Room room:rooms){
			roomTypes.add(room.getRoomType());
		}
		
		if (roomTypes.containsAll(moduleTypes)){
			return true;
		}else{
			return false;
		}	
	}
	
	/** checks if enough time across all rooms to schedule all exams.
	 * @param modules arraylist of modules to be scheduled
	 * @param rooms arraylist of rooms to be used
	 * @param examPeriodLength length of exam period.
	 * @return false if not, true otherwise
	 */
	private boolean checkEnoughTime(ArrayList<Module> modules, ArrayList<Room> rooms,int examPeriodLength){
		int roomTime=0;
		int moduleTime=0;
		
		for(Module module:modules){
			moduleTime=moduleTime+module.getExamLength();
		}
		
		for(Room room:rooms){			
			roomTime=roomTime+(room.getRoomEnd()-room.getRoomStart());
		}
		
		if (moduleTime>(roomTime*examPeriodLength)){
			return false;
		}else{
			return true;
		}
	}	
	
	/** Checks if enough time per room type to schedule all exams of that type
	 * @param modules arraylist of modules to be scheduled
	 * @param rooms arraylist of rooms to be used
	 * @param examPeriodLength length of exam period.
	 * @throws IllegalArgumentException exception containing reason schedule cannot be made 
	 */
	private void checkAdvancedTime(ArrayList<Module> modules, ArrayList<Room> rooms,int examPeriodLength) throws IllegalArgumentException{
		Set<String> roomTypes = new HashSet<String>();
		Set<String> moduleTypes = new HashSet<String>();
		
		for(Module module:modules){
			moduleTypes.add(module.getType());
		}
		
		for(Room room:rooms){
			roomTypes.add(room.getRoomType());
		}
		
		ArrayList<String> roomTimeType = new ArrayList<String>(roomTypes);
		ArrayList<String> moduleTimeType = new ArrayList<String>(moduleTypes);
		
		HashMap<String, Integer> roomTimeByType = new HashMap<String, Integer>();
		
		for(String room: roomTimeType){
			roomTimeByType.put(room, 0);
		}
		
		HashMap<String, Integer> moduleTimeByType = new HashMap<String, Integer>();
		
		for(String module: moduleTimeType){
			moduleTimeByType.put(module, 0);
		}
		
		int value=0;
		
		for(Module module:modules){
			value= moduleTimeByType.get(module.getType());
			value=value+module.getExamLength();
			moduleTimeByType.put(module.getType(), value);
		}
		
		for(Room room:rooms){
			value= roomTimeByType.get(room.getRoomType());
			value=value+(room.getRoomEnd()-room.getRoomStart());
			roomTimeByType.put(room.getRoomType(), value);
		}
		
		for (String module:moduleTypes){
			System.out.println(moduleTimeByType.get(module)+"  "+roomTimeByType.get(module)*examPeriodLength);
			if (moduleTimeByType.get(module)>(roomTimeByType.get(module)*examPeriodLength)){
				throw new IllegalArgumentException("Number of hours of exam scheduled in " + module +" room type is greater than number of hours of that type avialable!" );
			}
		}	
	}
	
	/** Checks room capcity ensuring that the max module size is less than the max room size.
	 * @param modules arraylist of modules to be scheduled
	 * @param rooms arraylist of rooms to be used
	 * @return false if room capacity < module size, true otherwise.
	 */
	private boolean checkRoomCapacity(ArrayList<Module> modules, ArrayList<Room> rooms){
		int maxRoomSize=0;
		int maxModuleSize=0;
		
		for(Module module:modules){
			if (maxModuleSize<module.getModuleSize()){
				maxModuleSize=module.getModuleSize();
			}
		}
		for(Room room:rooms){
			if (maxRoomSize<room.getCapacity()){
				maxRoomSize=room.getCapacity();
			}
		}
		if (maxModuleSize>maxRoomSize){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * algorithm of the program
	 * @param modules Arraylist of all modules to be scheduled
	 * @param rooms Arraylist of all rooms to be scheduled
	 * @param examPeriodLength the numberOfdays which the exams can be schedule in
	 * @return boolean True if generated succesfully, false if not
	 */
	public boolean generateSchedule(ArrayList<Module> modules, ArrayList<Room> rooms, int examPeriodLength) throws IllegalArgumentException {
		
		checkValidityOfData(modules,rooms,examPeriodLength);
		schedule= new Schedule(rooms, examPeriodLength);
		
		ArrayList<Module> sortedModules= new ArrayList<Module>(manageDupicateModules(modules));
		
		if (checkRoomCapacity(sortedModules,rooms)==false){
			throw new IllegalArgumentException("Clashed modules are too big for rooms");
		}
		
		beginScheduler(manageDupicateModules(modules));
		
		if (generatedSuccessfully=true){
			scheduledModules=modules;
		}
		return generatedSuccessfully;
	}
	
	/**
	 * Returns scheduled modules. Note, this MUST be ran after generate schedule.
	 * @return Arraylist of the scheduled modules
	 * @throws IllegalStateException if not ran after a schedule has been generated.
	 */
	public ArrayList<Module> getScheduledModules()throws IllegalArgumentException{
		if (generatedSuccessfully==false){
			throw new IllegalArgumentException("must generate schedule first");
		}
		return scheduledModules;
	}
	
	/**
	 * Generates a schedule and returns the now scheduled modules
	 * @param modules Arraylist of all modules to be scheduled
	 * @param rooms Arraylist of all rooms to be scheduled
	 * @param examPeriodLength the numberOfdays which the exams can be schedule in
	 * @return Arraylist of the scheduled module
	 * @throws IllegalArgumentException if schedule could not be generated.
	 */
	public ArrayList<Module> generateAndReturnSchedule(ArrayList<Module> modules, ArrayList<Room> rooms, int examPeriodLength) throws IllegalArgumentException{
		if (generateSchedule(modules,rooms,examPeriodLength)==true){
			return getScheduledModules();
		}else{
			throw new IllegalArgumentException("Cannot generate schedule");
		}
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
		/**
		 * begins the scheduler		
		 * @param managedModules Arraylist of modules, which have been clashed correctly.
		 */
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
		
		/**
		 * Attempts to schedule a module to the schedule
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
