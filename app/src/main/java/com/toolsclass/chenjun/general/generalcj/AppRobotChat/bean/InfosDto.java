package com.toolsclass.chenjun.general.generalcj.AppRobotChat.bean;

import java.util.List;

/**
 * 作者：陈骏 on 2017/5/23 22:59
 * QQ：200622550
 */

public class InfosDto {
    private List<Infos> infosList;
    private String nextPageUrl;

    public List<Infos> getInfoList() {
        return this.infosList;
    }

    public void setInfoList(List<Infos> infosList) {
        this.infosList = infosList;
    }

    public String getNextPageUrl() {
        return this.nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }
}
