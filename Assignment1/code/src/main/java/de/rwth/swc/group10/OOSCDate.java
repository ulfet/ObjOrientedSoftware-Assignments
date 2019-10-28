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
        require(day <= MAXIMUM[getMonth() - 1], "pre-v: The day (%o) cannot be greater than (%o)", day, MAXIMUM[getMonth() - 1]);

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

        if (getDay() + daysToAdd > MAXIMUM[getMonth() - 1]) {
            // substract all comming days of the current month + 1 for the switch to the next month
            daysToAdd -= (MAXIMUM[getMonth() - 1] - getDay()) + 1;
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
            // substract all comming month and one extra for the switch to the next year
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
            setDay(MAXIMUM[getMonth() - 1]);
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

        // TODO: Implementation

        ensure(invariant(), "The invariant is not valid!");
        return 0;
    }

    public int timeBetween(int type, DateInterface otherDate) {
        require(invariant(), "inv-v");

        // TODO: Implementation

        ensure(invariant(), "The invariant is not valid!");
        return 0;
    }

    public void syncWithUTCTimeserver() {
        require(invariant(), "inv-v");

        ArrayList<Integer> parts = getCurrentTimeFromUTCTimeServer();

        // Only set new date, if there is a valid result
        if (parts.size() == 3) {
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
        String[] dateSplitted = dateAllTogether.split("-");

        Integer year = Integer.valueOf(dateSplitted[0]);
        Integer month = Integer.valueOf(dateSplitted[1]);
        Integer day = Integer.valueOf(dateSplitted[2]);

        parts.add(0, year);
        parts.add(1, month);
        parts.add(2, day);

        return parts;
    }

    @Override
    public String toString() {
        require(invariant(), "inv-v");

        return String.format("%04d-%02d-%02d", getYear(), getMonth(), getDay());
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
