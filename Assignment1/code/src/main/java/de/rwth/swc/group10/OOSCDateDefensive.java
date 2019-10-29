package de.rwth.swc.group10;

import static org.valid4j.Assertive.ensure;
import static org.valid4j.Assertive.require;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import de.rwth.swc.group10.exceptions.*;

public class OOSCDateDefensive implements DateInterfaceDefensive {
	
	private int _year;
    private int _month;
    private int _day;
    
    private static final String TIMESERVER_URL = "https://andthetimeis.com/utc/now";

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
    
    public OOSCDateDefensive() {
    	_year = 1;
        _month = 1;
        _day = 1;
    }

	@Override
	public void setDate(int year, int month, int day) throws WrongYearException, WrongMonthException, WrongDayException{
		// although setter functions for year, month, and day checks for wrong inputs
		//		just for demonstration of defensive programming, we also checked for possible mistakes in this function too
		//	normally, as those 3 setter functions checks for wrong inputs, double checking here is not necessary
		if (year >= 0) 
		{
			if (month >= 1) 
			{
				if (day >= 1) 
				{
					// all safe now, defense is victorious
					setYear(year);
			        setMonth(month);
			        setDay(day);
					
				} 
				else 
				{
					throw new WrongDayException();
				}
			} 
			else 
			{
				throw new WrongMonthException();
			}
		}
		else 
		{
			throw new WrongYearException();
		}
	}

	@Override
	public void setYear(int year) throws WrongYearException {
		if (year >= 0)
			_year = year;
		else
			throw new WrongYearException();
	}

	@Override
	public void setMonth(int month) throws WrongMonthException {
		if (month >= 1)
			_month = month;
		else
			throw new WrongMonthException();
	}

	@Override
	public void setDay(int day) throws WrongDayException {
		if (day >= 1)
			_day = day;
		else
			throw new WrongDayException();
	}

	@Override
	public int getYear() {
		return _year;
	}

	@Override
	public int getMonth() {
		return _month;
	}

	@Override
	public int getDay() {
		return _day;
	}

	@Override
	public void addDays(int daysToAdd) throws WrongDayException, WrongMonthException, WrongYearException {
		if (daysToAdd <= 0)
			throw new WrongDayException();
		
		if (getDay() + daysToAdd > MAXIMUM[getMonth() - 1]) {
            // subtract all coming days of the current month + 1 for the switch to the next month
            daysToAdd -= (MAXIMUM[getMonth() - 1] - getDay()) + 1;
            addMonths(1);
            setDay(1);

            if (daysToAdd > 0) {
                addDays(daysToAdd);
            }
        } else {
            setDay(getDay() + daysToAdd);
        }
	}

	@Override
	public void addMonths(int monthsToAdd) throws WrongMonthException, WrongYearException {
		if (monthsToAdd <= 0)
			throw new WrongMonthException();
		
		if (getMonth() + monthsToAdd > 12) {
            // subtract all coming month and one extra for the switch to the next year
            monthsToAdd -= (12 - getMonth()) + 1;
            addYears(1);
            setMonth(1);

            if (monthsToAdd > 0) {
                addMonths(monthsToAdd);
            }
        } else {
            setMonth(getMonth() + monthsToAdd);
        }
	}

	@Override
	public void addYears(int yearsToAdd) throws WrongYearException {
		if (yearsToAdd <= 0)
			throw new WrongYearException();
		else
			setYear(getYear() + yearsToAdd);
	}

	@Override
	public void removeDays(int daysToRemove) throws WrongDayException, WrongMonthException, WrongYearException {
		if (daysToRemove <= 0)
			throw new WrongDayException();
		
		if (daysToRemove > getDay()) {
            daysToRemove -= getDay();
            removeMonths(1);
            setDay(MAXIMUM[getMonth() - 1]);
            removeDays(daysToRemove);
        } else {
            setDay(getDay() - daysToRemove);
        }
	}

	@Override
	public void removeMonths(int monthsToRemove) throws WrongMonthException, WrongYearException {
		if (monthsToRemove <= 0)
			throw new WrongMonthException();
		
		if (monthsToRemove > getMonth()) {
            monthsToRemove -= getMonth();
            removeYears(1);
            setMonth(12);
            removeMonths(monthsToRemove);
        } else {
            setMonth(getMonth() - monthsToRemove);
        }
	}

	@Override
	public void removeYears(int yearsToRemove) throws WrongYearException {
		if (yearsToRemove <= 0)
			throw new WrongYearException();
		else
			setYear(getYear() - yearsToRemove);
	}
	
	int daysSinceChrist() {
		int daysAccumulated = 0;

		// distinction between years made up of 366 days
		// not including the current year (i < currentYear)
		int currentYear = this.getYear();
		for (int i=0; i < currentYear; i++) {
			if ((i % 4) == 0) {
				daysAccumulated += 366;
			}
			else
				daysAccumulated += 365;
		}

		// month days are accumulated
		//		with respect to extra years
		int currentMonth = this.getMonth();
		for (int i=1; i < currentMonth; i++) {
			daysAccumulated += MAXIMUM[i-1];

			// check for extra year && february
			if ((i == 2) && (currentYear % 4 == 0)) {
				daysAccumulated += 1;
			}
		}

		daysAccumulated += this.getDay();

		return daysAccumulated;
	}

	@Override
	public int daysBetween(DateInterfaceDefensive otherDate) {
		int thisInstanceDays = this.daysSinceChrist();
        int inputInstanceDays = ((OOSCDateDefensive) otherDate).daysSinceChrist();
        int difference = thisInstanceDays - inputInstanceDays;
        
        return difference;
	}

	@Override
	public int timeBetween(int type, DateInterfaceDefensive otherDate) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void syncWithUTCTimeserver() throws WrongYearException, WrongMonthException, WrongDayException {

        ArrayList<Integer> parts = getCurrentTimeFromUTCTimeServer();

        // Only set new date, if there is a valid result
        if (parts.size() == 3) {
            Integer yearReceived = parts.get(0);
            Integer monthReceived = parts.get(1);
            Integer dayReceived = parts.get(2);

            setDate(yearReceived, monthReceived, dayReceived);
        }
    }
	
	ArrayList<Integer> getCurrentTimeFromUTCTimeServer() {
		
        ArrayList<Integer> parts = new ArrayList<>();

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(TIMESERVER_URL);
        CloseableHttpResponse response = null;

        try {
            response = httpClient.execute(request);
        } catch (ClientProtocolException e1) {
            // In case of an exception just return an empty list
            return parts;
        } catch (IOException e1) {
            // In case of an exception just return an empty list
            return parts;
        }

        HttpEntity entity = response.getEntity();
        String result = null;

        if (entity != null) {
            try {
                result = EntityUtils.toString(entity);
            } catch (ParseException e) {
                // In case of an exception just return an empty list
                return parts;
            } catch (IOException e) {
                // In case of an exception just return an empty list
                return parts;
            }
        }

        // result LIKE 2019-10-27 20:20:56 +00:00
        String dateAllTogether = result.split(" ")[0]; //2019-10-27
        String[] dateSplitted = dateAllTogether.split("-");

        Integer year = Integer.valueOf(dateSplitted[0]);
        Integer month = Integer.valueOf(dateSplitted[1]);
        Integer day = Integer.valueOf(dateSplitted[2]);

        parts.add(0, year);
        parts.add(1, month);
        parts.add(2, day);

        return parts;
    }

}
