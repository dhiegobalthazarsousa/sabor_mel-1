/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eagles.sabor_mel.model;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author dhiego.balthazar
 */
public class DateGenerator {
    
    private static final GregorianCalendar CALENDAR = new GregorianCalendar();

    public static int getDay() {
        return CALENDAR.get(Calendar.DAY_OF_MONTH);
    }
    
    public static int getMonth(){
        return CALENDAR.get(Calendar.MONTH);
    }
    
    public static int getYear(){
        return CALENDAR.get(Calendar.YEAR);
    }
    
    public static String dateFormat(Calendar cal){
        DecimalFormat df = new DecimalFormat("00");
        DecimalFormat dff = new DecimalFormat("0000");
        
        String data = "";
        data = df.format(cal.get(Calendar.DAY_OF_MONTH)) + "/";
        data += df.format(cal.get(Calendar.MONTH)+1) + "/";
        data += dff.format(cal.get(Calendar.YEAR));
        
        return data;
    }
}
