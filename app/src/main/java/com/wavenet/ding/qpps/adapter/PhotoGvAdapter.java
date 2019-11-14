package com.wavenet.ding.qpps.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;


/**
 * 照片适配
 * Created by Admin on 2016/7/4.
 */
public class PhotoGvAdapter extends BaseAdapter {
    Context context;
    public PhotoGvAdapter(Context context, final ArrayList<String> company_license) {
        this.context=context;
    }


    @Override
    public int getCount() {
        return 0;
    }

    public Object getItem(int arg0) {
        return null;
    }

    public long getItemId(int arg0) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
//        if (convertView == null) {
//            convertView = LayoutInflater.from(context).inflate(R.layout.view_photo_gvitem,
//                    parent, false);
//            holder = new ViewHolder();
//            holder.image = (ImageView) convertView
//                    .findViewById(R.id.item_grida_image);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        if (position == Bimp.tempSelectBitmap.size()) {
//            holder.image.setImageBitmap(BitmapFactory.decodeResource(
//                    context.getResources(), R.mipmap.tbbtn_takephoto));
//            if (position == 8) {
//                holder.image.setVisibility(View.GONE);
//            }
//        } else {
////            Picasso.with(context).load(new File(Bimp.tempSelectBitmap.get(position).getImagePath())).into( holder.image);
//            holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
//        }

        return convertView;
    }

    public class ViewHolder {
        public ImageView image;
    }

}
