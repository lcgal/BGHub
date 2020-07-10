package com.example.bghub.Utils;

import android.os.SystemClock;
import android.view.View;

public abstract class OnSingleClickListener  implements View.OnClickListener {

    private long mLastClickTime = 0;

    public abstract void onSingleClick(View v);

    @Override
    public void onClick(View view) {

        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();

        onSingleClick(view);
    }
}
