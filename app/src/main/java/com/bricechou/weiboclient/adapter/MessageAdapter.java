package com.bricechou.weiboclient.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Binding the current login user message list data.
 *
 * @author BriceChou
 * @datetime 16-6-20 15:21
 * @TODO This is not important feature. So we finish it at the end of the project.
 */
public class MessageAdapter extends BaseAdapter {
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return convertView;
    }

    /**
     * To manage the Message page layout resource.
     * Only create this class once time.
     *
     * @author BriceChou
     * @datetime 16-6-12 11:39
     */
    public static class MessageViewHolder {

    }
}
