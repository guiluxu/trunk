package com.wavenet.ding.qpps.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import com.wavenet.ding.qpps.R;

import java.util.Calendar;


/**
 * Created by Administrator on 2017/7/25.
 */

public class DateDialog extends AlertDialog implements DialogInterface.OnClickListener,
        DatePicker.OnDateChangedListener {
    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String DAY = "day";

    private DatePicker mDatePicker;

    private OnDateSetListener mDateSetListener;


    public DateDialog(Context context) {
        super(context);
        init();
    }

    public DateDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        init();
    }

    public DateDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }
public void init(){
    getWindow().setLayout(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    final Context themeContext = getContext();
    final LayoutInflater inflater = LayoutInflater.from(themeContext);
    final View view = inflater.inflate(R.layout.dialog_data, null);
    setView(view);
    // 设置对话框大小
//    WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
//    layoutParams.width = mDatePicker.getWidth();
//    layoutParams.height = view.getHeight();
//    getWindow().setAttributes(layoutParams);
    mDatePicker = (DatePicker) view.findViewById(R.id.datePicker);
    setButton(BUTTON_POSITIVE, "选择", this);
    setButton(BUTTON_NEGATIVE, "取消", this);
    final Calendar calendar = Calendar.getInstance();
    int yes=calendar.get(Calendar.YEAR);
    int month= calendar.get(Calendar.MONTH);
    int day=calendar.get(Calendar.DAY_OF_MONTH);

}
    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case BUTTON_POSITIVE:
                if (mDateSetListener != null) {
                    // Clearing focus forces the dialog to commit any pending
                    // changes, e.g. typed text in a NumberPicker.
                    mDatePicker.clearFocus();
                    String m=mDatePicker.getMonth()+1+"";
                    String d=mDatePicker.getDayOfMonth()+"";
                    if (m.length()==1){
                        m="0"+m;
                    }
                    if (d.length()==1){
                        d="0"+d;
                    }
                    mDateSetListener.onDateSet(mDatePicker, mDatePicker.getYear(),m
                            ,d );
                }
                break;
            case BUTTON_NEGATIVE:
                cancel();
                break;
        }
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mDatePicker.init(year, monthOfYear, dayOfMonth, this);
    }
    public interface OnDateSetListener {
        /**
         * @param view the picker associated with the dialog
         * @param year the selected year
         * @param month the selected month (0-11 for compatibility with
         *              {@link Calendar#MONTH})
         * @param dayOfMonth th selected day of the month (1-31, depending on
         *                   month)
         */
        void onDateSet(DatePicker view, int year, String month, String dayOfMonth);
    }
    public void setOnDateSetListener( OnDateSetListener listener) {
        mDateSetListener = listener;
    }
}
