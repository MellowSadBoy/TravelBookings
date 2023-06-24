package hcmuaf.edu.vn.bookingtravel.api.base.utils;

import hcmuaf.edu.vn.bookingtravel.api.model.tour.enums.TimeHowLong;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static Date getStartDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (null != date) {
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            calendar.set(Calendar.SECOND, 0);
            return calendar.getTime();
        }
        return null;
    }

    public static Date getEndDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        if (null != date) {
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            return calendar.getTime();
        }
        return null;
    }

    public static Date augmentDay(Date date, int augmentDay) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, augmentDay);
        return calendar.getTime();
    }
    public static Date augmentDaySeconds(Date date, long augmentSeconds) {
        // Khởi tạo một đối tượng Calendar
        Calendar calendar = Calendar.getInstance();
        // Đặt ngày của Calendar là ngày cần tăng
        calendar.setTime(date);
        // Cộng thêm số giây vào ngày
        calendar.add(Calendar.SECOND, (int) augmentSeconds);
        // Lấy ngày mới sau khi tăng số giây
        return calendar.getTime();
    }


}
