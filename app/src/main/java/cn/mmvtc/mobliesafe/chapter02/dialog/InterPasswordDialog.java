package cn.mmvtc.mobliesafe.chapter02.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.mmvtc.mobliesafe.R;
/**
 * 自定义对话框：输入密码对话框的逻辑代码
 */

public class InterPasswordDialog extends Dialog implements
        View.OnClickListener {
    private TextView mTitleTV;//标题
    public EditText mFirstPWDET;//首次输入密码
    private Button mOKBtn;
    private Button mCancleBtn;
    private MyCallBack myCallBack;

    public InterPasswordDialog(Context context) {
        super(context, R.style.dialog_custom);
    }

    public void setCallBack(MyCallBack myCallBack) {
        this.myCallBack = myCallBack;

    }

    //设置对话框标题
    public void setTitle(String text) {
        if (!TextUtils.isEmpty(text)) {
            mTitleTV.setText(text);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inter_password_dialog);
        initView();
    }

    private void initView() {
        mTitleTV = (TextView) findViewById(R.id.tv_interpwd_title);
        mFirstPWDET = (EditText) findViewById(R.id.et_inter_password);
        mOKBtn = (Button) findViewById(R.id.btn_comfirm);
        mCancleBtn = (Button) findViewById(R.id.btn_dismiss);
        mOKBtn.setOnClickListener(this);
        mCancleBtn.setOnClickListener(this);

    }
    public String getPassword(){
        return mFirstPWDET.getText().toString();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_comfirm:
                myCallBack.confirm();
                break;
            case R.id.btn_dismiss:
                myCallBack.cancle();
                break;
        }

    }

    public interface MyCallBack {
        void confirm();
        void cancle();
    }
}
