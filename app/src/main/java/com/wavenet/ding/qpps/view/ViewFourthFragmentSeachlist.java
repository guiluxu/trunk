package com.wavenet.ding.qpps.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.dereck.library.refreshrecyclerview.pulltorefresh.PullToRefreshRecyclerView;
import com.dereck.library.utils.ToastUtils;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.PolylineBuilder;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.esri.arcgisruntime.symbology.SimpleRenderer;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.adapter.FourthSeachRecyclerViewAdapter;
import com.wavenet.ding.qpps.adapter.SearchHisAdapter;
import com.wavenet.ding.qpps.bean.AdapterBean;
import com.wavenet.ding.qpps.bean.SearchHistory;
import com.wavenet.ding.qpps.fragment.FourthFragment;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;


public class ViewFourthFragmentSeachlist extends LinearLayout implements View.OnClickListener, AdapterView.OnItemClickListener, PullToRefreshRecyclerView.OnRefreshAndLoadMoreListener {
    public PullToRefreshRecyclerView listviewseach;
    public FourthSeachRecyclerViewAdapter mTradapter;
    public boolean isQuerySeach = false;
    public ArrayList<AdapterBean> mSeachAdapterBean = new ArrayList<>();
    public int mSeachPageindex = 1;
    public int mCheckId;
    Context mContext;
    FourthFragment mFourthFragment;
    //    private LoadingWaitView mLoadingWaitView;
    RadioGroup mRgfSeach;
    RadioButton rb_fseach1,rb_fseach5;
    SimpleRenderer lineRenderer;

    SimpleRenderer pointRenderer;
    PolylineBuilder lineGeometry;
    boolean isShow = false;

    //历史记录
    public RecyclerView mSearchHisView;
    public List<SearchHistory.DataBean> historyList = new ArrayList<>();
    public SearchHisAdapter mHisAdapter;


    public ViewFourthFragmentSeachlist(Context context) {
        super(context);
        initView(context);
    }

    public ViewFourthFragmentSeachlist(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ViewFourthFragmentSeachlist(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        mContext = context;
        setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT));
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater.from(mContext).inflate(R.layout.view_fourthfragmentseach, this);
        if (!isInEditMode()) {
            setOnClickListener(this);
            mRgfSeach = findViewById(R.id.rg_fseach);
//            rb_fseach1 = findViewById(R.id.rb_fseach1);
            listviewseach = findViewById(R.id.listviewseach);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            listviewseach.setLayoutManager(linearLayoutManager);
            listviewseach.setPullRefreshEnabled(true);
            listviewseach.setLoadMoreEnabled(true);
            listviewseach.setRefreshAndLoadMoreListener(this);
            mTradapter = new FourthSeachRecyclerViewAdapter(mContext, mSeachAdapterBean);
            listviewseach.setAdapter(mTradapter);

            //搜索历史记录
            mSearchHisView = findViewById(R.id.search_his_listview);
            LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(mContext);
            linearLayoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
            mSearchHisView.setLayoutManager(linearLayoutManager1);
            mHisAdapter = new SearchHisAdapter(mContext, historyList);
            mSearchHisView.setAdapter(mHisAdapter);

            SimpleLineSymbol lineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, Color.GREEN, 5);
            lineRenderer = new SimpleRenderer(lineSymbol);

            SimpleMarkerSymbol pointSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.RED, 16);
            // create simple renderer
            pointRenderer = new SimpleRenderer(pointSymbol);
            initCheckedItem();
            setdata();

