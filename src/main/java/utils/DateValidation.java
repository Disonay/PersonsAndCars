package utils;

import java.util.Calendar;
import java.util.Date;

import static java.util.Calendar.*;

public class DateValidation {
    public static int getYearsTillNow(Date oldDate) {
        Calendar calendarOldDate = getCalendar(oldDate);
        Calendar currentDate = Calendar.getInstance();

        int diff = currentDate.get(YEAR) - calendarOldDate.get(YEAR);
        if (currentDate.get(MONTH) < calendarOldDate.get(MONTH) ||
                (currentDate.get(MONTH) == calendarOldDate.get(MONTH) && currentDate.get(DATE) < calendarOldDate.get(DATE))) {
            diff--;
        }
        return diff;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }
}
