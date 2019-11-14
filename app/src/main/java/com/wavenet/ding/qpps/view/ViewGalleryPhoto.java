package com.wavenet.ding.qpps.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.utils.photo.adater.MyPageAdapter;
import com.wavenet.ding.qpps.utils.photo.util.Bimp;
import com.wavenet.ding.qpps.utils.photo.util.ImageItem;
import com.wavenet.ding.qpps.utils.photo.zoom.PhotoView;
import com.wavenet.ding.qpps.utils.photo.zoom.ViewPagerFixed;

import org.devio.takephoto.model.TImage;

import java.io.IOException;
import java.util.ArrayList;


public class ViewGalleryPhoto extends LinearLayout implements View.OnClickListener {
    private Intent intent;
    // 返回按钮
    private Button back_bt;
    // 发送按钮
    private Button send_bt;
    //删除按钮
    private Button del_bt;

    //当前的位置
    private int location = 0;

    private ArrayList<View> listViews = new ArrayList<View>();
    private ViewPagerFixed pager;
    private MyPageAdapter adapter;
    Context mContext;
    RequestOptions options;
    public ViewGalleryPhoto(Context context) {
        super(context);
        initView(context);
    }

    public ViewGalleryPhoto(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ViewGalleryPhoto(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater.from(mContext).inflate(R.layout.view_photo_adatergallery, this);
        if (!isInEditMode()) {

            back_bt = (Button) findViewById(R.id.gallery_back);
            send_bt = (Button) findViewById(R.id.send_button);
            del_bt = (Button)findViewById(R.id.gallery_del);

            pager = (ViewPagerFixed) findViewById(R.id.gallery01);
            adapter = new MyPageAdapter(listViews);
            pager.setAdapter(adapter);
            pager.setPageMargin((int)getResources().getDimensionPixelOffset(R.dimen.dp10));
             options = new RequestOptions();
            options  .placeholder(R.mipmap.tbbtn_takephoto)				//加载成功之前占位图
                    .error(R.mipmap.tbbtn_takephoto)  .fitCenter();

            back_bt.setOnClickListener(this);
            send_bt.setOnClickListener(this);
            del_bt.setOnClickListener(this);
            pager.setOnPageChangeListener(pageChangeListener);
        }
    }
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.gallery_back:
                setVisibility(GONE);
                break;
                case R.id.send_button:
                    setVisibility(GONE);
                break;
                case R.id.gallery_del:
                    if (mNotifyDataSetChangedListen!=null){
                        mNotifyDataSetChangedListen.notifyDataSetChangedListen(location);
                    }
                    Bimp.tempSelectBitmap.remove(location);
                    send_bt.setText("完成"+"(" + Bimp.tempSelectBitmap.size() + "/"+9+")");
                    pager.removeAllViews();
                    listViews.remove(location);
                if (listViews.size() == 0) {
                    setVisibility(GONE);
                } else {
                    adapter.setListViews(listViews);
                    adapter.notifyDataSetChanged();
                }


                break;
        }
    }
    public void setData(int id, ArrayList<TImage> imaDatas){
        setVisibility(VISIBLE);
        del_bt.setVisibility(VISIBLE);
        listViews.clear();
        pager.removeAllViews();
        Bimp.tempSelectBitmap.clear();
        for (int i = 0; i < imaDatas.size(); i++) {
            ImageItem mII=new ImageItem();
            mII.setImagePath(imaDatas.get(i).getOriginalPath());
            Bimp.tempSelectBitmap.add(mII);
        }
        for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
            initListViews( i);
        }
        adapter.setListViews(listViews);
        adapter.notifyDataSetChanged();
        isShowOkBt();
        location=id;
        pager.setCurrentItem(id);
    }
    public void setData1(int id, ArrayList<String> imaDatas){
        setVisibility(VISIBLE);
        del_bt.setVisibility(INVISIBLE);
        listViews.clear();
        pager.removeAllViews();
        Bimp.tempSelectBitmap.clear();
        for (int i = 0; i < imaDatas.size(); i++) {
            ImageItem mII=new ImageItem();
            mII.setImagePath(imaDatas.get(i));
            Bimp.tempSelectBitmap.add(mII);
        }
        for (int i = 0; i < Bimp.tempSelectBitmap.size(); i++) {
            initListViews( i);
        }
        adapter.setListViews(listViews);
        adapter.notifyDataSetChanged();
        isShowOkBt();
        location=id;
        pager.setCurrentItem(id);
    }
    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        public void onPageSelected(int arg0) {
            location = arg0;
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        public void onPageScrollStateChanged(int arg0) {

        }
    };

    public void isShowOkBt() {
        if (Bimp.tempSelectBitmap.size() > 0) {
            send_bt.setText("完成"+"(" + Bimp.tempSelectBitmap.size() + "/"+9+")");
            send_bt.setPressed(true);
            send_bt.setClickable(true);
            send_bt.setTextColor(Color.WHITE);
        } else {
            send_bt.setPressed(false);
            send_bt.setClickable(false);
            send_bt.setTextColor(Color.parseColor("#E1E0DE"));
        }
    }

    private void initListViews(int i) {
        PhotoView img = new PhotoView(mContext);
        img.setBackgroundColor(0xff000000);
//		holder.image.setImageBitmap(Bimp.tempSelectBitmap.get(position).getBitmap());
//        if (Bimp.tempSelectBitmap.get(i).getImagePath().startsWith("http:")){
            Glide.with(mContext)
                    .load(Bimp.tempSelectBitmap.get(i).getImagePath()).apply(options)
                    .into(img);
//        }else {
//            img.setImageBitmap(bm);
//        }

        img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        listViews.add(img);
    }

    public Bitmap getBitmap(String imagePath) {
        Bitmap bitmap=null;

            try {
                if (imagePath.contains("http://")) {
                    bitmap = Bimp.netPicToBmp(imagePath);
                } else {
                    bitmap = Bimp.revitionImageSize(imagePath);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        return bitmap;
    }
    public interface NotifyDataSetChangedListen {
        void notifyDataSetChangedListen(int p);

    }
    NotifyDataSetChangedListen mNotifyDataSetChangedListen;
    public void setNotifyDataSetChangedListen(NotifyDataSetChangedListen mNotifyDataSetChangedListen){
        this.mNotifyDataSetChangedListen=mNotifyDataSetChangedListen;
    }

}
