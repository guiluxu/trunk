package com.wavenet.ding.qpps.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.bean.AdminPeopleBean;
import com.wavenet.ding.qpps.bean.AdminPersonBean2;
import com.wavenet.ding.qpps.utils.AppConfig;
import com.wavenet.ding.qpps.utils.AppTool;


public class ViewFourthFragmentPeopledetail extends LinearLayout implements View.OnClickListener {
    Context mContext;
    TextView textview1, textview2, textview3, textview4, textview5;
    TextView man_name, tv_useridentity2;
    ImageView man_header, man_status;
    TextView man_jiezhen, man_phone, man_danweimingcheng;
    ImageView chuzhi, xuncha, shijian, yanghu;
    RelativeLayout relative_4, relative_3;

    //图片Url进行拼接
    public static String baseImageUrl =AppConfig.BeasUrl+ "2088";

    public ViewFourthFragmentPeopledetail(Context context) {
        super(context);
        initView(context);
    }

    public ViewFourthFragmentPeopledetail(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ViewFourthFragmentPeopledetail(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT));
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater.from(mContext).inflate(R.layout.view_fourthfragmentpeopledetail, this);
        if (!isInEditMode()) {
            setOnClickListener(this);
            textview5 = findViewById(R.id.tv_useridentity);
            textview1 = findViewById(R.id.tv_username);
            textview2 = findViewById(R.id.tv_state);
            textview3 = findViewById(R.id.tv_company);
            textview4 = findViewById(R.id.tv_addr);
            findViewById(R.id.iv_close).setOnClickListener(this);
            findViewById(R.id.iv_close2).setOnClickListener(this);
            man_name = (TextView)findViewById(R.id.man_name);
            tv_useridentity2 = (TextView)findViewById(R.id.tv_useridentity2);
            man_header = (ImageView)findViewById(R.id.man_header);
            man_status = (ImageView)findViewById(R.id.man_status);
            man_jiezhen = (TextView)findViewById(R.id.man_jiezhen);
            man_phone = (TextView)findViewById(R.id.man_phone);
            man_danweimingcheng = (TextView)findViewById(R.id.man_danweimingcheng);
            chuzhi = (ImageView)findViewById(R.id.chuzhi);
            xuncha = (ImageView)findViewById(R.id.xuncha);
            shijian = (ImageView)findViewById(R.id.shijian);
            chuzhi.setOnClickListener(this);
            xuncha.setOnClickListener(this);
            shijian.setOnClickListener(this);

            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Medium.ttf");
            tv_useridentity2.setTypeface(typeface);
            man_name.setTypeface(typeface);
            relative_3 = (RelativeLayout)findViewById(R.id.relative_3);
            relative_4 = (RelativeLayout)findViewById(R.id.relative_4);
            yanghu = (ImageView)findViewById(R.id.yanghu);
            yanghu.setOnClickListener(this);
        }
    }
String SCOMNAME="";
    public void setdata(AdminPeopleBean.TPersonCollectionBean.TPersonBean b) {
        cleanData();
//        setVisibility(VISIBLE);
        showOrHide(true, "", "");
        textview1.setText(AppTool.getNullStr(b.SMANNAME));
        textview2.setText(AppTool.getNullStr(b.STATUS));
        textview3.setText(AppTool.getNullStr(b.SCOMNAME));
        SCOMNAME=b.SCOMNAME;
        textview4.setText(AppTool.getNullStr(b.STOWNID));
        textview5.setText(AppTool.getNullStr("（" + b.SMANNAMEABBREVIATION + "）"));
        man_name.setText(AppTool.getNullStr(b.SMANNAME));
        tv_useridentity2.setText("（"+AppTool.getNullStr(b.SMANNAMEABBREVIATION)+"）");
        if(b.NHDPIC!=null){
            Glide.with(mContext).load(baseImageUrl+b.NHDPIC).into(man_header);
        }else {
            if (b.SMANNAMEABBREVIATION.equals("巡查人员")){
                man_header.setImageResource(R.mipmap.img_xunc_tx);
            }else {
                man_header.setImageResource(R.mipmap.img_yanghc_tx);
            }
        }
        if (b.SMANNAMEABBREVIATION.equals("巡查人员")){
            relative_3.setVisibility(VISIBLE);
            relative_4.setVisibility(GONE);
        }else {
            relative_3.setVisibility(GONE);
            relative_4.setVisibility(VISIBLE);
        }
        /*if (b.SMANNAMEABBREVIATION.equals("巡查人员")){
            man_header.setImageResource(R.mipmap.img_xunc_tx);
        }else {
            man_header.setImageResource(R.mipmap.img_yanghc_tx);
        }*/
        if(AppTool.getNullStr(b.STATUS).equals("离线")){
            man_status.setImageResource(R.mipmap.ico_renyzt_lix);
        }else if(AppTool.getNullStr(b.STATUS).equals("在线")){
            man_status.setImageResource(R.mipmap.ico_renyzt_zaix);
        }
        man_jiezhen.setText(AppTool.getNullStr(b.STOWNID));
        man_phone.setText(AppTool.getNullStr(""+b.SPHONE));
        man_danweimingcheng.setText(AppTool.getNullStr(b.SCOMNAME));
    }

