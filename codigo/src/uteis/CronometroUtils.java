package uteis;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CronometroUtils {

    // String('03:00') -> Integer(180)
    public static Integer MinSecStringToMinSecInteger (String segundos) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        Date newData = sdf.parse(segundos);
        return newData.getMinutes() * 60 + newData.getSeconds();
    }

    // Integer(180) -> String('03:00')
    public static String MinSecIntegerToMinSecString (Integer segundos) {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        Date newData = new Date(0, 0, 0, 0, 0, segundos);
        String dateString = sdf.format(newData).toString();
        return dateString;
    }


}
