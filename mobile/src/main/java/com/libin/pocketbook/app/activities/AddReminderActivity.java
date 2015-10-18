package com.libin.pocketbook.app.activities;

import android.content.pm.ApplicationInfo;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.libin.pocketbook.app.activities.BaseActivity;
import com.libin.pocketbook.app.R;
import com.libin.pocketbook.app.database.ReminderExecutor;
import com.libin.pocketbook.app.fragments.AppPickerFragment;
import com.libin.pocketbook.app.fragments.AppPickerFragment_;
import com.libin.pocketbook.app.fragments.DatePickerFragment;
import com.libin.pocketbook.app.model.PocketReminder;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by libin on 8/5/14.
 */
@EActivity(R.layout.activity_add_reminder)
public class AddReminderActivity extends BaseActivity
        implements AppPickerFragment.OnAppPickedListener , DatePickerFragment.OnDateSelectListener{

    @ViewById(R.id.titleEditTextView)
    EditText titleEditTextView;

    @ViewById(R.id.descriptionEditTextView)
    EditText descriptionEditTextView;

    @ViewById(R.id.urlEditTextView)
    EditText urlEditTextView;

    @ViewById(R.id.launcherButton)
    ImageButton launcherButton;

    @ViewById(R.id.saveReminderButton)
    Button saveReminderButton;

    @ViewById(R.id.datePickerButton)
    Button datePickerButton;

    @Bean
    ReminderExecutor reminderExecutor;

    @ViewById(R.id.fancyToolbar)
    Toolbar mToolbar;


    @AfterViews
    public void onViewSetup() {
        setSupportActionBar(mToolbar);
    }

    @Click(R.id.saveReminderButton)
    public void onSaveReminderButtonClicked() {
        // make sure the inputs are valid
        String title = titleEditTextView.getText().toString();
        String url = urlEditTextView.getText().toString();
        String description = descriptionEditTextView.getText().toString();

        if(TextUtils.isEmpty(title) || datePickerButton.getTag() == null) {
            showShortToast(getString(R.string.check_all_inputs));
            return;
        }

        PocketReminder pocketReminder = new PocketReminder();
        pocketReminder.setTitle(title);
        String packageName = (String) launcherButton.getTag();

        if(!TextUtils.isEmpty(packageName)) pocketReminder.setPackageName(packageName);
        if(!TextUtils.isEmpty(url)) pocketReminder.setUrl(url);
        if(!TextUtils.isEmpty(description)) pocketReminder.setDescription(description);
        pocketReminder.setDate((String) datePickerButton.getTag());
        // add to data base
        reminderExecutor.add(pocketReminder);
        showShortToast(getString(R.string.new_reminder_added));
        //finish the activity
        finish();
    }

    @Click(R.id.launcherButton)
    public void onLauncherButtonClicked() {
        AppPickerFragment_ appPickerFragment_ = new AppPickerFragment_();
        appPickerFragment_.setOnAppPickedListener(this);
        appPickerFragment_.show(getSupportFragmentManager(), "dialog");
    }

    @Click(R.id.datePickerButton)
    public void onDatePickerButtonClicked() {
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setOnDateSelectListener(this);
        datePickerFragment.show(getSupportFragmentManager(), "DatePickerDialog");
    }

    @Override
    public void onAppPicked(ApplicationInfo applicationInfo) {
        // select the picked app launcher icon
        launcherButton.setTag(applicationInfo.processName);
        launcherButton.setImageDrawable(getPackageManager()
                .getApplicationIcon(applicationInfo));
    }


    @Override
    public void onDateSelected(String date) {
        datePickerButton.setText(date);
        datePickerButton.setTag(date);
    }
}
