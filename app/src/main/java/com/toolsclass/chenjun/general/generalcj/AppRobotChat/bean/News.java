package com.toolsclass.chenjun.general.generalcj.AppRobotChat.bean;

/**
 * 作者：陈骏 on 2017/5/23 22:55
 * QQ：200622550
 * 新闻信息
 * article	新闻标题
 * source	新闻来源
 * icon	新闻图片
 * detailurl	新闻详情链接
 */

public class News {
    private String article;
    private String source;
    private String icon;
    private String detailurl;

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDetailurl() {
        return detailurl;
    }

    public void setDetailurl(String detailurl) {
        this.detailurl = detailurl;
    }
}
