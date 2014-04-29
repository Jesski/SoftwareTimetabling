package uk.ac.ncl.csc8005.group3.scheduler;

import java.util.*;

//import uk.ac.ncl.csc8005.group3.scheduler.Utils.Time;

/**
 * @author:  Denny S Antony & Luke McMahon 
 * Date: 28/04/2014
 */
public class Room {
	private String roomNumber;
	private String roomType;
	private ArrayList<Module> modules;
	private int roomStart; // startTime for using room, eg 9:00
	private int roomEnd; // end time for using room, eg 17:00
	private int timeLeftInRoom; // time left in room, eg if can use 9:00 till
									// 17:00, but already used 2 hours then
									// equals 6
	private int roomFireBreak; // time that must be between each exam end and
									// start, for this room. Default 0
	private int capacity;

	/**
	 * * Constructor for objects of type room
	 * @param roomNumber- of the room
	 * @param roomType- of the room-e.g computer lab, exam hall etc
	 * @param roomStart- the time the room is available from
	 * @param roomEnd- the time the room ends
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
		this.roomFireBreak = 1;
		this.capacity = capacity;
	}

	/**
	 * * Constructor for objects of type room with a fire break as well
	 * @param roomNumber- of the room
	 * @param roomType- of the room-e.g computer lab, exam hall etc
	 * @param roomStart- the time the room is available from
	 * @param roomEnd- the time the room ends
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

	/**
	 * @param number
	 */
	public void setRoomNumber(String number) {
		roomNumber = number;
	}

	/**
	 * @param type
	 */
	public void setRoomType(String type) {
		roomType = type;
	}

	/**
	 * @param module that we are trying to schedule into this room
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

	/**
	 * @return
	 */
	public String getRoomType() {
		return roomType;
	}

	/**
	 * @return
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

	public int getCapacity() {
		return capacity;
	}
}
