package uk.ac.ncl.csc8005.group3.scheduler.Utils;

/**
 * @author:  Denny S Antony & Luke McMahon 
 * Date: 28/04/2014
 
public class Time {
	private final int hour;
	private final int minute;
	
	public Time(int hour, int minute)throws IllegalArgumentException{
		
		if (hour<23 || hour>0){
			this.hour=hour;
		}else{
			throw new IllegalArgumentException("cannot have hours greater than 23 or hour less than 0");
		}
				
		if (minute<59|| minute>0){
			this.minute=minute;
		}else{
			throw new IllegalArgumentException("cannot have minute greater than 59 or minute less than 0");
		}
	}
	
	public int getFullTimeInMinutes(){
		return (hour*60+minute);
	}
	
	public int getHour(){
		return hour;
	}
	
	public int getMinute(){
		return minute;
	}
	
	public String minuteAsString(){
		if (minute<=9){
			return "0"+minute;
		}
		return ""+minute;
	}
	
	public String hourAsString(){
		if (hour<=9){
			return "0"+hour;
		}
		return ""+hour;
	}
	
	public String getTime(){
		return hourAsString()+":"+minuteAsString();
	}
	
	public static Time doubleAsTime(double doubleTime){
		int hours=(int)(doubleTime-doubleTime%1.0);
		int minutes=(int) (doubleTime%1.0*60);
		return new Time(hours, minutes);
	}

	@Override
	public String toString() {
		return getTime();
	}
	*/
	
	
}
