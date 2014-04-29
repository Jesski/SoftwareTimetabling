package uk.ac.ncl.csc8005.group3.scheduler;

//import uk.ac.ncl.csc8005.group3.scheduler.Utils.Time;
import java.util.*;

/**
 * @author:  Denny S Antony & Luke McMahon 
 * Date: 28/04/2014
 */
public class Module implements Comparable<Module>{
	private String id;
	private ArrayList<String> clashedModules; // other Modules that must be ran at same time
	private HashMap<String, Integer> coupledModules; // modules that cannot be ran on same day (ie other modules also taken by students on this module)													// this module)
	private int examLength;// length of examination
	private int moduleSize; // number of students in module
	private String type; // room type <----change to enum
	private int time; // time exam is scheduled for.
	private int day;// the day the module has been scheduled for.
	private String room; //the room module has been scheduled for.

	/**
	 * Constructor for objects of type module
	 * @param id - id of the module
	 * @param clashedModules- arraylist of modules that need to be clashed with this module(ran at the same time as the module)
	 * @param coupledModules- arraylist of modules that need to be couple with this module(cannot be ran at the same time as the module because the students do both these modules so they cannot be at the same place at the same time)
	 * @param examLength- the length of the exam for this module
	 * @param moduleSize- the number of people who do this module
	 * @param type- the type of room which the exam needs to be conducted in
	 */
	public Module(String id, ArrayList<String> clashedModules, HashMap<String, Integer> coupledModules, int examLength, int moduleSize, String type) {
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
	 * Constructor for objects of type module, used to create super modules, by collating several together
	 * @param modules- ArrayList of modules
	 */
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
	 * Returns the modules type
	 * @return type the module type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Returns the ArrayList of the modules clashed Modules
	 * @return clashed modules, and arrayList of the clashed modules
	 */
	public ArrayList<String> getClashedModules() {
		return clashedModules;
	}

	/**
	 * @return returns a hashmap of coupled modules with the module id and number of students in each module
	 */
	public HashMap<String, Integer> getCoupledModules() {
		return coupledModules;
	}

	/**
	 * @return the length of exam for this module
	 */
	public int getExamLength() {
		return examLength;
	}

	/**
	 * @return the number of students taking this module
	 */
	public int getModuleSize() {
		return moduleSize;
	}

	/**
	 * @return the id of the module
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * @param time 
	 */
	public void setTime(int time){
		this.time=time;		
	}
	
	/**
	 * @return
	 */
	public int getTime(){
		return time;		
	}
	
	//is needed
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
