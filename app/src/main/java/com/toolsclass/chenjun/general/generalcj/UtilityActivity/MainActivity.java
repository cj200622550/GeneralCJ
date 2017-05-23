package com.toolsclass.chenjun.general.generalcj.UtilityActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.toolsclass.chenjun.general.generalcj.AppMedia.activity.MusicActivity;
import com.toolsclass.chenjun.general.generalcj.AppSystemInfo.fragment.SystemystemInfoFragment;
import com.toolsclass.chenjun.general.generalcj.R;
import com.toolsclass.chenjun.general.generalcj.Setting.fragment.SettingFragment;
import com.toolsclass.chenjun.general.generalcj.Setting.fragment.SystemFragment;
import com.toolsclass.chenjun.general.generalcj.fragment.AboutFragment;
import com.toolsclass.chenjun.general.generalcj.fragment.ChangeSkinFragment;
import com.toolsclass.chenjun.general.generalcj.fragment.MainFragment;
import com.toolsclass.chenjun.general.generalcj.logger.LogUtils;
import com.toolsclass.chenjun.general.generalcj.solid.library.activity.base.BaseActivity;
import com.toolsclass.chenjun.general.generalcj.solid.library.fragment.WebViewFragment;
import com.toolsclass.chenjun.general.generalcj.solid.library.utils.SnackBarUtils;
import com.toolsclass.chenjun.general.generalcj.solid.library.utils.ViewUtils;

public class MainActivity extends BaseActivity {
    private DrawerLayout mDrawerLayout;//侧边菜单视图
    private ActionBarDrawerToggle mDrawerToggle;  //菜单开关
    private Toolbar mToolbar;
    private NavigationView mNavigationView;//侧边菜单项
    private TextView _name;// 侧边菜单名字

    private FragmentManager mFragmentManager;
    private Fragment mCurrentFragment;
    private MenuItem mPreMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null)
            LogUtils.i("NULL");
        else {
            LogUtils.i("NOT NULL");
        }
    }

    @Override
    protected void setUpView() {
        mToolbar = $(R.id.toolbar);
        mDrawerLayout = $(R.id.drawer_layout);
        mNavigationView = $(R.id.navigation_view);

        View headview = mNavigationView.getHeaderView(0); // 获取NavigationView头部控件
        _name = (TextView) headview.findViewById(R.id.profile_name); // 找到profile_name ID

        mToolbar.setTitle(getString(R.string._string_Books_movies_search));
        _name.setText("小乖");

        //这句一定要在下面几句之前调用，不然就会出现点击无反应
        setSupportActionBar(mToolbar);
        setNavigationViewItemClickListener();
        //ActionBarDrawerToggle配合Toolbar，实现Toolbar上菜单按钮开关效果。
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mToolbar.setNavigationIcon(R.drawable.ic_drawer_home);

        initDefaultFragment();
        dynamicAddSkinEnableView(mToolbar, "background", R.color.colorPrimary);
        dynamicAddSkinEnableView(mNavigationView.getHeaderView(0), "background", R.color.colorPrimary);
        dynamicAddSkinEnableView(mNavigationView, "navigationViewMenu", R.color.colorPrimary);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.activity_main;
    }

    @Override
    protected void init() {
        mFragmentManager = getSupportFragmentManager();
    }

    /**
     * 初始化默认Fragment
     */
    private void initDefaultFragment() {
        LogUtils.i("initDefaultFragment");
        mCurrentFragment = ren.solid.library.utils.ViewUtils.createFragment(MainFragment.class);

        mFragmentManager.beginTransaction().add(R.id.frame_content, mCurrentFragment).commit();
        mPreMenuItem = mNavigationView.getMenu().getItem(0);
        mPreMenuItem.setChecked(true);
    }

    /**
     * 左边菜单点击事件
     */
    private void setNavigationViewItemClickListener() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (null != mPreMenuItem) {
                    mPreMenuItem.setChecked(false);
                }
                switch (item.getItemId()) {
                    case R.id.navigation_item_home:
                        mToolbar.setTitle(getString(R.string._string_Books_movies_search));
                        switchFragment(MainFragment.class);
                        break;
                    case R.id.navigation_item_robot_chat:
                        mToolbar.setTitle(getString(R.string._string_robot_chat));
//                        switchFragment(GanHuoFragment.class);
                        break;
                    case R.id.navigation_item_media:
//                        mToolbar.setTitle(getString(R.string._string_media));
//                        switchFragment(BlogFragment.class);
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), MusicActivity.class);
                        intent.putExtras(getIntent());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                        break;
                    case R.id.navigation_item_system_info:
                        mToolbar.setTitle(getString(R.string._string_system_info));
                        switchFragment(SystemystemInfoFragment.class);
                        break;
                    case R.id.navigation_item_setting:
                        mToolbar.setTitle(getString(R.string.action_settings));
                        switchFragment(SettingFragment.class);
                        break;
                    case R.id.navigation_item_switch_theme:
                        mToolbar.setTitle(getString(R.string.action_switch_theme));
                        switchFragment(ChangeSkinFragment.class);
                        break;
                    case R.id.navigation_item_about:
                        mToolbar.setTitle(getString(R.string.action_about));
                        switchFragment(AboutFragment.class);
                        break;
                    case R.id.navigation_item_outsys:
                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("退出提示");
                        builder.setMessage("确定要退出系统？");
                        builder.setNegativeButton("取消", null);
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                System.exit(0);
                            }
                        });
                        builder.show();
                        break;
                    default:
                        break;
                }
                item.setChecked(true);
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                mPreMenuItem = item;
                return false;
            }
        });
    }

    //切换Fragment
    private void switchFragment(Class<?> clazz) {
        Fragment to = ViewUtils.createFragment(clazz);
        if (to.isAdded()) {
            LogUtils.i("Added");
            mFragmentManager.beginTransaction().hide(mCurrentFragment).show(to).commitAllowingStateLoss();
        } else {
            LogUtils.i("Not Added");
            mFragmentManager.beginTransaction().hide(mCurrentFragment).add(R.id.frame_content, to).commitAllowingStateLoss();
        }
        mCurrentFragment = to;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtils.i("onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //super.onRestoreInstanceState(savedInstanceState);
        LogUtils.i("onRestoreInstanceState");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_toolbar, menu);
        return true;
    }

    /**
     * 右上角菜单点击事件
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
//            startActivityWithoutExtras(SettingActivity.class);
        } else if (id == R.id.action_about) {
            startActivityWithoutExtras(AboutActivity.class);
//            startActivityWithoutExtras(AboutTwoActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }


    private long lastBackKeyDownTick = 0;
    public static final long MAX_DOUBLE_BACK_DURATION = 1500;

    /**
     * 返回监听
     */
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {//当前抽屉是打开的，则关闭
            mDrawerLayout.closeDrawer(Gravity.LEFT);
            return;
        }

        if (mCurrentFragment instanceof WebViewFragment) {//如果当前的Fragment是WebViewFragment 则监听返回事件
            WebViewFragment webViewFragment = (WebViewFragment) mCurrentFragment;
            if (webViewFragment.canGoBack()) {
                webViewFragment.goBack();
                return;
            }
        }

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);

//        long currentTick = System.currentTimeMillis();
//        if (currentTick - lastBackKeyDownTick > MAX_DOUBLE_BACK_DURATION) {
//            SnackBarUtils.makeShort(mDrawerLayout, "再按一次退出").success();
//            lastBackKeyDownTick = currentTick;
//        } else {
//            finish();
//            System.exit(0);
//        }
    }
}
