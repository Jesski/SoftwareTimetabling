package uk.ac.ncl.csc8005.group3.scheduler;

import java.util.ArrayList;

import uk.ac.ncl.csc8005.group3.scheduler.Utils.Time;

public class SuperModule extends Module{
	private ArrayList<Module> clashedModules = new ArrayList<Module>(); //used to allow easy updating of orginal modules time, date, & room

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
