package com.wavenet.ding.qpps.activity;

import android.content.Intent;
import android.view.View;

import com.dereck.library.utils.ToastUtils;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.api.QPSWApplication;
import com.wavenet.ding.qpps.utils.AppTool;

public class WebViewErrorActivity extends WebViewActivity implements View.OnClickListener {
@Override
    public void dealData() {
        boolean showR = getIntent().getBooleanExtra("showR",false);
    isShowRight( showR);
    }
    @Override
    public void onClick(View view) {
    super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_jiucuo:
                try {
                    if(AppTool.isNull(QPSWApplication.maBean.url)){
                        ToastUtils.showToast("id为空");
                        return;
                    }
                    String urlJC=getIntent().getStringExtra("urlJC");
                    if (AppTool.isNull(urlJC)){
                        ToastUtils.showToast("正在开发中,敬请期待");
return;
                    }
//                    Intent intent=new Intent(this, ErrorcorrectionActivity.class);
                    Intent intent=new Intent(this, WebViewErrorCorrectActivity.class);
                    intent.putExtra("url",urlJC);
                    startActivity(intent);
                }catch (Exception e){
                    ToastUtils.showToast(e.toString());
                }


                break;
        }
    }

}