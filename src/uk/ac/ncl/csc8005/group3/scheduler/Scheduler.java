package uk.ac.ncl.csc8005.group3.scheduler;

import java.util.*;
import java.io.*;

import uk.ac.ncl.csc8005.group3.scheduler.Utils.SortedArrayList;

public class Scheduler {
	private ArrayList<Module> modules = new ArrayList<Module>();
	private Stack<Module> unscheduledModules;

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
		//unscheduledModules = new Stack<Module>();
		System.out.println("Ran3");
		//manageDupicateModules();
		System.out.println("Ran4");
<<<<<<< HEAD
=======
		//unscheduledModules.addAll(modules);
		System.out.println("Ran5");
		//System.out.println(unscheduledModules.peek());
		beginScheduler();
		System.out.println("Ran6");

>>>>>>> 0f7eb197c9795c4763f540e4b25eafeea1f6d393
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

<<<<<<< HEAD
	

	/*public void addmodule(ArrayList<Module> unscheduled, Module previouslyScheduled) {
=======
/*
	public void addmodule(ArrayList<Module> unscheduled, Module previouslyScheduled) {
>>>>>>> 0f7eb197c9795c4763f540e4b25eafeea1f6d393
		// THIS IS AN INFINITE LOOP
		System.out.println(count);
		count = count + 1;
		Module previousModule = null;
int count = 0;
		if (unscheduled.isEmpty()==false) {
		
			Module module = unscheduled.();
			module.toString();

			try {
				if (previouslyScheduled != null) {
					unscheduled.push(previouslyScheduled);
				}
				schedule.scheduleModule(module);

			} catch (Exception e) {
				previousModule = schedule.removeLastModule();
				if (previousModule == null) {
					Module module2 = unscheduled.pop();
					unscheduled.push(module);
					unscheduled.push(module2);
				} else {
					unscheduled.push(module);
				}
			} finally {
				addmodule(unscheduled, previousModule);
			}
	}*/

				
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
