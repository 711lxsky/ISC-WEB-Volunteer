package com.isc.backstage.utils;

import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * @Author: 711lxsky
 * @Date: 2023-12-20
 */
@Component
@Data
@Getter
public class DateUtil {

    public Calendar getCurrentTimeForCalendar(){
        return Calendar.getInstance();
    }

    public Date getCurrentTimeForDate(){
        return cn.hutool.core.date.DateUtil.date(Calendar.getInstance());
    }
}
