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

	public Module(ArrayList<Module> modules) {
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

	@Override
	public String toString() {
		return "Module [id=" + id + ", clashedModules=" + clashedModules
				+ ", coupledModules=" + coupledModules + ", examLength="
				+ examLength + ", moduleSize=" + moduleSize + ", type=" + type
				+ ", time=" + time + ", day=" + day + ", room=" + room + "]";
	}

	public String getType() {
		return type;
	}

	public ArrayList<String> getClashedModules() {
		return clashedModules;
	}

	public void setClashedModules(ArrayList<String> clashedModules) {
		this.clashedModules = clashedModules;

	}

	public void addClashedModule(String id) {
		clashedModules.add(id);
	}

	public HashMap<String, Integer> getCoupledModules() {
		return coupledModules;
	}

	public void setCoupledModules(HashMap<String, Integer> coupledModules) {
		this.coupledModules = coupledModules;
	}

	public void setExamLength(double examLength) {
		this.examLength = examLength;
	}

	public double getExamLength() {
		return examLength;
	}

	public int getModuleSize() {
		return moduleSize;
	}

	public String getId() {
		return id;
	}
	
	public void setTime(Time time){
		this.time=time;		
	}
	
	public Time getTime(){
		return time;		
	}
	
	public void setDayNumber(int day){
		this.day=day;		
	}
	
	public int getDayNumber(){
		return day;		
	}
	
	public void setRoom(String room){
		this.room=room;		
	}
	
	public String getRoomName(){
		return room;		
	}
	

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
	@Override
	public int compareTo(Module module) {
		return (module.getModuleSize()-this.moduleSize);
	}
}
