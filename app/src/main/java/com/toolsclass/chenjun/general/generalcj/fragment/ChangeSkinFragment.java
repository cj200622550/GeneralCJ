package com.toolsclass.chenjun.general.generalcj.fragment;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.toolsclass.chenjun.general.generalcj.R;
import com.toolsclass.chenjun.general.generalcj.logger.LogUtils;
import com.toolsclass.chenjun.general.generalcj.solid.library.utils.SnackBarUtils;

import java.io.File;

import ren.solid.library.fragment.base.BaseFragment;
import ren.solid.library.utils.FileUtils;
import ren.solid.skinloader.listener.ILoaderListener;
import ren.solid.skinloader.load.SkinManager;

/**
 * Created by _SOLID
 * Date:2016/4/14
 * Time:10:44
 */
public class ChangeSkinFragment extends BaseFragment {

    private static String SKIN_BROWN_NAME = "skin_brown.skin";
    private static String SKIN_BLACK_NAME = "skin_black.skin";
    private static String SKIN_DIR;

    @Override
    protected int setLayoutResourceID() {
        return R.layout.fragment_chang_skin;
    }

    @Override
    protected void init() {
        SKIN_DIR = FileUtils.getSkinDirPath(getMContext());
    }

    @Override
    protected void setUpView() {
        $(R.id.ll_green).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SkinManager.getInstance().restoreDefaultTheme();

            }
        });
        $(R.id.ll_brown).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String skinFullName = SKIN_DIR + File.separator + "skin_brown.skin";
                FileUtils.moveRawToDir(getMContext(), "skin_brown.skin", skinFullName);
                File skin = new File(skinFullName);
                if (!skin.exists()) {
                    SnackBarUtils.makeShort(v,"请检查" + skinFullName + "是否存在").warning();
//                    Toast.makeText(getMContext(), "请检查" + skinFullName + "是否存在", Toast.LENGTH_SHORT).show();
                    return;
                }
                SkinManager.getInstance().load(skin.getAbsolutePath(),
                        new ILoaderListener() {
                            @Override
                            public void onStart() {
                                LogUtils.e("loadSkinStart");
                            }

                            @Override
                            public void onSuccess() {
                                LogUtils.e( "loadSkinSuccess");
                                SnackBarUtils.makeShort(v,"切换成功").success();
//                                Toast.makeText(getMContext(), "切换成功", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailed() {
                                LogUtils.e("loadSkinFail");
                                SnackBarUtils.makeShort(v,"切换失败").danger();
//                                Toast.makeText(getMContext(), "切换失败", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        $(R.id.ll_black).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final String skinFullName = SKIN_DIR + File.separator + "skin_black.skin";
                FileUtils.moveRawToDir(getMContext(), "skin_black.skin", skinFullName);
                File skin = new File(skinFullName);
                if (!skin.exists()) {
                    SnackBarUtils.makeShort(v,"请检查" + skinFullName + "是否存在").warning();
//                    Toast.makeText(getMContext(), "请检查" + skinFullName + "是否存在", Toast.LENGTH_SHORT).show();
                    return;
                }
                SkinManager.getInstance().load(skin.getAbsolutePath(),
                        new ILoaderListener() {
                            @Override
                            public void onStart() {
                                LogUtils.e("loadSkinStart");
                            }

                            @Override
                            public void onSuccess() {
                                LogUtils.e("loadSkinSuccess");
                                SnackBarUtils.makeShort(v,"切换成功").success();
//                                Toast.makeText(getMContext(), "切换成功", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailed() {
                                LogUtils.e( "loadSkinFail");
                                SnackBarUtils.makeShort(v,"切换失败").danger();
//                                Toast.makeText(getMContext(), "切换失败", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

}
