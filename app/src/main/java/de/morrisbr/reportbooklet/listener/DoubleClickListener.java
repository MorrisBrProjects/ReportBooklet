package de.morrisbr.reportbooklet.listener;

import android.view.View;
import android.widget.AdapterView;

public abstract class DoubleClickListener implements AdapterView.OnItemClickListener {
    private static final long DOUBLE_CLICK_TIME_DELTA = 300;
    long lastClickTime = 0;

    public abstract void onDoubleClick(AdapterView<?> adapterView, View view, int i, long j);
    public abstract void onSingleClick(AdapterView<?> adapterView, View view, int i, long j);

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        long clickTime = System.currentTimeMillis();
        if (clickTime - this.lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
            onDoubleClick(adapterView, view, position, l);
        } else {
            onSingleClick(adapterView, view, position, l);
        }
        this.lastClickTime = clickTime;
    }
}