//              initData();
        }
    }

    public void setdata() {
        mRgfSeach.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int radioButtonId) {
                clearDataAdapter();
                mCheckId = getCheckedItem();
                if (isQuerySeach) {
                    mSeachPageindex = 1;
                    mFourthFragment.querySeach(mCheckId, null);
                }
            }
        });
        mTradapter.setOnItemClickListener(new FourthSeachRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                FourthFragment.onClickgraphicsOverlayPoint.getGraphics().clear();
                FourthFragment.onClickgraphicsOverlayLine.getGraphics().clear();
                mFourthFragment.mFragmentView.setVisibility(GONE);
                mFourthFragment.setmEtseachstate(false);
                mFourthFragment.mLegend.changeLayer(mFourthFragment.urlarry[mCheckId]);

                Point pointGeometry = null;
                Graphic mGraphic = null;
                if (mCheckId == 1 || mCheckId == 3) {//下标是1、3代表管道，其他都是Point// line graphic
                    FourthFragment.onClickgraphicsOverlayLine.setRenderer(lineRenderer);
                    lineGeometry = new PolylineBuilder(SpatialReferences.getWgs84());
                    lineGeometry.addPoint(mSeachAdapterBean.get(position).mGeometry.paths.get(0).get(0).get(0), mSeachAdapterBean.get(position).mGeometry.paths.get(0).get(0).get(1));
                    lineGeometry.addPoint(mSeachAdapterBean.get(position).mGeometry.paths.get(0).get(1).get(0), mSeachAdapterBean.get(position).mGeometry.paths.get(0).get(1).get(1));
                    mGraphic = new Graphic(lineGeometry.toGeometry());
                    Point mPoint = new Point(mSeachAdapterBean.get(position).mGeometry.paths.get(0).get(0).get(0), mSeachAdapterBean.get(position).mGeometry.paths.get(0).get(0).get(1), SpatialReferences.getWgs84());

                    FourthFragment.onClickgraphicsOverlayLine.getGraphics().add(mGraphic);
                    FourthFragment.onClickgraphicsOverlayLine.setVisible(true);
                    mFourthFragment.mMapView.setViewpointGeometryAsync(lineGeometry.getExtent(), 100);

                } else {
                    FourthFragment.onClickgraphicsOverlayPoint.setRenderer(pointRenderer);
                    pointGeometry = new Point(mSeachAdapterBean.get(position).mGeometry.x, mSeachAdapterBean.get(position).mGeometry.y, SpatialReferences.getWgs84());
                    mGraphic = new Graphic(pointGeometry);

                    FourthFragment.onClickgraphicsOverlayPoint.getGraphics().add(mGraphic);
                    FourthFragment.onClickgraphicsOverlayPoint.setVisible(true);
                    mFourthFragment.mMapView.setViewpointCenterAsync(pointGeometry, 500);
                }
                mSeachAdapterBean.get(position).mCenterPoint=mGraphic.getGeometry().getExtent().getCenter();
                mSeachAdapterBean.get(position).index=mFourthFragment.urlarry[mCheckId];
                mFourthFragment.mViewFourthFragmentSeachdetail.setdata(mSeachAdapterBean.get(position));
                setVisibility(GONE);


            }
        });

        mHisAdapter.setOnHisClickListener(new SearchHisAdapter.OnHisItemClickListener() {
            @Override
            public void onItemClick(View v, String str) {
                mCheckId = getCheckedItem();
                mFourthFragment.setSearchEdit(str);
                mFourthFragment.querySeach(mCheckId, str);
            }

            @Override
            public void onClearClick(View v, List<SearchHistory.DataBean> hisList, int pos) {
                mFourthFragment.clearHis(hisList, pos, mHisAdapter);

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (l >= 0) {

//            adminActivity.startActivity(intent);
        }
    }

    @Override
    public void onRecyclerViewRefresh() {
        if (AppTool.isNull(mFourthFragment.mEtseach.getText().toString())) {
            listviewseach.refreshComplete();
            ToastUtils.showToast("请输入查询关键字");
            return;
        }
        if (mFourthFragment.mObjectIdsBean == null || mFourthFragment.mObjectIdsBean.objectIds == null || mFourthFragment.mObjectIdsBean.objectIds.size() == 0) {
            listviewseach.refreshComplete();
            ToastUtils.showToast("没有查询到符合条件的数据");
            return;
        }
        mSeachPageindex = 1;
        mFourthFragment.presenter.AdminGetObjectDetails(mFourthFragment.urlarry[mCheckId], AppTool.getPagingID(mFourthFragment.mObjectIdsBean, mSeachPageindex), mFourthFragment.filterkey1[mCheckId]);

    }

    @Override
    public void onRecyclerViewLoadMore() {
        if (AppTool.isNull(mFourthFragment.mEtseach.getText().toString())) {
            listviewseach.loadMoreComplete();
            ToastUtils.showToast("请输入查询关键字");
            return;
        }
        if (mFourthFragment.mObjectIdsBean == null || mFourthFragment.mObjectIdsBean.objectIds == null || mFourthFragment.mObjectIdsBean.objectIds.size() == 0) {
            listviewseach.loadMoreComplete();
            ToastUtils.showToast("没有查询到符合条件的数据");
            return;
        }
        mFourthFragment.presenter.AdminGetObjectDetails(mFourthFragment.urlarry[mCheckId], AppTool.getPagingID(mFourthFragment.mObjectIdsBean, mSeachPageindex), mFourthFragment.filterkey1[mCheckId]);

    }

    public int getCheckedItem() {
        for (int i = 0; i < mRgfSeach.getChildCount(); i++) {
            RadioButton rb = (RadioButton) mRgfSeach.getChildAt(i);
            if (rb.isChecked()) {
                mCheckId = i;
                return i;
            }
        }
        return mCheckId = 0;
    }
public  void initCheckedItem(){
    String role=  SPUtil.getInstance(mContext).getStringValue(SPUtil.APP_ROLE);
            if ("区调度员".equals(role)||"区管理员".equals(role)||"街镇管理员".equals(role)){
                mRgfSeach.check(0);
                initCheckedItem(R.id.rb_fseach1);
            }else {
                mRgfSeach.check(R.id.rb_fseach3);
                initCheckedItem(GONE);
            }
}
    public  void initCheckedItem(int isShowGW){
        for (int i = 0; i < 4; i++) {
                RadioButton rb = (RadioButton) mRgfSeach.getChildAt(i);
                rb.setVisibility(isShowGW);
        }
    }
    public void clearDataAdapter() {
        mSeachAdapterBean.clear();
        mTradapter.notifyDataSetChanged();
    }

    public void setmFourthFragment(FourthFragment mFourthFragment) {
        this.mFourthFragment = mFourthFragment;
    }

    public void showOrHide(Boolean isShow) {
        this.isShow = isShow;
        this.setAnimation(AnimationUtils.loadAnimation(mContext, !isShow ? R.anim.push_bottomvff_out : R.anim.push_bottomvff_in));

    }


}


