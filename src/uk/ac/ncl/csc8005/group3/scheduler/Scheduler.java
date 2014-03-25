package uk.ac.ncl.csc8005.group3.scheduler;

import java.util.*;
import java.io.*;


import uk.ac.ncl.csc8005.group3.scheduler.Module;
import uk.ac.ncl.csc8005.group3.scheduler.Schedule;
import uk.ac.ncl.csc8005.group3.scheduler.Utils.SortedArrayList;

public class Scheduler {
	private ArrayList<Module> modules = new ArrayList<Module>();

	// private DatabaseIO db;
	private Date examStart;
	private Date examEnd;
	private Schedule schedule = new Schedule();

	// private Schedule schedule = new Schedule(examStart, examEnd);

	public void addData() {
		ArrayList<String> clashedModules1 = new ArrayList<String>();
		ArrayList<String> clashedModules2 = new ArrayList<String>();
		ArrayList<String> clashedModules3 = new ArrayList<String>();
		ArrayList<String> clashedModules4 = new ArrayList<String>();
		ArrayList<String> clashedModules5 = new ArrayList<String>();// other
		HashMap<String, Integer> coupledModules1 = new HashMap<String, Integer>();
		HashMap<String, Integer> coupledModules2 = new HashMap<String, Integer>();
		HashMap<String, Integer> coupledModules3 = new HashMap<String, Integer>();
		HashMap<String, Integer> coupledModules4 = new HashMap<String, Integer>();
		HashMap<String, Integer> coupledModules5 = new HashMap<String, Integer>();// modules
		clashedModules1.add("CSC8005");
		clashedModules5.add("CSC8001");
		clashedModules2.add("CSC8004");
		clashedModules4.add("CSC8002");
		coupledModules1.put("CSC8004", 10);
		coupledModules3.put("CSC8005", 10);
		modules.add(new Module("CSC8001", clashedModules1, coupledModules1,
				2.00, 15, "CMP"));
		modules.add(new Module("CSC8002", clashedModules2, coupledModules2,
				1.00, 35, "LCT"));
		modules.add(new Module("CSC8003", clashedModules3, coupledModules3,
				3.00, 100, "LCT"));
		modules.add(new Module("CSC8004", clashedModules4, coupledModules4,
				4.00, 21, "ART"));
		modules.add(new Module("CSC8005", clashedModules5, coupledModules5,
				2.00, 35, "CMP"));
	}

	public Scheduler() {
		// c db = new DatabaseIO(this.file);
		// c modules=new ArrayList<Module>(db.getModule());
		// c rooms= new ArrayList<Room>(db.getRooms());
		// c days=new ArrayList<Day>(); //<-- add size, remove arraylist
	}

	public void generateSchedule() {

		System.out.println("Ran1");
		addData();
		System.out.println("Ran2");
		
		System.out.println("Ran3");
		manageDupicateModules(modules);
		System.out.println("Ran4");

		System.out.println("Ran5");
		//System.out.println(unscheduledModules.peek());
		beginScheduler();
		System.out.println("Ran6");

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
			managedModules.add(new Module(modulesToBeClashedTogether)); //create the new super module from old
			modulesWithClashes.remove(tempModule); //remove module from to be clashed list as now clashed
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

				
		public void beginScheduler(){
			SortedArrayList<Module> unscheduledModules = new SortedArrayList<Module>();
			for (Module module: modules){
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
