package com.wavenet.ding.qpps.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dereck.library.utils.ToastUtils;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.activity.WebViewErrorActivity;
import com.wavenet.ding.qpps.adapter.FourFragmentListAdapter;
import com.wavenet.ding.qpps.api.QPSWApplication;
import com.wavenet.ding.qpps.bean.AdapterBean;
import com.wavenet.ding.qpps.fragment.FourthFragment;
import com.wavenet.ding.qpps.utils.AppConfig;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.MapUtil;

import java.util.ArrayList;


public class ControllerFourthFragmentView extends LinearLayout implements View.OnClickListener, AdapterView.OnItemClickListener {
    public FourFragmentListAdapter mTradapter;
    public RadioButton mRbf1;
    Context mContext;
    //    private LoadingWaitView mLoadingWaitView;
    RadioGroup mRgf;
    RadioButton  mRbf2, mRbf3, mRbf4, mRbf5, mRbf6, mRbf7;
    ListView listview;
    ArrayList<AdapterBean> mBeanList = new ArrayList<>();
    ArrayList<AdapterBean> mBeanListPSJ = new ArrayList<>();
    ArrayList<AdapterBean> mBeanListPSGD = new ArrayList<>();
    ArrayList<AdapterBean> mBeanListPFK = new ArrayList<>();
    ArrayList<AdapterBean> mBeanListPSBZ = new ArrayList<>();
    ArrayList<AdapterBean> mBeanListWSCLC = new ArrayList<>();
    ArrayList<AdapterBean> mBeanListZDPFK = new ArrayList<>();
    ArrayList<AdapterBean> mBeanListPSH = new ArrayList<>();

    public ControllerFourthFragmentView(Context context) {
        super(context);
        initView(context);
    }

