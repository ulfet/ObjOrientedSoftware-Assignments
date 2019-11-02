package de.rwth.swc.group10;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import de.rwth.swc.group10.exceptions.WrongDayException;
import de.rwth.swc.group10.exceptions.WrongMonthException;
import de.rwth.swc.group10.exceptions.WrongYearException;

public class OOSCDateDefensiveTest {
	private DateInterfaceDefensive _date;
	
	@BeforeEach
    void init() {
        _date = new OOSCDateDefensive();
    }
	
	/*
	 * 
	 * tests for setX functions
	 * 
	 */
	@Test
	void setYearTest() throws WrongYearException {
		// both cases are tested here
		
		// safe-case
		int aSafeValue = 10;
		_date.setYear(aSafeValue);
		assertEquals(aSafeValue, _date.getYear());
		
		// exception case
		Executable ex = () -> _date.setYear(-1);
		Assertions.assertThrows(WrongYearException.class,  ex);
	}
	
	@Test
	void setMonthTest() throws WrongMonthException {
		// both cases are tested here
		
		// safe-case
		int aSafeValue = 10;
		_date.setMonth(aSafeValue);
		assertEquals(aSafeValue, _date.getMonth());
		
		// exception case
		Executable ex = () -> _date.setMonth(-1);
		Assertions.assertThrows(WrongMonthException.class, ex);
	}
	
	@Test
	void setDaysTest() throws WrongDayException {
		// both cases are tested here
		
		// safe-case
		int aSafeValue = 10;
		_date.setDay(aSafeValue);
		assertEquals(aSafeValue, _date.getDay());
		
		// exception case
		Executable ex = () -> _date.setDay(-1);
		Assertions.assertThrows(WrongDayException.class, ex);
		
		Executable ex2 = () -> _date.setDay(32);
		Assertions.assertThrows(WrongDayException.class, ex2);
	}
	
	
	
	/*
	 * 
	 * tests for addX functions
	 * 
	 */
	
	@Test
	void addYearsTest() throws WrongYearException {
		// both cases are tested here
		
		// safe-case
		int aSafeValue = 10;
		int oldValue = _date.getYear();
		int wantedValue = aSafeValue + oldValue;
		
		_date.addYears(aSafeValue);
		assertEquals(wantedValue, _date.getYear());
		
		// exception case
		Executable ex = () -> _date.addYears(-1);
		Assertions.assertThrows(WrongYearException.class, ex);
	}
	
	@Test
	void addMonthsTest() throws WrongMonthException, WrongYearException {
		// both cases are tested here
		
		// safe-case
		
		// this assumes a single leap in month value
		int aSafeValue_Month = 11;
		int oldValue_Month = _date.getMonth();
		
		int wantedValue_Month = (aSafeValue_Month + oldValue_Month);
		if (wantedValue_Month > 12)
			wantedValue_Month = wantedValue_Month  % 12;
		if (wantedValue_Month == 0)
			wantedValue_Month = 1;
		
		int oldValue_Year = _date.getYear();
		int wantedValue_Year = oldValue_Year;
		if (aSafeValue_Month + oldValue_Month > 12 )
			wantedValue_Year = oldValue_Year + (aSafeValue_Month + oldValue_Month) / 12;
		
		_date.addMonths(aSafeValue_Month);
		assertEquals(wantedValue_Month, _date.getMonth());
		assertEquals(wantedValue_Year, _date.getYear());
		
		// exception case
		Executable ex = () -> _date.addMonths(-1);
		Assertions.assertThrows(WrongMonthException.class, ex);
	}
	
