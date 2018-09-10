package time;

import static java.time.temporal.ChronoField.DAY_OF_WEEK;

import java.time.Clock;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.ZoneId;

/**
 * @author liqingyun
 * @date 2018/8/16
 * 自然时间工具，计算当前时间的月、周、日的开始或者结束时间
 */
public class NaturalTimeUtil {

    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();

    public static long nextMonthStartTimeMilli(long timestamp) {
        Clock clock = Clock.fixed(Instant.ofEpochMilli(timestamp), DEFAULT_ZONE_ID);
        Month nextMonth = MonthDay.now(clock).getMonth().plus(1);
        return LocalDate.now(clock).with(MonthDay.of(nextMonth, 1)).atStartOfDay(DEFAULT_ZONE_ID)
                .toInstant().toEpochMilli();
    }

    public static long nextWeekStartTimeMilli(long timestamp) {
        return LocalDate.now(Clock.fixed(Instant.ofEpochMilli(timestamp), DEFAULT_ZONE_ID))
                .plusWeeks(1).with(DayOfWeek.MONDAY).atStartOfDay(DEFAULT_ZONE_ID).toInstant()
                .toEpochMilli();
    }

    public static long tomorrowStartTimeMilli(long timestamp) {
        return LocalDate.now(Clock.fixed(Instant.ofEpochMilli(timestamp), DEFAULT_ZONE_ID))
                .plusDays(1).atStartOfDay(DEFAULT_ZONE_ID).toInstant().toEpochMilli();
    }

    public static long nextMonthLeftTimeMilli() {
        long now = System.currentTimeMillis();
        return nextMonthStartTimeMilli(now) - now;
    }

    public static long nextWeekLeftTimeMilli() {
        long now = System.currentTimeMillis();
        return nextWeekStartTimeMilli(now) - now;
    }

    public static long tomorrowLeftTimeMilli() {
        long now = System.currentTimeMillis();
        return tomorrowStartTimeMilli(now) - now;
    }

    public static long dayStartTime(long time) {
        LocalDateTime dt = LocalDateTime.ofInstant(Instant.ofEpochMilli(time),
                ZoneId.systemDefault());
        return dayStartTime(dt);
    }

    public static long dayStartTime() {
        return dayStartTime(LocalDateTime.now());
    }

    public static long weekStartTime(long time) {
        LocalDateTime dt = LocalDateTime.ofInstant(Instant.ofEpochMilli(time),
                ZoneId.systemDefault());
        return weekStartTime(dt);
    }

    public static long weekStartTime() {
        return weekStartTime(LocalDateTime.now());
    }

    public static long monthStartTime(long time) {
        LocalDateTime dt = LocalDateTime.ofInstant(Instant.ofEpochMilli(time),
                ZoneId.systemDefault());
        return monthStartTime(dt);
    }

    public static long monthStartTime() {
        return monthStartTime(LocalDateTime.now());
    }

    private static long dayStartTime(LocalDateTime dt) {
        return dt.withHour(0).withMinute(0).withSecond(0).withNano(0).atZone(DEFAULT_ZONE_ID)
                .toInstant().toEpochMilli();
    }

    private static long weekStartTime(LocalDateTime dt) {
        return dt.with(DAY_OF_WEEK, 1).withHour(0).withMinute(0).withSecond(0).withNano(0)
                .atZone(DEFAULT_ZONE_ID).toInstant().toEpochMilli();
    }

    private static long monthStartTime(LocalDateTime dt) {
        return dt.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0)
                .atZone(DEFAULT_ZONE_ID).toInstant().toEpochMilli();
    }

    public static void main(String[] args) {
        System.out.println(nextMonthStartTimeMilli(System.currentTimeMillis()));
        System.out.println(nextMonthLeftTimeMilli());
    }
}