    public ControllerFourthFragmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ControllerFourthFragmentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT));
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater.from(mContext).inflate(R.layout.view_fourthfragment, this);
        if (!isInEditMode()) {
            setOnClickListener(this);
//            mLoadingWaitView =findViewById(R.id.loadingWaitView);
            findViewById(R.id.iv_close).setOnClickListener(this);
            mRgf = findViewById(R.id.rg_f);
            mRbf1 = findViewById(R.id.rb_f1);
            mRbf2 = findViewById(R.id.rb_f2);
            mRbf3 = findViewById(R.id.rb_f3);
            mRbf4 = findViewById(R.id.rb_f4);
            mRbf5 = findViewById(R.id.rb_f5);
            mRbf6 = findViewById(R.id.rb_f6);
            mRbf7 = findViewById(R.id.rb_f7);
            listview = findViewById(R.id.listview);
            setdata();
            mTradapter = new FourFragmentListAdapter(mContext, mBeanList);
            listview.setAdapter(mTradapter);
            listview.setOnItemClickListener(this);
//              initData();
        }
    }

    public void setdata() {
        mRgf.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int radioButtonId) {
                RadioButton rb = findViewById(radioButtonId);
                String str = rb.getText().toString();
                setResetRg();
                initmRbfStylecheked(rb);
                FourthFragment.onClickgraphicsOverlayPoint.setVisible(true);
                switch (str) {
                    case "雨水井":
                    case "污水井":
                    case "合流井":
                        FourthFragment.onClickgraphicsOverlayPoint.setVisible(true);
                        FourthFragment.onClickgraphicsOverlayLine.setVisible(false);
                        mBeanList.clear();
                        mBeanList.addAll(mBeanListPSJ);
                        break;
                    case "雨水管":
                    case "污水管":
                    case "合流管":
                    case "其他管":
                        FourthFragment.onClickgraphicsOverlayPoint.setVisible(false);
                        FourthFragment.onClickgraphicsOverlayLine.setVisible(true);
                        mBeanList.clear();
                        mBeanList.addAll(mBeanListPSGD);
                        break;  case "排放口":
                        mBeanList.clear();
                        mBeanList.addAll(mBeanListPFK);
                        break;
                    case "排水泵站":
                        mBeanList.clear();
                        mBeanList.addAll(mBeanListPSBZ);
                        break;
                    case "污水处理厂":
                        mBeanList.clear();
                        mBeanList.addAll(mBeanListWSCLC);
                        break;
                    case "重点排放口":
                        mBeanList.clear();
                        mBeanList.addAll(mBeanListZDPFK);
                        break;
                    case "排水户":
                        mBeanList.clear();
                        mBeanList.addAll(mBeanListPSH);
                        break;
                }

                MapUtil.getInstance(mContext).addGraphicsOverlaychange(mBeanList);
                mTradapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_close:
          if (mA!=null){
             mA.getbackcall();
          }
                setVisibility(GONE);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(mContext, WebViewErrorActivity.class);
//        Intent intent = new Intent(mContext, ErrorcorrectionActivity.class);
        String url = "";
        String urlJC = "";
        String title = "";
        if (l >= 0) {
            QPSWApplication.maBean=mBeanList.get((int) l);
            if (mRbf1.isChecked()) {
                title = "排水井";
                url = String.format(AppConfig.DetailsurlPSJ, mBeanList.get((int) l).url);
                urlJC = String.format(AppConfig.JCurlPSJ, mBeanList.get((int) l).urlJC);

//                String url=String.format(AppConfig.DetailsurlPSJ,"2011111408563779904118082");
//                intent.putExtra("url",url);
            } else if (mRbf2.isChecked()) {
                title = "排水管道";
                url = String.format(AppConfig.DetailsurlPSGD, mBeanList.get((int) l).url);
//                String url1=String.format(AppConfig.DetailsurlPSGD,"2011111408563779904118082");

            } else if (mRbf3.isChecked()) {
                title = "排放口";
                url = String.format(AppConfig.DetailsurlPFK, mBeanList.get((int) l).url);
                QPSWApplication.maBean.index=AppConfig.PFK;
            } else if (mRbf4.isChecked()) {
                title = "排水泵站";
                url = String.format(AppConfig.DetailsurlPSBZ, mBeanList.get((int) l).url);

                QPSWApplication.maBean.index=AppConfig.PSBZ;
            } else if (mRbf5.isChecked()) {
                title = "污水处理厂";
                url = String.format(AppConfig.DetailsurlWSCLC, mBeanList.get((int) l).url);

                QPSWApplication.maBean.index=AppConfig.WSCLC;
            } else if (mRbf6.isChecked()) {
                title = "重点排放口";
                url = String.format(AppConfig.DetailsurlZDPFK, mBeanList.get((int) l).url);

                QPSWApplication.maBean.index=AppConfig.ZDPFK;
            } else if (mRbf7.isChecked()) {
                title = "排水户";
                url = String.format(AppConfig.DetailsurlPSH, mBeanList.get((int) l).url);

                QPSWApplication.maBean.index=AppConfig.PSH;
            } else {
                title = "排水井";
                url = String.format(AppConfig.DetailsurlPSJ, mBeanList.get((int) l).url);
                urlJC = String.format(AppConfig.JCurlPSJ, mBeanList.get((int) l).urlJC);
            }

//            Bundle d = new Bundle();
            if (AppTool.isNull(url)) {
                ToastUtils.showToast("地址为空");
                return;
            }

            intent.putExtra("title", mBeanList.get((int) l).title.replace(":","").replace("@","").replace("#",""));
            intent.putExtra("url", url);
            intent.putExtra("urlJC", urlJC);
            intent.putExtra("showR", true);
            mContext.startActivity(intent);
        }
    }

    public void mNotifyDataSet(ArrayList<AdapterBean> bList, int mTable) {
        if (bList.size() == 0)
            return;
        if (mTable == AppConfig.PSJ) {
            mRbf1.setText(bList.get(0).title.replace(":","").replace("@",""));
            setDataView(bList, mBeanListPSJ, mRbf1);
        } else if (mTable == AppConfig.PSGD) {
            mRbf2.setText(bList.get(0).title.replace(":","").replace("#",""));
            setDataView(bList, mBeanListPSGD, mRbf2);
        } else if (mTable == AppConfig.PFK) {
            setDataView(bList, mBeanListPFK, mRbf3);
        } else if (mTable == AppConfig.PSBZ) {
            setDataView(bList, mBeanListPSBZ, mRbf4);
        } else if (mTable == AppConfig.WSCLC) {
            setDataView(bList, mBeanListWSCLC, mRbf5);
        } else if (mTable == AppConfig.ZDPFK) {
            setDataView(bList, mBeanListZDPFK, mRbf6);
        } else if (mTable == AppConfig.PSH) {
            setDataView(bList, mBeanListPSH, mRbf7);
        }
        mTradapter.notifyDataSetChanged();
    }

    public void initStyle() {
        mBeanList.clear();
        mTradapter.notifyDataSetChanged();
        initmRbfStyle();
        mRbf1.setVisibility(GONE);
        mRbf2.setVisibility(GONE);
        mRbf3.setVisibility(GONE);
        mRbf4.setVisibility(GONE);
        mRbf5.setVisibility(GONE);
        mRbf6.setVisibility(GONE);
        mRbf7.setVisibility(GONE);
        setVisibility(GONE);
    }

    public void setDataView(ArrayList<AdapterBean> bList, ArrayList<AdapterBean> mBList, RadioButton mRbf) {
        mRbf.setVisibility(VISIBLE);
        mBList.clear();
        mBList.addAll(bList);

    }

    public void showData() {
        setVisibility(VISIBLE);
        if (mRbf2.getVisibility() == VISIBLE && mRbf1.getVisibility() == VISIBLE) {
            initmRbfStylecheked(mRbf1);
            mBeanList.clear();
            mBeanList.addAll(mBeanListPSJ);

        } else if (mRbf2.getVisibility() == VISIBLE) {
            initmRbfStylecheked(mRbf2);
            mBeanList.clear();
            mBeanList.addAll(mBeanListPSGD);

        } else if (mRbf1.getVisibility() == VISIBLE) {
            initmRbfStylecheked(mRbf1);
            mBeanList.clear();
            mBeanList.addAll(mBeanListPSJ);
        }
        if (mRbf3.getVisibility() == VISIBLE) {
            initmRbfStylecheked(mRbf3);
            mBeanList.clear();
            mBeanList.addAll(mBeanListPFK);
        } else if (mRbf4.getVisibility() == VISIBLE) {
            initmRbfStylecheked(mRbf4);
            mBeanList.clear();
            mBeanList.addAll(mBeanListPSBZ);
        } else if (mRbf5.getVisibility() == VISIBLE) {
            initmRbfStylecheked(mRbf5);
            mBeanList.clear();
            mBeanList.addAll(mBeanListWSCLC);
        } else if (mRbf6.getVisibility() == VISIBLE) {
            initmRbfStylecheked(mRbf6);
            mBeanList.clear();
            mBeanList.addAll(mBeanListZDPFK);
        } else if (mRbf7.getVisibility() == VISIBLE) {
            initmRbfStylecheked(mRbf7);
            mBeanList.clear();
            mBeanList.addAll(mBeanListPSH);
        }
        mTradapter.notifyDataSetChanged();
        MapUtil.getInstance(mContext).addGraphicsOverlaychange(mBeanList);
    }

    public void initmRbfStyle() {
        initmRbfStyle(mRbf1);
        initmRbfStyle(mRbf2);
        initmRbfStyle(mRbf3);
        initmRbfStyle(mRbf4);
        initmRbfStyle(mRbf5);
        initmRbfStyle(mRbf6);
        initmRbfStyle(mRbf7);
    }

    public void initmRbfStyle(RadioButton mRbf) {
        mRbf.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
        mRbf.setBackground(mContext.getResources().getDrawable(R.drawable.shape_rbg_bfourthfragment1));
    }

    public void initmRbfStylecheked(RadioButton mRbf) {
        mRbf.setChecked(true);
        mRbf.setTextColor(mContext.getResources().getColor(R.color.white));
        mRbf.setBackground(mContext.getResources().getDrawable(R.drawable.shape_rbg_bfourthfragment2));
    }

    public void setResetRg() {
        int num = mRgf.getChildCount();
        for (int i = 0; i < num; i++) {
            RadioButton rB = findViewById(mRgf.getChildAt(i).getId());
            if (rB != null) {
                initmRbfStyle(rB);
            }
        }
    }
public A mA;
public interface A{
      void  getbackcall();
}
public void setA(A mA){
    this.mA=mA;
    }
}


