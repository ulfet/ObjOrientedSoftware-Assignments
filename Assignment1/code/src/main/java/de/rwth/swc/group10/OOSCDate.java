package de.rwth.swc.group10;

import static org.valid4j.Assertive.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class OOSCDate implements DateInterface {

    private int _year;
    private int _month;
    private int _day;

    // TODO: Does not work for leap-years!
    private static final int[] MAXIMUM = {
        31,     // JAN
        28,     // FEB
        31,     // MAR
        30,     // APR
        31,     // MAY
        30,     // JUN
        31,     // JUL
        31,     // AUG
        30,     // SEP
        31,     // OCT
        30,     // NOV
        31      // DEC
    };

    private static int getMaximum(int year, int month)
    {
        if (month != 1)
        {
            return MAXIMUM[month];
        }
        else
        {
            if (isLeapYear(getYear()))
            {
                return MAXIMUM[1] + 1;
            }
            else
            {
                return MAXIMUM[1];
            }
        }
    }

    private static bool isLeapYear(int year)
    {
        if (year % 400) return false;
        if (year % 100) return false;
        if (year % 4) return true;
        return false;
    }

    public OOSCDate() {
        _year = 1;
        _month = 1;
        _day = 1;

        ensure(invariant(), "The invariant is not valid!");
    }

    public void setDate(int year, int month, int day) {
        require(invariant(), "inv-v");

        setYear(year);
        setMonth(month);
        setDay(day);

        ensure(getYear() == year, "The year is not properly set");
        ensure(getMonth() == month, "The month is not properly set");
        ensure(getDay() == day, "The day is not properly set");
        ensure(invariant(), "The invariant is not valid!");
    }

    public void setYear(int year) {
        require(invariant(), "inv-v");
        require(year >= 0, "pre-v: The year (%o) must be positive", year);

        _year = year;

        ensure(getYear() == year);
        ensure(invariant(), "The invariant is not valid!");
    }

    public void setMonth(int month) {
        require(invariant(), "inv-v");
        require(month >= 1, "pre-v: The month (%o) must be greater than 0", month);
        require(month <= 12, "pre-v: The month (%o) cannot be greater than 12", month);

        _month = month;

        ensure(getMonth() == month);
        ensure(invariant(), "The invariant is not valid!");
    }

    public void setDay(int day) {
        require(invariant(), "inv-v");
        require(day >= 1, "pre-v: The day (%o) must be greater than 0", day);
        require(day <= getMaximum(getYear(), getMonth() - 1), "pre-v: The day (%o) cannot be greater than (%o)", day, getMaximum(getYear(), getMonth() - 1));

        _day = day;

        ensure(getDay() == day);
        ensure(invariant(), "The invariant is not valid!");
    }

    public int getYear() {
        require(invariant(), "inv-v");

        return _year;
    }

    public int getMonth() {
        require(invariant(), "inv-v");

        return _month;
    }

    public int getDay() {
        require(invariant(), "inv-v");

        return _day;
    }

    public void addDays(int daysToAdd) {
        require(invariant(), "inv-v");
        require(daysToAdd > 0, "pre-v: The daysToAdd (%o) have to be positive", daysToAdd);

        if (getDay() + daysToAdd > getMaximum(getYear(), getMonth() - 1)) {
            // substract all coming days of the current month + 1 for the switch to the next month
            daysToAdd -= (getMaximum(getYear(), getMonth() - 1) - getDay()) + 1;
            addMonths(1);
            setDay(1);

            if (daysToAdd > 0) {
                addDays(daysToAdd);
            }
        } else {
            setDay(getDay() + daysToAdd);
        }

        ensure(invariant(), "The invariant is not valid!");
    }

    public void addMonths(int monthsToAdd) {
        require(invariant(), "inv-v");
        require(monthsToAdd > 0, "pre-v: The monthsToAdd (%o) have to be positive", monthsToAdd);

        if (getMonth() + monthsToAdd > 12) {
            // substract all coming month and one extra for the switch to the next year
            monthsToAdd -= (12 - getMonth()) + 1;
            addYears(1);
            setMonth(1);

            if (monthsToAdd > 0) {
                addMonths(monthsToAdd);
            }
        } else {
            setMonth(getMonth() + monthsToAdd);
        }

        ensure(invariant(), "The invariant is not valid!");
    }

    public void addYears(int yearsToAdd) {
        require(invariant(), "inv-v");
        require(yearsToAdd > 0, "pre-v: The years (%o) to add have to be positive", yearsToAdd);

        setYear(getYear() + yearsToAdd);

        ensure(invariant(), "The invariant is not valid!");
    }

    public void removeDays(int daysToRemove) {
        require(invariant(), "inv-v");
        require(daysToRemove > 0, "pre-v: The days to remove (%o) have to be positive", daysToRemove);

        if (daysToRemove > getDay()) {
            daysToRemove -= getDay();
            removeMonths(1);
            setDay(getMaximum(getYear(), getMonth() - 1));
            removeDays(daysToRemove);
        } else {
            setDay(getDay() - daysToRemove);
        }

        ensure(invariant(), "The invariant is not valid!");
    }

    public void removeMonths(int monthsToRemove) {
        require(invariant(), "inv-v");
        require(monthsToRemove > 0, "pre-v: The month to remove (%o) have to be positive", monthsToRemove);

        if (monthsToRemove > getMonth()) {
            monthsToRemove -= getMonth();
            removeYears(1);
            setMonth(12);
            removeMonths(monthsToRemove);
        } else {
            setMonth(getMonth() - monthsToRemove);
        }

        ensure(invariant(), "The invariant is not valid!");
    }

    public void removeYears(int yearsToRemove) {
        require(invariant(), "inv-v");
        require(yearsToRemove > 0, "pre-v: The years to remove (%o) have to be positive", yearsToRemove);
        require(yearsToRemove <= getYear(), "pre-v: The years to remove (%o) have to be less or equal then the current year (%o)",yearsToRemove, getYear());

        setYear(getYear() - yearsToRemove);

        ensure(invariant(), "The invariant is not valid!");
    }

    public int daysBetween(DateInterface otherDate) {
        require(invariant(), "inv-v");

        int result = timeBetween(DATETYPE_DAY, otherDate);

        ensure(invariant(), "The invariant is not valid!");
        return result;
    }

    public int timeBetween(int type, DateInterface otherDate) {
        require(invariant(), "inv-v");

        int result = 0;

        // Check for easy case
        if (type == DATETYPE_YEAR)
        {
            result = abs(this.getYear() - otherDate.getYear());
        }
        else
        {
            DateInterface greater;
            DateInterface smaller;

            if (getYear() > otherDate.getYear())
            {
                greater = this;
                smaller = otherDate;
            }
            else
            {
                if (getYear() == otherDate.getYear())
                {
                    if (getMonth() > otherDate.getMonth())
                    {
                        greater = this;
                        smaller = otherDate;
                    }
                    else
                    {
                        if (getMonth() == otherDate.getMonth())
                        {
                            if (getDay() > otherDate.getDay()
                            {
                                greater = this;
                                smaller = otherDate;
                            }
                            else
                            {
                                if (getDay() == otherDate.getDay())
                                {
                                    return 0;
                                }
                                else
                                {
                                    greater = otherDate;
                                    smaller = this;
                                }
                            }
                        }
                        else
                        {
                            greater = otherDate;
                            smaller = this;
                        }
                    }
                }
                else
                {
                    greater = otherDate;
                    smaller = this;
                }
            }

            OOSCDate intermediate = new OOSCDate();
            intermediate.setDate(getDay(), getMonth(), getYear());

            if (greater.getYear() > intermediate.getYear())
            {
                if (type == DATETYPE_MONTH)
                {
                    result += (greater.getYear() - intermediate.getYear() - 1) * 12;
                }
                else
                {
                    // Day
                    for (int i = intermediate.getYear(); i < greater.getYear() - 1; i++)
                    {
                        if (isLeapYear(i))
                        {
                            result += 367;
                        }
                        else
                        {
                            result += 365;
                        }
                    }
                }

                intermediate.setMonth(1);
            }

            if (greater.getMonth() > intermediate.getMonth())
            {
                if (type == DATETYPE_MONTH)
                {
                    result += greater.getMonth() - 1;
                }
                else
                {
                    // Day
                    for (int i = intermediate.getMonth(); i < greater.getMonth() - 1; i++)
                    {
                        result += getMaximum(intermediate.getYear(), i);
                    }
                }

                intermediate.setDay(1);
            }

            if (greater.getDay() > intermediate.getDay())
            {
                if (type == DATETYPE_DAY)
                {
                    result += greater.getDay() - 1;
                }
            }
        }

        ensure(invariant(), "The invariant is not valid!");
        return result;
    }

    public void syncWithUTCTimeserver() {
        require(invariant(), "inv-v");

        // TODO: implementation done, peer review
        ArrayList<Integer> xList = getCurrentTimeFromUTCTimeServer();
        Integer yearReceived = xList.get(0);
        Integer monthReceived = xList.get(1);
        Integer dayReceived = xList.get(2);
        
        setDate(yearReceived, monthReceived, dayReceived);

        ensure(invariant(), "The invariant is not valid!");
    }
    
    public ArrayList<Integer> getCurrentTimeFromUTCTimeServer() {
    	
    	CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet("https://andthetimeis.com/utc/now");
        CloseableHttpResponse response = null;
        
		try {
			response = httpClient.execute(request);
		} catch (ClientProtocolException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        HttpEntity entity = response.getEntity();
        String result = null;
        
        if (entity != null) {
			try {
				result = EntityUtils.toString(entity);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            //System.out.println(result);
        }
        // result LIKE 2019-10-27 20:20:56 +00:00
        ArrayList<Integer> allThreeParts = new ArrayList<Integer>();
        String dateAllTogether = result.split(" ")[0]; //2019-10-27
        String[] dateSplitted = dateAllTogether.split("-"); 
        Integer year = Integer.valueOf(dateSplitted[0]);
        Integer month = Integer.valueOf(dateSplitted[1]);
        Integer day = Integer.valueOf(dateSplitted[2]);
        allThreeParts.add(0, year);
        allThreeParts.add(1, month);
        allThreeParts.add(2, day);
        
		return allThreeParts;
    	
    }

    @Override
    public String toString() {
        require(invariant(), "inv-v");

        String result = getDay() + "." + getMonth() + "." + getYear();

        ensure(result != NULL, "Post-v: Result is null");
        ensure(result.length == 0, "Post-v: Result is empty");
        ensure(invariant(), "The invariant is not valid!");
        return super.toString();
    }

    private Boolean invariant() {
        require(_year >= 0, "pre-v: The year have to be greater or equals 0");
        require(_month > 0, "pre-v: The month have to be greater 0");
        require(_month <= 12, "pre-v: The month must not be greater 12");
        require(_day > 0, "pre-v: The day have to be greater 0");
        require(_day <= MAXIMUM[_month -1], "pre-v: The day have to be less or equal %o", MAXIMUM[_month - 1]);

        return true;
    }
}
