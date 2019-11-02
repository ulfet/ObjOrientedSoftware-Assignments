package de.rwth.swc.group10;

import de.rwth.swc.group10.exceptions.WrongDayException;
import de.rwth.swc.group10.exceptions.WrongMonthException;
import de.rwth.swc.group10.exceptions.WrongYearException;

public interface DateInterfaceDefensive {
    public static final int DATETYPE_YEAR = 0;
    public static final int DATETYPE_DAY = 2;
    public static final int DATETYPE_MONTH = 1;

    public void setDate(int year, int month, int day) throws WrongYearException, WrongMonthException, WrongDayException;
    public void setYear(int year) throws WrongYearException;
    public void setMonth(int month) throws WrongMonthException;
    public void setDay(int day) throws WrongDayException;

    public int getYear();
    public int getMonth();
    public int getDay();

    public void addDays(int daysToAdd) throws WrongDayException, WrongMonthException, WrongYearException;
    public void addMonths(int monthsToAdd) throws WrongMonthException, WrongYearException;
    public void addYears(int yearsToAdd) throws WrongYearException;

    public void removeDays(int daysToRemove) throws WrongDayException, WrongMonthException, WrongYearException;
    public void removeMonths(int monthsToRemove) throws WrongMonthException, WrongYearException;
    public void removeYears(int yearsToRemove) throws WrongYearException;

    public int daysBetween(DateInterfaceDefensive otherDate);

    public int timeBetween(int type, DateInterfaceDefensive otherDate);

    public void syncWithUTCTimeserver() throws WrongYearException, WrongMonthException, WrongDayException;

    @Override
    public String toString();
    
    // added for testing
	public int getMaximum(int year, int month);
}

