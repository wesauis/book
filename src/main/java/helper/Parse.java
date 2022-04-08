package helper;

import java.sql.Date;
import java.util.logging.Logger;

/**
 *
 * @author w2gam
 */
public class Parse {

    public static Integer asInt(String number) {
        if (number == null) {
            return null;
        }

        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException err) {
            return null;
        }
    }
    
    public static Date asDate(String date) {
        if (date == null) {
            return null;
        }

        try {
            return Date.valueOf(date);
        } catch (IllegalArgumentException err) {
            return null;
        }
    }
    
    public static String asString(String string) {
        if (string == null) {
            return null;
        }
        
        if (string.isBlank()) {
            return null;
        }
        
        return string.trim();
    }
    
}
