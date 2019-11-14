package com.wavenet.ding.qpps.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wavenet.ding.qpps.R;

import org.devio.takephoto.model.TImage;

import java.util.ArrayList;

public class DeleteImageRecyclerViewAdapter extends RecyclerView.Adapter<DeleteImageRecyclerViewAdapter.MyViewHolder> {

    final ArrayList<TImage> imaDatas;
    Context context;
    private OnItemClickListener mOnItemClickListener = null;

    public DeleteImageRecyclerViewAdapter(Context context, final ArrayList<TImage> company_license) {
        this.context = context;
        this.imaDatas = company_license;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_yh_report_recyclerview, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);


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
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.itemView.setTag(position);
        holder. iv_video.setVisibility(View.GONE);
        holder. iv_sound.setVisibility(View.GONE);
//        iv_video,iv_sound
//        if (imaDatas.size()==0)
//            return;
        if (imaDatas.get(position).mType == 0) {
            RequestOptions options = new RequestOptions();
            Glide.with(context)
                    .load(imaDatas.get(position).getOriginalPath()).apply(options)
                    .into(holder.imageView);

        }else if (imaDatas.get(position).mType == 1){
            holder. iv_video.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(imaDatas.get(position).getOriginalPath())
                    .into(holder.imageView);
        } else {
            holder. iv_sound.setVisibility(View.VISIBLE);

        }

        holder.iv_delete.setVisibility(View.VISIBLE);
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imaDatas.remove(position);
                notifyDataSetChanged();

            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return imaDatas.size();
    }


    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        View view;
        ImageView imageView, iv_delete, iv_video,iv_sound;

        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            imageView = view.findViewById(R.id.iv);
            iv_delete = view.findViewById(R.id.iv_delete);
            iv_video = view.findViewById(R.id.iv_video);
            iv_sound = view.findViewById(R.id.iv_sound);


        }
    }

}
