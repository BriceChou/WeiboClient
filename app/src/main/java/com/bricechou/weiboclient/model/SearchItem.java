package com.bricechou.weiboclient.model;

/**
 * Created by user on 6/21/16.
 */
public class SearchItem {
    private boolean isShowTopDivider;
    private int imageId;
    private String subhead;
    private String caption;

    public SearchItem(boolean isShowTopDivider, int imageId, String subhead, String caption) {
        this.isShowTopDivider = isShowTopDivider;
        this.imageId = imageId;
        this.subhead = subhead;
        this.caption = caption;
    }

    public boolean isShowTopDivider() {
        return isShowTopDivider;
    }

    public void setShowTopDivider(boolean showTopDivider) {
        isShowTopDivider = showTopDivider;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getSubhead() {
        return subhead;
    }

    public void setSubhead(String subhead) {
        this.subhead = subhead;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
