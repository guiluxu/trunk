package com.wavenet.ding.qpps.interf;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by zoubeiwen on 18/1/23.
 */

public class GoBackInteDef {
    public static final int DELEVIDEO = 100;
    public static final int PALYVIDEO = 101;
    public static final int REPLACEVIDEO = 102;//视频
    public static final int REPLACESOUND = 103;//语音
    public static int mGoBack;

    public static int setBack(@Onclick int goBack) {
        mGoBack = goBack;
        return mGoBack;
    }

    public static  int getBack() {
            return mGoBack;

    }
    @IntDef({
            DELEVIDEO,
            PALYVIDEO,
            REPLACEVIDEO,
            REPLACESOUND,

    })
    @Retention(RetentionPolicy.SOURCE) //表示注解所存活的时间,在运行时,而不会存在. class 文件.
    public @interface Onclick { //接口，定义新的注解类型
    }



    public   interface GoBack {
        void goBack(int mGoBack);
    }
    public   interface A {
        void goBack( int falg);
    }
}
