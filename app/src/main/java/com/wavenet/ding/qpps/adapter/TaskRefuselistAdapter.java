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

public class TaskRefuselistAdapter extends BaseAdapter {
    public static String id = "";
    private Context mContext;
    private ArrayList<TasklistBean.ValueBean> mListBeen;
    private SpannableString textv;

    public TaskRefuselistAdapter(Context context, ArrayList<TasklistBean.ValueBean> mbean) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_taskrefuseitem, null);
            cacheView.textview5 = convertView.findViewById(R.id.tv_timename);
            cacheView.textview6 = convertView.findViewById(R.id.tv_username);
            cacheView.textview7 = convertView.findViewById(R.id.tv_resonname);
            cacheView.textview1 = convertView.findViewById(R.id.tv_time);
            cacheView.textview2 = convertView.findViewById(R.id.tv_user);
            cacheView.textview3 = convertView.findViewById(R.id.tv_reson);
            cacheView.textview4 = convertView.findViewById(R.id.tv_resonedit);
            cacheView.textview8 = convertView.findViewById(R.id.tv_title);
            cacheView.ll1 = convertView.findViewById(R.id.ll_resonedit);

            convertView.setTag(cacheView);
        } else {
            cacheView = (CacheViewHolder) convertView.getTag();
        }
        TasklistBean.ValueBean b = mListBeen.get(position);

        if ("W1006500006".equals(AppTool.getNullStr(b.S_STATUS))) {
            cacheView.textview8.setText("退单信息");
            cacheView.textview5.setText("退单时间");
            cacheView.textview6.setText("退单人");
            cacheView.textview7.setText("退单理由");
        } else {
            cacheView.textview8.setText("拒绝信息");
            cacheView.textview5.setText("拒绝时间");
            cacheView.textview6.setText("拒绝人员");
            cacheView.textview7.setText("拒绝理由");
        }
        cacheView.textview1.setText(AppTool.setTvTime(b.T_TM));
        cacheView.textview2.setText(AppTool.getNullStr(b.S_MAN_FULLNAME));
        if (!AppTool.isNull((b.S_REASON))) {
            cacheView.textview3.setVisibility(View.VISIBLE);
            cacheView.textview3.setText(AppTool.getNullStr(b.S_REASON));
        } else {
            cacheView.textview3.setVisibility(View.GONE);
        }

        if (true) {
            cacheView.textview4.setText(AppTool.getNullStr(b.S_REMARK));
        } else {
            cacheView.rl1.setVisibility(View.GONE);
        }
//        cacheView.textview2.setText(AppTool.getNullStr("测试3"));
//        if (!AppTool.isNull("测试4")){
//            cacheView.textview3.setText(QPSWApplication.getInstance().m.get(AppTool.getNullStr("测试5")));
//        }
//        if (true){
//            cacheView.ll1.setVisibility(View.VISIBLE);
//            cacheView.textview4.setText(AppTool.getNullStr("测试6"));
//        }else {
//            cacheView.ll1.setVisibility(View.GONE);
//        }


        return convertView;
    }
    //    BridgeListener mBridgeListener;
//    public  void setBridgeListener(BridgeListener mBridgeListener){
//        this.mBridgeListener=mBridgeListener;
//    }

}
