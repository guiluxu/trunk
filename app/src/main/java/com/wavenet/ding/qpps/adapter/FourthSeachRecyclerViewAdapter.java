package com.wavenet.ding.qpps.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.bean.AdapterBean;

import java.util.ArrayList;

public class FourthSeachRecyclerViewAdapter extends RecyclerView.Adapter<FourthSeachRecyclerViewAdapter.FourthSeachViewHolder> {


    private Context mContext;
    private ArrayList<AdapterBean> mListBeen;
    private OnItemClickListener mOnItemClickListener = null;

    public FourthSeachRecyclerViewAdapter(Context context, ArrayList<AdapterBean> mListBeen) {
        this.mContext = context;
        this.mListBeen = mListBeen;
    }

    @Override
    public FourthSeachViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, R.layout.adapter_fourthfragment, null);
        FourthSeachViewHolder myViewHolder = new FourthSeachViewHolder(view);


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
    public void onBindViewHolder(final FourthSeachViewHolder holder, final int position) {

        holder.itemView.setTag(position);
        AdapterBean b = mListBeen.get(position);
        String id = position + 1 + "";
        holder.textview1.setText(id);
        if (id.length() > 2) {
            holder.textview1.setBackground(mContext.getResources().getDrawable(R.drawable.adapter_idrec));
        } else {
            holder.textview1.setBackground(mContext.getResources().getDrawable(R.drawable.or_bg_id));
        }
        holder.textview2.setText(TextUtils.isEmpty(b.mag1) ? "" : b.mag1);
        holder.textview3.setText(TextUtils.isEmpty(b.mag2) ? "" : b.mag2);

        holder.textview4.setText((TextUtils.isEmpty(b.mag3) ? "" : b.mag3));
        holder.caizhi_type.setText("");
        if(b.guandao_caizhi_type != null){
            holder.caizhi_type.setText((TextUtils.isEmpty(b.guandao_caizhi_type) ? "" : b.guandao_caizhi_type));
        }
        if(b.jingai_caizhi_type != null){
            holder.caizhi_type.setText((TextUtils.isEmpty(b.jingai_caizhi_type) ? "" : b.jingai_caizhi_type));
        }
        if(b.jingai_caizhi_type==null&&b.guandao_caizhi_type==null){
            holder.type_rela.setVisibility(View.GONE);
        }else {
            holder.type_rela.setVisibility(View.VISIBLE);
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

    public class FourthSeachViewHolder extends RecyclerView.ViewHolder {
        View view;
        TextView textview1, textview2, textview3, textview4, caizhi_type;
        RelativeLayout type_rela;
        public FourthSeachViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            textview1 = view.findViewById(R.id.tv_num);
            textview2 = view.findViewById(R.id.tv_mag1);
            textview3 = view.findViewById(R.id.tv_mag2);
            textview4 = view.findViewById(R.id.tv_mag3);
            caizhi_type = view.findViewById(R.id.caizhi_type);
            type_rela = view.findViewById(R.id.type_rela);

        }
    }

}
