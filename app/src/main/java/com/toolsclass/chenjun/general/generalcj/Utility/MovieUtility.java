package com.toolsclass.chenjun.general.generalcj.Utility;

import com.toolsclass.chenjun.general.generalcj.bean.MovieBean;
import com.toolsclass.chenjun.general.generalcj.bean.MovieDetailBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/15.
 */

public class MovieUtility {

    /**
     * List 根据格式转换为字符串
     * @param list 源数据
     * @param separator 需要分割的字符
     * @return 1/2/3/4
     */
    public static String StringTo(List<String> list, String separator) {
        if (list.size() == 0) {
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            char Char = separator.charAt(0);
            for (int i = 0; i < list.size(); i++) {
                if (i == list.size() - 1) {
                    sb.append(list.get(i));
                } else {
                    sb.append(list.get(i));
                    sb.append(Char);
                }
            }
            return sb.toString();
        }
    }

    /**
     * 获取导演列表
     * @param list 源数据
     * @param separator 分割符号
     * @return 1/2/3/4
     */
    public static String getDirector(List<MovieBean.Directors> list, String separator){
        if (list.size() == 0){
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            char Char = separator.charAt(0);
            for (int i = 0; i < list.size(); i++){
                if (i == list.size() - 1) {
                    sb.append(list.get(i).getName());
                } else {
                    sb.append(list.get(i).getName());
                    sb.append(Char);
                }
            }
            return sb.toString();
        }
    }
    /**
     * 获取详细导演列表
     * @param list 源数据
     * @param separator 分割符号
     * @return 1/2/3/4
     */
    public static String getDetailDirector(List<MovieDetailBean.Directors> list, String separator){
        if (list.size() == 0){
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            char Char = separator.charAt(0);
            for (int i = 0; i < list.size(); i++){
                if (i == list.size() - 1) {
                    sb.append(list.get(i).getName());
                } else {
                    sb.append(list.get(i).getName());
                    sb.append(Char);
                }
            }
            return sb.toString();
        }
    }

    /**
     * 获取主演
     * @param list 源数据
     * @param separator 分割符号
     * @return 1/2/3/4
     */
    public static String getCasts (List<MovieBean.Casts> list, String separator){
        if (list.size() == 0){
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            char Char = separator.charAt(0);
            for (int i = 0; i < list.size(); i++) {
                if (i == list.size() - 1) {
                    sb.append(list.get(i).getName());
                } else {
                    sb.append(list.get(i).getName());
                    sb.append(Char);
                }
            }
            return sb.toString();
        }
    }
    /**
     * 获取详细接main主演
     * @param list 源数据
     * @param separator 分割符号
     * @return 1/2/3/4
     */
    public static String getDetailCasts (List<MovieDetailBean.Casts> list, String separator){
        if (list.size() == 0){
            return "";
        } else {
            StringBuilder sb = new StringBuilder();
            char Char = separator.charAt(0);
            for (int i = 0; i < list.size(); i++) {
                if (i == list.size() - 1) {
                    sb.append(list.get(i).getName());
                } else {
                    sb.append(list.get(i).getName());
                    sb.append(Char);
                }
            }
            return sb.toString();
        }
    }
}
