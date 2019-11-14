package com.wavenet.ding.qpps.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.bean.CacheViewHolder;
import com.wavenet.ding.qpps.bean.TasklistBean;
import com.wavenet.ding.qpps.utils.AppTool;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/10/13.
 */

public class TasklistAdapter extends BaseAdapter {

    public static String id = "";

    private Context mContext;
    private ArrayList<TasklistBean.ValueBean> mListBeen;
    private SpannableString textv;

    public TasklistAdapter(Context context, ArrayList<TasklistBean.ValueBean> mbean) {
        this.mContext = context;
        this.mListBeen = mbean;

    }

    @Override
    public int getCount() {
        return mListBeen.size();
    }

    @Override
    public Object getItem(int position) {

        return mListBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CacheViewHolder cacheView = null;
        if (convertView == null) {

            cacheView = new CacheViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_taskingitem, null);

            cacheView.ll1 = convertView.findViewById(R.id.ll_mag);
            cacheView.imageView1 = convertView.findViewById(R.id.iv_mag);
            cacheView.imageView2 = convertView.findViewById(R.id.iv_deal);
            cacheView.textview1 = convertView.findViewById(R.id.tv_time);
            cacheView.textview2 = convertView.findViewById(R.id.tv_mag2);
            cacheView.textview3 = convertView.findViewById(R.id.tv_addr);
//            cacheView.textview4 = convertView.findViewById(R.id.tv_mag4);
            cacheView.textview5 = convertView.findViewById(R.id.tv_clasmall);
            cacheView.textview6 = convertView.findViewById(R.id.tv_nav);// 导航
            cacheView.textview7 = convertView.findViewById(R.id.tv_dis);

            convertView.setTag(cacheView);
        } else {
            cacheView = (CacheViewHolder) convertView.getTag();
        }
        TasklistBean.ValueBean b = mListBeen.get(position);
        if (b.isSelect) {
            cacheView.imageView1.setImageResource(R.mipmap.radio_yes);
        } else {
            cacheView.imageView1.setImageResource(R.mipmap.radio_no);
        }
        if ("W1008100001".equals(b.S_EMERGENCY)) {
//            cacheView.imageView1.setVisibility(View.INVISIBLE);
            cacheView.imageView2.setImageResource(R.mipmap.ico_chuzhi_daicl_green);
        } else if ("W1008100002".equals(b.S_EMERGENCY)) {
            cacheView.imageView2.setImageResource(R.mipmap.ico_chuzhi_daicl_cheng);
        } else if ("W1008100003".equals(b.S_EMERGENCY)) {
            cacheView.imageView2.setImageResource(R.mipmap.ico_chuzhi_daicl_red);
        }
//        final String sid=b.sId;
        try {
            cacheView.textview1.setText(AppTool.getNullStr(b.T_IN_DATE).substring(5, 16).replace("/", "-"));
        } catch (Exception e) {
            e.printStackTrace();
            cacheView.textview1.setText(AppTool.getNullStr(b.T_IN_DATE).replace("/", "-"));

        }
        cacheView.textview2.setText(AppTool.getNullStr(b.S_SOURCE_CN));
        cacheView.textview3.setText(AppTool.getNullStr(b.S_NAME));
//        cacheView.textview4.setText(QPSWApplication.getInstance().m.get(AppTool.getNullStr(b.SCATEGORY)));
        cacheView.textview5.setText(AppTool.getNullStr(b.S_TYPE_CN));
        cacheView.textview7.setText(b.Dis + "m");
        cacheView.ll1.setTag(position);
        cacheView.ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer postion1 = (Integer) v.getTag();

                final TasklistBean.ValueBean tb = mListBeen.get(postion1);

                if (mA != null) {
                    mA.selectData(tb);
                }
                if (!tb.isSelect) {
                    for (int i = 0; i < mListBeen.size(); i++) {
                        mListBeen.get(i).isSelect = false;
                    }
                }
                tb.isSelect = true;
                notifyDataSetChanged();


            }

        });
        cacheView.textview6.setTag(position);
        cacheView.textview6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer postion1 = (Integer) v.getTag();

                final TasklistBean.ValueBean tb = mListBeen.get(postion1);
                if (mA != null) {
                    mA.getAData(tb);
                }
            }

        });
        return convertView;
    }

    public interface A {
        void getAData(TasklistBean.ValueBean b);

        void selectData(TasklistBean.ValueBean b);
    }

    A mA;

    public void setAListener(A mA) {
        this.mA = mA;
    }

    public void setDefSelect(int position) {
        if (mListBeen.size() > 0) {
            mListBeen.get(position).isSelect = true;
            if (mA != null) {
                mA.selectData(mListBeen.get(position));
            }
            notifyDataSetChanged();
        }
    }
}
