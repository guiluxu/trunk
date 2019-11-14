package com.wavenet.ding.qpps.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dereck.library.base.BaseMvpActivity;
import com.dereck.library.utils.AppManager;
import com.dereck.library.utils.GsonUtils;
import com.dereck.library.utils.ToastUtils;
import com.wavenet.ding.qpps.R;
import com.wavenet.ding.qpps.bean.ChangePasBean;
import com.wavenet.ding.qpps.mvp.p.ChangePasswordRequestPresenter;
import com.wavenet.ding.qpps.mvp.v.ChangePasswordActivityRequestView;
import com.wavenet.ding.qpps.utils.AppTool;
import com.wavenet.ding.qpps.utils.LogUtils;
import com.wavenet.ding.qpps.utils.SPUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ding on 2018/9/13.
 */

public class ChangePasswordActivity extends BaseMvpActivity<ChangePasswordActivityRequestView, ChangePasswordRequestPresenter> implements ChangePasswordActivityRequestView {

    @BindView(R.id.iv_clean)
    ImageView mIvclean;
    @BindView(R.id.etPassword1)
    EditText etPassword1;
    @BindView(R.id.etPassword2)
    EditText etPassword2;
    @BindView(R.id.btn_sure)
    Button btnsure;
    @BindView(R.id.etPassword3)
    EditText etPassword3;
    @BindView(R.id.et_username)
    EditText mEtusername;
    @BindView(R.id.ll_username)
    LinearLayout mLlusername;
boolean isloginper=false;
    @Override
    public int getLayoutId() {
        return R.layout.activity_change_password;
    }

    @Override
    public void init() {
setTitle("修改密码");
        isloginper=getIntent().getBooleanExtra("isloginper",false);
        if (isloginper){
            mLlusername.setVisibility(View.VISIBLE);
        }else {
            mLlusername.setVisibility(View.GONE);
        }
        btnsure.setEnabled(false);
        btnsure.setBackground(getResources().getDrawable(R.drawable.shape_changepasselectno_bgbtn));
        mEtusername.addTextChangedListener(textWatcher);
        etPassword1.addTextChangedListener(textWatcher);
        etPassword2.addTextChangedListener(textWatcher);
        etPassword3.addTextChangedListener(textWatcher);
    }

    @Override
    public void requestData() {

    }
    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
//

        }

        @Override
        public void afterTextChanged(Editable s) {
            oldpas=etPassword1.getText().toString().trim();
            newpas1=etPassword2.getText().toString().trim();
            newpas2=etPassword3.getText().toString().trim();
            username=mEtusername.getText().toString().trim();

            if (AppTool.isNull(oldpas)){
                mIvclean.setVisibility(View.INVISIBLE);
            }else {
                mIvclean.setVisibility(View.VISIBLE);
            }
            if (isloginper){
                if (AppTool.isNull(oldpas) ||AppTool.isNull(username) ||AppTool.isNull(newpas1)||AppTool.isNull(newpas2)  ) {
                    btnsure.setEnabled(false);
                    btnsure.setBackground(getResources().getDrawable(R.drawable.shape_changepasselectno_bgbtn));

                }else {
                    btnsure.setEnabled(true);
                    btnsure.setBackground(getResources().getDrawable(R.drawable.shape_changepasselected_bgbtn));
                }
            }else {
                if (AppTool.isNull(oldpas) ||AppTool.isNull(newpas1)||AppTool.isNull(newpas2)  ) {
                    btnsure.setEnabled(false);
                    btnsure.setBackground(getResources().getDrawable(R.drawable.shape_changepasselectno_bgbtn));

                }else {
                    btnsure.setEnabled(true);
                    btnsure.setBackground(getResources().getDrawable(R.drawable.shape_changepasselected_bgbtn));
                }
            }


        }
    };
    @Override
    protected ChangePasswordRequestPresenter createPresenter() {
        return new ChangePasswordRequestPresenter() {
        };
    }
    String oldpas;
    String newpas1;
    String newpas2;
    String username;
    @OnClick({ R.id.btn_sure,R.id.iv_clean})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_clean:
                oldpas="";
                etPassword1.setText("");
                break;
            case R.id.btn_sure:
                 oldpas=etPassword1.getText().toString().trim();
                 newpas1=etPassword2.getText().toString().trim();
                 newpas2=etPassword3.getText().toString().trim();
               username=mEtusername.getText().toString().trim();
               if (isloginper &&AppTool.isNull(oldpas) ){
                   ToastUtils.showToast("请填写完整数据");
                   return;
               }
                if (AppTool.isNull(oldpas) ||AppTool.isNull(newpas1)||AppTool.isNull(newpas2)  ) {

                    ToastUtils.showToast("请填写完整数据");

                } else {
                   if (!isloginper){
                       if (!oldpas.equals(SPUtil.getInstance(ChangePasswordActivity.this).getStringValue(SPUtil.USERPWDOLD))) {
                           ToastUtils.showToast("旧密码输入错误");
                           return;
                       }
                   }
                    if (newpas1.equals(newpas2)) {

                        if (newpas1.length() > 5) {
                            presenter.changePassword(ChangePasswordActivity.this, newpas1,oldpas,username,isloginper);
                        } else {


                            ToastUtils.showToast("密码长度不能小于6位");

                        }
                    } else {


                        ToastUtils.showToast("两次密码不一致");
                    }
                }


                break;
        }
    }

    @Override
    public void resultSuccess(int what, String result) {

        String status = GsonUtils.getObject(result, ChangePasBean.class).Code;

        if ("200".equals(status)) {
            SPUtil.getInstance(ChangePasswordActivity.this).remove(SPUtil.USERPWD);
            AppManager.getAppManager().finishAllActivity();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            ToastUtils.showToast("修改成功");
        } else {


            ToastUtils.showToast("修改失败");
        }

        //
        LogUtils.e("登录", result);
    }

    @Override
    public void show() {
        showDialog();
    }

    @Override
    public void hide() {
        cancelDialog();
    }

    @Override
    public void resultFailure(int what, String result) {
        ToastUtils.showToast("联网失败");
    }
}
