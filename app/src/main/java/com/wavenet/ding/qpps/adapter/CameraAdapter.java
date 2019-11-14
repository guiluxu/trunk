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

/**
 * Created by zoubeiwen on 2018/7/24.
 */

public class CameraAdapter extends RecyclerView.Adapter<CameraAdapter.cameraViewHolder> {

    final ArrayList<TImage> imaDatas;
    Context context;
    private OnItemClickListener mOnItemClickListener = null;

    public CameraAdapter(Context context, ArrayList<TImage> imaDatas) {
        this.context = context;
        this.imaDatas = imaDatas;

    }

    @Override
    public int getItemCount() {

        return imaDatas.size();
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(cameraViewHolder holder, final int position) {

        holder.itemView.setTag(position);
//        if (position == 0) {
//            Glide.with(context)
//                    .load(R.mipmap.img_pic)
//                    .into(holder.mIvphoto);
//            holder.mIVdelete.setVisibility(View.GONE);
//        } else {
        holder.mIVdelete.setVisibility(View.VISIBLE);
        holder.mIVdelete.setTag(position);
        holder.mIVdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imaDatas.remove(position);
                if (mOnItemClickListener != null) {
                    //注意这里使用getTag方法获取position
                    mOnItemClickListener.onItemClick(v, (int) v.getTag());
                }
                notifyDataSetChanged();
            }
        });
        RequestOptions options = new RequestOptions();
        Glide.with(context)
                .load(imaDatas.get(position).getOriginalPath()).apply(options)
                .into(holder.mIvphoto);

//        }
//        holder.mIvphoto.setTag(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    //注意这里使用getTag方法获取position
                    mOnItemClickListener.onItemClick(v, (int) v.getTag());
                }
            }
        });

    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder  view_photo_gvitem.xml
    @Override
    public cameraViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_photo_gvitem,null);
        View view = View.inflate(context, R.layout.view_photo_gvitem, null);
        cameraViewHolder cameraHolder = new cameraViewHolder(view);


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    //注意这里使用getTag方法获取position
                    mOnItemClickListener.onItemClick(v, (int) v.getTag());
                }
            }
        });
        return cameraHolder;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    class cameraViewHolder extends RecyclerView.ViewHolder {

        View view;
        ImageView mIvphoto, mIVdelete;

        public cameraViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            mIvphoto = view.findViewById(R.id.iv_photo);
            mIVdelete = view.findViewById(R.id.iv_delete);


        }
    }

}
