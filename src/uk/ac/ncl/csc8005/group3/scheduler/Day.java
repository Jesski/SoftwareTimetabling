package uk.ac.ncl.csc8005.group3.scheduler;
import java.util.*;

public class Day {
	private ArrayList<Room> rooms;
	private HashSet<Module> scheduledModules;

	public Day(ArrayList<Room> rooms){
		this.rooms= new ArrayList<Room>(rooms);
	}
	
	public void addModule(Module module) throws IllegalArgumentException  {
		boolean scheduled=false;
		int count=0;
		
		//check not already scheduled!
		for (Module scheduledModule:scheduledModules){
			if (scheduledModule.equals(module)){
				throw new IllegalArgumentException("Already scheduled!");// Might need different exception.
			}
		}
			
		//try to add module to each room.. without clashes. If cannot then throw exception.
		while (scheduled==false){
			// needs to take into account room sizes.
			try{
				rooms.get(count).addModule(module);	
				scheduledModules.add(module);
				scheduled=true; // update scheduled files.
			}
			catch(IllegalArgumentException e)
			{
			}
			if (count>(rooms.size()-1)){
				throw new IllegalArgumentException("Not possible to schedule");
			}	
			count=count+1;
				
		}
		
	}
	
}