String name="";
    public void setdata2(AdminPersonBean2.DataBean bean) {
        cleanData();
//        setVisibility(VISIBLE);
        showOrHide(true, "", "");
        name=bean.getS_MAN_NAME();
        textview1.setText(AppTool.getNullStr(bean.getS_MAN_NAME()));
        textview2.setText(AppTool.getNullStr(bean.getSTATUS()));
        textview3.setText(AppTool.getNullStr(bean.getS_COM_NAME()));
        SCOMNAME=bean.getS_COM_NAME();
        textview4.setText(AppTool.getNullStr(bean.getS_TOWNID()));
        textview5.setText(AppTool.getNullStr("（" + bean.getS_MAN_NAME_ABBREVIATION() + "）"));
        man_name.setText(AppTool.getNullStr(bean.getS_MAN_NAME()));
        tv_useridentity2.setText("（"+AppTool.getNullStr(bean.getS_MAN_NAME_ABBREVIATION())+"）");
        if(bean.getHDPIC()!=null){
            Glide.with(mContext).load(baseImageUrl+bean.getHDPIC()).into(man_header);
        }else {
            if (bean.getS_MAN_NAME_ABBREVIATION().equals("巡查人员")){
                man_header.setImageResource(R.mipmap.img_xunc_tx);
            }else {
                man_header.setImageResource(R.mipmap.img_yanghc_tx);
            }
        }
        if (bean.getS_MAN_NAME_ABBREVIATION().equals("巡查人员")){
            relative_3.setVisibility(VISIBLE);
            relative_4.setVisibility(GONE);
        }else {
            relative_3.setVisibility(GONE);
            relative_4.setVisibility(VISIBLE);
        }
        if(AppTool.getNullStr(bean.getSTATUS()).equals("离线")){
            man_status.setImageResource(R.mipmap.ico_renyzt_lix);
        }else if(AppTool.getNullStr(bean.getSTATUS()).equals("在线")){
            man_status.setImageResource(R.mipmap.ico_renyzt_zaix);
        }
        man_jiezhen.setText(AppTool.getNullStr(bean.getS_TOWNID()));
        man_phone.setText(AppTool.getNullStr(""+bean.getS_PHONE()));
        man_danweimingcheng.setText(AppTool.getNullStr(bean.getS_COM_NAME()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
//                setVisibility(GONE);
                showOrHide(false, "", "");
                break;
            case R.id.iv_close2:
//                setVisibility(GONE);
                showOrHide(false, "", "");
                break;
            case R.id.chuzhi:
//                showOrHide(false, "http://222.66.154.70:2088/QP_H5/module/home/index.html#/CZ_List?company=", "");
                goWebCallBack.goWebActivity(AppConfig.Adminchuzhi+SCOMNAME, "",name);
                break;
            case R.id.xuncha:
                //跳转h5
//                showOrHide(false,"http://222.66.154.70:2088/QP_H5/module/home/index.html#/XC_List?company=", "");
                goWebCallBack.goWebActivity(AppConfig.Adminxuncha+SCOMNAME, "",name);
                break;
            case R.id.shijian:
                //跳转h5
//                showOrHide(false, "http://222.66.154.70:2088/QP_H5/module/home/index.html#/SJ_List?company=", "");
                goWebCallBack.goWebActivity(AppConfig.Adminshijian+SCOMNAME, "",name);
                break;
            case R.id.yanghu:
//                showOrHide(false, "http://222.66.154.70:2088/QP_H5/module/home/index.html#/SJ_List?", "yanghu");
                goWebCallBack.goWebActivity(AppConfig.Adminyanghu, "yanghu",name);
                break;
        }
    }

    public void cleanData() {
        textview1.setText("");
        textview2.setText("");
        textview3.setText("");
        textview4.setText("");
        textview5.setText("");
        man_name.setText("");
        tv_useridentity2.setText("");
        man_jiezhen.setText("");
        man_phone.setText("");
        man_danweimingcheng.setText("");
    }

    public void showOrHide(Boolean isShow, final String url, final String flag) {
        Animation animation = AnimationUtils.loadAnimation(mContext, !isShow ? R.anim.push_bottomvff_out : R.anim.push_bottomvff_in);
        this.setAnimation(animation);
        this.setVisibility(isShow ? View.VISIBLE : View.GONE);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if(!url.equals("")){
                    goWebCallBack.goWebActivity(url, flag,name);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    ViewFourthFragmentPeopledetail.GoWebCallBack goWebCallBack;

    public interface GoWebCallBack{
        public void goWebActivity(String url, String flag,String name);
    }

    public void setGoWebCallBack(ViewFourthFragmentPeopledetail.GoWebCallBack g){
        this.goWebCallBack = g;
    }

}


