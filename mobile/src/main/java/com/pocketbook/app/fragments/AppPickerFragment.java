package com.pocketbook.app.fragments;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.pocketbook.app.R;
import com.pocketbook.app.utils.AppUtil;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by libin on 8/5/14.
 */
@EFragment(R.layout.app_picker_fragment_dialog)
public class AppPickerFragment extends android.support.v4.app.DialogFragment
        implements AdapterView.OnItemClickListener{

    @ViewById(R.id.gridView)
    GridView gridView;

    @SystemService
    LayoutInflater layoutInflater;

    private  ArrayList<ApplicationInfo> packages = new ArrayList<ApplicationInfo>();
    private OnAppPickedListener mOnAppPickedListener;
    @AfterViews
    public void onSetupView() {
        getDialog().setTitle(R.string.pick_app_title);
        gridView.setOnItemClickListener(this);
        loadInstalledApps();
    }

    private void loadInstalledApps() {


        //get a list of installed apps.
        List<ApplicationInfo> installedApplications = getActivity()
                .getPackageManager()
                .getInstalledApplications(PackageManager.GET_META_DATA);
        // sort the app by display name
        Collections.sort(installedApplications, new ApplicationInfo.DisplayNameComparator(
                getActivity().getPackageManager()));
        // filter the system packages
        for (ApplicationInfo applicationInfo : installedApplications) {
            if ( ( (applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) != true) {
                packages.add(applicationInfo);
                //pickerAdapter.add(applicationInfo);
            }
        }
        AppPickerAdapter pickerAdapter =
                new AppPickerAdapter(packages);
        gridView.setAdapter(pickerAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        dismiss();
        if(mOnAppPickedListener != null)
            mOnAppPickedListener.onAppPicked(packages.get(position));
    }

    private class AppPickerAdapter extends BaseAdapter {

        final ArrayList<ApplicationInfo> applicationInfos;

        public AppPickerAdapter(ArrayList<ApplicationInfo> applicationInfos) {
            super();
            this.applicationInfos = applicationInfos;
        }

        @Override
        public int getCount() {
            return applicationInfos.size();
        }

        @Override
        public Object getItem(int i) {
            return applicationInfos.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        public View getView(int position , View contentView , ViewGroup parent) {
            View rowView = contentView;
            ViewHolder viewHolder;
            ApplicationInfo applicationInfo =  this.applicationInfos.get(position);//getItem(position);
            if(rowView == null ) {
                rowView = layoutInflater.inflate(R.layout.app_picker_list_item, null);
                viewHolder = new ViewHolder();
                viewHolder.appNameView = (TextView) rowView.findViewById(R.id.appNameView);
                viewHolder.appLauncherIcon = (ImageView) rowView.findViewById(R.id.appLauncherIcon);
                rowView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) rowView.getTag();
            }
            viewHolder.appNameView.setText(applicationInfo.loadLabel(getActivity().getPackageManager()).toString());
            viewHolder.appLauncherIcon.setImageDrawable(getActivity()
                    .getPackageManager()
                    .getApplicationIcon(applicationInfo));

            //AppUtil.setScaleAnimation(rowView , 300);
            setAnimation(rowView);
            return rowView;
        }
    }

    private static class ViewHolder {
        private TextView appNameView;
        private ImageView appLauncherIcon;
    }

    public void setOnAppPickedListener(OnAppPickedListener ln) {
        mOnAppPickedListener = ln;
    }

    public interface OnAppPickedListener {
        public void onAppPicked(ApplicationInfo applicationInfo);
    }

    public void setAnimation(View view) {
        if(view != null) {
            Animation slide = AnimationUtils.loadAnimation(
                    getActivity().getApplicationContext(),R.anim.view_slide_up_anim);
            view.startAnimation(slide);
        }
    }
}
