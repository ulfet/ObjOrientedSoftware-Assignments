package de.rwth.swc.group10;

public interface OOSCDateTimeInterface extends DateInterface {

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
}
