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
        assertEquals("0001-01-01", _date.toString());

        _date.setDate(2019, 10, 28);
        assertEquals("2019-10-28", _date.toString());
    }
}
