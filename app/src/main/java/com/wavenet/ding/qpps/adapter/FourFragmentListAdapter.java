package com.wavenet.ding.qpps.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.bean.AdapterBean;
import com.wavenet.ding.qpps.bean.CacheViewHolder;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/10/13.
 */

public class FourFragmentListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<AdapterBean> mListBeen;
    private SpannableString textv;

    public FourFragmentListAdapter(Context context, ArrayList<AdapterBean> mListBeen) {
        this.mContext = context;
        this.mListBeen = mListBeen;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CacheViewHolder cacheView = null;
        if (convertView == null) {
            cacheView = new CacheViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_fourthfragment, null);
            cacheView.textview1 = convertView.findViewById(R.id.tv_num);
            cacheView.textview2 = convertView.findViewById(R.id.tv_mag1);
            cacheView.textview3 = convertView.findViewById(R.id.tv_mag2);
            cacheView.textview4 = convertView.findViewById(R.id.tv_mag3);
            cacheView.caizhi_type = convertView.findViewById(R.id.caizhi_type);
            cacheView.type_rela = convertView.findViewById(R.id.type_rela);
//            cacheView.textview5 =  convertView.findViewById(R.id.tv_time);
            convertView.setTag(cacheView);
        } else {
            cacheView = (CacheViewHolder) convertView.getTag();
        }
        AdapterBean b = mListBeen.get(position);
        String id = position + 1 + "";
        cacheView.textview1.setText(id);
        if (id.length() > 2) {
            cacheView.textview1.setBackground(mContext.getResources().getDrawable(R.drawable.adapter_idrec));
        } else {
            cacheView.textview1.setBackground(mContext.getResources().getDrawable(R.drawable.or_bg_id));
        }
        cacheView.textview2.setText(TextUtils.isEmpty(b.mag1) ? "" : b.mag1);
        cacheView.textview3.setText(TextUtils.isEmpty(b.mag2) ? "" : b.mag2);

        cacheView.textview4.setText((TextUtils.isEmpty(b.mag3) ? "" : b.mag3));
        if (b.guandao_caizhi_type==null&&b.jingai_caizhi_type==null){
            cacheView.type_rela.setVisibility(View.GONE);
        }else {
            if(b.guandao_caizhi_type!=null){
                cacheView.caizhi_type.setText((TextUtils.isEmpty(b.guandao_caizhi_type) ? "" : b.guandao_caizhi_type));
            }
            if(b.jingai_caizhi_type!=null){
                cacheView.caizhi_type.setText((TextUtils.isEmpty(b.jingai_caizhi_type) ? "" : b.jingai_caizhi_type));
            }
        }

        /*if(b.type.equals("guandao")){
            cacheView.caizhi_type.setText((TextUtils.isEmpty(b.guandao_caizhi_type) ? "" : b.guandao_caizhi_type));
        }else if(b.type.equals("jinggai")){
            cacheView.caizhi_type.setText((TextUtils.isEmpty(b.jingai_caizhi_type) ? "" : b.jingai_caizhi_type));
        }*/

//        String timeyesstr= AppTool.getCurrentDate("yyyy");
//        String time="";
//        String quality="";
//        time= b.tLastSamplingTime;
//        if (time==null){
//            time="";
//        }
//        if (!TextUtils.isEmpty(b.nLastQuality)){
//            quality=b.nLastQuality;
//        }
//        if (!TextUtils.isEmpty(time)){
//            if (time.contains("0001") ){
//                time="";
//            }else {
//            time=time.substring(0, time.length() - 3);
//            time="("+time+")";
//        }}
//        if (!TextUtils.isEmpty(time) ){
//        if ( time.contains(timeyesstr)){
//            cacheView.textview5.setText(quality+time.replace(timeyesstr+"/",""));
//        }else {
//            cacheView.textview5.setText(quality+time);
//        }}else {
//            cacheView.textview5.setText(quality+time);
//        }
        return convertView;
    }
}
