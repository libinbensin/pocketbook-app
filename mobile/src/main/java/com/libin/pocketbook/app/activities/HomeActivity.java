package com.libin.pocketbook.app.activities;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.libin.pocketbook.app.R;
import com.libin.pocketbook.app.ReminderUtils;
import com.libin.pocketbook.app.adapter.ReminderAdapter;
import com.libin.pocketbook.app.adapter.ReminderRecyclerAdapter;
import com.libin.pocketbook.app.database.ReminderExecutor;
import com.libin.pocketbook.app.model.PocketReminder;
import com.libin.pocketbook.app.utils.DividerItemDecoration;
import com.libin.pocketbook.app.view.ReminderView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by libinsalal on 10/18/15.
 */
@EActivity(R.layout.activity_home)
public class HomeActivity extends BaseActivity implements ReminderView.OnReminderViewListener{

    @ViewById(R.id.collapsingToolbar)
    CollapsingToolbarLayout mCollapsingToolbar;

    @ViewById(R.id.fancyToolbar)
    Toolbar mToolbar;

    @ViewById(R.id.reminderListView)
    RecyclerView mReminderListView;

    @Bean
    ReminderExecutor reminderExecutor;

    @Bean
    ReminderUtils reminderUtils;

    @AfterViews
    protected void onAfterViews() {
        setSupportActionBar(mToolbar);
        mReminderListView.setHasFixedSize(true);
        mReminderListView.addItemDecoration(new DividerItemDecoration(this));

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mReminderListView.setLayoutManager(mLayoutManager);
        loadReminderItems(reminderUtils.getCurrentDateString());
    }

    public void loadReminderItems(String date) {

        List<PocketReminder> pocketReminderList = new ArrayList<>();

        HashMap<Long, PocketReminder> reminderHashMap = reminderExecutor.getAll(date);
        if(reminderHashMap != null && reminderHashMap.size() > 0) {
            pocketReminderList.addAll(reminderHashMap.values());
        }

        if(!pocketReminderList.isEmpty()) {
            ReminderRecyclerAdapter reminderAdapter = new ReminderRecyclerAdapter(pocketReminderList , this);
            mReminderListView.setAdapter(reminderAdapter);
        }
    }

    @Click(R.id.floatingActionButton)
    protected void onAddReminderClicked(){
        AddReminderActivity_
                .intent(this)
                .start();
    }

    @Override
    public void onItemLaunchViewClicked(PocketReminder pocketReminder) {
        // launch the app
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage(pocketReminder.getPackageName());
        if(launchIntent != null) {
            startActivity(launchIntent);
        }else {
            showShortToast(getString(R.string.unable_to_launch_app));
        }
    }
}
