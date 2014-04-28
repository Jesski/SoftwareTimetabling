package uk.ac.ncl.csc8005.group3.scheduler.Test;

import java.util.Calendar;

import org.junit.Test;

import uk.ac.ncl.csc8005.group3.scheduler.DatabaseIO;

public class DatabaseIOTest {

	@Test
	public void testDatabase() {
		

		
		


		DatabaseIO db = new DatabaseIO();

		Calendar cal = Calendar.getInstance();
		java.sql.Date date=new java.sql.Date(cal.getTime().getTime());

		try{
		db.openDatabase();
		}
		catch (Exception e){
		}
		
		db.deleteTable();
		db.writeToDB("moduleID", 5.0, 1000, "room", date);
		db.writeToDB("moduleID2", 5.0, 1000, "room", date);
		
		System.out.println("hello");
	}

}
