package com.wavenet.ding.qpps.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.dereck.library.utils.ToastUtils;
import com.esri.arcgisruntime.loadable.LoadStatus;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.activity.AdminActivity;
import com.wavenet.ding.qpps.interf.CallBackMap;
import com.wavenet.ding.qpps.interf.OnClickInteDef;


public class ViewFourthFragementLegend1 extends LinearLayout implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    public ImageView mIvbengzhan, mIvguanwang, mIvjianchajing, mIvwushuichang, mIvpaishuihu, mIvzhongdianpfk, mIvpfk;
    Context mContext;
    View mView;
    AdminActivity mAdminActivity;
    RadioGroup mRgmap;
    int sum = 0;
    int sum1 = 0;
    /**
     * 侧滑动画
     */
    boolean isShow = false;//侧滑菜单是否显示隐藏
    CallBackMap mCallBackMap;
    String mOnClick = "TDTVEC";

    public ViewFourthFragementLegend1(Context context) {
        super(context);
        initView(context);
    }

    public ViewFourthFragementLegend1(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ViewFourthFragementLegend1(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        mAdminActivity = (AdminActivity) context;
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater.from(mContext).inflate(R.layout.view_legend, this);
        if (!isInEditMode()) {
            mView = findViewById(R.id.v_bg);
            mView.setOnClickListener(this);
            //地图切换
            //地图切换
            mRgmap = findViewById(R.id.rg_map);
            mRgmap.setOnCheckedChangeListener(this);
            mIvbengzhan = findViewById(R.id.iv_bengzhan);
            mIvbengzhan.setOnClickListener(this);

            mIvguanwang = findViewById(R.id.iv_guanwangys);
            mIvguanwang.setOnClickListener(this);

            mIvjianchajing = findViewById(R.id.iv_jianchajing);
            mIvjianchajing.setOnClickListener(this);

            mIvwushuichang = findViewById(R.id.iv_wushuichang);
            mIvwushuichang.setOnClickListener(this);

            mIvpaishuihu = findViewById(R.id.iv_paishuihu);
            mIvpaishuihu.setOnClickListener(this);

            mIvzhongdianpfk = findViewById(R.id.iv_zhongdianpfk);
            mIvzhongdianpfk.setOnClickListener(this);

            mIvpfk = findViewById(R.id.iv_pfk);
            mIvpfk.setOnClickListener(this);

        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.v_bg:
                if (mView.getVisibility() == View.VISIBLE) {
                    showOrHide("");
                }
                break;
            case R.id.iv_bengzhan:
                setShowLayer(mIvbengzhan, 1);

                break;
            case R.id.iv_guanwangys:
                setShowLayer(mIvguanwang, 2);

                break;
            case R.id.iv_jianchajing:
                setShowLayer(mIvjianchajing, 3);

                break;
            case R.id.iv_wushuichang:
                setShowLayer(mIvwushuichang, 4);

                break;
            case R.id.iv_paishuihu:
                setShowLayer(mIvpaishuihu, 6);

                break;
            case R.id.iv_zhongdianpfk:
                setShowLayer(mIvzhongdianpfk, 5);

                break;
            case R.id.iv_pfk:
                setShowLayer(mIvpfk, 10);
                break;
        }
    }

    public void setShowLayer(ImageView iv, int index) {
        if (mAdminActivity.mFourthFragment.mainMapImageLayer.getLoadStatus() != LoadStatus.LOADED) {
            ToastUtils.showToast("图层加载失败");
            return;
        }
        if (!iv.isSelected()) {
            sum++;
        } else {
            sum--;
        }
        if (sum > 3) {
            sum = 3;
            ToastUtils.showToast("最多选择一个图层");
            return;
        }
        iv.setSelected(!iv.isSelected());


        set(iv, index);

    }

    public void set(ImageView iv, int index) {
        mAdminActivity.mFourthFragment.mainMapImageLayer.getSubLayerContents().get(index).setVisible(iv.isSelected());
        if (index == 2) {
            mAdminActivity.mFourthFragment.mainMapImageLayer.getSubLayerContents().get(2).getSubLayerContents().get(0).setVisible(iv.isSelected());
            mAdminActivity.mFourthFragment.mainMapImageLayer.getSubLayerContents().get(2).getSubLayerContents().get(0).setVisible(iv.isSelected());
            mAdminActivity.mFourthFragment.mainMapImageLayer.getSubLayerContents().get(2).getSubLayerContents().get(0).getSubLayerContents().get(2).setVisible(iv.isSelected());
        }
        if (index == 3) {
            mAdminActivity.mFourthFragment.mainMapImageLayer.getSubLayerContents().get(3).setVisible(iv.isSelected());
            mAdminActivity.mFourthFragment.mainMapImageLayer.getSubLayerContents().get(3).getSubLayerContents().get(0).setVisible(iv.isSelected());
            mAdminActivity.mFourthFragment.mainMapImageLayer.getSubLayerContents().get(3).getSubLayerContents().get(0).getSubLayerContents().get(1).setVisible(iv.isSelected());
        }
    }

    public void initShowLayer(boolean IsShowLayer) {
        mIvbengzhan.setSelected(IsShowLayer);
        mIvguanwang.setSelected(IsShowLayer);
        mIvjianchajing.setSelected(IsShowLayer);
        mIvwushuichang.setSelected(IsShowLayer);
        mIvpaishuihu.setSelected(IsShowLayer);
        mIvzhongdianpfk.setSelected(IsShowLayer);
        mIvpfk.setSelected(IsShowLayer);

    }

    public void initShowLayerchange() {
        if (mIvbengzhan.isSelected()) {
            set(mIvbengzhan, 1);
        }
        if (mIvguanwang.isSelected()) {
            set(mIvguanwang, 2);
        }
        if (mIvjianchajing.isSelected()) {
            set(mIvjianchajing, 3);
        }
        if (mIvwushuichang.isSelected()) {
            set(mIvwushuichang, 4);
        }
        if (mIvpaishuihu.isSelected()) {
            set(mIvpaishuihu, 6);
        }
        if (mIvzhongdianpfk.isSelected()) {
            set(mIvzhongdianpfk, 5);
        }
        if (mIvpfk.isSelected()) {
            set(mIvpfk, 10);
        }
    }

    public int showLayerSum() {
        sum1 = 0;
        getSum(mIvbengzhan);
        getSum(mIvguanwang);
        getSum(mIvjianchajing);
        getSum(mIvwushuichang);
        getSum(mIvpaishuihu);
        getSum(mIvzhongdianpfk);
        getSum(mIvpfk);
        return sum;
    }

    public int getSum(ImageView iv) {

        if (iv.isSelected()) {
            sum1++;
        }
        return sum1;
    }

    public void showOrHide(String show) {
        isShow = this.getVisibility() == View.VISIBLE;
        this.setAnimation(AnimationUtils.loadAnimation(mContext, !isShow ? R.anim.in_from_right : R.anim.out_from_right));
        this.setVisibility(!isShow ? View.VISIBLE : View.GONE);
        mView.setVisibility(!isShow ? View.VISIBLE : View.GONE);

    }

    /**
     * 属性侧滑动画
     */
    public void showOrHide(final boolean show, View v) {
        translate(show, v);
//        v.setAnimation(AnimationUtils.loadAnimation(mContext, show ? R.anim.in_lefttoright : R.anim.out_righttoleft))；
    }

    /**
     * 平移属性侧滑动画
     */
    public void translate(final boolean show, final View v) {
        ObjectAnimator.ofFloat(v, "translationX", v.getTranslationX(), show ? -840f : 0.0f).setDuration(500).start();
    }

    public void setCallBackMap(CallBackMap mCallBackMap) {
        this.mCallBackMap = mCallBackMap;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        if (radioGroup == mRgmap) {
            switch (radioGroup.getCheckedRadioButtonId()) {
                case R.id.rb_map:
                    mOnClick = OnClickInteDef.setOnclick(OnClickInteDef.TDTVEC);
                    break;
                case R.id.rb_satellite:
                    mOnClick = OnClickInteDef.setOnclick(OnClickInteDef.TDTIMG);
                    break;
            }
            mCallBackMap.onClick(mOnClick);
        }
    }
}
