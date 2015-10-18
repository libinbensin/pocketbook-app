package com.libin.pocketbook.app.utils;


import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.libin.pocketbook.app.R;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

/**
 * Created by salall on 9/7/14.
 */
public final class AppUtil {

    private static Toast mToast;

    private AppUtil() {
    }

    public static final String DEFAULT_PLAYLIST = "Default";

    public static final String PLAYLIST_NAME = "PlaylistName";

    public static String toLowerCase(String s) {
        if (s == null) {
            return "";
        }
        return s.toLowerCase(Locale.US);
    }

    public static boolean hasValue(String s) {
        return ((s != null && s.length() > 0) ? true : false);
    }

    public static void setImageAnimation(ImageView imageView) {
        setImageAnimation(imageView, 2000);
    }

    public static void setImageAnimation(ImageView imageView, int duration) {
        ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1,
                ScaleAnimation.RELATIVE_TO_SELF, .5f,
                ScaleAnimation.RELATIVE_TO_SELF, .5f);
        scale.setDuration(duration);
        imageView.startAnimation(scale);
    }

    public static void setScaleAnimation(View view, int duration) {
        ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1,
                ScaleAnimation.RELATIVE_TO_SELF, .5f,
                ScaleAnimation.RELATIVE_TO_SELF, .5f);
        scale.setDuration(duration);
        view.startAnimation(scale);
    }

    public static void hideAnimation(View view , int duration) {
        Animation animation = new TranslateAnimation(0,0,0, 0);
        animation.setDuration(duration);
        animation.setFillAfter(true);
        view.startAnimation(animation);
    }

    public static int getLength(String s) {
        return (s != null ? s.length() : 0);
    }

    public static String toString(byte[] bytes) {
        try {
            String s = new String(bytes, "UTF-8");
            return s;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] toBytes(String s) {
        try {
            return s.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static double toDouble(String s) {
        if (hasValue(s)) {
            return Double.valueOf(s);
        } else {
            return 0;
        }
    }

    public static Animation getScaleAninmation(int duration) {
        ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1,
                ScaleAnimation.RELATIVE_TO_SELF, .5f,
                ScaleAnimation.RELATIVE_TO_SELF, .5f);
        scale.setDuration(duration);
        return scale;
    }

    public static int getDisplayWidth(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();

        display.getMetrics(displayMetrics);
        return displayMetrics.widthPixels;///displayMetrics.densityDpi;
    }

    private static int getDeviceLayoutSize(Context context) {
        int screenLayout = context.getResources().getConfiguration().screenLayout;
        screenLayout &= Configuration.SCREENLAYOUT_SIZE_MASK;

        switch (screenLayout) {
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                return 1;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                return 2;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                return 3;
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                return 4;
            default:
                return 3;
        }
    }


}
