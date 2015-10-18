package com.pocketbook.app.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.pocketbook.app.R;
import com.pocketbook.app.ReminderUtils;
import com.pocketbook.app.activities.AddReminderActivity_;
import com.pocketbook.app.activities.BaseActivity;
import com.pocketbook.app.adapter.ReminderAdapter;
import com.pocketbook.app.database.ReminderExecutor;
import com.pocketbook.app.model.PocketReminder;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;

import java.util.HashMap;

/**
 * Created by libin on 8/5/14.
 */
@EFragment(R.layout.reminder_list_fragment)
public class ReminderFragment extends Fragment implements ReminderAdapter.OnItemLaunchViewListener{

    @SystemService
    LayoutInflater layoutInflater;

    @ViewById(R.id.reminderListView)
    ListView reminderListView;

    private ReminderAdapter reminderAdapter;

    @Bean
    ReminderExecutor reminderExecutor;

    @Bean
    ReminderUtils reminderUtils;

    private OnFakeViewTouchListener mOnFakeViewTouchListener;

    @AfterViews
    public void setupView() {
        addFakeView();
        addReminderView();
        reminderAdapter = new ReminderAdapter(getActivity() , R.layout.reminder_list);
        reminderAdapter.setOnItemLaunchViewListener(this);
        reminderListView.setAdapter(reminderAdapter);
        loadReminderItems(reminderUtils.getCurrentDateString());
    }

    private void addFakeView() {
        View fakeLayout = layoutInflater.inflate(R.layout.fake_calendar_layout , null);
        fakeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                if(mOnFakeViewTouchListener != null)
                    mOnFakeViewTouchListener.onFakeViewTouch(motionEvent);
                return true;
            }
        });
        reminderListView.addHeaderView(fakeLayout , null , false);
    }

    private void addReminderView() {
        View addReminderView = layoutInflater.inflate(R.layout.add_reminder , null);
        addReminderView.findViewById(R.id.addReminderButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddReminderActivity_
                        .intent(getActivity())
                        .start();
            }
        });
        reminderListView.addHeaderView(addReminderView, null, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void loadReminderItems(String date) {

        if(reminderAdapter != null && !reminderAdapter.isEmpty()) {
            reminderAdapter.clear();
            reminderListView.setAdapter(null);
            reminderAdapter = new ReminderAdapter(getActivity() , R.layout.reminder_list);
            reminderListView.setAdapter(reminderAdapter);
            reminderAdapter.setOnItemLaunchViewListener(this);
        }
        HashMap<Long, PocketReminder> reminderHashMap = reminderExecutor.getAll(date);
        if(reminderHashMap != null && reminderHashMap.size() > 0) {
            reminderAdapter.addAll(reminderHashMap.values());
        }
    }


    @Override
    public void onItemLaunchViewClicked(PocketReminder pocketReminder) {
        // launch the app
        Intent launchIntent = getActivity()
                .getPackageManager().getLaunchIntentForPackage(pocketReminder.getPackageName());
        if(launchIntent != null) {
            startActivity(launchIntent);
        }else {
            ((BaseActivity)getActivity()).showShortToast(getString(R.string.unable_to_launch_app));
        }
    }

    public void setOnFakeViewTouchListener(OnFakeViewTouchListener listener) {
        mOnFakeViewTouchListener = listener;
    }

    public interface OnFakeViewTouchListener {
        public void onFakeViewTouch(MotionEvent motionEvent);
    }
}
