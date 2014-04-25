package uk.ac.ncl.csc8005.group3.scheduler;

import java.util.*;

import uk.ac.ncl.csc8005.group3.scheduler.Utils.Time;

public class Room {
	private String roomNumber;
	private String roomType;
	private ArrayList<Module> modules;
	private double roomStart; // startTime for using room, eg 9:00
	private double roomEnd; // end time for using room, eg 17:00
	private double timeLeftInRoom; // time left in room, eg if can use 9:00 till
									// 17:00, but already used 2 hours then
									// equals 6
	private double roomFireBreak; // time that must be between each exam end and
									// start, for this room. Default 1 hour
	private int capacity;

	/**
	 * @param roomNumber
	 * @param roomType
	 * @param roomStart
	 * @param roomEnd
	 * @param capacity
	 */
	public Room(String roomNumber, String roomType, double roomStart,
			double roomEnd, int capacity) {
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
	 * @param roomNumber
	 * @param roomType
	 * @param roomStart
	 * @param roomEnd
	 * @param roomFireBreak
	 * @param capacity
	 */
	public Room(String roomNumber, String roomType, double roomStart,
		double roomEnd, double roomFireBreak, int capacity) {
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
	 * @param module
	 * @return
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
		module.setTime(Time.doubleAsTime(roomStart+((roomEnd - roomStart)-timeLeftInRoom))); // sets scheduled time in this room
		module.setRoom(roomNumber);
		
		timeLeftInRoom = timeLeftInRoom - module.getExamLength()
				- roomFireBreak;
		return true;
	}

	/**
	 * @return
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
	 * @return
	 */
	public double getRoomStart() {
		return roomStart;
	}

	/**
	 * @return
	 */
	public double getRoomEnd() {
		return roomEnd;
	}

	/**
	 * @return
	 */
	public double getTimeLeftInRoom() {
		return timeLeftInRoom;
	}

	/**
	 * @param module
	 * @return
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
}
