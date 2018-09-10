package time;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author liqingyun
 * @date 2018/8/16
 */
public class DateTimeFormatterUtil {

    public static String epochMilli2IsoDate(long epochMilli) {
        LocalDateTime dt = LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli),
                ZoneId.systemDefault());
        return dt.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public static long isoDate2EpochMilli(String timeString) {
        LocalDateTime dt = LocalDateTime.parse(timeString, DateTimeFormatter.ISO_LOCAL_DATE);
        return dt.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }
}
