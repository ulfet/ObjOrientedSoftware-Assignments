package de.rwth.swc.group10;

import static org.valid4j.Assertive.*;

import java.io.IOException;
import java.util.ArrayList;


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

    private static final String TIMESERVER_URL = "https://andthetimeis.com/utc/now";

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
        if (month != 2) {
            // Not february
            return MAXIMUM[month - 1];
        } else {
            // In leap years the maximum is 29
            if (isLeapYear(year)) {
                return MAXIMUM[1] + 1;
            } else {
                return MAXIMUM[1];
            }
        }
    }

    private static boolean isLeapYear(int year)
    {
        if (year % 400 == 0) {
            return true;
        }

        if (year % 100 == 0) {
            return false;
        }

        if (year % 4 == 0) {
            return true;
        }

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
        require(day <= getMaximum(getYear(), getMonth()), "pre-v: The day (%d) cannot be greater than (%d)", day, getMaximum(getYear(), getMonth()));

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

        if (getDay() + daysToAdd > getMaximum(getYear(), getMonth())) {
            // substract all coming days of the current month + 1 for the switch to the next month
            daysToAdd -= (getMaximum(getYear(), getMonth()) - getDay()) + 1;

            setDay(1);
            addMonths(1);

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
            setMonth(1);
            addYears(1);

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
            setDay(getMaximum(getYear(), getMonth()));
            removeDays(daysToRemove);
        } else if (daysToRemove == getDay()) {
            removeMonths(1);
            setDay(MAXIMUM[getMonth() - 1]);
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
        } else if (monthsToRemove == getMonth()) {
        	removeYears(1);
            setMonth(12);
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
        require(otherDate != null, "pre-v: otherDate is NULL");

        int result = 0;

        if (type == DATETYPE_YEAR) {
            // Easiest case doesn't include any counting
            result = Math.abs(this.getYear() - otherDate.getYear());
        } else {
            DateInterface greater;
            DateInterface smaller;

            if (getYear() > otherDate.getYear() ||
                (getYear() == otherDate.getYear() &&
                    (getMonth() > otherDate.getMonth() ||
                    (getMonth() == otherDate.getMonth() && getDay() > otherDate.getDay())))) {
                greater = this;
                smaller = otherDate;
            } else {
                greater = otherDate;
                smaller = this;
            }

            OOSCDate intermediate = new OOSCDate();
            intermediate.setDate(smaller.getYear(), smaller.getMonth(), smaller.getDay());

            while (!(intermediate.getYear() == greater.getYear() &&
                     intermediate.getMonth() == greater.getMonth() &&
                     (type == DATETYPE_MONTH || intermediate.getDay() == greater.getDay()))) {
                if (type == DATETYPE_DAY) {
                    intermediate.addDays(1);
                    result++;
                } else {
                    intermediate.addMonths(1);
                    result++;
                }
            }
        }

        ensure(invariant(), "The invariant is not valid!");
        return result;
    }

    public void syncWithUTCTimeserver() {
        require(invariant(), "inv-v");

        ArrayList<Integer> parts = getCurrentTimeFromUTCTimeServer();

        // Only set new date, if there is a valid result
        if (parts.size() > 0) {
            Integer yearReceived = parts.get(0);
            Integer monthReceived = parts.get(1);
            Integer dayReceived = parts.get(2);

            setDate(yearReceived, monthReceived, dayReceived);
        }

        ensure(invariant(), "The invariant is not valid!");
    }

    ArrayList<Integer> getCurrentTimeFromUTCTimeServer() {
        require(invariant(), "inv-v");

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
        String timeAllTogether = result.split(" ")[1]; //20:20:56
        String[] dateSplitted = dateAllTogether.split("-");
        String[] timeSplitted = timeAllTogether.split(":");

        Integer year = Integer.valueOf(dateSplitted[0]);
        Integer month = Integer.valueOf(dateSplitted[1]);
        Integer day = Integer.valueOf(dateSplitted[2]);
        
        Integer hour = Integer.valueOf(timeSplitted[0]);
        Integer minute = Integer.valueOf(timeSplitted[1]);
        Integer second = Integer.valueOf(timeSplitted[2]);

        parts.add(0, year);
        parts.add(1, month);
        parts.add(2, day);
        
        parts.add(3, hour);
        parts.add(4, minute);
        parts.add(5, second);

        return parts;
    }

    @Override
    public String toString() {
        require(invariant(), "inv-v");

        String result = String.format("%02d.%02d.%04d", getDay(), getMonth(), getYear());

        ensure(result != null, "Post-v: Result is null");
        ensure(result.length() > 0, "Post-v: Result is empty");
        ensure(invariant(), "The invariant is not valid!");

        return result;
    }

    private Boolean invariant() {
        require(_year >= 0, "pre-v: The year have to be greater or equals 0");
        require(_month > 0, "pre-v: The month have to be greater 0");
        require(_month <= 12, "pre-v: The month must not be greater 12");
        require(_day > 0, "pre-v: The day have to be greater 0");
        require(_day <= getMaximum(_year, _month), "pre-v: The day has to be less or equal %d", getMaximum(_year, _month));

        return true;
    }
}
