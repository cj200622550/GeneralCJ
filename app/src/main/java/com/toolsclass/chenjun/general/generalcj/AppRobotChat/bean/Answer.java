package com.toolsclass.chenjun.general.generalcj.AppRobotChat.bean;

import java.util.List;

/**
 * 作者：陈骏 on 2017/5/23 22:54
 * QQ：200622550
 * 机器人回答类
 */

public class Answer {

    private String code;//返回码
    private String text;//内容
    private String url;//链接
    private List<News> listNews;//新闻列表
    private List<Cook> listCook;//菜谱列表
    private String jsoninfo;//

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<News> getListNews() {
        return listNews;
    }

    public void setListNews(List<News> listNews) {
        this.listNews = listNews;
    }

    public List<Cook> getListCook() {
        return listCook;
    }

    public void setListCook(List<Cook> listCook) {
        this.listCook = listCook;
    }

    public String getJsoninfo() {
        return jsoninfo;
    }

    public void setJsoninfo(String jsoninfo) {
        this.jsoninfo = jsoninfo;
    }
}
