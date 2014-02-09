package uk.ac.ncl.csc8005.group3.scheduler;

import java.util.*;
import java.io.*;


public class Scheduler {
	private ArrayList<Module> modules;
	private ArrayList<Room> rooms;
	private ArrayList<Day> days;
	private Date examStart;
	private Date examEnd;
	private int numberOfDays;
	
	private File file;
	private DatabaseIO db;
	
	public Scheduler(String file, Date examStart, Date examEnd){
		this.file= new File(file);
		db = new DatabaseIO(this.file);
		modules=new ArrayList<Module>(db.getModule());
	    rooms= new ArrayList<Room>(db.getRooms());
	    days=new ArrayList<Day>();
	    numberOfDays=examStart-examEnd;
	}
	
	public Schedule generateSchedule(){
		
		
	}
	
	
}
