package com.ibas.brta.vehims.util;

import com.ibas.brta.vehims.exception.BadRequestException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static Date getDateFromString(String date, String format) {
        try {
            SimpleDateFormat df = new SimpleDateFormat(format);
            return df.parse(date);
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return null;
    }

    public static String datetoReportString(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if (size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }
}
