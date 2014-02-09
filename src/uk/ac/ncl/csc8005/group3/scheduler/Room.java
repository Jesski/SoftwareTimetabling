package uk.ac.ncl.csc8005.group3.scheduler;
import java.util.*;
public class Room {
	private String roomNumber;
	private String roomType;
	private int seat;
	private ArrayList<Module> modules;
	private double roomStart; //startTime for using room, eg 9:00
	private double roomEnd; //end time for using room, eg 17:00
	private double timeLeftInRoom; // time left in room, eg if can use 9:00 till 17:00, but already used 2 hours then equals 6  
	private double roomFireBreak; //time that must be between each exam end and start, for this room.
	private int capacity;

 	public Room(String roomNumber, String roomType, int seat, double roomStart, double roomEnd, double roomFireBreak, int capacity)
	{
		this.roomNumber = roomNumber;
		this.roomType = roomType;
		this.seat = seat;
		this.modules=new ArrayList<Module>();
		this.roomStart=roomStart;
		this.roomEnd=roomEnd;
		timeLeftInRoom= roomEnd-roomStart;
		this.roomFireBreak=roomFireBreak;
		this.capacity=capacity;
	}

	public void setRoomNumber(String number)
	{
		roomNumber = number;
	}
	public void setRoomType(String type)
	{
		roomType = type;
	}
	public void setSeat(int amount)
	{
		seat = amount;
	}
	public void addModule(Module module){
		if (module.getModuleSize()<capacity){
			if (module.getExamLength()+roomFireBreak < timeLeftInRoom){
				modules.add(module);
				timeLeftInRoom= timeLeftInRoom - module.getExamLength();
			}else{
				throw new IllegalArgumentException("Not enough time left in room");
			}
		}else{
			throw new IllegalArgumentException("Not a big enough room");
		}
	}
	
	/*--- Get methods ---*/
	
 	public String getRoomNumber()
	{
		return roomNumber;
	}
	public String getRoomType()
	{
		return roomType;
	}
	public int getSeat()
	{
		return seat;
	}
	public ArrayList<Module> getModules(){
		return new ArrayList<Module>(modules);
	}
	
	public double getRoomStart() {
		return roomStart;
	}
	public double getRoomEnd() {
		return roomEnd;
	}
	public double getTimeLeftInRoom(){
		return timeLeftInRoom;
	}

	
	@Override
	public String toString() {
		return "Room [roomNumber=" + roomNumber + ", roomType=" + roomType
				+ ", seat=" + seat + ", modules=" + modules + ", roomStart="
				+ roomStart + ", roomEnd=" + roomEnd + ", timeLeftInRoom="
				+ timeLeftInRoom + ", roomFireBreak=" + roomFireBreak + "]";
	}
	

	
}
