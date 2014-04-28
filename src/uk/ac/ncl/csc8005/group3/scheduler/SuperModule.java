package uk.ac.ncl.csc8005.group3.scheduler;

import java.util.ArrayList;

import uk.ac.ncl.csc8005.group3.scheduler.Utils.Time;


/**
 * @author:  Denny S Antony & Luke McMahon 
 * Date: 28/04/2014
 */
public class SuperModule extends Module{
	private ArrayList<Module> clashedModules = new ArrayList<Module>(); //used to allow easy updating of orginal modules time, date, & room

	/**
	 * Constructor for objects of Supermodule
	 * @param modules arraylist of modules that share a similar questions so need to be made into a supermodule(Clashed Modules)
	 */
	public SuperModule(ArrayList<Module> modules) {
		super(modules);
		clashedModules = new ArrayList<Module>(modules);
	}
	

	public void setTime(Time time){
		super.setTime(time);
		
		for (Module module: clashedModules){
			module.setTime(time);
		}		
	}
	
	public void setRoom(String room){
		super.setRoom(room);
		
		for (Module module: clashedModules){
			module.setRoom(room);
		}	
	}
	
	public void setDayNumber(int day){
		super.setDayNumber(day);
		for (Module module: clashedModules){
			module.setDayNumber(day);
		}
	}

	@Override
	public String toString() {
		return super.toString()+" SuperModule [clashedModules=" + clashedModules + "]";
	}
}
