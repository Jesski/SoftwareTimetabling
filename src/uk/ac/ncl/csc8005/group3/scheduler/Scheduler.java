package uk.ac.ncl.csc8005.group3.scheduler;

import java.util.*;
import java.io.*;
import java.text.*;


public class Scheduler {
	private ArrayList<Module> modules; 	
	private ArrayList<Room> rooms;
	private ArrayList<Day> days;
//	private Date examStart;
//	private Date examEnd;
//	private int numberOfDays;
	
	private File file;
	private DatabaseIO db;
	
	final long millisecondsInADay = 1000 * 60 * 60 * 24;
	
	public Scheduler(String file){
	//public Scheduler(String file, String examStart, String examEnd){
		
		ArrayList<String> temp;
		
		this.file= new File(file);
		
//c		db = new DatabaseIO(this.file);
		
//c		modules=new ArrayList<Module>(db.getModule());
//c	    rooms= new ArrayList<Room>(db.getRooms());
//c	    days=new ArrayList<Day>();	//<-- add size, remove arraylist
	    
	    //this.examStart=new SimpleDateFormat("d MMMM, yyyy", Locale.ENGLISH).parse(examStart);
	    //this.examEnd=new SimpleDateFormat("d MMMM, yyyy", Locale.ENGLISH).parse(examEnd);;
	    //numberOfDays = (int) ((this.examStart.getTime() - this.examEnd.getTime())/ millisecondsInADay ); // <---check
	    
	}

	
	public void manageDupicateModules(){
	    //creating Lookup table for Id
	    HashMap<String, Integer> lookUpId;
		lookUpId= new HashMap<String, Integer>();
	    
	    int count=0;
	    for(Module module:modules){
	    	lookUpId.put(module.getId(),count);
	    	count++;
	    }
	    
		ArrayList<Integer> modulePos = new ArrayList<Integer>();
		ArrayList<Module> clashedModules= new ArrayList<Module>();
		ArrayList<Module> newModules= new ArrayList<Module>();
		ArrayList<Boolean> alreadyRan= new ArrayList<Boolean>();
		ArrayList<String> tempModuleIds= new ArrayList<String>();
		
		
		for(int x=0; x<modules.size();x++){
				alreadyRan.add(false);
		}
	    		
		for(int x=0; x<modules.size();x++){
			if(alreadyRan.get(x)==false){
				tempModuleIds.addAll(modules.get(x).getClashedModules());
	    	if (tempModuleIds.size()>0){
	    		for(String id:tempModuleIds){
	    			modulePos.add(lookUpId.get(id));
	    			alreadyRan.set(lookUpId.get(id), true);
	    		}
	    		for (Integer y:modulePos){
	    			clashedModules.add(modules.get(y));
	    		}
	    			newModules.add(new Module(clashedModules));	
	    		}
			}else{}	
		}
		
		Iterator<Module> itr= modules.iterator();
		
		while (itr.hasNext()){
			Module ItrModule=itr.next();
			if(ItrModule.getClashedModules().size()>0){
				itr.remove();
			}
		}
		
		modules.addAll(newModules);
	} 
		
	
	
	public void generateSchedule(){
		ArrayList<String> unScheduledErrors = new ArrayList<String>();
		for (Module module:modules){ //these can be swapped?
			try{		
				for (Day day:days){ // check.
					try{
						day.addModule(module);
					} catch(Exception e){
					}			
				}
			}catch(Exception e){
				unScheduledErrors.add(module.getId());
			}
			
		}
	}	
}
	
	
