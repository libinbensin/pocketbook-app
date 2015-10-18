package com.libin.pocketbook.app.activities;

import android.os.Bundle;
import android.view.MotionEvent;

import com.libin.pocketbook.app.R;
import com.libin.pocketbook.app.fragments.CalendarFragment;
import com.libin.pocketbook.app.fragments.CalendarFragment_;
import com.libin.pocketbook.app.fragments.ReminderFragment;
import com.libin.pocketbook.app.fragments.ReminderFragment_;

import org.androidannotations.annotations.EActivity;

/**
 * Created by libin on 7/30/14.
 */
@EActivity(R.layout.activity_landing)
public class LandingActivity extends BaseActivity
        implements CalendarFragment.OnDateSelectListener , ReminderFragment.OnFakeViewTouchListener{

    CalendarFragment_ calendarFragment_;
    ReminderFragment_ reminderFragment_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
            calendarFragment_ = new CalendarFragment_();
            showCalendarFragment(calendarFragment_ , CalendarFragment.class.getSimpleName());
            reminderFragment_ = new ReminderFragment_();
            showReminderFragment(reminderFragment_, ReminderFragment.class.getSimpleName());
        } else {
            calendarFragment_ = (CalendarFragment_) getSupportFragmentManager()
                    .findFragmentByTag(CalendarFragment.class.getSimpleName());
            reminderFragment_ = (ReminderFragment_) getSupportFragmentManager()
                    .findFragmentByTag(ReminderFragment.class.getSimpleName());
        }
        calendarFragment_.setOnDateSelectListener(this);
        reminderFragment_.setOnFakeViewTouchListener(this);
    }

    private void showReminderFragment(ReminderFragment_ reminderFragment , String name) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.ReminderFragmentLayout , reminderFragment , name)
                .commit();
    }

    private void showCalendarFragment(CalendarFragment_ fragment , String name) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.CalendarFragmentLayout ,fragment , name)
                .commit();
        fragment.setOnDateSelectListener(this);
    }

    @Override
    public void onDateSelected(String date) {
        reminderFragment_.loadReminderItems(date);
    }

    @Override
    public void onFakeViewTouch(MotionEvent motionEvent) {
        if(calendarFragment_ != null && calendarFragment_.getView() != null)
            calendarFragment_.getView().dispatchTouchEvent(motionEvent);
    }
}
