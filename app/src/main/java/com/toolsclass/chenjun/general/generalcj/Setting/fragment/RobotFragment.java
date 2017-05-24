package com.toolsclass.chenjun.general.generalcj.Setting.fragment;

import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.iflytek.cloud.SpeechConstant;
import com.toolsclass.chenjun.general.generalcj.AppRobotChat.config.Const;
import com.toolsclass.chenjun.general.generalcj.AppRobotChat.db.ChatMsgDao;
import com.toolsclass.chenjun.general.generalcj.AppRobotChat.util.DialogUtil;
import com.toolsclass.chenjun.general.generalcj.AppRobotChat.util.ToastUtil;
import com.toolsclass.chenjun.general.generalcj.AppRobotChat.view.ActionSheetCenterDialog;
import com.toolsclass.chenjun.general.generalcj.Libs.sharelibrary.Share;
import com.toolsclass.chenjun.general.generalcj.R;
import com.toolsclass.chenjun.general.generalcj.logger.LogUtils;
import com.toolsclass.chenjun.general.generalcj.view.UISwitchButton;

import ren.solid.library.fragment.base.BaseFragment;

/**
 * 作者：陈骏 on 2017/5/24 12:20
 * QQ：200622550
 */

public class RobotFragment extends BaseFragment {

    private RelativeLayout rl_1,rl_2,rl_3;
    private RadioButton rb_voice, rb_text;
    private UISwitchButton cb_speech;
    private TextView tv_1, tv_2;
    private ActionSheetCenterDialog actionSheetCenterDialog1, actionSheetCenterDialog2;
    private ChatMsgDao chatMsgDao;

    private String[] SET_VOICE_RECORD = {"xiaoyan","xiaoqi","xiaomei","xiaolin","xiaorong","xiaoqian","xiaokun"
            ,"xiaoqiang","xiaoxin","nannan"};
    private String[] SET_VOICE_RECORD_NAME = {"小燕（普通话）","小琪（普通话）","小梅（粤语）","晓琳（台湾普通话）",
            "小蓉（四川话）","小倩（（东北话））","小坤（河南话）","小强（湖南话）","小新（普通话）",
            "楠楠（普通话）"};

    @Override
    protected int setLayoutResourceID() {
        return R.layout.setting_fragment_robot;
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        initView();
        initData();
    }
    private void initView(){
        rl_1 = $(R.id.rl_1);//聊天记录
        rl_2 = $(R.id.rl_2);//录音设置
        rl_3 = $(R.id.rl_3);//朗读语言

        tv_1 = $(R.id.tv_1);
        tv_2 = $(R.id.tv_2);

        rb_voice = $(R.id.rb_voice); //录音以语音形式发送
        rb_text = $(R.id.rb_text); // 录音以文字形式发送
        cb_speech = $(R.id.cb_speech); // 回复内容直接朗读

        rl_1.setOnClickListener(CleanClick); // 清除聊天记录
        rl_2.setOnClickListener(RecordClick); // 录音语言
        rl_3.setOnClickListener(ReadClick); // 朗读语言
        rb_voice.setOnClickListener(rbVoiceClick);
        rb_text.setOnClickListener(rbTextClick);
        cb_speech.setOnCheckedChangeListener(speech);
        chatMsgDao = new ChatMsgDao(getMContext());
    }

