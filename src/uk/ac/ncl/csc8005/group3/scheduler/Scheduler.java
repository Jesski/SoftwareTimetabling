package uk.ac.ncl.csc8005.group3.scheduler;

import java.util.*;
import java.io.*;
import java.text.*;


public class Scheduler {
	private ArrayList<Module> modules;
	private ArrayList<Room> rooms;
	private ArrayList<Day> days;
	private Date examStart;
	private Date examEnd;
	private int numberOfDays;
	
	private File file;
	private DatabaseIO db;
	
	final long millisecondsInADay = 1000 * 60 * 60 * 24;
	
	public Scheduler(String file, String examStart, String examEnd){
		this.file= new File(file);
		db = new DatabaseIO(this.file);
		modules=new ArrayList<Module>(db.getModule());
	    rooms= new ArrayList<Room>(db.getRooms());
	    days=new ArrayList<Day>();	
	    this.examStart=new SimpleDateFormat("d MMMM, yyyy", Locale.ENGLISH).parse(examStart);
	    this.examEnd=new SimpleDateFormat("d MMMM, yyyy", Locale.ENGLISH).parse(examEnd);;
	    
	    numberOfDays = (int) ((this.examStart.getTime() - this.examEnd.getTime())/ millisecondsInADay );
	}
	
	public Schedule generateSchedule(){
		
		
	}
	
	
}
