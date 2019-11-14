package com.wavenet.ding.qpps.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.guoqi.actionsheet.ActionSheet;
import com.wavenet.ding.qpps.R;


public class ActionSheetSuper extends ActionSheet{


    public static Dialog showSheet(Context context, boolean isReport,final ActionSheet.OnActionSheetSelected actionSheetSelected,
                                   DialogInterface.OnCancelListener cancelListener) {
        final Dialog dialog = new Dialog(context, R.style.ActionSheet);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.view_action_sheet, null);
        final int cFullFillWidth = 10000;
        layout.setMinimumWidth(cFullFillWidth);

        TextView picTextView = (TextView) layout.findViewById(R.id.picTextView);
        TextView camTextView = (TextView) layout.findViewById(R.id.camTextView);
        TextView cancelTextView = (TextView) layout.findViewById(R.id.cancelTextView);
        if (isReport){
            picTextView.setVisibility(View.GONE);
        } else {
            picTextView.setVisibility(View.VISIBLE);
        }

        picTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                actionSheetSelected.onClick(CHOOSE_PICTURE);
                dialog.dismiss();
            }
        });

        camTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                actionSheetSelected.onClick(TAKE_PICTURE);
                dialog.dismiss();
            }
        });
        cancelTextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                actionSheetSelected.onClick(CANCEL);
                dialog.dismiss();
            }
        });


        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x = 0;
        final int cMakeBottom = -1000;
        lp.y = cMakeBottom;
        lp.gravity = Gravity.BOTTOM;
        dialog.onWindowAttributesChanged(lp);
        dialog.setCanceledOnTouchOutside(true);
        if (cancelListener != null)
            dialog.setOnCancelListener(cancelListener);

        dialog.setContentView(layout);
        dialog.show();

        return dialog;
    }
}
