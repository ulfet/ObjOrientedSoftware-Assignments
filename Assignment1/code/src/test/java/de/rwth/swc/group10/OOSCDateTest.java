package de.rwth.swc.group10;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OOSCDateTest {

    private DateInterface _date;

    @BeforeEach
    void init() {
        _date = new OOSCDate();
    }

    @Test
    public void constructValidDate() {
        assertEquals(1, _date.getDay());
        assertEquals(1, _date.getMonth());
        assertEquals(1, _date.getYear());
    }

    @Test
    public void addDaysMoreThan31() {
        _date.addDays(40);

        assertEquals(1, _date.getYear());
        assertEquals(2, _date.getMonth());
        assertEquals(10, _date.getDay());
    }

    @Test
    public void add31Days() {
        _date.addDays(31);

        assertEquals(1, _date.getYear());
        assertEquals(2, _date.getMonth());
        assertEquals(1, _date.getDay());
    }

    @Test
    public void addMonthsMoreThan12() {
        _date.addMonths(13);

        assertEquals(2, _date.getYear());
        assertEquals(2, _date.getMonth());
        assertEquals(1, _date.getDay());
    }

    @Test
    public void add12Month() {
        _date.addMonths(12);

        assertEquals(2, _date.getYear());
        assertEquals(1, _date.getMonth());
        assertEquals(1, _date.getDay());
    }

    @Test
    public void addYears() {
        _date.addYears(10);

        assertEquals(11, _date.getYear());
        assertEquals(1, _date.getMonth());
        assertEquals(1, _date.getDay());
    }
    
    @Test
    public void removeDays() {
    	DateInterface testDate = new OOSCDate(); 
    	testDate.setDate(2019, 3, 1);
    	testDate.removeDays(65);
    	
    	assertEquals(2018, testDate.getYear());
    	assertEquals(12, testDate.getMonth());
    	assertEquals(26, testDate.getDay());
    }

    @Test
    public void timeBetweenTest()
    {
        DateInterface otherDate = new OOSCDate();
        _date.setDate(2019, 10, 29);

        otherDate.setDate(2019, 10, 30);
        assertEquals(1, _date.daysBetween(otherDate));

        otherDate.setDate(2019, 10, 28);
        assertEquals(1, _date.daysBetween(otherDate));
        assertEquals(0, _date.timeBetween(DateInterface.DATETYPE_MONTH, otherDate));
        assertEquals(0, _date.timeBetween(DateInterface.DATETYPE_YEAR, otherDate));

        otherDate.setDate(2018, 10, 28);
        assertEquals(366, _date.daysBetween(otherDate));
        assertEquals(1, _date.timeBetween(DateInterface.DATETYPE_YEAR, otherDate));

        // Expected values from WolframAlpha
        otherDate.setDate(1997, 6, 25);
        assertEquals(8161, otherDate.daysBetween(_date));
        assertEquals(268, otherDate.timeBetween(DateInterface.DATETYPE_MONTH, _date));
        assertEquals(22, _date.timeBetween(DateInterface.DATETYPE_YEAR, otherDate));
    }

    @Test
    public void syncWithUTCTimeserverTest() {
        _date.syncWithUTCTimeserver();

        ArrayList<Integer> xList = ((OOSCDate) _date).getCurrentTimeFromUTCTimeServer();
        Integer yearOnline = xList.get(0);
        Integer monthOnline = xList.get(1);
        Integer dayOnline = xList.get(2);

        assertEquals(_date.getYear(), yearOnline);
        assertEquals(_date.getMonth(), monthOnline);
        assertEquals(_date.getDay(), dayOnline);

        System.out.println("Passed syncWithUTCTimeserverTest");
    }

    @Test
    public void DateToString() {
        assertEquals("01.01.0001", _date.toString());

        _date.setDate(2019, 10, 28);
        assertEquals("28.10.2019", _date.toString());
    }
    
    @Test
    public void daysBetweenTest() {
    	_date.setDate(2019, 1, 1);
    	DateInterface otherDate = new OOSCDate();
    	otherDate.setDate(2018, 1, 1);
    	
    	int diff = _date.daysBetween(otherDate);
    	assertEquals(diff, 365);
    	System.out.println("Paased daysBetweenTest");
    }
}
