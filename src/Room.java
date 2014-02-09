
public class Room {
	
	private String roomNumber;
	private String roomType;
	private int seat;
	//private ArrayList<Module> modules;
	
	public Room(String roomNumber, String roomType, int seat)
	{
		this.roomNumber = roomNumber;
		this.roomType = roomType;
		this.seat = seat;
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
	
	public String toString()
	{
		return (roomNumber + roomType + seat);
	}
}