    private void initData() {
        String str1 = Share.getString(Const.XF_SET_VOICE_RECORD);
        String str2 = Share.getString(Const.XF_SET_VOICE_READ);
        if (str1 == null) {
            tv_1.setText("录音语言：mandarin");
        } else {
            tv_1.setText("录音语言：" + str1);
        }
        if (str2 == null) {
            tv_2.setText("朗读语言：" + SET_VOICE_RECORD_NAME[0]);
        } else {
            if (str1.equals(SET_VOICE_RECORD[0]))
                tv_2.setText("朗读语言：" + SET_VOICE_RECORD_NAME[0]);
            else if (str1.equals(SET_VOICE_RECORD[1]))
                tv_2.setText("朗读语言：" + SET_VOICE_RECORD_NAME[1]);
            else if (str1.equals(SET_VOICE_RECORD[2]))
                tv_2.setText("朗读语言：" + SET_VOICE_RECORD_NAME[2]);
            else if (str1.equals(SET_VOICE_RECORD[3]))
                tv_2.setText("朗读语言：" + SET_VOICE_RECORD_NAME[3]);
            else if (str1.equals(SET_VOICE_RECORD[4]))
                tv_2.setText("朗读语言：" + SET_VOICE_RECORD_NAME[4]);
            else if (str1.equals(SET_VOICE_RECORD[5]))
                tv_2.setText("朗读语言：" + SET_VOICE_RECORD_NAME[5]);
            else if (str1.equals(SET_VOICE_RECORD[6]))
                tv_2.setText("朗读语言：" + SET_VOICE_RECORD_NAME[6]);
            else if (str1.equals(SET_VOICE_RECORD[7]))
                tv_2.setText("朗读语言：" + SET_VOICE_RECORD_NAME[7]);
            else if (str1.equals(SET_VOICE_RECORD[8]))
                tv_2.setText("朗读语言：" + SET_VOICE_RECORD_NAME[8]);
            else if (str1.equals(SET_VOICE_RECORD[9]))
                tv_2.setText("朗读语言：" + SET_VOICE_RECORD_NAME[9]);
        }
        String str3 = Share.getString(Const.IM_VOICE_TPPE) == null ? "" : Share.getString(Const.IM_VOICE_TPPE);
        String str4 = Share.getString(Const.IM_SPEECH_TPPE) == null ? "" : Share.getString(Const.IM_SPEECH_TPPE);
        if(!TextUtils.isEmpty(str3) && str3.equals("1")){
            rb_voice.setChecked(true);
        }else{
            rb_text.setChecked(true);
        }
        if(!TextUtils.isEmpty(str4)&&str4.equals("1")){
            cb_speech.setChecked(true);
        }else{
            cb_speech.setChecked(false);
        }
    }

