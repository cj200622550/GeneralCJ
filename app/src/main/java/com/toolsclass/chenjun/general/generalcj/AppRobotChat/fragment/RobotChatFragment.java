package com.toolsclass.chenjun.general.generalcj.AppRobotChat.fragment;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toolsclass.chenjun.general.generalcj.AppRobotChat.activity.ChatActivity;
import com.toolsclass.chenjun.general.generalcj.AppRobotChat.bean.Msg;
import com.toolsclass.chenjun.general.generalcj.AppRobotChat.config.Const;
import com.toolsclass.chenjun.general.generalcj.AppRobotChat.db.ChatMsgDao;
import com.toolsclass.chenjun.general.generalcj.AppRobotChat.util.ExpressionUtil;
import com.toolsclass.chenjun.general.generalcj.R;
import com.toolsclass.chenjun.general.generalcj.logger.LogUtils;

import ren.solid.library.fragment.base.BaseFragment;

/**
 * 作者：陈骏 on 2017/5/23 21:38
 * QQ：200622550
 */

public class RobotChatFragment extends BaseFragment implements View.OnClickListener{
    LinearLayout ll_chat;
    TextView tv_content, tv_time;
    ChatMsgDao chatMsgDao;
    @Override
    protected int setLayoutResourceID() {
        return R.layout.robot_activity_robot_chat;
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        ll_chat = $(R.id.ll_chat);
        tv_content = $(R.id.tv_content);
        tv_time = $(R.id.tv_time);
        ll_chat.setOnClickListener(this);

        chatMsgDao = new ChatMsgDao(getMContext());

    }

    @Override
    protected void setUpData() {
        super.setUpData();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.i("isVisibleToUser");
        if (isVisibleToUser) {

            VisibleInfo(); // 显示最后一次聊天
        } else {

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.i("onResume");
        VisibleInfo();
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.i("onPause");
    }

    /**
     * 显示最后一条信息
     */
    private void VisibleInfo(){
        Msg msg = chatMsgDao.queryTheLastMsg();
        if (msg != null) {
            tv_time.setText(msg.getDate());
            switch (msg.getType()) {
                case Const.MSG_TYPE_TEXT:
                    tv_content.setText(ExpressionUtil.prase(getMContext(),tv_content,msg.getContent()));
                    break;
                case Const.MSG_TYPE_IMG:
                    tv_content.setText("[图片]");
                    break;
                case Const.MSG_TYPE_VOICE:
                    tv_content.setText("[语音]");
                    break;
                case Const.MSG_TYPE_LOCATION:
                    tv_content.setText("[位置]");
                    break;
                case Const.MSG_TYPE_MUSIC:
                    tv_content.setText("[歌曲]");
                    break;
            }
        }
    }


    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.ll_chat: // 跳转到聊天界面
                Intent intent=new Intent();
                intent.setClass(getMContext(),ChatActivity.class);
                startActivity(intent);
                ((Activity) getMContext()).overridePendingTransition(R.anim.robot_push_in_right, R.anim.robot_push_out_left);
                break;
        }
    }
}
