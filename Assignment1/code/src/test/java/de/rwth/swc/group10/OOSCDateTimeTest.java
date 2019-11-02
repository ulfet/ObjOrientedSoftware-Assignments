package de.rwth.swc.group10;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OOSCDateTimeTest {

	private DateTimeInterface _dateTime;

    @BeforeEach
    void init() {
        _dateTime = new OOSCDateTime();
        _dateTime.setDateTime(2019, 11, 1, 20, 10, 30);
    }
    
    @Test
    public void constructValidDateTime() {
        assertEquals(1, _dateTime.getDay());
        assertEquals(11, _dateTime.getMonth());
        assertEquals(2019, _dateTime.getYear());
        assertEquals(20, _dateTime.getHour());
        assertEquals(10, _dateTime.getMinute());
        assertEquals(30, _dateTime.getSecond());
    }
    
    @Test
    public void setHour() {
    	_dateTime.setHour(15);
    	
    	assertEquals(15, _dateTime.getHour());
    }
    
    @Test
    public void setMinute() {
    	_dateTime.setMinute(45);
    	
    	assertEquals(45, _dateTime.getMinute());
    }
    
    @Test
    public void setSecond() {
    	_dateTime.setSecond(52);
    	
    	assertEquals(52, _dateTime.getSecond());
    }
    
    @Test
    public void setTime() {
    	_dateTime.setTime(14, 30, 0);
    	
    	assertEquals(14, _dateTime.getHour());
        assertEquals(30, _dateTime.getMinute());
        assertEquals(0, _dateTime.getSecond());
    }
    
    @Test
    public void addHours() {
    	_dateTime.addHours(10);
    	
    	assertEquals(2, _dateTime.getDay());
        assertEquals(11, _dateTime.getMonth());
        assertEquals(2019, _dateTime.getYear());
        assertEquals(6, _dateTime.getHour());
        assertEquals(10, _dateTime.getMinute());
        assertEquals(30, _dateTime.getSecond());
    }
    
    @Test
    public void addMinutes() {
    	_dateTime.addMinutes(70);
    	
    	assertEquals(1, _dateTime.getDay());
        assertEquals(11, _dateTime.getMonth());
        assertEquals(2019, _dateTime.getYear());
        assertEquals(21, _dateTime.getHour());
        assertEquals(20, _dateTime.getMinute());
        assertEquals(30, _dateTime.getSecond());
    }
    
    @Test
    public void addSeconds() {
    	_dateTime.addSeconds(40);
    	
    	assertEquals(1, _dateTime.getDay());
        assertEquals(11, _dateTime.getMonth());
        assertEquals(2019, _dateTime.getYear());
        assertEquals(20, _dateTime.getHour());
        assertEquals(11, _dateTime.getMinute());
        assertEquals(10, _dateTime.getSecond());
    }
    
    @Test
    public void removeHours() {
    	_dateTime.removeHours(30);
    	
    	assertEquals(31, _dateTime.getDay());
        assertEquals(10, _dateTime.getMonth());
        assertEquals(2019, _dateTime.getYear());
        assertEquals(14, _dateTime.getHour());
        assertEquals(10, _dateTime.getMinute());
        assertEquals(30, _dateTime.getSecond());
    }
    
    @Test
    public void removeMinutes() {
    	_dateTime.removeMinutes(20);
    	
    	assertEquals(1, _dateTime.getDay());
        assertEquals(11, _dateTime.getMonth());
        assertEquals(2019, _dateTime.getYear());
        assertEquals(19, _dateTime.getHour());
        assertEquals(50, _dateTime.getMinute());
        assertEquals(30, _dateTime.getSecond());
    }
    
    @Test
    public void removeSeconds() {
    	_dateTime.removeSeconds(50);
    	
    	assertEquals(1, _dateTime.getDay());
        assertEquals(11, _dateTime.getMonth());
        assertEquals(2019, _dateTime.getYear());
        assertEquals(20, _dateTime.getHour());
        assertEquals(9, _dateTime.getMinute());
        assertEquals(40, _dateTime.getSecond());
    }  
    
    @Test
    public void timeBetween() {
    	DateTimeInterface otherDateTime = new OOSCDateTime();
    	otherDateTime.setDateTime(2018, 11, 20, 19, 38, 21);
    	
    	assertEquals(1, _dateTime.timeBetween(DateTimeInterface.DATETYPE_YEAR, otherDateTime));
    	assertEquals(12, _dateTime.timeBetween(DateTimeInterface.DATETYPE_MONTH, otherDateTime));
    	assertEquals(346, _dateTime.timeBetween(DateTimeInterface.DATETYPE_DAY, otherDateTime));
    	assertEquals(8305, _dateTime.timeBetween(DateTimeInterface.DATETYPE_HOUR, otherDateTime));
    	assertEquals(498272, _dateTime.timeBetween(DateTimeInterface.DATETYPE_MINUTE, otherDateTime));
    	assertEquals(29896329, _dateTime.timeBetween(DateTimeInterface.DATETYPE_SECOND, otherDateTime));
    }
    
    @Test
    public void dateTimeToString() {
    	assertEquals("01.11.2019 20:10:30", _dateTime.toString());
    }
}
