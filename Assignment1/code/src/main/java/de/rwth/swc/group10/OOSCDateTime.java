package de.rwth.swc.group10;

import static org.valid4j.Assertive.ensure;
import static org.valid4j.Assertive.require;

public class OOSCDateTime extends OOSCDate implements OOSCDateTimeInterface  {

	private int _hour;
    private int _minute;
    private int _second;
	
	@Override
	public void setDateTime(int year, int month, int day, int hour, int minute, int second) {
		setDate(year, month, day);
		setTime(hour, minute, second);
	}

	@Override
	public void setTime(int hour, int minute, int second) {
		require(invariant(), "inv-v");

        setHour(hour);
        setMinute(minute);
        setSecond(second);
        
        ensure(getHour() == hour, "The hour is not properly set");
        ensure(getMinute() == minute, "The minute is not properly set");
        ensure(getSecond() == second, "The second is not properly set");
        ensure(invariant(), "The invariant is not valid!");
		
	}

	@Override
	public void setHour(int hour) {
		require(invariant(), "inv-v");
        require(hour >= 0, "pre-v: The hour (%o) have to be greater or equals 0", hour);
        require(hour <= 23, "pre-v: The hour (%o) cannot be greater than 23", hour);

        _hour = hour;

        ensure(getHour() == hour);
        ensure(invariant(), "The invariant is not valid!");
	}

	@Override
	public void setMinute(int minute) {
		require(invariant(), "inv-v");
        require(minute >= 0, "pre-v: The minute (%o) have to be greater or equals 0", minute);
        require(minute <= 59, "pre-v: The minute (%o) cannot be greater than 59", minute);

        _minute = minute;

        ensure(getMinute() == minute);
        ensure(invariant(), "The invariant is not valid!");
		
	}

	@Override
	public void setSecond(int second) {
		require(invariant(), "inv-v");
        require(second >= 0, "pre-v: The second (%o) have to be greater or equals 0", second);
        require(second <= 59, "pre-v: The second (%o) cannot be greater than 59", second);

        _second = second;

        ensure(getSecond() == second);
        ensure(invariant(), "The invariant is not valid!");		
	}

	@Override
	public int getHour() {
		require(invariant(), "inv-v");
		return _hour;
	}

	@Override
	public int getMinute() {
		require(invariant(), "inv-v");
		return _minute;
	}

	@Override
	public int getSecond() {
		require(invariant(), "inv-v");
		return _second;
	}

	@Override
	public void addSeconds(int secondsToAdd) {
		require(invariant(), "inv-v");
        require(secondsToAdd > 0, "pre-v: The secondsToAdd (%o) have to be positive", secondsToAdd);

        if (getSecond() + secondsToAdd > 59) {
            // substract all coming seconds and switch to the next minute
        	secondsToAdd -= (59 - getSecond());
            setSecond(0);
            addMinutes(1);

            if (secondsToAdd > 0) {
                addSeconds(secondsToAdd);
            }
        } else {
            setSecond(getSecond() + secondsToAdd);
        }

        ensure(invariant(), "The invariant is not valid!");
		
	}

	@Override
	public void addMinutes(int minutesToAdd) {
		require(invariant(), "inv-v");
        require(minutesToAdd > 0, "pre-v: The minutesToAdd (%o) have to be positive", minutesToAdd);

        if (getMinute() + minutesToAdd > 59) {
            // substract all coming minutes and switch to the next hour
        	minutesToAdd -= (59 - getMinute());
            setMinute(0);
            addHours(1);

            if (minutesToAdd > 0) {
                addMinutes(minutesToAdd);
            }
        } else {
            setMinute(getMinute() + minutesToAdd);
        }

        ensure(invariant(), "The invariant is not valid!");		
	}

	@Override
	public void addHours(int hoursToAdd) {
		require(invariant(), "inv-v");
        require(hoursToAdd > 0, "pre-v: The hoursToAdd (%o) have to be positive", hoursToAdd);

        if (getHour() + hoursToAdd > 23) {
            // substract all hours month and switch to the next day
        	hoursToAdd -= (23 - getHour());
            setHour(0);
            addDays(1);

            if (hoursToAdd > 0) {
                addHours(hoursToAdd);
            }
        } else {
            setHour(getHour() + hoursToAdd);
        }

        ensure(invariant(), "The invariant is not valid!");		
	}

	@Override
	public void removeSeconds(int secondsToRemove) {
		require(invariant(), "inv-v");
        require(secondsToRemove > 0, "pre-v: The second to remove (%o) have to be positive", secondsToRemove);

        if (secondsToRemove > getSecond()) {
        	secondsToRemove -= getSecond();
            removeMinutes(1);
            setSecond(59);
            removeSeconds(secondsToRemove);
        } else {
            setSecond(getSecond() - secondsToRemove);
        }

        ensure(invariant(), "The invariant is not valid!");		
	}

	@Override
	public void removeMinutes(int minutesToRemove) {
		require(invariant(), "inv-v");
        require(minutesToRemove > 0, "pre-v: The minute to remove (%o) have to be positive", minutesToRemove);

        if (minutesToRemove > getMinute()) {
        	minutesToRemove -= getMinute();
            removeHours(1);
            setMinute(59);
            removeMinutes(minutesToRemove);
        } else {
            setMinute(getMinute() - minutesToRemove);
        }

        ensure(invariant(), "The invariant is not valid!");			
	}

	@Override
	public void removeHours(int hoursToRemove) {
		require(invariant(), "inv-v");
        require(hoursToRemove > 0, "pre-v: The hour to remove (%o) have to be positive", hoursToRemove);

        if (hoursToRemove > getHour()) {
        	hoursToRemove -= getHour();
            removeDays(1);
            setHour(23);
            removeHours(hoursToRemove);
        } else {
            setHour(getHour() - hoursToRemove);
        }

        ensure(invariant(), "The invariant is not valid!");			
	}
	
	private Boolean invariant() {
        require(_hour >= 0, "pre-v: The hour have to be greater or equals 0");
        require(_hour <= 23, "pre-v: The hour must not be greater 23");
        require(_minute >= 0, "pre-v: The minute have to be greater 0");
        require(_minute <= 59, "pre-v: The minute must not be greater 59");
        require(_second >= 0, "pre-v: The second have to be greater 0");
        require(_second <= 59, "pre-v: The second must not be greater 59");

        return true;
    }

	
}
