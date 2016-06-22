package com.bricechou.weiboclient.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;

import com.bricechou.weiboclient.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sina.weibo.sdk.openapi.models.Status;

/**
 * @author BriceChou
 * @datetime 16-6-21 10:30
 * @TODO To show the grid layout image.
 */
public class StatusGridImagesAdapter extends BaseAdapter {

    private Context mContext;
    private Status mStatus;
    private ImageLoader mImageLoader;

    public StatusGridImagesAdapter(Context context, Status status) {
        this.mContext = context;
        this.mStatus = status;
        mImageLoader = ImageLoader.getInstance();
    }

    @Override
    public int getCount() {
        return mStatus.pic_urls.size();
    }

    @Override
    public String getItem(int position) {
        return mStatus.pic_urls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final StatusGridImagesViewHolder holder;
        if (convertView == null) {
            holder = new StatusGridImagesViewHolder();
            convertView = View.inflate(mContext, R.layout.item_status_grid_image, null);
            holder.mImageViewStatusImage = (ImageView) convertView.findViewById(R.id.iv_status_image);
            convertView.setTag(holder);
        } else {
            holder = (StatusGridImagesViewHolder) convertView.getTag();
        }

        // use the program to calculate the every image width.
        GridView mGridView = (GridView) parent;
        // get the view horizontal spacing and number columns
        int horizontalSpacing = mGridView.getHorizontalSpacing();
        int numColumns = mGridView.getNumColumns();
        // calculate the item width
        // three item have two horizontal spacing line
        int itemWidth = (mGridView.getWidth() - (numColumns - 1) * horizontalSpacing
                - mGridView.getPaddingLeft() - mGridView.getPaddingRight()) / numColumns;

        // set all grid image view with this width value
        // set height = width
        LayoutParams params = new LayoutParams(itemWidth, itemWidth);
        holder.mImageViewStatusImage.setLayoutParams(params);

        // bind the image data into image view
        String urls = getItem(position);
        mImageLoader.displayImage(urls, holder.mImageViewStatusImage);

        return convertView;
    }


    /**
     * To manage the StatusGridImages layout resource.
     * Only create this class once time.
     *
     * @author BriceChou
     * @datetime 16-6-21 15:54
     */
    public static class StatusGridImagesViewHolder {
        // item_status_grid_image.xml file
        public ImageView mImageViewStatusImage;
    }
}
