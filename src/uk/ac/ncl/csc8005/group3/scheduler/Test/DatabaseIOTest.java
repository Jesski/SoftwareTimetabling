package uk.ac.ncl.csc8005.group3.scheduler.Test;

import java.sql.Connection;
import java.sql.Statement;

import org.junit.Test;

import uk.ac.ncl.csc8005.group3.scheduler.DatabaseIO;

public class DatabaseIOTest {

	@Test
	public void testDatabase() {
		
		DatabaseIO db = new DatabaseIO();
		System.out.print(db.getModules());
		System.out.print(db.getRooms());
	}

}
