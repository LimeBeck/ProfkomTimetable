package ru.profdstu.profkomtimetable;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by metal on 09.03.2018.
 */

public final class WeekCompute {
    static public int Today(){
        Date today = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(today);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    static private int Weeks(){
        int days;
        int firstSeptemberDay;
        Date today = new Date();
        Calendar calendar = new GregorianCalendar();
        Calendar firstSeptember = new GregorianCalendar();
        calendar.setTime(today);
        int todayDay = calendar.get(Calendar.DAY_OF_YEAR);
        if(calendar.get(Calendar.MONTH)>=9){
            firstSeptember.set(calendar.get(Calendar.YEAR),9,1);
            firstSeptemberDay = firstSeptember.get(Calendar.DAY_OF_YEAR);
            days = todayDay - firstSeptemberDay;
        }
        else {
            firstSeptember.set(calendar.get(Calendar.YEAR)-1,9,1);
            Calendar lastDayOfYear = new GregorianCalendar(calendar.get(Calendar.YEAR)-1,12,1);
            int lastDay = lastDayOfYear.get(Calendar.DAY_OF_YEAR);
            firstSeptemberDay = firstSeptember.get(Calendar.DAY_OF_YEAR);

            days = firstSeptemberDay-lastDay+todayDay;
        }

        return days/7;
    }

    static public Boolean IsWeekEven(){
        return Weeks() % 2 == 0;
    }

    static public int WeekEven(){
        if(Weeks()%2!=0){
            return 1;
        }
        else{
            return 2;
        }
    }
}
