package com.libin.pocketbook.app.view;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.libin.pocketbook.app.R;
import com.libin.pocketbook.app.model.PocketReminder;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by libinsalal on 10/18/15.
 */
@EViewGroup(R.layout.reminder_list_item)
public class ReminderView extends RelativeLayout{

    @ViewById(R.id.reminderItemTitle)
    TextView mReminderItemTitleView;

    @ViewById(R.id.reminderItemUrl)
    TextView mReminderItemUrl;

    @ViewById(R.id.reminderItemLaunchView)
    ImageButton mReminderItemLaunchView;

    private final Context mContext;

    public ReminderView(Context context) {
        super(context);
        mContext = context;
    }

    public ReminderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public ReminderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    public void bindValues(final PocketReminder pocketReminder , final OnReminderViewListener itemLaunchViewListener){

        mReminderItemTitleView.setText(pocketReminder.getTitle());
        mReminderItemUrl.setText(pocketReminder.getUrl());
        try {
            mReminderItemLaunchView.setImageDrawable(
                    mContext.getPackageManager().getApplicationIcon(pocketReminder.getPackageName()));
        } catch (PackageManager.NameNotFoundException e) {
            // FIXME
        }
        mReminderItemLaunchView.setTag(pocketReminder);
        mReminderItemLaunchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(itemLaunchViewListener != null)
                    itemLaunchViewListener.onItemLaunchViewClicked(pocketReminder);
            }
        });
    }


    public interface OnReminderViewListener {
        public void onItemLaunchViewClicked(PocketReminder pocketReminder);
    }
}
