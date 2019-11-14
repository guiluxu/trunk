package com.wavenet.ding.qpps.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.bean.DropBean;
import com.wavenet.ding.qpps.utils.AppTool;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/6/24.
 */

public class ControllerXJRecordSelecetTop extends LinearLayout {
    Context mContext;
    View v_h;
    private DropdownButton dropdownButton1;
    private DropdownButton dropdownButton2;
    private DropdownButton dropdownButton3;
    private DropdownButton dropdownButton4;
    private List<DropBean> drop1;
    private List<DropBean> drop2;
    private List<DropBean> drop3;
    private List<DropBean> drop4;

    public ControllerXJRecordSelecetTop(Context context) {
        super(context);

        initView(context);
    }

    public ControllerXJRecordSelecetTop(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        LayoutInflater.from(mContext).inflate(R.layout.view_xjrecordselecttop, this);
        if (!isInEditMode()) {
            dropdownButton1 = findViewById(R.id.time1);
            dropdownButton2 = findViewById(R.id.time2);
            dropdownButton3 = findViewById(R.id.time3);
            dropdownButton4 = findViewById(R.id.d_youxian);
            v_h = findViewById(R.id.v_h);

            initSomeData();
            dropdownButton1.setData(drop1, v_h);
            dropdownButton2.setData(drop2, v_h);
            dropdownButton3.setData(drop3, v_h);
            dropdownButton4.setData(drop4, v_h);
        }
    }

    private void initSomeData() {
        drop1 = new ArrayList<>();
        drop2 = new ArrayList<>();
        drop3 = new ArrayList<>();
        drop4 = new ArrayList<>();
        AppTool.getDictionaries(mContext);
        drop1.add(new DropBean("一星期之内", AppTool.getBeforeData(0, 7, AppTool.FORMAT_YMDHMS), 1));
        drop1.add(new DropBean("一个月之内", AppTool.getBeforeData(1, 0, AppTool.FORMAT_YMDHMS), 1));
        drop1.add(new DropBean("全部", "", 1));

        drop2.add(new DropBean("全部来源", "", 2));
        drop2.add(new DropBean("群众举报", "W1007500001", 2));
        drop2.add(new DropBean("街镇自查", "W1007500003", 2));
        drop2.add(new DropBean("巡查上报", "W1007500004", 2));
        drop2.add(new DropBean("热线转交", "W1007500002", 2));

        drop3.add(new DropBean("全部状态", "", 3));
        drop3.add(new DropBean("未派遣", "W1006500001", 3));
        drop3.add(new DropBean("已派遣", "W1006500002", 3));
        drop3.add(new DropBean("执行中", "W1006500003", 3));
        drop3.add(new DropBean("已完成", "W1006500004", 3));
        drop3.add(new DropBean("已拒绝", "W1006500005", 3));
        drop3.add(new DropBean("已退单", "W1006500006", 3));


        drop4.add(new DropBean("全部级别", "", 4));
        drop4.add(new DropBean("紧急", "W1008100002", 4));
        drop4.add(new DropBean("非常紧急", "W1008100003", 4));
        drop4.add(new DropBean("普通", "W1008100001", 4));
    }

    public void notifyData(int position, int tab) {
        if (tab == 1) {
            dropdownButton1.notifyData(position);
        } else if (tab == 2) {
            dropdownButton2.notifyData(position);
        } else if (tab == 3) {
            dropdownButton3.notifyData(position);
        }
    }

    public String getItmestr(int postion, int tab) {
        if (tab == 1) {
            return drop1.get(postion).getKey();
        } else if (tab == 2) {
            return drop2.get(postion).getKey();
        } else if (tab == 3) {
            return drop3.get(postion).getKey();
        } else if (tab == 4) {
            return drop4.get(postion).getKey();
        }
        return "";
    }

    public void setOnDropItemSelectListener(DropdownButton.OnDropItemSelectListener onDropItemSelectListener) {
        dropdownButton1.setOnDropItemSelectListener(onDropItemSelectListener);
        dropdownButton2.setOnDropItemSelectListener(onDropItemSelectListener);
        dropdownButton3.setOnDropItemSelectListener(onDropItemSelectListener);
        dropdownButton4.setOnDropItemSelectListener(onDropItemSelectListener);
    }
}
