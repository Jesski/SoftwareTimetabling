package uk.ac.ncl.csc8005.group3.scheduler;

import java.util.*;
import java.io.*;


import uk.ac.ncl.csc8005.group3.scheduler.Module;
import uk.ac.ncl.csc8005.group3.scheduler.Schedule;
import uk.ac.ncl.csc8005.group3.scheduler.Utils.SortedArrayList;

public class Scheduler {
	private ArrayList<Module> modules = new ArrayList<Module>();
	private ArrayList<Room> rooms = new ArrayList<Room>();
	
	private Schedule schedule; 
	
	public Scheduler() {
		// c db = new DatabaseIO(this.file);
		// c modules=new ArrayList<Module>(db.getModule());
		// c rooms= new ArrayList<Room>(db.getRooms());
		// c days=new ArrayList<Day>(); //<-- add size, remove arraylist
	}


	public void generateSchedule(ArrayList<Module> modules, ArrayList<Room> rooms, int examPeriodLength) {

		System.out.println("Ran1");
		schedule= new Schedule(rooms, examPeriodLength);
		System.out.println("Ran2");
		manageDupicateModules(modules);
		System.out.println("Ran3");
		beginScheduler(manageDupicateModules(modules));
		System.out.println("Ran4");

	}
	
	///// possible other method for managing modules -- not sure if it works either
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
			managedModules.add(new Module(modulesToBeClashedTogether)); //create the new super module from old
			modulesWithClashes.remove(tempModule); //remove module from to be clashed list as now clashed
			
			if (modulesWithClashes.size()==0){ // if ran for each module in modules list of clashed modules, then finished this module so exit loop.
				finishedClashing=true;
			}				
		}
		
		return managedModules;
	}

	public void manageDupicateModules() {
		HashMap<String, Integer> lookUpId=new HashMap<String, Integer>();
		HashMap<String,Boolean> alreadRan=new HashMap<String,Boolean>();
		ArrayList<String> tempModuleIds = new ArrayList<String>();
		
		for (int x = 0; x < modules.size(); x++) {
			if (modules.get(x).getClashedModules().size() > 0) {
				lookUpId.put(modules.get(x).getId(), x);	
				alreadRan.put(modules.get(x).getId(), false);
			}
		}
		
		String superModuleName;
		double ExamLength;
		int moduleSize;
		
		String Roomtype;
		
		ArrayList<String> clashedModules3 = new ArrayList<String>();
		HashMap<String, Integer> superCoupledModules=new HashMap<String, Integer>();	
		HashMap<String, Integer> patch=new HashMap<String, Integer>();	
		
		for (Map.Entry<String, Integer> entry : lookUpId.entrySet()) {
			
		    String key = entry.getKey();
		    Integer value = entry.getValue();
		    
		    if(alreadRan.get(key)==false){
		    	superModuleName=key+" ";
		    	
		    	Module tempModule=modules.get(value);
		    	ExamLength=tempModule.getExamLength();
		    	moduleSize=tempModule.getModuleSize();
		    	Roomtype=tempModule.getType();
		    	tempModuleIds=tempModule.getClashedModules();
		    	
		    	for(String d:tempModuleIds){
		    		tempModule=modules.get(lookUpId.get(d));
		    		superModuleName+=d+" ";
		    		moduleSize+=tempModule.getModuleSize();
		    		patch=tempModule.getCoupledModules();
		    		Map<String,Integer> tmp = new HashMap(patch);
		    		tmp.keySet().removeAll(superCoupledModules.keySet());
		    		superCoupledModules.putAll(tmp);
		    		//what if two coupledMdules are same
		    		alreadRan.put(d, true);
		    	}
		    	modules.add(new Module(superModuleName, clashedModules3, superCoupledModules,
						ExamLength, moduleSize, Roomtype));	//change clashedModules3
		    }
		}
		

		Iterator<Module> itr = modules.iterator();

		while (itr.hasNext()) {
			Module ItrModule = itr.next();
			if (ItrModule.getClashedModules().size() > 0) {
				itr.remove();
			}
		}
	}

				
		public void beginScheduler(ArrayList<Module> managedModules){
			SortedArrayList<Module> unscheduledModules = new SortedArrayList<Module>();
			for (Module module: managedModules){
				unscheduledModules.add(module);
			}
			
			for (Module module: unscheduledModules){
				System.out.println(module.toString());
			}
			
			addmodules(unscheduledModules, null, false, 0);
			
			System.out.println(schedule);
		}
		
		
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
				System.out.println("Generated schedule");
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
					}catch(ArrayIndexOutOfBoundsException e){ //if this error thrown, then schedule is empty, and attempt at scheduling first module has been exhausted
						return false;
					}
					addmodules(unscheduledModules, null, true,count); //call self with null and looping (acts like while loop)
				}
			}else{
				unscheduledModules.remove(moduleToSchedule); //removed now scheduled module from unscheduled arrayList;
				addmodules(unscheduledModules, null, false,count); //call self with new unscheduled list
			}
			System.out.println("could not generate schedule");
			return false;				
	}	
}
