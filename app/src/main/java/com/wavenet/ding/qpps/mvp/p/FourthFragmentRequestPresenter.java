package com.wavenet.ding.qpps.mvp.p;


import com.dereck.library.base.BaseMvpPersenter;
import com.dereck.library.observer.CommonObserver;
import com.dereck.library.utils.ToastUtils;
import com.google.gson.Gson;
import com.wavenet.ding.qpps.adapter.SearchHisAdapter;
import com.wavenet.ding.qpps.bean.SearchHistory;
import com.wavenet.ding.qpps.mvp.m.FourthFragmentRequestModel;
import com.wavenet.ding.qpps.mvp.v.AdminFragmentRequestView;
import com.wavenet.ding.qpps.utils.LogUtils;

import org.json.JSONObject;

import java.util.List;

public class FourthFragmentRequestPresenter extends BaseMvpPersenter<AdminFragmentRequestView> {

    private FourthFragmentRequestModel mFourthFragmentRequestModel;

    public FourthFragmentRequestPresenter() {
        this.mFourthFragmentRequestModel = new FourthFragmentRequestModel();
    }

    /*
    id不能为
        public final static int PSJ = 42;   //下标 3  0  1
       public final static int PSGD = 14;//下标2 0 2//
       public final static int PSGDJ1 = 42;//下标3 0 1//
       public final static int PSGDJ2 = 43;//下标3 0 2//
       public final static int PSGDJ3 = 44;//下标3 0 3//
       public final static int PSGDWS = 24;//下标2 1 2
       public final static int PSGDWSJ1 = 47;//下标3 1  1
       public final static int PSGDWSJ2 = 48;//下标3  1  2
       public final static int PFK = 63;//下标10
       public final static int PSBZ = 7;//下标1
       public final static int WSCLC = 53;//下标4
       public final static int ZDPFK = 54;//下标5
       public final static int PSH = 67;//下标12

    */
    public void AdminGetObjectIds(String idstr, String filterkey, String filtervalue, String townname) {
        //上报人，状态
        requestData(0, 1, "");
        mFourthFragmentRequestModel.AdminGetObjectIds(idstr, filterkey, filtervalue, townname, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestData(1, 1, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                LogUtils.d("qingpu", "search result = " + new Gson().toJson(s));
                requestData(2, 1, (String) s);
            }
        });
    }

    public void AdminGetObjectDetails(final int url, String ids, String filterkey) {
        //上报人，状态
        requestData(0, url, "");
        mFourthFragmentRequestModel.AdminGetObjectDetails(url, ids, filterkey, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestData(1, url, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, url, (String) s);
            }
        });
    }

    public void requestPeople(final int urlid, String filterStr) {//urlid 301巡检，302养护
        //上报人，状态
        requestData(0, urlid, "");
        mFourthFragmentRequestModel.requestPeople(filterStr, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestData(1, urlid, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, urlid, (String) s);
            }
        });
    }

    public void requestPeople2(final int urlid, String filterStr) {//urlid 1008巡检，1009养护
        //上报人，状态
        requestData(0, urlid, "");
        mFourthFragmentRequestModel.requestPeople2(filterStr, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestData(1, urlid, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, urlid, (String) s);
            }
        });
    }
    public void getJCData_id(final int resultid, String url) {//urlid 1008巡检，1009养护
        //上报人，状态
        requestDataJC(0, resultid, "");
        mFourthFragmentRequestModel.getJCData_id(url, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestDataJC(1, resultid, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestDataJC(2, resultid, (String) s);
            }
        });
    }    public void getJCData_his(final int resultid, String url) {//urlid 1008巡检，1009养护
        //上报人，状态
        requestDataJC(0, resultid, "");
        mFourthFragmentRequestModel.getJCData_his(url, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestDataJC(1, resultid, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestDataJC(2, resultid, (String) s);
            }
        });
    }

    public void requestAdminHeader(final int urlid, String filterStr) {//urlid 301巡检，302养护
        mFourthFragmentRequestModel.requestAdminHead(filterStr, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                requestData(1, urlid, errorMsg);
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                requestData(2, urlid, (String) s);
//                Log.d("qingpu", "headerData = "+(String)s);
            }
        });
    }

    public void requestData(int state, int resultid, String content) {
        if (getmMvpView() != null) {
            if (state == 0) {
                getmMvpView().show();
            } else if (state == 1) {
                getmMvpView().hide();
                getmMvpView().requestFailure(resultid, content);
            } else if (state == 2) {
                getmMvpView().hide();
                getmMvpView().requestSuccess(resultid, content);
            }
        }
    }
    public void requestDataJC(int state, int resultid, String content) {
        if (getmMvpView() != null) {
            if (state == 0) {
                getmMvpView().show();
                return;
            }

            getmMvpView().hide();
            if (state == 1) {
                ToastUtils.showToast(content);
                getmMvpView().requestFailureJC(resultid, content);
            } else if (state == 2) {
                try {
                    JSONObject JB=new JSONObject(content);
                    if (JB.getInt("Code")==200){
                        getmMvpView().requestSuccessJC(resultid,content);
                    }else {
                       ToastUtils.showToast(JB.getString("Msg"));
                        getmMvpView().requestFailureJC(resultid, content);
                    }
                } catch (Exception e) {
                    ToastUtils.showToast(e.getMessage());
                    e.printStackTrace();
                }

            }



        }
    }

    /**
     * 历史搜索记录
     */
    public void requestSearchHis(String user) {
        if (getmMvpView() != null) {
            getmMvpView().show();
        }

        mFourthFragmentRequestModel.requestSearchHis(user, new CommonObserver<Object>() {
            @Override
            protected void onError(String errorMsg) {
                requestData(1, 6, errorMsg);
            }

            @Override
            protected void onSuccess(Object o) {
                requestData(2, 6, (String) o);
            }
        });
    }

    /**
     * 增加搜索记录
     */
    public void addSearchHis(String user, String str) {
        mFourthFragmentRequestModel.addSearchHis(user, str, new CommonObserver<Object>() {
            @Override
            protected void onError(String errorMsg) {
                ToastUtils.showToast(errorMsg);
            }

            @Override
            protected void onSuccess(Object o) {
                LogUtils.d("bll", "addSearchHis-->" +  o);
            }
        });
    }

    /**
     * 清空搜索记录
     */
    public void clearHisList(final List<SearchHistory.DataBean> hisList, String user, final SearchHisAdapter mAdapter) {
        mFourthFragmentRequestModel.clearSearchHis(user, new CommonObserver<Object>() {
            @Override
            protected void onError(String errorMsg) {
                ToastUtils.showToast(errorMsg);
            }

            @Override
            protected void onSuccess(Object o) {
                hisList.clear();
                mAdapter.notifyDataSetChanged();
                requestData(2, 8, (String) o);
                ToastUtils.showToast("清空成功！");
            }
        });
    }
}