    CompoundButton.OnCheckedChangeListener speech = new CompoundButton.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
            if (isChecked) {
                Share.putString(Const.IM_SPEECH_TPPE, "1");//回复内容直接朗读
            } else {
                Share.putString(Const.IM_SPEECH_TPPE, "0");
            }
        }
    };

    ActionSheetCenterDialog.OnSheetItemClickListener onSheetItemClickListener1 = new ActionSheetCenterDialog.OnSheetItemClickListener() {
        @Override
        public void onClick(int which) {
            switch (which) {
                case 1:
                    tv_1.setText("录音语言：mandarin(普通话)");
                    Share.putString(Const.XF_SET_VOICE_RECORD, "mandarin");
                    break;
                case 2:
                    tv_1.setText("录音语言：cantonese(粤语)");
                    Share.putString(Const.XF_SET_VOICE_RECORD, "cantonese");
                    break;
                case 3:
                    tv_1.setText("录音语言：henanese(河南话)");
                    Share.putString(Const.XF_SET_VOICE_RECORD, "henanese");
                    break;
                case 4:
                    tv_1.setText("录音语言：en_us(英语)");
                    Share.putString(Const.XF_SET_VOICE_RECORD, "en_us");
                    break;
            }
        }
    };

    ActionSheetCenterDialog.OnSheetItemClickListener onSheetItemClickListener2 = new ActionSheetCenterDialog.OnSheetItemClickListener() {
        @Override
        public void onClick(int which) {
            switch (which) {
                case 1:
                    tv_2.setText("朗读语言：" + SET_VOICE_RECORD_NAME[0]);
                    Share.putString(Const.XF_SET_VOICE_READ, SET_VOICE_RECORD[0]);
                    break;
                case 2:
                    tv_2.setText("朗读语言：" + SET_VOICE_RECORD_NAME[1]);
                    Share.putString(Const.XF_SET_VOICE_READ, SET_VOICE_RECORD[1]);
                    break;
                case 3:
                    tv_2.setText("朗读语言：" + SET_VOICE_RECORD_NAME[2]);
                    Share.putString(Const.XF_SET_VOICE_READ, SET_VOICE_RECORD[2]);
                    break;
                case 4:
                    tv_2.setText("朗读语言：" + SET_VOICE_RECORD_NAME[3]);
                    Share.putString(Const.XF_SET_VOICE_READ, SET_VOICE_RECORD[3]);
                    break;
                case 5:
                    tv_2.setText("朗读语言：" + SET_VOICE_RECORD_NAME[4]);
                    Share.putString(Const.XF_SET_VOICE_READ, SET_VOICE_RECORD[4]);
                    break;
                case 6:
                    tv_2.setText("朗读语言：" + SET_VOICE_RECORD_NAME[5]);
                    Share.putString(Const.XF_SET_VOICE_READ, SET_VOICE_RECORD[5]);
                    break;
                case 7:
                    tv_2.setText("朗读语言：" + SET_VOICE_RECORD_NAME[6]);
                    Share.putString(Const.XF_SET_VOICE_READ, SET_VOICE_RECORD[6]);
                    break;
                case 8:
                    tv_2.setText("朗读语言：" + SET_VOICE_RECORD_NAME[7]);
                    Share.putString(Const.XF_SET_VOICE_READ, SET_VOICE_RECORD[7]);
                    break;
                case 9:
                    tv_2.setText("朗读语言：" + SET_VOICE_RECORD_NAME[8]);
                    Share.putString(Const.XF_SET_VOICE_READ, SET_VOICE_RECORD[8]);
                    break;
                case 10:
                    tv_2.setText("朗读语言：" + SET_VOICE_RECORD_NAME[9]);
                    Share.putString(Const.XF_SET_VOICE_READ, SET_VOICE_RECORD[9]);
                    break;
            }
        }
    };

    View.OnClickListener RecordClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            LogUtils.i("录音语言");
            actionSheetCenterDialog1 = new ActionSheetCenterDialog(getMContext())
                    .builder()
                    .addSheetItem("mandarin(普通话)", ActionSheetCenterDialog.SheetItemColor.Blue, onSheetItemClickListener1)
                    .addSheetItem("cantonese(粤语)", ActionSheetCenterDialog.SheetItemColor.Blue, onSheetItemClickListener1)
                    .addSheetItem("henanese(河南话)", ActionSheetCenterDialog.SheetItemColor.Blue, onSheetItemClickListener1)
                    .addSheetItem("en_us(英语)", ActionSheetCenterDialog.SheetItemColor.Blue, onSheetItemClickListener1)
            ;
            actionSheetCenterDialog1.show();
        }
    };

    View.OnClickListener ReadClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            LogUtils.i("朗读语言");
            actionSheetCenterDialog2 = new ActionSheetCenterDialog(getMContext())
                    .builder()
                    .addSheetItem(SET_VOICE_RECORD_NAME[0], ActionSheetCenterDialog.SheetItemColor.Blue, onSheetItemClickListener2)
                    .addSheetItem(SET_VOICE_RECORD_NAME[1], ActionSheetCenterDialog.SheetItemColor.Blue, onSheetItemClickListener2)
                    .addSheetItem(SET_VOICE_RECORD_NAME[2], ActionSheetCenterDialog.SheetItemColor.Blue, onSheetItemClickListener2)
                    .addSheetItem(SET_VOICE_RECORD_NAME[3], ActionSheetCenterDialog.SheetItemColor.Blue, onSheetItemClickListener2)
                    .addSheetItem(SET_VOICE_RECORD_NAME[4], ActionSheetCenterDialog.SheetItemColor.Blue, onSheetItemClickListener2)
                    .addSheetItem(SET_VOICE_RECORD_NAME[5], ActionSheetCenterDialog.SheetItemColor.Blue, onSheetItemClickListener2)
                    .addSheetItem(SET_VOICE_RECORD_NAME[6], ActionSheetCenterDialog.SheetItemColor.Blue, onSheetItemClickListener2)
                    .addSheetItem(SET_VOICE_RECORD_NAME[7], ActionSheetCenterDialog.SheetItemColor.Blue, onSheetItemClickListener2)
                    .addSheetItem(SET_VOICE_RECORD_NAME[8], ActionSheetCenterDialog.SheetItemColor.Blue, onSheetItemClickListener2)
                    .addSheetItem(SET_VOICE_RECORD_NAME[9], ActionSheetCenterDialog.SheetItemColor.Blue, onSheetItemClickListener2);
            actionSheetCenterDialog2.show();
        }
    };

    View.OnClickListener rbVoiceClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Share.putString(Const.IM_VOICE_TPPE, "1");//以语音形式发送
            LogUtils.i("以语音形式发送");
        }
    };
    View.OnClickListener rbTextClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Share.putString(Const.IM_VOICE_TPPE, "2");//已文本形式发送
            LogUtils.i("已文本形式发送");
        }
    };
    View.OnClickListener CleanClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            DialogUtil.showChooseDialog(getMContext(),"提示","您确定要情况聊天记录吗？","立马清空","容朕想想", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    chatMsgDao.deleteTableData();
                    ToastUtil.showToast(getMContext(),"聊天记录已情况");
                }
            });
        }
    };
}
