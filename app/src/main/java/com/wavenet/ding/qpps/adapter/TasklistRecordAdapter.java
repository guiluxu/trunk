package com.wavenet.ding.qpps.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.api.QPSWApplication;
import com.wavenet.ding.qpps.bean.TasklistBean;
import com.wavenet.ding.qpps.utils.AppConfig;
import com.wavenet.ding.qpps.utils.AppTool;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/10/13.
 */

public class TasklistRecordAdapter extends RecyclerView.Adapter<TasklistRecordAdapter.TasklistRecordViewHolder> {
    //
    public static String id = "";
    OnItemClickListener mOnItemClickListener;
    private Context mContext;
    private ArrayList<TasklistBean.ValueBean> mListBeen;
    private SpannableString textv;

    public TasklistRecordAdapter(Context context, ArrayList<TasklistBean.ValueBean> mbean) {
        this.mContext = context;
        this.mListBeen = mbean;

    }

    @Override
    public TasklistRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.adapter_taskrecorditem, null);
        TasklistRecordViewHolder myViewHolder = new TasklistRecordViewHolder(view);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    //注意这里使用getTag方法获取position
                    mOnItemClickListener.onItemClick(v, (int) v.getTag());
                }
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final TasklistRecordViewHolder holder, final int position) {
        holder.itemView.setTag(position);
        TasklistBean.ValueBean b = mListBeen.get(position);
        holder.imageView2.setVisibility(View.VISIBLE);
        if ("W1006500004".equals(b.S_STATUS)) {
//            cacheView.imageView1.setVisibility(View.INVISIBLE);
            holder.imageView2.setImageResource(R.mipmap.ico_lishi_yiwc);
        } else if ("W1006500001".equals(b.S_STATUS) || "W1006500006".equals(b.S_STATUS)) {
            holder.imageView2.setImageResource(R.mipmap.ico_lishi_daicl);
        } else if ("W1006500003".equals(b.S_STATUS)) {
            holder.imageView2.setImageResource(R.mipmap.ico_lishi_zhixz);
        }
        holder.textview1.setText(AppTool.getNullStr(b.T_CREATE).substring(0, 10));
        holder.textview2.setText(QPSWApplication.getInstance().m.get(AppTool.getNullStr(b.S_SOURCE)));
        holder.textview3.setText(AppTool.getNullStr(b.S_NAME));
        holder.textview4.setText(QPSWApplication.getInstance().m.get(AppTool.getNullStr(b.S_CATEGORY)));
        holder.textview5.setText(QPSWApplication.getInstance().m.get(AppTool.getNullStr(b.S_TYPE)));
        RequestOptions options = new RequestOptions()
                .fitCenter();
        if (!AppTool.isNull(b.S_MANGE_ID)) {

            Glide.with(mContext)
                    .load(AppConfig.BeasUrl+"2083/file/down/SJSB?relyid=" + b.S_MANGE_ID).apply(options)
                    .into(holder.imageView1);
        } else {
            Glide.with(mContext)
                    .load(R.mipmap.img_yangh_jiazsb).apply(options)
                    .into(holder.imageView1);
        }

    }

    @Override
    public int getItemCount() {
        return mListBeen.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class TasklistRecordViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView textview1, textview2, textview3, textview4, textview5;
        ImageView imageView2, imageView1;
        LinearLayout ll1;

        public TasklistRecordViewHolder(View itemView) {
            super(itemView);
            view = itemView;

            ll1 = view.findViewById(R.id.ll_mag);
//            cacheView.imageView1 = convertView.findViewById(R.id.iv_magrecord);
            textview1 = view.findViewById(R.id.tv_mag1);
            textview2 = view.findViewById(R.id.tv_mag2);
            textview3 = view.findViewById(R.id.tv_mag3);
            textview4 = view.findViewById(R.id.tv_mag4);
            textview5 = view.findViewById(R.id.tv_mag5);
            imageView2 = view.findViewById(R.id.iv_deal);
            imageView1 = view.findViewById(R.id.iv_mag1);

        }
    }
//        extends BaseAdapter {
//
//    public static String id = "";
//    private Context mContext;
//    private ArrayList<TasklistBean.ValueBean> mListBeen;
//    private SpannableString textv;
//
//    public TasklistRecordAdapter(Context context, ArrayList<TasklistBean.ValueBean> mbean) {
//        this.mContext = context;
//        this.mListBeen = mbean;
//
//    }
//
//    @Override
//    public int getCount() {
//        return mListBeen.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//
//        return mListBeen.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        CacheViewHolder cacheView = null;
//        if (convertView == null) {
//
//            cacheView = new CacheViewHolder();
//            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_taskrecorditem, null);
//
//            cacheView.ll1 = convertView.findViewById(R.id.ll_mag);
////            cacheView.imageView1 = convertView.findViewById(R.id.iv_magrecord);
//            cacheView.textview1 = convertView.findViewById(R.id.tv_mag1);
//            cacheView.textview2 = convertView.findViewById(R.id.tv_mag2);
//            cacheView.textview3 = convertView.findViewById(R.id.tv_mag3);
//            cacheView.textview4 = convertView.findViewById(R.id.tv_mag4);
//            cacheView.textview5 = convertView.findViewById(R.id.tv_mag5);
//            cacheView.imageView2 = convertView.findViewById(R.id.iv_deal);
//
//            convertView.setTag(cacheView);
//        } else {
//            cacheView = (CacheViewHolder) convertView.getTag();
//        }
//        TasklistBean.ValueBean b = mListBeen.get(position);
//
////        if (b.isSelect) {
////            cacheView.imageView1.setImageResource(R.mipmap.radio_yes);
////        } else {
////            cacheView.imageView1.setImageResource(R.mipmap.radio_no);
////        }
//        cacheView.imageView2.setVisibility(View.VISIBLE);
//        if ("W1006500004".equals(b.SSTATUS) || "W10065000033".equals(b.SSTATUS)) {
////            cacheView.imageView1.setVisibility(View.INVISIBLE);
//            cacheView.imageView2.setImageResource(R.mipmap.ico_lishi_yiwc);
//        } else {
//            cacheView.imageView2.setImageResource(R.mipmap.ico_lishi_daicl);
//        }
//        cacheView.textview1.setText(AppTool.getNullStr(b.TCREATE).substring(0, 10));
//        cacheView.textview2.setText(QPSWApplication.getInstance().m.get(AppTool.getNullStr(b.SSOURCE)));
//        cacheView.textview3.setText(AppTool.getNullStr(b.SNAME));
//        cacheView.textview4.setText(QPSWApplication.getInstance().m.get(AppTool.getNullStr(b.SCATEGORY)));
//        cacheView.textview5.setText(QPSWApplication.getInstance().m.get(AppTool.getNullStr(b.STYPE)));
//        cacheView.ll1.setTag(position);
////        cacheView.ll1.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                Integer postion1 = (Integer) v.getTag();
////
////                final TasklistBean.ValueBean tb = mListBeen.get(postion1);
////                MainMapXJActivity.STASKID = tb.STASKID;
////                id = tb.STASKID;
////                if (!tb.isSelect) {
////                    for (int i = 0; i < mListBeen.size(); i++) {
////                        mListBeen.get(i).isSelect = false;
////                    }
////                }
////                tb.isSelect = true;
////                notifyDataSetChanged();
////            }
////
////        });
//        return convertView;
//    }
////    BridgeListener mBridgeListener;
////    public  void setBridgeListener(BridgeListener mBridgeListener){
////        this.mBridgeListener=mBridgeListener;
////    }

}
