package uk.ac.ncl.csc8005.group3.scheduler.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

import uk.ac.ncl.csc8005.group3.scheduler.Module;

public class DayTest {

	@Test
	public void testAddModule() {
		fail("Not yet implemented");
	}

	@Test
	public void testCheckCoupledModules() {
		ArrayList<String> clashedModuels1 = new ArrayList<String>();
		clashedModuels1.add("CSC8001");
		clashedModuels1.add("CSC8002");
		HashMap<String, Integer> coupledModules1 = new HashMap<String, Integer>();
		coupledModules1.put("CSC8004", 10);

		Module testModule1 = new Module("CSC8004", clashedModuels1,
				coupledModules1, 1.00, 15, "CMP");
		
		
		ArrayList<String> clashedModuels2 = new ArrayList<String>();
		clashedModuels1.add("CSC8001");
		clashedModuels1.add("CSC8002");
		HashMap<String, Integer> coupledModules2 = new HashMap<String, Integer>();
		coupledModules1.put("CSC8004", 10);

		Module testModule2 = new Module("CSC8004", clashedModuels2,	coupledModules2, 1.00, 15, "CMP");
		
	}

	@Test
	public void testRemoveModule() {
		fail("Not yet implemented");
	}

	@Test
	public void testLookFor() {
		fail("Not yet implemented");
	}

}
