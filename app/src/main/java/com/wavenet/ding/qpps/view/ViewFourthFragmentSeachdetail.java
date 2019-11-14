package com.wavenet.ding.qpps.view;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dereck.library.utils.ToastUtils;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.activity.MainMapXJActivity;
import com.wavenet.ding.qpps.activity.MainMapYHActivity;
import com.wavenet.ding.qpps.activity.WebViewErrorActivity;
import com.wavenet.ding.qpps.api.QPSWApplication;
import com.wavenet.ding.qpps.bean.AdapterBean;
import com.wavenet.ding.qpps.fragment.FourthFragment;
import com.wavenet.ding.qpps.utils.AppConfig;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.MapUtil;


public class ViewFourthFragmentSeachdetail extends LinearLayout implements View.OnClickListener {
    //    AdminActivity mAdminActivity;
    MainMapXJActivity mActivityXJ;
    Context mContext;
    TextView textview1, textview2, textview3, textview4, caizhi_type;
    RelativeLayout type_rela;
    RadioButton mRB;
    String title = "";
    String burl = "";
    String burlJC = "";
    int ui = 0;//0巡检 1养护  2管理员
    public ViewFourthFragmentSeachdetail(Context context) {
        super(context);
        initView(context);
    }

    public ViewFourthFragmentSeachdetail(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ViewFourthFragmentSeachdetail(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
//        mAdminActivity = (AdminActivity) context;
        setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT));
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater.from(mContext).inflate(R.layout.view_fourthfragmentseachdetail, this);
        if (!isInEditMode()) {
//            setOnClickListener(this);
            mRB = findViewById(R.id.rb_f1);
            textview1 = findViewById(R.id.tv_num);
            textview2 = findViewById(R.id.tv_mag1);
            textview3 = findViewById(R.id.tv_mag2);
            textview4 = findViewById(R.id.tv_mag3);
            caizhi_type = findViewById(R.id.caizhi_type);
            findViewById(R.id.iv_close).setOnClickListener(this);
            findViewById(R.id.ll_godetail).setOnClickListener(this);
            textview1.setText("1");
            type_rela = findViewById(R.id.type_rela);

        }


    }

    public void setdata(AdapterBean b) {
        QPSWApplication.maBean=b;
        cleanData();
//        this.setVisibility(VISIBLE);
        textview2.setText(TextUtils.isEmpty(b.mag1) ? "" : b.mag1);
        textview3.setText(TextUtils.isEmpty(b.mag2) ? "" : b.mag2);
        textview4.setText(TextUtils.isEmpty(b.mag3) ? "" : b.mag3);
        caizhi_type.setText("");
        if(b.jingai_caizhi_type==null&&b.guandao_caizhi_type==null){
            caizhi_type.setText("");
            type_rela.setVisibility(GONE);
        }else {
            if(b.guandao_caizhi_type!=null){
                caizhi_type.setText(TextUtils.isEmpty(b.guandao_caizhi_type) ? "" : b.guandao_caizhi_type);
                type_rela.setVisibility(VISIBLE);
            }
            if(b.jingai_caizhi_type!=null){
                caizhi_type.setText(TextUtils.isEmpty(b.jingai_caizhi_type) ? "" : b.jingai_caizhi_type);
                type_rela.setVisibility(VISIBLE);
            }
        }
        title=b.title;
        mRB.setText(title.replace("#","").replace("@",""));
        burl = b.url;
        burlJC = b.urlJC;
        showOrHide(true);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
                if (MapUtil.ui == 0) {
                    MainMapXJActivity.onClickgraphicsOverlayPoint.getGraphics().clear();
                    MainMapXJActivity.onClickgraphicsOverlayLine.getGraphics().clear();
                } else if (MapUtil.ui == 1) {
                    MainMapYHActivity mActivity = (MainMapYHActivity) mContext;
                    mActivity.mAAtt.initB().graphicsOverlayClean(mActivity.mAAtt.initB().onClickgraphicsOverlayPoint);
                    mActivity.mAAtt.initB().graphicsOverlayClean(mActivity.mAAtt.initB().onClickgraphicsOverlayLine);
                } else if (MapUtil.ui == 2) {
                    FourthFragment.onClickgraphicsOverlayPoint.getGraphics().clear();
                    FourthFragment.onClickgraphicsOverlayLine.getGraphics().clear();
                }

//                setVisibility(GONE);
                showOrHide(false);
                break;
            case R.id.ll_godetail:
                goDetail();
                break;
        }
    }

    public void goDetail() {
        String url = "";
        String urlJC="";
        if (title.contains("@")) {
            title=title.replace("@","");
            url = String.format(AppConfig.DetailsurlPSJ, burl);
            urlJC = String.format(AppConfig.JCurlPSJ, burlJC);
//                String url=String.format(AppConfig.DetailsurlPSJ,"2011111408563779904118082");
//                intent.putExtra("url",url);
        } else if (title.contains("#")) {
            title=title.replace("#","");
            url = String.format(AppConfig.DetailsurlPSGD, burl);
//                String url1=String.format(AppConfig.DetailsurlPSGD,"2011111408563779904118082");
        } else if ("排放口".equals(title)) {
            url = String.format(AppConfig.DetailsurlPFK, burl);

        } else if ("排水泵站".equals(title)) {
            url = String.format(AppConfig.DetailsurlPSBZ, burl);

        } else if ("污水处理厂".equals(title)) {
            url = String.format(AppConfig.DetailsurlWSCLC, burl);

        } else if ("重点排放口".equals(title)) {
            url = String.format(AppConfig.DetailsurlZDPFK, burl);

        } else if ("排水户".equals(title)) {
            url = String.format(AppConfig.DetailsurlPSH, burl);

        } else {
            title = "排水井";
            url = String.format(AppConfig.DetailsurlPSJ, burl);
            urlJC = String.format(AppConfig.JCurlPSJ, urlJC);

        }
        if (AppTool.isNull(url)) {
            ToastUtils.showToast("地址为空");
            return;
        }
        QPSWApplication.maBean.title=title;
        Intent intent = new Intent(mContext, WebViewErrorActivity.class);
//        Intent intent = new Intent(mContext, ErrorcorrectionActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.putExtra("urlJC", urlJC);
//        intent.putExtra("maBean", maBean);
        intent.putExtra("showR", true);
        mContext.startActivity(intent);
    }

    public void cleanData() {
        textview2.setText("");
        textview3.setText("");
        textview4.setText("");
        title = "";
        mRB.setText("");
        burl = "";
    }

    public void showOrHide(Boolean isShow) {
        Animation animation = AnimationUtils.loadAnimation(mContext, !isShow ? R.anim.push_bottomvff_out : R.anim.push_bottomvff_in);
        this.setAnimation(animation);
        this.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }


}


