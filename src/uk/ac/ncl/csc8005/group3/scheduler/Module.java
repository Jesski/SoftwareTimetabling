package uk.ac.ncl.csc8005.group3.scheduler;

import uk.ac.ncl.csc8005.group3.scheduler.Utils.Time;
import java.util.*;

public class Module implements Comparable<Module>{
	private String id;
	private ArrayList<String> clashedModules; // other Modules that must be ran at same time
	private HashMap<String, Integer> coupledModules; // modules that cannot be ran on same day (ie other modules also taken by students on this module)													// this module)
	private double examLength;// length of examination
	private int moduleSize; // number of students in module
	private String type; // room type <----change to enum
	private Time time; // time exam is scheduled for.
	private int day;// the day the module has been scheduled for.
	private String room; //the room module has been scheduled for.

	/**
	 * @param id
	 * @param clashedModules
	 * @param coupledModules
	 * @param examLength
	 * @param moduleSize
	 * @param type
	 */
	public Module(String id, ArrayList<String> clashedModules, HashMap<String, Integer> coupledModules, double examLength, int moduleSize, String type) {
		if (moduleSize > 0) {
			this.id = id;
			this.clashedModules = new ArrayList<String>(clashedModules);
			this.coupledModules = new HashMap<String, Integer>(coupledModules);
			this.examLength = examLength;
			this.moduleSize = moduleSize;
			this.type = type;
		} else {
			throw new IllegalArgumentException("Module size is 0");
		}
	}

	/**
	 * @param modules
	 */
	Module(ArrayList<Module> modules) {
		this.id="";
		this.moduleSize=0;
		this.clashedModules = new ArrayList<String>();
		this.coupledModules = new HashMap<String, Integer>();
		this.examLength = 0;
		this.moduleSize = 0;
		
		for (Module module : modules) {
			this.id = module.getId() + this.id;
			this.moduleSize = module.getModuleSize() + this.moduleSize;
			this.coupledModules.putAll(module.getCoupledModules());
			if (module.getExamLength() > this.examLength) { // note, might need different room types as well.
				this.examLength = module.getExamLength();
			}
			type = module.getType();
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Module [id=" + id + ", clashedModules=" + clashedModules
				+ ", coupledModules=" + coupledModules + ", examLength="
				+ examLength + ", moduleSize=" + moduleSize + ", type=" + type
				+ ", time=" + time + ", day=" + day + ", room=" + room + "]";
	}

	/**
	 * Returns the moduels type
	 * @return type the module type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Returns the ArrayList of the modules clashed Modules
	 * @return clashed modules, and arrayList of teh clashed modules
	 */
	public ArrayList<String> getClashedModules() {
		return clashedModules;
	}

	//not sure if needed?
	/**
	 * 
	 * @param clashedModules
	 */
	public void setClashedModules(ArrayList<String> clashedModules) {
		this.clashedModules = clashedModules;

	}

	/**
	 * @return
	 */
	public HashMap<String, Integer> getCoupledModules() {
		return coupledModules;
	}

	//not sure if needed?
	/**
	 * @param coupledModules
	 */
	public void setCoupledModules(HashMap<String, Integer> coupledModules) {
		this.coupledModules = coupledModules;
	}

	//not sure if needed?
	/**
	 * @param examLength
	 */
	public void setExamLength(double examLength) {
		this.examLength = examLength;
	}

	/**
	 * @return
	 */
	public double getExamLength() {
		return examLength;
	}

	/**
	 * @return
	 */
	public int getModuleSize() {
		return moduleSize;
	}

	/**
	 * @return
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @param time
	 */
	public void setTime(Time time){
		this.time=time;		
	}
	
	/**
	 * @return
	 */
	public Time getTime(){
		return time;		
	}
	
	//not sure if needed?
	/**
	 * @param day
	 */
	public void setDayNumber(int day){
		this.day=day;		
	}
	
	/**
	 * @return
	 */
	public int getDayNumber(){
		return day;		
	}
	
	//not sure if needed?
	/**
	 * @param room
	 */
	public void setRoom(String room){
		this.room=room;		
	}
	
	//not sure if needed?
	/**
	 * @return
	 */
	public String getRoomName(){
		return room;		
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(examLength);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + moduleSize;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Module other = (Module) obj;
		if (Double.doubleToLongBits(examLength) != Double
				.doubleToLongBits(other.examLength))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (moduleSize != other.moduleSize)
			return false;
		return true;
	}

	

    /**
     * sets logic for comparison of two module objects.
     * Modules are compared and stored by size (number of pupils), with larger modules returning higher values.
     *  
     * @return 0 if objects are the same
     * @return <0 if in a.compareTo(b) a<b
     * @return >0 if in a.compareTo(b) a>b
     */
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Module module) {
		return (module.getModuleSize()-this.moduleSize);
	}
}
