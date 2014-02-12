package uk.ac.ncl.csc8005.group3.scheduler;
import java.util.*;

public class Module {
	private String id;
	private ArrayList<String> clashedModules; // other Modules that must be ran at same time 
	private Map<String,Integer> ModuleDetails= new HashMap<String,Integer>(); // modules that cannot be ran on same day (ie other modules also taken by students on this module)
	private double examLength;// length of examination
	private int moduleSize; //number of students in module
	
	public Module(String id, ArrayList<String> clashedModules, HashMap<String,Integer> ModuleDetails, double examLength, int moduleSize){
		if (moduleSize>0){
			this.id=id;
			this.clashedModules= new ArrayList<String>(clashedModules);
			this.ModuleDetails= new HashMap<String,Integer>();
			this.examLength= examLength;
			this.moduleSize=moduleSize;
		}else{
			throw new IllegalArgumentException("Module size is 0");
		}
	}
	public ArrayList<String> getClashedModules() {
		return  (clashedModules);//defensive programming
	}
	public void setClashedModules(ArrayList<String> clashedModules) {
		this.clashedModules = clashedModules;
		
	}	
	public void addClashedModule(String id){
		clashedModules.add(id);
	}
	
	public Map<String,Integer> getModuleDetails() {
		return  ModuleDetails;//defensive programming
	}
	public void setModuleDetails(Map<String,Integer> coupledModules) {
		this.ModuleDetails = coupledModules;
	}
	
	public void setExamLength(double examLength){
		this.examLength=examLength;
	}
	public double getExamLength(){
		return examLength;
	}
	
	public int getModuleSize() {
		return moduleSize;
	}
	
	public String getId() {
		return id;
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

	//@Override
	//public String toString() {
		//return "Module [id=" + id + ", clashedModules=" + clashedModules
		//		+ ", coupledModules=" + coupledModules + ", examLength="
			//	+ examLength + ", moduleSize=" + moduleSize + "]";
	//}
	
	
	
}
