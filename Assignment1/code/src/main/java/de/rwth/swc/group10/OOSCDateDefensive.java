package de.rwth.swc.group10;

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

    public int getMaximum(int year, int month)
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
        if (year >= 0) {
            if (month >= 1 && month <= 12) {
                if (day >= 1 && day <= getMaximum(year, month)) {
                    // all safe now, defense is victorious
                    setYear(year);
                    setMonth(month);
                    setDay(day);
                } else {
                    throw new WrongDayException();
                }
            } else {
                throw new WrongMonthException();
            }
        } else {
            throw new WrongYearException();
        }
    }

    @Override
    public void setYear(int year) throws WrongYearException {
        if (year >= 0) {
            _year = year;
        } else {
            throw new WrongYearException();
        }
    }

    @Override
    public void setMonth(int month) throws WrongMonthException {
        if (month >= 1 && month <= 12) {
            _month = month;
        } else {
            throw new WrongMonthException();
        }
    }

    @Override
    public void setDay(int day) throws WrongDayException {
        if (day >= 1 && day <= getMaximum(this.getYear(), this.getMonth())) {
            _day = day;
        } else {
            throw new WrongDayException();
        }
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
        if (daysToAdd < 0) {
            throw new WrongDayException();
        }

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
    }

    @Override
    public void addMonths(int monthsToAdd) throws WrongMonthException, WrongYearException {
        if (monthsToAdd < 0) {
            throw new WrongMonthException();
        }

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
    }

    @Override
    public void addYears(int yearsToAdd) throws WrongYearException {
        if (yearsToAdd < 0) {
            throw new WrongYearException();
        } else {
            setYear(getYear() + yearsToAdd);
        }
    }

    @Override
    public void removeDays(int daysToRemove) throws WrongDayException, WrongMonthException, WrongYearException {
        if (daysToRemove < 0) {
            throw new WrongDayException();
        }

        if (daysToRemove > getDay()) {
            daysToRemove -= getDay();
            removeMonths(1);
            setDay(getMaximum(getYear(), getMonth()));
            removeDays(daysToRemove);
        } else if (daysToRemove == getDay()) {
            removeMonths(1);
            setDay(getMaximum(getYear(), getMonth()));
        } else {
            setDay(getDay() - daysToRemove);
        }
    }

    @Override
    public void removeMonths(int monthsToRemove) throws WrongMonthException, WrongYearException {
        if (monthsToRemove < 0) {
            throw new WrongMonthException();
        }

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
    }

    @Override
    public void removeYears(int yearsToRemove) throws WrongYearException {
        if (yearsToRemove <= 0) {
            throw new WrongYearException();
        } else {
            setYear(getYear() - yearsToRemove);
        }
    }

    @Override
    public int daysBetween(DateInterfaceDefensive otherDate) {
        int result = timeBetween(DATETYPE_DAY, otherDate);
        return result;
    }

    @Override
    public int timeBetween(int type, DateInterfaceDefensive otherDate) {
        int result = 0;

        if (otherDate == null) {
            throw new IllegalArgumentException("otherDate");
        }

        if (type == DATETYPE_YEAR) {
            // Easiest case doesn't include any counting
            result = Math.abs(this.getYear() - otherDate.getYear());
        } else {
            DateInterfaceDefensive greater;
            DateInterfaceDefensive smaller;

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

        return result;
    }

    @Override
    public void syncWithUTCTimeserver() throws WrongYearException, WrongMonthException, WrongDayException {

        ArrayList<Integer> parts = getCurrentTimeFromUTCTimeServer();

        // Only set new date, if there is a valid result
        if (parts.size() > 0) {
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

}
