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
import com.wavenet.ding.qpps.bean.RoadBean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/10/13.
 */

public class YHReportListViewAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<RoadBean> mListBeen;
    private SpannableString textv;

    public YHReportListViewAdapter(Context context, ArrayList<RoadBean> mbean) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_yh_report, null);
            cacheView.ll1 = convertView.findViewById(R.id.ll_mag);
            cacheView.imageView1 = convertView.findViewById(R.id.iv_mag);
            cacheView.textview1 = convertView.findViewById(R.id.tv_mag1);
            cacheView.textview2 = convertView.findViewById(R.id.tv_mag2);
            cacheView.textview3 = convertView.findViewById(R.id.tv_mag3);
            convertView.setTag(cacheView);
        } else {
            cacheView = (CacheViewHolder) convertView.getTag();
        }
        RoadBean b = mListBeen.get(position);
        if (b.isSelect) {
            cacheView.imageView1.setImageResource(R.mipmap.radio_yes);
        } else {
            cacheView.imageView1.setImageResource(R.mipmap.radio_no);
        }

        cacheView.textview1.setText(mListBeen.get(position).NAME);
        cacheView.textview2.setText(mListBeen.get(position).QD_ROAD);
        cacheView.textview3.setText(mListBeen.get(position).ZD_ROAD);

        return convertView;
    }
//    BridgeListener mBridgeListener;
//    public  void setBridgeListener(BridgeListener mBridgeListener){
//        this.mBridgeListener=mBridgeListener;
//    }

}
