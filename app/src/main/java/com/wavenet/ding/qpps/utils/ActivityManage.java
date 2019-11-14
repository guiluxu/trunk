package com.wavenet.ding.qpps.utils;

import android.app.Activity;

import com.dereck.library.base.BaseMvpActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


/**
 * Created by Administrator on 2017/10/10.
 */

public class ActivityManage {
    // ActivityManage实例
    private static ActivityManage INSTANCE;
    /**
     * activity对象列表,用于activity统一管理
     */
    public Stack<BaseMvpActivity> activityList = new Stack<BaseMvpActivity>();

    /**
     * 获取CrashHandler实例 ,单例模式
     */
    public static ActivityManage getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ActivityManage();
        }
        return INSTANCE;
    }
    // ------------------------------activity管理-----------------------//

    /**
     * activity管理：从列表中移除activity,可以在 CommActivity onDestroy 移除
     */
    public void removeActivity(BaseMvpActivity activity) {
        activityList.remove(activity);
    }

    /**
     * activity管理：添加activity到列表，可以在onCreate时添加
     */
    public void addActivity(BaseMvpActivity activity) {
        activityList.add(activity);
    }

    /**
     * activity管理：结束所有activity
     */
    public void finishAllActivity() {
        for (BaseMvpActivity activity : activityList) {
            if (null != activity) {
                activity.finish();
            }
        }
        activityList.clear();
    }

    /**
     * 获取已开启的Activity列表
     */
    public List<BaseMvpActivity> getActivitys() {
        return activityList;
    }

    /**
     * 获取已开启的Activity列表,可过滤不需要的
     *
     * @param filterActivitys 过滤不要的Activity class
     * @return
     */
    public List<BaseMvpActivity> getActivitys(Class<?>... filterActivitys) {
        List<BaseMvpActivity> newActivities = new ArrayList<BaseMvpActivity>();
        for (BaseMvpActivity activity : activityList) {
            // 过滤不要的
            if (null != activity && filterActivitys != null) {
                // 对应判断，过滤掉不需要的
                for (int i = 0; i < filterActivitys.length; i++) {
                    String filterAct = filterActivitys[i].getName();
                    if (!activity.getClass().getName().equals(filterAct)) {
                        // 添加到新集合
                        newActivities.add(activity);
                    }
                }
            }
        }
        return newActivities;
    }

    /**
     * 结束线程,一般与finishAllActivity()一起使用 例如:
     * killApp();(一般应用程序不应该调用这个方法，直接关掉程序的所有activity即可)
     */
    public void killApp() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 显示在最上面的Activity
     */
    public Activity getTopActivity() {
        if (activityList.size() > 0)
            return activityList.get(activityList.size() - 1);
        return null;
    }

    /**
     * get current Activity 获取当前Activity（栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityList.lastElement();
        return activity;
    }

    /**
     * 结束当前Activity（栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityList.lastElement();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            activityList.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityList) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 退出应用程序
     */
    public void AppExit() {
        try {

            finishAllActivity();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
        }
    }

}
