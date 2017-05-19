package com.toolsclass.chenjun.general.generalcj.AppSystemInfo.model;

/**
 * 作者：陈骏 on 2017/5/18 15:20
 * QQ：200622550
 */

public class NetworkModel {
    private int id;
    private String name;
    private String val;

    public NetworkModel(String name, String val) {
        this.name = name;
        this.val = val;
    }

    public NetworkModel(int id, String name, String val) {
        this.id = id;
        this.name = name;
        this.val = val;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "NetworkModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", val='" + val + '\'' +
                '}';
    }
}
