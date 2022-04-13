package com.example.bghub.ui.cardStack;

import android.content.Context;

import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;

public class CustomCardStackLayoutManager extends CardStackLayoutManager {
    public CustomCardStackLayoutManager(Context context) {
        super(context);
    }

    public CustomCardStackLayoutManager(Context context, CardStackListener listener) {
        super(context, listener);
    }
}
