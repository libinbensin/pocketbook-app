package com.pocketbook.app.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by libin on 8/5/14.
 */
public  class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private OnDateSelectListener mOnDateSelectListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        dismiss();
        StringBuilder dateBuilder = new StringBuilder();
        dateBuilder.append(month);
        dateBuilder.append(day);
        dateBuilder.append(year);
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
