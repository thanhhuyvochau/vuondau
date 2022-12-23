package fpt.capstone.vuondau.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.threeten.bp.ZoneOffset;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DayUtil {

    public static Boolean checkDay(String one, String two) throws ParseException {

        Instant datOne = Instant.parse(one);
        String oneSubString = datOne.toString().substring(0, 10).replaceAll("-", " ");


        Instant dayTwo = Instant.parse(two);
        String twoSubString = dayTwo.toString().substring(0, 10).replaceAll("-", " ");


        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy MM dd");


        Date dateOne = myFormat.parse(oneSubString);
        Date dateTwo = myFormat.parse(twoSubString);
        long check = dateOne.getTime() - dateTwo.getTime();
        if (check > 0) {
            return false;
        }
        return true;

    }

    public static  Instant convertDayInstant (Instant day ) {

        ZoneId zone = ZoneId.of("Asia/Ho_Chi_Minh");
        LocalDate localDate = day.atZone(zone).toLocalDate();
        Instant instant1 = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        return instant1.plus(2, ChronoUnit.HOURS);

    }

    public static Boolean checkDate(String one, String two , Integer plusDay ) throws ParseException {

        Instant datOne = Instant.parse(one).plus(plusDay, ChronoUnit.DAYS);
        String oneSubString = datOne.toString().substring(0, 10).replaceAll("-", " ");


        Instant dayTwo = Instant.parse(two);
        String twoSubString = dayTwo.toString().substring(0, 10).replaceAll("-", " ");


        SimpleDateFormat myFormat = new SimpleDateFormat("yyyy MM dd");


        Date dateOne = myFormat.parse(oneSubString);
        Date dateTwo = myFormat.parse(twoSubString);
        long check = dateOne.getTime() - dateTwo.getTime();
        if (check >= 0) {
            return false;
        }
        return true;

    }

    public static LocalDate getDatesBetweenUsingJava8(String startDate, java.time.DayOfWeek dow ) throws ParseException {
        Instant start = Instant.parse(startDate);
        String oneSubString = start.toString().substring(0, 10);
        LocalDate startLocalDate = LocalDate.parse(oneSubString) ;
        LocalDate endDate = startLocalDate.plusDays(8);

        // lấy tất cả ngày / thứ trong 1 tuần : tính từ ngày bắt dầu

        long numOfDaysBetween = ChronoUnit.DAYS.between(startLocalDate, endDate);
        List<LocalDate> collectDay = IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysBetween)
                .mapToObj(startLocalDate::plusDays)
                .collect(Collectors.toList());

        for (LocalDate ld: collectDay) {
            java.time.DayOfWeek dayf = ld.getDayOfWeek();
            System.out.println(dayf);

            if (dow.equals(dayf)) {
                System.out.println(dayf.getValue());
                return ld;
            }
        }
        return null;

    }


}
