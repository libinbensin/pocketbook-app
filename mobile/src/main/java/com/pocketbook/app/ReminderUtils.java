package com.pocketbook.app;

import org.androidannotations.annotations.EBean;

import java.util.Calendar;

/**
 * Created by libin on 8/6/14.
 */
@EBean(scope = EBean.Scope.Singleton)
public class ReminderUtils {

    public String getCurrentDateString() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        StringBuilder dateBuilder = new StringBuilder();
        dateBuilder.append(month);
        dateBuilder.append(day);
        dateBuilder.append(year);
        return dateBuilder.toString();
    }
}
