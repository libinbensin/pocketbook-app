package com.pocketbook.app.adapter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.pocketbook.app.R;
import com.pocketbook.app.model.PocketReminder;

/**
 * Created by libin on 8/5/14.
 */
public class ReminderAdapter extends ArrayAdapter<PocketReminder> {

    private final Context mContext;
    private OnItemLaunchViewListener mOnItemLaunchViewListener;

    public ReminderAdapter(Context context, int resource) {
        super(context, resource);
        mContext = context;
    }

    @Override
    public View getView(int position , View contentView , ViewGroup parent) {

        View rowView = contentView;
        ReminderViewHolder reminderViewHolder;
        PocketReminder pocketReminder = getItem(position);

        if(rowView == null) {
            rowView = LayoutInflater.from(mContext).inflate(R.layout.reminder_list_item, null);
            reminderViewHolder = new ReminderViewHolder();
            reminderViewHolder.reminderItemTitleView = (TextView) rowView.findViewById(R.id.reminderItemTitle);
            reminderViewHolder.reminderItemUrl = (TextView) rowView.findViewById(R.id.reminderItemUrl);
            reminderViewHolder.reminderItemLaunchView = (ImageButton)rowView.findViewById(R.id.reminderItemLaunchView);

            rowView.setTag(reminderViewHolder);
        } else {
            reminderViewHolder = (ReminderViewHolder) rowView.getTag();
        }

        reminderViewHolder.reminderItemTitleView.setText(pocketReminder.getTitle());
        reminderViewHolder.reminderItemUrl.setText(pocketReminder.getUrl());
        try {
            reminderViewHolder.reminderItemLaunchView.setImageDrawable(
                    mContext.getPackageManager().getApplicationIcon(pocketReminder.getPackageName()));
        } catch (PackageManager.NameNotFoundException e) {
            // FIXME
        }
        reminderViewHolder.reminderItemLaunchView.setTag(pocketReminder);
        reminderViewHolder.reminderItemLaunchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnItemLaunchViewListener != null)
                    mOnItemLaunchViewListener.onItemLaunchViewClicked((PocketReminder) view.getTag());
            }
        });


        return rowView;
    }


    private static class ReminderViewHolder {
        public TextView reminderItemTitleView;
        public TextView reminderItemDescriptionView;
        public ImageButton reminderItemLaunchView;
        public TextView reminderItemUrl;
    }

    public void setOnItemLaunchViewListener(OnItemLaunchViewListener ln) {
        mOnItemLaunchViewListener = ln;
    }

    public interface OnItemLaunchViewListener {
        public void onItemLaunchViewClicked(PocketReminder pocketReminder);
    }
}
