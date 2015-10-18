package com.libin.pocketbook.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.libin.pocketbook.app.model.PocketReminder;
import com.libin.pocketbook.app.view.ReminderView;
import com.libin.pocketbook.app.view.ReminderView_;

import java.util.List;

/**
 * Created by libinsalal on 10/18/15.
 */
public class ReminderRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<PocketReminder> mPocketReminderList;
    ReminderView.OnReminderViewListener mOnReminderViewListener;
    public ReminderRecyclerAdapter(List<PocketReminder> pocketReminderList , ReminderView.OnReminderViewListener itemLaunchViewListener) {
        mPocketReminderList = pocketReminderList;
        mOnReminderViewListener = itemLaunchViewListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ReminderView reminderView = ReminderView_.build(parent.getContext());

        return new ViewHolder(reminderView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ReminderView reminderView = (ReminderView) holder.itemView;
        reminderView.bindValues(mPocketReminderList.get(position), mOnReminderViewListener);
    }

    @Override
    public int getItemCount() {
        return mPocketReminderList != null ? mPocketReminderList.size() : 0;
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(ReminderView itemView) {
            super(itemView);
        }
    }
}
