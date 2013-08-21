package uteis;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Uteis {

//    public static byte[] getRandomArray(int paramInt, boolean paramBoolean) {
//    }
    public static ArrayList<Integer> initArrayList(Integer... integers) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (Integer i : integers) {
            list.add(i);
        }
        return list;
    }

    public static String MinSecIntegerToMinSecString(Integer segundos) {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        Date newData = new Date(0, 0, 0, 0, 0, segundos);
        String dateString = sdf.format(newData).toString();
        return dateString;
    }

    public static String inputStreamToString(InputStream in) {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        try {
            for (int n; (n = in.read(b)) != -1;) {
                out.append(new String(b, 0, n));
            }
        } catch (IOException ex) {
            Logger.getLogger(Uteis.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out.toString();
    }
}
