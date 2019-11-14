package com.wavenet.ding.qpps.view;

import android.app.AlertDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.dereck.library.utils.StringUtils;
import com.wavenet.ding.qpps.R;

/**
 * Create on 2019/5/28 16:52 by bll
 */


public class DialogUtil {

    private static volatile DialogUtil singleton;

    private DialogUtil() {
    }

    public static DialogUtil getSingleton() {
        if (singleton == null) {
            synchronized (DialogUtil.class) {
                if (singleton == null) {
                    singleton = new DialogUtil();
                }
            }
        }
        return singleton;
    }

    public void showTipDialog(Context context, String okMessage, String content) {
        View view = View.inflate(context, R.layout.dialog_tips, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle("提示").setView(view).setCancelable(false);
        final AlertDialog dialog = builder.create();
        TextView tvSure = view.findViewById(R.id.tv_ok);
        if (!StringUtils.isEmpty(okMessage)) {
            tvSure.setText(okMessage);
        }
        TextView tv_no = view.findViewById(R.id.tv_no);
        TextView tv_content = view.findViewById(R.id.tv_content);
        tv_content.setText(content);
        tv_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
//                if (listener != null) {
//                    listener.onCancelClick();
//                }
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (listener != null) {
                    listener.onOkClick();
                }
            }
        });
        dialog.setCancelable(true);
        dialog.show();
    }

    public interface OnButtonClickListener {
        void onOkClick();

//        void onCancelClick();
    }

    private OnButtonClickListener listener;

    public void setOnButtonClickListener(OnButtonClickListener listener) {
        this.listener = listener;
    }

}
