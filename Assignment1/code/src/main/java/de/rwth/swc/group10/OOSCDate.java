import static org.valid4j.Assertive.*;

public class OOSCDate implements DateInterface {

    private int _year;
    private int _month;
    private int _day;

    private static final int[] MAXIMUM = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    public OOSCDate() {
        // TODO: constructor
    }

    public void setDate(int year, int month, int day) {
        // TODO: preconditions

        setYear(year);
        setMonth(month);
        setDay(day);

        ensure(getYear() == year, "The year is not properly set");
        ensure(getMonth() == month, "The month is not properly set");
        ensure(getDay() == day, "The day is not properly set");
        ensure(invariant(), "The invariant is not valid!");
    }

    public void setYear(int year) {
        require(year >= 0, "pre-v: The year (%o) must be positive", year);

        _year = year;

        ensure(getYear() == year);
        ensure(invariant(), "The invariant is not valid!");
    }

    public void setMonth(int month) {
        require(month >= 1, "pre-v: The month (%o) must be greater than 0", month);
        require(month <= 12, "pre-v: The month (%o) cannot be greater than 12", month);

        _month = month;

        ensure(getMonth() == month);
        ensure(invariant(), "The invariant is not valid!");
    }

    public void setDay(int day) {
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
            daysToAdd -= getDay();
            addMonths(1);
            addDays(daysToAdd);
        }

        setDay(getDay() + daysToAdd);

        ensure(invariant(), "The invariant is not valid!");
    }

    public void addMonths(int monthsToAdd) {
        require(invariant(), "inv-v");
        require(monthsToAdd > 0, "pre-v: The monthsToAdd (%o) have to be positive", monthsToAdd);

        if (getMonth() + monthsToAdd > 12) {
            monthsToAdd -= getMonth();
            addYears(1);
            addMonths(monthsToAdd);
        }

        setMonth(getMonth() + monthsToAdd);

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

        // TODO: Implementation

        ensure(invariant(), "The invariant is not valid!");
    }

    public void removeMonths(int monthsToRemove) {
        require(invariant(), "inv-v");

        // TODO: Implementation

        ensure(invariant(), "The invariant is not valid!");
    }

    public void removeYears(int yearsToRemove) {
        require(invariant(), "inv-v");

        // TODO: Implementation

        ensure(invariant(), "The invariant is not valid!");
    }

    public int daysBetween(DateInterface otherDate) {
        require(invariant(), "inv-v");

        // TODO: Implementation

        return 0;
    }

    public int timeBetween(int type, DateInterface otherDate) {
        require(invariant(), "inv-v");

        // TODO: Implementation

        return 0;
    }

    public void syncWithUTCTimeserver() {
        require(invariant(), "inv-v");

        ensure(invariant(), "The invariant is not valid!");
    }

    @Override
    public String toString() {
        require(invariant(), "inv-v");

        // TODO: Implementation

        return super.toString();
    }

    private Boolean invariant() {
        // TODO: Have to be implemented
        return false;
    }
}
