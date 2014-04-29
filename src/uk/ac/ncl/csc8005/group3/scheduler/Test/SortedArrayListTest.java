package uk.ac.ncl.csc8005.group3.scheduler.Test;

import static org.junit.Assert.*;

import org.junit.Test;

import uk.ac.ncl.csc8005.group3.scheduler.Utils.SortedArrayList;

public class SortedArrayListTest {

	@Test
	public void testAddE() {
		SortedArrayList<Integer> sortedList= new SortedArrayList<Integer>();
		int i =0;
		sortedList.add(5);
		sortedList.add(4);
		sortedList.add(3);
		sortedList.add(2);
		sortedList.add(1);
		
		for (Integer integer:sortedList){
			i=i+1;
			assertTrue(integer== i);
		}
		
		sortedList.add(-1);
		assertTrue(sortedList.get(0)==-1);
		
	}

}
