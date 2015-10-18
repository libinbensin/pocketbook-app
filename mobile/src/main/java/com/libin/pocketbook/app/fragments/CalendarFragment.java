package com.libin.pocketbook.app.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.MotionEvent;
import android.widget.DatePicker;

import com.libin.pocketbook.app.R;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;

/**
 * Created by libin on 7/30/14.
 */
@EFragment(R.layout.calendar_fragment)
public class CalendarFragment extends Fragment implements DatePicker.OnDateChangedListener{

    private OnDateSelectListener mOnDateSelectListener;

    @ViewById(R.id.datePickerView)
    DatePicker datePickerView;

    @AfterInject
    public void  onViewInject() {

    }

    @AfterViews
    public void onViewSetup() {
        Calendar calendar = Calendar.getInstance();

        datePickerView.init(calendar.get(Calendar.YEAR) ,
                calendar.get(Calendar.MONTH) ,
                calendar.get(Calendar.DAY_OF_MONTH) , this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public void onDateChanged(DatePicker datePicker, int i, int i2, int i3) {
        StringBuilder dateBuilder = new StringBuilder();
        dateBuilder.append(datePicker.getMonth());
        dateBuilder.append(datePicker.getDayOfMonth());
        dateBuilder.append(datePicker.getYear());
        if(mOnDateSelectListener != null)
            mOnDateSelectListener.onDateSelected(dateBuilder.toString());

    }

    public void setOnDateSelectListener(OnDateSelectListener listener) {
        mOnDateSelectListener = listener;
    }

    public interface OnDateSelectListener {
        public void onDateSelected(String date);
    }
}
