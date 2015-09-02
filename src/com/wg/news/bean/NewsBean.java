package com.wg.news.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by EXP on 2015/8/26.
 */
public class NewsBean {

    private String template;
    private boolean hasCover;
    private int hasHead;
    private String skipID;

    //刷新时间时间
    private String lmodify;
    //图片url
    private String imgsrc;
    //新闻世间
    private String ptime;//: "2015-08-26 00:52:35"
    //回复数量
    private int replyCount;
    //标题
    private String title;
    //新闻类型
    private String alias;

    //新闻页面地址
    private String url_3w;
    //投票统计
    private int votecount;
    //新闻摘要
    private String digest;
    //布局类型
    private String skipType;

    private int hasImg;
    private boolean hasIcon;
    private String cid;
    private String docid;
    private int hasAD;
    private int order;
    private List<NewsImage> imgextra = new ArrayList<NewsImage>();
//    : [
//    {
//        imgsrc: "http://img5.cache.netease.com/3g/2015/8/26/20150826005325c66db.jpg"
//    },
//    {
//        imgsrc: "http://img1.cache.netease.com/3g/2015/8/26/2015082600532723267.jpg"
//    }
//            ],
    private int priority;
    private String ename;//: "androidnews",
    private String tname;//: "头条",
    private String photosetID;//: "54GI0096|74768",
    public class NewsImage{
        private String imgsrc;
        public String getImgsrc(){
            return imgsrc;
        }
        public void setImgsrc(String src){
            imgsrc = src;
        }
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public boolean isHasCover() {
        return hasCover;
    }

    public void setHasCover(boolean hasCover) {
        this.hasCover = hasCover;
    }

    public int getHasHead() {
        return hasHead;
    }

    public void setHasHead(int hasHead) {
        this.hasHead = hasHead;
    }

    public String getSkipID() {
        return skipID;
    }

    public void setSkipID(String skipID) {
        this.skipID = skipID;
    }

    public String getLmodify() {
        return lmodify;
    }

    public void setLmodify(String lmodify) {
        this.lmodify = lmodify;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    public void setImgsrc(String imgsrc) {
        this.imgsrc = imgsrc;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public int getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(int replyCount) {
        this.replyCount = replyCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl_3w() {
        return url_3w;
    }

    public void setUrl_3w(String url_3w) {
        this.url_3w = url_3w;
    }

    public int getVotecount() {
        return votecount;
    }

    public void setVotecount(int votecount) {
        this.votecount = votecount;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public int getHasImg() {
        return hasImg;
    }

    public void setHasImg(int hasImg) {
        this.hasImg = hasImg;
    }

    public boolean isHasIcon() {
        return hasIcon;
    }

    public void setHasIcon(boolean hasIcon) {
        this.hasIcon = hasIcon;
    }

    public String getSkipType() {
        return skipType;
    }

    public void setSkipType(String skipType) {
        this.skipType = skipType;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public int getHasAD() {
        return hasAD;
    }

    public void setHasAD(int hasAD) {
        this.hasAD = hasAD;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<NewsImage> getImgextra() {
        return imgextra;
    }

    public void setImgextra(List<NewsImage> imgextra) {
        this.imgextra = imgextra;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getPhotosetID() {
        return photosetID;
    }

    public void setPhotosetID(String photosetID) {
        this.photosetID = photosetID;
    }
}

