package com.wavenet.ding.qpps.utils;

import android.text.InputFilter;
import android.text.Spanned;

import com.dereck.library.utils.ToastUtils;

/**
 * Created by zoubeiwen on 2018/11/13.
 */

public class MaxTextLengthFilter implements InputFilter {

    private int mMaxLength;
    //构造方法中传入最多能输入的字数
    public MaxTextLengthFilter(int max) {
        mMaxLength = max;
    }

    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {
        int keep = mMaxLength - (dest.length() - (dend - dstart));
        if (keep < (end - start)) {
            ToastUtils.showToast("最多只能输入" + mMaxLength + "个字");
        }
        if (keep <= 0) {
            return "";
        } else if (keep >= end - start) {
            return null;
        } else {
            return source.subSequence(start, start + keep);
        }
    }

}
