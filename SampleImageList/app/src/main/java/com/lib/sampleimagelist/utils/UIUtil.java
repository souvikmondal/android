package com.lib.sampleimagelist.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.TypedValue;

/**
 * Created by souvik on 7/11/2016.
 */
public final class UIUtil {

    private static ProgressDialog mProgressDialog;

    public static void displayBusyIndicator(String title, String message, Context context) {
        cancelBusyIndicator();
        mProgressDialog = ProgressDialog.show(context, title, message);
    }

    public static void cancelBusyIndicator() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
            mProgressDialog = null;
        }
    }

    private int getPixelFromDP(Context context, int dp) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, context.getResources().getDisplayMetrics());
        return (int)px;
    }

}
