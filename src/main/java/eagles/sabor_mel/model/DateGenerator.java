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
    
    public static String getMonthName(int month){
        String out = "";
        
        switch(month){
            case 0:
                out = "Janeiro";
                break;
            case 1:
                out = "Fevereiro";
                break;
            case 2:
                out = "Março";
                break;
            case 3:
                out = "Abril";
                break;
            case 4:
                out = "Maio";
                break;
            case 5:
                out = "Junho";
                break;
            case 6:
                out = "Julho";
                break;
            case 7:
                out = "Agosto";
                break;
            case 8:
                out = "Setembro";
                break;
            case 9:
                out = "Outubro";
                break;
            case 10:
                out = "Novembro";
                break;
            case 11:
                out = "Dezembro";
                break;
        }
        
        return out;
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
    
    public static Calendar getCalendar(String data){
        int dia   = Integer.parseInt(data.substring(0, 2));
        int mes   = Integer.parseInt(data.substring(3, 5));
        int ano   = Integer.parseInt(data.substring(6, 10));
        
        Calendar c = Calendar.getInstance();
        
        c.set(ano, mes-1, dia);
         
        return c;
    }
    
    public static String getReverseData(Calendar cal){
        DecimalFormat df = new DecimalFormat("00");
        DecimalFormat dff = new DecimalFormat("0000");
        
        String data = "";
        data = df.format(cal.get(Calendar.DAY_OF_MONTH)) + "/";
        data += df.format(cal.get(Calendar.MONTH)+1) + "/";
        data += dff.format(cal.get(Calendar.YEAR));
        
        return data;
    }
}