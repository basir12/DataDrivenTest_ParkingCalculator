package com.ParkingLibrary;

import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DatePicker {

    private int currentYear;
    private int currentMonth;
    private int currentDay;
    private String currentMonthString;

    private int futureYear;
    private int futureMonth;
    private int futureDay;
    private String futureMonthString;
    private int numberClicks;
    private int counter;
    
    private String departureDate;
    private String userSlectedDate;
    
    public String getUserSelectedDate(){
        return userSlectedDate;
    }
    
    public int getNumberClicks() {
        return numberClicks;
    }
    
    public int getCurrentDay() {
        return currentDay;
    }
    
    public int getFutureDay(){
        return futureDay;
    }    

    public String getFutureMonth() {
        return futureMonthString;
    }

    public String getCurrentMonth() {
        return currentMonthString;
    }

    public void calculateTime(String mmddyyyy) {
        
        DateTime currentDate;
        // first time calling the method
        if (counter == 0) {            
             currentDate = DateTime.now();
            departureDate = mmddyyyy;
            userSlectedDate = mmddyyyy;
        }else
        {// second time calling the method
            DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy");
            currentDate = dtf.parseDateTime(departureDate);
            userSlectedDate = mmddyyyy;
        }
        counter ++;
        
        currentMonthString = currentDate.toString("MMMM");
        currentYear = currentDate.getYear();
        currentMonth = currentDate.getMonthOfYear();
        currentDay = currentDate.getDayOfMonth();    
        
        DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/yyyy");
        DateTime futureDate = dtf.parseDateTime(mmddyyyy);
        futureMonthString = futureDate.toString("MMMM");
        futureYear = futureDate.getYear();
        futureMonth = futureDate.getMonthOfYear();
        futureDay = futureDate.getDayOfMonth();

        DateTime date1 = new DateTime().withDate(currentYear, currentMonth, currentDay);
        DateTime date2 = new DateTime().withDate(futureYear, futureMonth, futureDay);

        int totalMonths = Months.monthsBetween(date1, date2).getMonths();
        numberClicks = totalMonths;

        // System.out.println("current date: [" +currentDate + "]");
        //System.out.println("future date: [" + futureDate + "]");
        // System.out.println(Months.monthsBetween(date1, date2).getMonths());
        //System.out.println("number of clicks: " + numberClicks);
    }

    public static void main(String[] args) {
        DatePicker myObject = new DatePicker();
        myObject.calculateTime("10/03/2019");
        myObject.calculateTime("02/03/2019");
        myObject.calculateTime("02/27/2019");
        myObject.calculateTime("10/30/2019");
        myObject.calculateTime("01/15/2019");
        myObject.calculateTime("01/20/2019");

    }
}