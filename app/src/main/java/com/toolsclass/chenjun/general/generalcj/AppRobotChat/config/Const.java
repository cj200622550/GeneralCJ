package com.toolsclass.chenjun.general.generalcj.AppRobotChat.config;

import android.os.Environment;

/**
 * 作者：陈骏 on 2017/5/23 21:33
 * QQ：200622550
 */

public class Const {
    public static final String FILE_IMG_CACHE = Environment.getExternalStorageDirectory() + "/CJ/images/cache/";
    public static final String FILE_VOICE_CACHE = Environment.getExternalStorageDirectory() + "/CJ/voice/";
    public static final String FILE_DOWNLOAD = Environment.getExternalStorageDirectory() + "/CJ/download/";

    public static final String XF_VOICE_APPID="5716d793";//讯飞语音appid
    public final static String XF_SET_VOICE_RECORD="VOICE_RECORD";//录音语言
    public final static String XF_SET_VOICE_READ="XF_SET_VOICE_READ";//朗读语言

    public final static String IM_VOICE_TPPE="IM_VOICE_TPPE";//语音聊天形式
    public final static String IM_SPEECH_TPPE="IM_SPEECH_TPPE";//聊天回复是否直接朗读
    /**
     * 默认横坐标
     */
    public final static double LOC_LONGITUDE = 116.403119;
    /**
     * 默认纵坐标
     */
    public final static double LOC_LATITUDE = 39.915378;
    /**
     * 实时定位地址
     */
    public final static String ADDRESS = "ADDRESS";
    /**
     * 实时定位城市
     */
    public final static String CITY = "CITY";
    /**
     * 实时定位坐标
     */
    public final static String LOCTION = "LOCTION";

    /**
     * 版本号,如果版本更新时更新了引导图，则将"VERSION_CODE"名改变即可,没有更新则不改变；
     * 为了统一，更改策略为：
     * VERSION_CODE="VERSION_CODE_1"
     * VERSION_CODE="VERSION_CODE_2"
     * VERSION_CODE="VERSION_CODE_3"
     * 。。。。。。
     */
    public final static String VERSION_CODE = "VERSION_CODE";

    //静态地图API
    public static  final String LOCATION_URL_S = "http://api.map.baidu.com/staticimage?width=320&height=240&zoom=17&center=";
    public static  final String LOCATION_URL_L = "http://api.map.baidu.com/staticimage?width=480&height=800&zoom=17&center=";

    public static final String MSG_TYPE_TEXT="msg_type_text";//文本消息
    public static final String MSG_TYPE_IMG="msg_type_img";//图片
    public static final String MSG_TYPE_VOICE="msg_type_voice";//语音
    public static final String MSG_TYPE_LOCATION="msg_type_location";//位置
    public static final String MSG_TYPE_MUSIC="msg_type_music";//音乐
    public static final String MSG_TYPE_LIST="msg_type_list";//新闻

    //机器人api，注意key为本人所有，使用时请到图灵机器人官网注册http://www.tuling123.com
    public static final String ROBOT_URL="http://www.tuling123.com/openapi/api";
    public static final String ROBOT_KEY="f1ba34080a6470bbb0668ca4b6c2b161";

    /**
     * 分隔符
     */
    public final static String  SPILT = "☆";
}
