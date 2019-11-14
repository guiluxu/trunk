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
import com.wavenet.ding.qpps.bean.Legendbean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/10/13.
 */

public class MapLegendAdapter extends BaseAdapter {

    public static String id = "";

    private Context mContext;
    private ArrayList<Legendbean> mListBeen;
    private SpannableString textv;

    public MapLegendAdapter(Context context, ArrayList<Legendbean> mbean) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_maplegend, null);

            cacheView.ll1 = convertView.findViewById(R.id.ll_mag1);
            cacheView.imageView1 = convertView.findViewById(R.id.iv_mag1);
            cacheView.imageView2 = convertView.findViewById(R.id.iv_mag2);
            cacheView.textview1 = convertView.findViewById(R.id.tv_mag1);
            cacheView.textview2 = convertView.findViewById(R.id.tv_mag2);

            convertView.setTag(cacheView);
        } else {
            cacheView = (CacheViewHolder) convertView.getTag();
        }
        Legendbean b = mListBeen.get(position);
        if (b.isShowTitle){
            cacheView.textview1.setText(b.title);
            cacheView.textview1.setVisibility(View.VISIBLE);
        }else {
            cacheView.textview1.setVisibility(View.GONE);
        }
        cacheView.textview2.setText(b.name);
        cacheView.ll1.setSelected(b.isSelectLl);
        cacheView.imageView1.setSelected(b.isSelectIv);
        cacheView.imageView2.setImageResource(b.Ivid);
//if (b.isSelectIv){
//    if (mA!=null){
//        mA.toIvselected(b);
//    }
//}
        cacheView.ll1.setTag(position);
        cacheView.ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer p = (Integer) v.getTag();
                if (!mListBeen.get(p).isSelectLl){
                    for (int i = 0; i < mListBeen.size(); i++) {
                        mListBeen.get(i).isSelectLl = false;
                    }
                    mListBeen.get(p).isSelectLl=true;
                    mListBeen.get(p).isSelectIv=true;
                }
                if (mA!=null){
                    mA.toLlselected(mListBeen.get(p));
                }
                notifyDataSetChanged();
            }

        });
        cacheView.imageView1.setTag(position);
        cacheView.imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer p = (Integer) v.getTag();
                mListBeen.get(p).isSelectIv=!mListBeen.get(p).isSelectIv;
                if (mA!=null){
                    mA.toIvselected(mListBeen.get(p));
                }
                notifyDataSetChanged();
            }

        });

        return convertView;
    }

    public interface A {
        void toLlselected(Legendbean b);

        void toIvselected(Legendbean b);
    }

   public  A mA;

    public void setAListener(A mA) {
        this.mA = mA;
    }
}
