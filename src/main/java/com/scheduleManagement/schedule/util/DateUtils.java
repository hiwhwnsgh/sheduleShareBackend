package com.scheduleManagement.schedule.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class DateUtils {
    // 날짜를 파싱하는 메서드 추가
    public static Date parseDateString(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        java.util.Date parsedDate = dateFormat.parse(dateString);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parsedDate);
        calendar.add(Calendar.DAY_OF_MONTH, 1); // 날짜를 하루 추가

        java.util.Date updatedDate = calendar.getTime();

        return new Date(updatedDate.getTime());
    }
    public static String formatDate(Date date) {
        // java.sql.Date에서 날짜 정보 추출
        return date.toString();
    }
    public static String getInfo(java.util.Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateFormat.format(date); // 날짜를 문자열로 포맷

        // 포맷된 날짜를 클라이언트로 반환
        return formattedDate;
    }

}
