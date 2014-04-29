package uk.ac.ncl.csc8005.group3.scheduler;

import java.util.*;

/**
 * @authors:  Denny S Antony & Luke McMahon 
 * Date: 28/04/2014
 */
public class Room {
	private String roomNumber;
	private String roomType;
	private ArrayList<Module> modules;
	private int roomStart; // startTime for using room in minutes from midnight
	private int roomEnd; // end time for using room in minutes from midnight
	private int timeLeftInRoom; // time left in room in minutes
	private int roomFireBreak; // time that must be between each exam end and start Default 0
	private int capacity; // capacity of the room

	/**
	 * Constructor for objects of type room
	 * @param roomNumber- of the room
	 * @param roomType- of the room-e.g computer lab, exam hall etc
	 * @param roomStart- startTime for using room in minutes from midnight
	 * @param roomEnd- end time for using room in minutes from midnight
	 * @param capacity- of the room
	 */
	public Room(String roomNumber, String roomType, int roomStart,
			int roomEnd, int capacity) {
		this.roomNumber = roomNumber;
		this.roomType = roomType;
		this.modules = new ArrayList<Module>();
		this.roomStart = roomStart;
		this.roomEnd = roomEnd;
		timeLeftInRoom = roomEnd - roomStart;
		this.roomFireBreak = 0;
		this.capacity = capacity;
	}

	/**
	 * Constructor for objects of type room with a fire break as well
	 * @param roomNumber- of the room
	 * @param roomType- of the room-e.g computer lab, exam hall etc
	 * @param roomStart- startTime for using room in minutes from midnight
	 * @param roomEnd- end time for using room in minutes from midnight
	 * @param capacity- of the room
	 * @param roomFireBreak- the free time between two exams
	 */
	public Room(String roomNumber, String roomType, int roomStart,
		int roomEnd, int roomFireBreak, int capacity) {
		this.roomNumber = roomNumber;
		this.roomType = roomType;
		this.modules = new ArrayList<Module>();
		this.roomStart = roomStart;
		this.roomEnd = roomEnd;
		timeLeftInRoom = roomEnd - roomStart;
		this.roomFireBreak = roomFireBreak;
		this.capacity = capacity;
	}

	/*
	 * @param number
	 
	private void setRoomNumber(String number) {
		roomNumber = number;
	}
*/
	/*
	 * @param type
	 
	private void setRoomType(String type) {
		roomType = type;
	}
*/
	/** Attempt to add module to the room
	 * @param module is trying to be schedule into this room
	 * @return true if this module can be scheduled in this room
	 */
	public boolean addModule(Module module) {
		// need more conditions adding!

		if (roomType.equals(module.getType())) {
		} else {
			// wrong room type
			return false;
		}

		if (module.getModuleSize() > capacity) {
			// Not a big enough room
			return false;
		}

		if (module.getExamLength() + roomFireBreak > timeLeftInRoom) {
			// Not enough time left in room"
			return false;
		}

		modules.add(module);
		module.setTime(roomStart+((roomEnd - roomStart)-timeLeftInRoom)); // sets scheduled time in this room
		module.setRoom(roomNumber);
		
		timeLeftInRoom = timeLeftInRoom - module.getExamLength()- roomFireBreak;
		return true;
	}

	/**
	 * @return roomNumber of the room
	 */
	public String getRoomNumber() {
		return roomNumber;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Room [roomNumber=" + roomNumber + ", roomType=" + roomType
				+ ", modules=" + modules + ", roomStart=" + roomStart
				+ ", roomEnd=" + roomEnd + ", timeLeftInRoom=" + timeLeftInRoom
				+ ", roomFireBreak=" + roomFireBreak + ", capacity=" + capacity
				+ "]";
	}

	/** Return the room type
	 * @return the room type
	 */
	public String getRoomType() {
		return roomType;
	}

	/**get modules scheduled to the room
	 * @return modules csheduled to this room  
	 */
	public ArrayList<Module> getModules() {
		return new ArrayList<Module>(modules);
	}

	/**
	 * @return the start time of the room
	 */
	public int getRoomStart() {
		return roomStart;
	}

	/**
	 * @return the end time of the room
	 */
	public int getRoomEnd() {
		return roomEnd;
	}

	/**
	 * @return the time free in the room which can all be used to schedule further exams
	 */
	public int getTimeLeftInRoom() {
		return timeLeftInRoom;
	}

	/**
	 * @param module that is trying to be removed from the schedule for this room
	 * @return true if the module has been removed successfully
	 */
	public boolean removeModule(Module module) {
		if (modules.contains(module)){
			modules.remove(module);
			timeLeftInRoom = timeLeftInRoom + module.getExamLength() + roomFireBreak;
			return true;
		}else{
			return false;
		}
	}

	/**
	 * @return the capacity of the room
	 */
	public int getCapacity() {
		return capacity;
	}
}
