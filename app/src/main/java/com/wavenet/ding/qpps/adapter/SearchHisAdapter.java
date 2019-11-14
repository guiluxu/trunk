package com.wavenet.ding.qpps.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.bean.SearchHistory;

import java.util.List;

/**
 * Create on 2019/5/27 18:34 by bll
 */


public class SearchHisAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int TYPE_FOOTER_VIEW = 1;
    private Context mContext;
    private List<SearchHistory.DataBean> mHisList;

    public SearchHisAdapter(Context context, List<SearchHistory.DataBean> hisList) {
        this.mContext = context;
        this.mHisList = hisList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_FOOTER_VIEW) {
            View footView = LayoutInflater.from(mContext).inflate(R.layout.adapter_foot_view, viewGroup, false);
            return new FooterViewHolder(footView);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_search_his, null);
            HisViewHolder holder = new HisViewHolder(view);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        if (holder != null) {
            if (holder instanceof HisViewHolder) {
                HisViewHolder hisViewHolder = (HisViewHolder) holder;
                hisViewHolder.tvHis.setText(mHisList.get(position).getSEARCHVALUE());
                hisViewHolder.layoutHis.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onItemClick(v, mHisList.get(position).getSEARCHVALUE());
                        }
                    }
                });
            } else if (holder instanceof FooterViewHolder) {
                FooterViewHolder viewHolder = (FooterViewHolder) holder;
                viewHolder.clearAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (listener != null) {
                            listener.onClearClick(v, mHisList, position);
                        }
                    }
                });
            }
        }

    }

    @Override
    public int getItemViewType(int position) {
        /*当position是最后一个的时候，也就是比list的数量多一个的时候，则表示FooterView*/
        if (position + 1 == mHisList.size() + 1) {
            return TYPE_FOOTER_VIEW;
        }
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mHisList == null ? 0 : mHisList.size() + 1;
    }


    public class HisViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout layoutHis;
        private TextView tvHis;

        public HisViewHolder(View itemView) {
            super(itemView);
            layoutHis = itemView.findViewById(R.id.layout_his);
            tvHis = itemView.findViewById(R.id.tv_his);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout clearAll;

        public FooterViewHolder(View itemView) {
            super(itemView);
            if (itemView != null) {
                clearAll = itemView.findViewById(R.id.clear_all);
            }
        }
    }

    public interface OnHisItemClickListener {
        void onItemClick(View v, String str);

        void onClearClick(View v, List<SearchHistory.DataBean> hisList, int pos);
    }

    private OnHisItemClickListener listener;

    public void setOnHisClickListener(OnHisItemClickListener listener) {
        this.listener = listener;
    }
}
