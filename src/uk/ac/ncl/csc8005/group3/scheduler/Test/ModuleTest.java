package uk.ac.ncl.csc8005.group3.scheduler.Test;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import uk.ac.ncl.csc8005.group3.scheduler.Module;

public class ModuleTest {
	@Test
	public void testCompareTo() {
		ArrayList<String> clashedModuels1 = new ArrayList<String>();
		clashedModuels1.add("CSC8001");
		clashedModuels1.add("CSC8002");
		
		HashMap<String, Integer> coupledModules1 = new HashMap<String, Integer>();
		coupledModules1.put("CSC8003", 10);
		
		ArrayList<String> clashedModuels2 = new ArrayList<String>();
		
		HashMap<String, Integer> coupledModules2 = new HashMap<String, Integer>();

		Module testModule = new Module("CSC8003", clashedModuels1,coupledModules1, 60, 15, "CMP");
		Module testModule2 = new Module("CSC8005", clashedModuels2,coupledModules2, 60, 15, "EXE");
		
		assert(testModule.compareTo(testModule2)<0);
		assert(testModule2.compareTo(testModule)>0);
		assert(testModule2.compareTo(testModule2)==0);
		
		
	}
}
