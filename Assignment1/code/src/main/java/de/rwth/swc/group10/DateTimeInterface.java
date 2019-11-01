package de.rwth.swc.group10;

public interface DateTimeInterface extends DateInterface {

	public static final int DATETYPE_HOUR = 3;
    public static final int DATETYPE_MINUTE = 4;
    public static final int DATETYPE_SECOND = 5;
    
	public void setDateTime(int year, int month, int day, int hour, int minute, int second);
	public void setTime(int hour, int minute, int second);
    public void setHour(int hour);
    public void setMinute(int minute);
    public void setSecond(int second);

    public int getHour();
    public int getMinute();
    public int getSecond();

    public void addSeconds(int secondsToAdd);
    public void addMinutes(int minutesToAdd);
    public void addHours(int hoursToAdd);
    
    public void removeSeconds(int secondsToRemove);
    public void removeMinutes(int minutesToRemove);
    public void removeHours(int hoursToRemove);
    
    public int timeBetween(int type, DateTimeInterface otherDate);
}
