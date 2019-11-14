package com.wavenet.ding.qpps.demo.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.dereck.library.refreshrecyclerview.base.adapter.BaseRecyclerViewAdapter;
import com.dereck.library.refreshrecyclerview.base.adapter.BaseViewHolder;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.demo.bean.MyRes;
import com.wavenet.ding.qpps.demo.bean.TainingBean;

import java.util.List;

public class TrainingActivityRecyclerViewAdapter extends BaseRecyclerViewAdapter<TainingBean.RECBean> {


    public TrainingActivityRecyclerViewAdapter(Context mContext, List<TainingBean.RECBean> mDatas) {
        super(mContext, R.layout.item_training, mDatas);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, TainingBean.RECBean listBean) {
        baseViewHolder.setText(R.id.tvName, listBean.getSOURCE_NAME());
        baseViewHolder.setText(R.id.tvContent, "暂无描述");
        baseViewHolder.setText(R.id.tvTime, listBean.getCREATED_DATE());
        baseViewHolder.setText(R.id.tvNum, listBean.getVISIT_COUNT() + "");
        ImageView view = baseViewHolder.getView(R.id.iv);

        Glide.with(mContext).load(MyRes.BaseUrl + listBean.getSOURCE_PIC_URL()).into(view);
        Log.e("图片", MyRes.BaseUrl + listBean.getSOURCE_PIC_URL());
        switch (listBean.getSOURCE_TYPE()) {
            case "6":

                Glide.with(mContext).load(R.mipmap.ic_launcher).into((ImageView) baseViewHolder.getView(R.id.ivTab));

                break;
            case "2":
                Glide.with(mContext).load(R.mipmap.ic_launcher_round).into((ImageView) baseViewHolder.getView(R.id.ivTab));


                break;
            case "1":
                Glide.with(mContext).load(R.mipmap.ic_launcher).into((ImageView) baseViewHolder.getView(R.id.ivTab));

                break;
        }

    }


}