	@Test
	void addDaysTest() throws WrongDayException, WrongMonthException, WrongYearException {
		// both cases are tested here
		
		// safe-case
		
		// this assumes a single leap in month value
		int aSafeValue = 40;
		int oldValue = _date.getDay();
		
		int maxOfThatMonth = _date.getMaximum(_date.getYear(), _date.getMonth());
		int wantedValue = 0;
		if (maxOfThatMonth < (aSafeValue + oldValue)) {
			wantedValue = aSafeValue + oldValue - maxOfThatMonth;
		}
		else
			wantedValue = aSafeValue + oldValue;
		
		_date.addDays(aSafeValue);
		assertEquals(wantedValue, _date.getDay());
		
		// exception case
		Executable ex = () -> _date.addDays(-1);
		Assertions.assertThrows(WrongDayException.class, ex);
	}
	
	
	/*
	 * 
	 * tests for removeX functions
	 * 
	 */
	
	@Test
	void removeYearsTest() throws WrongYearException {
		
		// safe-case
		_date.setYear(100);
		
		int aSafeValue = 10;
		int oldValue = _date.getYear();
		int wantedValue = oldValue - aSafeValue;
		
		_date.removeYears(aSafeValue);
		assertEquals(wantedValue, _date.getYear());
		
		// exception case
		_date.setYear(100);
		int unsafeValue = 200;
		
		Executable ex = () -> _date.removeYears(unsafeValue);
		Assertions.assertThrows(WrongYearException.class, ex);
	}
	
	@Test
	void removeMonths() throws WrongMonthException, WrongYearException, WrongDayException {	
		
		// safe-case
		_date.setMonth(11);
		
		int aSafeValue = 10;
		int oldValue = _date.getMonth();
		int diff = oldValue - aSafeValue;
		
		
		_date.removeMonths(aSafeValue);
		assertEquals(diff, _date.getMonth());
		
		// exception case
		_date.setDate(1, 1, 1);
		int unsafeValue = 200;
		
		Executable ex = () -> _date.removeMonths(unsafeValue);
		Assertions.assertThrows(WrongYearException.class, ex);
		// yes, WrongYearException, because in the end, that is what runs out
	}
	
	@Test
	void removeDaysTest() throws WrongDayException, WrongMonthException, WrongYearException {
		// safe-case
		_date.setDay(11);
		
		int aSafeValue = 10;
		int oldValue = _date.getDay();
		int diff = oldValue - aSafeValue;
		
		
		_date.removeDays(aSafeValue);
		assertEquals(diff, _date.getDay());
		
		// exception case
		_date.setDate(1, 1, 1);
		int unsafeValue = 3200;
		
		Executable ex = () -> _date.removeDays(unsafeValue);
		Assertions.assertThrows(WrongYearException.class, ex);
		// yes, WrongYearException, because in the end, that is what runs out
	}
	
	@Test
	void daysBetweenTest() throws WrongYearException, WrongMonthException, WrongDayException {
		// safe-case
		DateInterfaceDefensive otherDate = new OOSCDateDefensive();
		otherDate.setDate(1, 1, 1);
		
		_date.setDate(1, 1, 30);
		
		int diff = _date.daysBetween(otherDate);
		assertEquals(diff, 29);
		
		// exception case
		// 		no exception case, as somehow one can find difference between two days
		//		see implementation details of daysBetween to see how
	}
	
	@Test
	void timeBetweenTest() throws WrongYearException, WrongMonthException, WrongDayException {
		// taken directly from OOSCDateTest.java
		DateInterfaceDefensive otherDate = new OOSCDateDefensive();
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
    public void syncWithUTCTimeserverTest() throws WrongYearException, WrongMonthException, WrongDayException {
        _date.syncWithUTCTimeserver();

        ArrayList<Integer> xList = ((OOSCDateDefensive) _date).getCurrentTimeFromUTCTimeServer();
        Integer yearOnline = xList.get(0);
        Integer monthOnline = xList.get(1);
        Integer dayOnline = xList.get(2);

        assertEquals(_date.getYear(), yearOnline);
        assertEquals(_date.getMonth(), monthOnline);
        assertEquals(_date.getDay(), dayOnline);

        System.out.println("Passed syncWithUTCTimeserverTest");
    }
	
}
