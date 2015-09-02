package com.wg.news.bean;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by EXP on 2015/8/31.
 */
public class NewsContentBean {

//    String B2AB9U9800963VRO;

    private String body;
    //    String users;
    private int replyCount;
    //延伸信息
    private List<NewsSpinfoBean> spinfo = new ArrayList<NewsSpinfoBean>();
    //        private String ydbaike;
//        private String link;
    private List<NewsImage> img = new ArrayList<NewsImage>();
    //        private String votes;
    //摘要
    private String digest;
    //         private String topiclist_news;
//        private String dkeys;
//          private String recommend;
//        private String topiclist;
    private String docid;
    private boolean picnews;
    private String title;
    private String tid;
    //    private String keyword_search;
    private List<NewsVideoBean> video = new ArrayList<NewsVideoBean>();
    //模版
    private String template;
//    private int threadVote;
//    private int threadAgainst;
    // private Array boboList;
    private String replyBoard;
    //新闻源
    private String source;
    private String voicecomment;
    private boolean hasNext;
    private List<NewsRelative> relative_sys;
    private String ptime;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<NewsImage> getImg() {
        return img;
    }

    public void setImg(List<NewsImage> img) {
        this.img = img;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public List<NewsSpinfoBean> getSpinfo() {
        return spinfo;
    }

    public void setSpinfo(List<NewsSpinfoBean> spinfo) {
        this.spinfo = spinfo;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public boolean isPicnews() {
        return picnews;
    }

    public void setPicnews(boolean picnews) {
        this.picnews = picnews;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public List<NewsVideoBean> getVideo() {
        return video;
    }

    public void setVideo(List<NewsVideoBean> video) {
        this.video = video;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }


    public String getReplyBoard() {
        return replyBoard;
    }

    public void setReplyBoard(String replyBoard) {
        this.replyBoard = replyBoard;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getVoicecomment() {
        return voicecomment;
    }

    public void setVoicecomment(String voicecomment) {
        this.voicecomment = voicecomment;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public List<NewsRelative> getRelative_sys() {
        return relative_sys;
    }

    public void setRelative_sys(List<NewsRelative> relative_sys) {
        this.relative_sys = relative_sys;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public class NewsRelative {
        private String id;
        private String title;
        private String source;
        private String type;
        private String ptime;
        private String href;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPtime() {
            return ptime;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }
    }

    public class NewsImage {
        private String ref;
        private String pixel;
        private String alt;
        private String src;

        public String getRef() {
            return ref;
        }

        public void setRef(String ref) {
            this.ref = ref;
        }

        public String getPixel() {
            return pixel;
        }

        public void setPixel(String pixel) {
            this.pixel = pixel;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public String getSrc() {
            return src;
        }

        public void setSrc(String src) {
            this.src = src;
        }
    }
}
