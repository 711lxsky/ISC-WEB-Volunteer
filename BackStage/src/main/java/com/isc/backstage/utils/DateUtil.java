package com.isc.backstage.utils;

import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.Calendar;

/**
 * @Author: 711lxsky
 * @Date: 2023-12-20
 */
@Component
@Data
@Getter
public class DateUtil {

    public Calendar getCurrentTime(){
        return Calendar.getInstance();
    }


}
