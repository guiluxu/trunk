package com.wavenet.ding.qpps.mvp.p;


import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.bll.greendao.FailYHResultDao;
import com.bll.greendao.dbean.FailYHResult;
import com.bll.greendao.dbean.FilePath;
import com.bll.greendao.manager.DBManager;
import com.dereck.library.base.BaseMvpPersenter;
import com.dereck.library.observer.CommonObserver;
import com.dereck.library.utils.ToastUtils;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.adapter.SearchHisAdapter;
import com.wavenet.ding.qpps.bean.SearchHistory;
import com.wavenet.ding.qpps.mvp.m.MainMapYHRequestModel;
import com.wavenet.ding.qpps.mvp.v.MainMapYHActivityRequestView;
import com.wavenet.ding.qpps.utils.AppTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;

public class MainMapYHRequestPresenter extends BaseMvpPersenter<MainMapYHActivityRequestView> {

    private final MainMapYHRequestModel mainMapYHRequestModel;

    public MainMapYHRequestPresenter() {
        this.mainMapYHRequestModel = new MainMapYHRequestModel();
    }

    //正常上传附件
    public void reporFile(final int what, final Map<String, Object> map, ArrayList<String> arrayList,
                          final String audioPath, final String videoPath, final ArrayList<String> imgPaths) {
        if (getmMvpView() != null) {
            getmMvpView().show();
        }

        mainMapYHRequestModel.requestFile(map, arrayList, new CommonObserver<Object>() {


            @Override
            protected void onError(String errorMsg) {
                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().hide();
                    getmMvpView().resultFailure(what, errorMsg, map, audioPath, videoPath, imgPaths);
                }

            }

            @Override
            protected void onSuccess(Object responseBody) {

                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().resultSuccess(what, (ResponseBody) responseBody);
                    getmMvpView().hide();
                }
            }
        });
    }

    /**************************上传附件失败重新上传  开始**********************/

    //上传失败，补传附件
    public void uploadFile(Context context) {

        //查询上传未成功的数据
        List<FailYHResult> xjResults = DBManager.getFailYHDao(context).queryBuilder().where(FailYHResultDao.Properties.IsSave.eq(false)).list();

        if (xjResults == null || xjResults.isEmpty()) {
            ToastUtils.showToast("没有数据可上传！");
            return;
        }
        int index = 0;
        fileRequest(context, xjResults, index);
    }

    private void fileRequest(Context context, List<FailYHResult> xjResults, int index) {

        if (getmMvpView() != null) {
            getmMvpView().show();
        }

        if (xjResults == null || xjResults.size() < 1 || index >= xjResults.size()) {
            if (getmMvpView() != null) {
                getmMvpView().hide();
            }
            //根据上传状态查询最新的数据列表，未上传成功的数据
            List<FailYHResult> successResults = DBManager.getFailYHDao(context).queryBuilder().where(FailYHResultDao.Properties.IsSave.eq(true)).list();
            int successSize = successResults.size();
            int failSize = xjResults.size() - successSize;
            String tip = "数据上传完成，成功:" + successSize + "条，失败:" + failSize + "条";
            if (failSize == 0) {
                getmMvpView().resultSuccess(10000, null);
            }
            showTipDialog(context, tip, successResults);
            return;
        }

        FailYHResult xjResult = xjResults.get(index);
        String x = xjResult.getX();
        String y = xjResult.getY();
        String relyid = xjResult.getRelyid();
        String sType = xjResult.getSType();
        String sDesc = xjResult.getSDesc();
        String yxfa = xjResult.getYxfa();
        String mfilevideo = xjResult.getVideoUrl();
        String audioPath = xjResult.getAudioUrl();
        List<FilePath> filePaths = xjResult.getImagePaths();

        Map<String, Object> map = new HashMap<>();

        map.put("x", x);
        map.put("y", y);
        map.put("relyid", relyid);//S_SJSB_ID
        map.put("yxfa", yxfa);
        map.put("S_TYPE", sType);
        map.put("S_DESC", sDesc);

        ArrayList<String> arrayList = new ArrayList<>();

        for (int i = 0; i < filePaths.size(); i++) {
            arrayList.add(filePaths.get(i).getImagePath());

        }
        if (!AppTool.isNull(mfilevideo)) {
            arrayList.add(mfilevideo);
        }
        if (!AppTool.isNull(audioPath)) {
            arrayList.add(audioPath);
        }

        reporFile(context, xjResults, map, arrayList, index);
    }

    public void reporFile(final Context context, final List<FailYHResult> xjResults, final Map<String, Object> map,
                          ArrayList<String> arrayList, final int index) {

        mainMapYHRequestModel.requestFile(map, arrayList, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                fileRequest(context, xjResults, index + 1);
            }

            @Override
            protected void onSuccess(Object responseBody) {
                //如果成功，将isSave置为true
                xjResults.get(index).setIsSave(true);
                DBManager.getFailYHDao(context).update(xjResults.get(index));

                fileRequest(context, xjResults, index + 1);
            }
        });
    }

    private void showTipDialog(final Context context, String tip, final List<FailYHResult> successResults) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setCancelable(false);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));  // 有白色背
        window.setContentView(R.layout.dialog_net_tips);
        window.findViewById(R.id.version_describe).setVisibility(View.VISIBLE);
        TextView tv_content = window.findViewById(R.id.tv_content);
        tv_content.setText(tip);

        window.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                DBManager.getFailYHDao(context).deleteInTx(successResults);
            }
        });
    }

    /**************************上传附件失败重新上传   结束**********************/

    public void reporXY(final int what, Map<String, Object> map) {
        if (getmMvpView() != null) {
            //   getmMvpView().show();
        }

        mainMapYHRequestModel.requestUPXY(map, new CommonObserver<Object>() {


            @Override
            protected void onError(String errorMsg) {
                //业务处理
                if (getmMvpView() != null) {
                    //   getmMvpView().hide();
                    getmMvpView().resultFailure(what, errorMsg);
                }

            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().resultStringSuccess(what, (String) s);
                    //   getmMvpView().hide();


                }
            }
        });
    }


    public void getPicUrl(final int what, Map<String, Object> map) {
        if (getmMvpView() != null) {
            getmMvpView().show();
        }

        mainMapYHRequestModel.getPicUrl(map, new CommonObserver<Object>() {


            @Override
            protected void onError(String errorMsg) {
                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().hide();
                    getmMvpView().resultFailure(what, errorMsg);
                }

            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().resultStringSuccess(what, (String) s);
                    getmMvpView().hide();


                }
            }
        });
    }


    public void userCancelTask(final int what, String id, Map<String, Object> map) {
        if (getmMvpView() != null) {
            getmMvpView().show();
        }

        mainMapYHRequestModel.userCancelTask(id, map, new CommonObserver<Object>() {


            @Override
            protected void onError(String errorMsg) {
                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().hide();
                    getmMvpView().resultFailure(what, errorMsg);
                }

            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().resultStringSuccess(what, (String) s);
                    getmMvpView().hide();


                }
            }
        });
    }


    public void userFinishTask(final int what, String id, Map<String, Object> map) {
        if (getmMvpView() != null) {
            getmMvpView().show();
        }

        mainMapYHRequestModel.userFinishTask(id, map, new CommonObserver<Object>() {


            @Override
            protected void onError(String errorMsg) {
                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().hide();
                    getmMvpView().resultFailure(what, errorMsg);
                }

            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().resultStringSuccess(what, (String) s);
                    getmMvpView().hide();


                }
            }
        });
    }

    public void getRelyid(final int what, Map<String, Object> map) {
        if (getmMvpView() != null) {
            getmMvpView().show();
        }

        mainMapYHRequestModel.requestRelyid(map, new CommonObserver<Object>() {


            @Override
            protected void onError(String errorMsg) {
                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().hide();
                    getmMvpView().resultFailure(what, errorMsg);
                }

            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().resultStringSuccess(what, (String) s);
                    getmMvpView().hide();


                }
            }
        });
    }

    public void AdminGetObjectIds(String idstr, String filterkey, String filtervalue, String townname) {
        if (getmMvpView() != null) {
            getmMvpView().show();
        }
        mainMapYHRequestModel.AdminGetObjectIds(idstr, filterkey, filtervalue, townname, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().hide();
                    getmMvpView().resultFailureMAP(1, errorMsg);
                }
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().hide();
                    getmMvpView().resultSuccessMAP(1, (String) s);
                }
            }
        });
    }

    public void AdminGetObjectDetails(final int url, String ids, String filterkey) {
        if (getmMvpView() != null) {
            getmMvpView().show();
        }
        mainMapYHRequestModel.AdminGetObjectDetails(url, ids, filterkey, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().hide();
                    getmMvpView().resultFailureMAP(url, errorMsg);
                }
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().resultSuccessMAP(url, (String) s);
                    getmMvpView().hide();


                }
            }
        });
    }

    public void requestPeople(final int urlid, String filterStr) {//urlid 301巡检，302养护
        if (getmMvpView() != null) {
            getmMvpView().show();
        }
        mainMapYHRequestModel.requestPeople(filterStr, new CommonObserver<Object>() {

            @Override
            protected void onError(String errorMsg) {
                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().hide();
                    getmMvpView().resultFailureMAP(urlid, errorMsg);
                }
            }

            @Override
            protected void onSuccess(Object s) {
                //业务处理
                if (getmMvpView() != null) {
                    getmMvpView().resultSuccessMAP(urlid, (String) s);
                    getmMvpView().hide();


                }
            }
        });
    }

    /**
     * 历史搜索记录
     */
    public void requestSearchHis(String user) {
        if (getmMvpView() != null) {
            getmMvpView().show();
        }

        mainMapYHRequestModel.requestSearchHis(user, new CommonObserver<Object>() {
            @Override
            protected void onError(String errorMsg) {
                if (getmMvpView() != null) {
                    getmMvpView().hide();
                    getmMvpView().resultFailureMAP(3, errorMsg);
                }
            }

            @Override
            protected void onSuccess(Object o) {
                if (getmMvpView() != null) {
                    getmMvpView().resultSuccessMAP(3, (String) o);
                    getmMvpView().hide();
                }
            }
        });
    }

    /**
     * 增加搜索记录
     */
    public void addSearchHis(String user, String str) {
        mainMapYHRequestModel.addSearchHis(user, str, new CommonObserver<Object>() {
            @Override
            protected void onError(String errorMsg) {
                ToastUtils.showToast(errorMsg);
            }

            @Override
            protected void onSuccess(Object o) {
            }
        });
    }

    /**
     * 清空搜索记录
     */
    public void clearHisList(final List<SearchHistory.DataBean> hisList, String user, final SearchHisAdapter mAdapter) {
        mainMapYHRequestModel.clearSearchHis(user, new CommonObserver<Object>() {
            @Override
            protected void onError(String errorMsg) {
                ToastUtils.showToast(errorMsg);
            }

            @Override
            protected void onSuccess(Object o) {
                hisList.clear();
                mAdapter.notifyDataSetChanged();
                if (getmMvpView() != null) {
                    getmMvpView().resultSuccessMAP(4, (String) o);
                    getmMvpView().hide();
                }
                ToastUtils.showToast("清空成功！");
            }
        });
    }
}
