package com.wavenet.ding.qpps.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.utils.AppAttribute;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 曲线图表
 * Created by Administrator on 2017/7/25.
 */

public class ViewChartDate extends LinearLayout implements View.OnClickListener
       , DatePicker.OnDateChangedListener {

    private Context mContext;

    private static final String YEAR = "year";
    private static final String MONTH = "month";
    private static final String DAY = "day";

    private  DatePicker mDatePicker;
    private OnDateSetListener mDateSetListener;

//    private ArrayList<HistoryWaterBean> hiswaterbean = new ArrayList<HistoryWaterBean>();

    private SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd"); //设置获取系统时间格式
    private String date = sDateFormat.format(new java.util.Date()); //获取当前系统

    private int CurPage = 1;
    private int pageSize = 100;
    private String s_no = "";
    AppAttribute mAppAttr;
    ImageView iv_close;
    public ViewChartDate(Context context) {
        super(context);
        initView(context);
    }

    public ViewChartDate(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ViewChartDate(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;

        LayoutInflater.from(mContext).inflate(R.layout.dialog_data, this);
        if (!isInEditMode()) {
            // 设置对话框大小
//    WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
//    layoutParams.width = mDatePicker.getWidth();
//    layoutParams.height = view.getHeight();
//    getWindow().setAttributes(layoutParams);
            mDatePicker = (DatePicker) findViewById(R.id.datePicker);
            iv_close = (ImageView) findViewById(R.id.iv_close);
            final Calendar calendar = Calendar.getInstance();
            int yes=calendar.get(Calendar.YEAR);
            int month= calendar.get(Calendar.MONTH);
            int day=calendar.get(Calendar.DAY_OF_MONTH);
        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                setVisibility(GONE);
                break;
        }
    }



    @Override
    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mDatePicker.init(year, monthOfYear, dayOfMonth, this);
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
