package com.invisee.finvest.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by fajarfatur on 11/5/15.
 */
public class PixelUtil {

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static int pxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    /*
    ViewTreeObserver vto = ivLogo.getViewTreeObserver();
    vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            removeOnGlobalLayoutListener(ivLogo, this);
            int width  = ivLogo.getMeasuredWidth();
            int height = ivLogo.getMeasuredHeight();
            Timber.i("width : %s", String.valueOf(width));
            Timber.i("height : %s", String.valueOf(height));
            Timber.i("width dp : %s", String.valueOf(PixelUtil.pxToDp(SplashScreenActivity.this, width)));
            Timber.i("height dp : %s", String.valueOf(PixelUtil.pxToDp(SplashScreenActivity.this, height)));
        }
    });


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void removeOnGlobalLayoutListener(View v, ViewTreeObserver.OnGlobalLayoutListener listener){
        if (Build.VERSION.SDK_INT < 16) {
            v.getViewTreeObserver().removeGlobalOnLayoutListener(listener);
        } else {
            v.getViewTreeObserver().removeOnGlobalLayoutListener(listener);
        }
    }
    */
}
