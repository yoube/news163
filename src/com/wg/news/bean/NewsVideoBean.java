package com.wg.news.bean;

/**
 * Created by EXP on 2015/8/31.
 */
public class NewsVideoBean {
    private String commentid;
    private String ref;
    private int topicid;
    //缩略图
    private String cover;
    //视频title
    private String alt;
    private String url_mp4;
    private String url_m3u8;
    private String broadcast;
    private String videosource;
    private String commentboard;
    private String appurl;
//            private String size;

    public String getCommentid() {
        return commentid;
    }

    public void setCommentid(String commentid) {
        this.commentid = commentid;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public int getTopicid() {
        return topicid;
    }

    public void setTopicid(int topicid) {
        this.topicid = topicid;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getUrl_mp4() {
        return url_mp4;
    }

    public void setUrl_mp4(String url_mp4) {
        this.url_mp4 = url_mp4;
    }

    public String getUrl_m3u8() {
        return url_m3u8;
    }

    public void setUrl_m3u8(String url_m3u8) {
        this.url_m3u8 = url_m3u8;
    }

    public String getBroadcast() {
        return broadcast;
    }

    public void setBroadcast(String broadcast) {
        this.broadcast = broadcast;
    }

    public String getVideosource() {
        return videosource;
    }

    public void setVideosource(String videosource) {
        this.videosource = videosource;
    }

    public String getCommentboard() {
        return commentboard;
    }

    public void setCommentboard(String commentboard) {
        this.commentboard = commentboard;
    }

    public String getAppurl() {
        return appurl;
    }

    public void setAppurl(String appurl) {
        this.appurl = appurl;
    }
}
