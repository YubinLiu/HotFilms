package com.example.hotfilm.util;

import android.widget.Toast;

/**
 * Created by yubin on 2017/2/17.
 */

public class ShowToastUtil {

    public static Toast mToast = null;

    public static void showToast(int resId) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApplication.getContext(),
                    resId, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(resId);
        }
        mToast.show();
    }

}
