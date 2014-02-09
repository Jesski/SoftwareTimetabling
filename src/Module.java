import java.util.*;

public class Module {
	private int id;
	private ArrayList<Integer> clashedModules; // other Modules that must be ran at same time
	private ArrayList<Integer> coupledModules; // modules that cannot be ran on same day (ie other modules also taken by students on this module)
	private double examLength;// length of examination
	private int moduleSize; //number of students in module
	
	public Module(Integer id, ArrayList<Integer> clashedModules, ArrayList<Integer> coupledModules, double examLength, int moduleSize){
		if (moduleSize>0){
			this.id=id;
			this.clashedModules= new ArrayList<Integer>(clashedModules);
			this.coupledModules= new ArrayList<Integer>(coupledModules);
			this.examLength= examLength;
			this.moduleSize=moduleSize;
		}else{
			throw new IllegalArgumentException("Module size is 0");
		}
	}
	
	public ArrayList<Integer> getClashedModules() {
		return new ArrayList<Integer>(clashedModules);//defensive programming
	}
	public void setClashedModules(ArrayList<Integer> clashedModules) {
		this.clashedModules = clashedModules;
		
	}	
	public void addClashedModule(Integer id){
		clashedModules.add(id);
	}
	
	public ArrayList<Integer> getCoupledModules() {
		return  new ArrayList<Integer>(coupledModules);//defensive programming
	}
	public void setCoupledModules(ArrayList<Integer> coupledModules) {
		this.coupledModules = coupledModules;
	}
	public void addCoupledModule(Integer id){
		coupledModules.add(id);
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
	
	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Module [id=" + id + ", clashedModules=" + clashedModules
				+ ", coupledModules=" + coupledModules + ", examLength="
				+ examLength + ", moduleSize=" + moduleSize + "]";
	}

	
}
