package cn.mmvtc.mobliesafe.chapter02;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import cn.mmvtc.mobliesafe.R;

import static android.R.id.edit;

/**
 * 向导三：
 * 功能：用于选择或输入安全联系人，sim卡发生变更，发送短信个安全号码
 */

public class SetUp3Activity extends BaseSetUpActivity implements
        View.OnClickListener {
    private EditText mInputPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup3);
        initView();
    }

    private void initView() {
        ((RadioButton) findViewById(R.id.rb_third)).setChecked(true);
        findViewById(R.id.btn_addcontact).setOnClickListener(this);
        mInputPhone = (EditText) findViewById(R.id.et_inputphone);
        String safephone = sp.getString("safephone", null);
        if (!TextUtils.isEmpty(safephone)) {
            mInputPhone.setText(safephone);
        }

    }

    @Override
    public void showNext() {
        //判断文本输入框中是否有电话号码
        String safephone = mInputPhone.getText().toString().trim();
        if (TextUtils.isEmpty(safephone)) {
            Toast.makeText(this, "请输入电话号码", Toast.LENGTH_SHORT);
            return;
        }
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("safephone", safephone);
        edit.commit();
        startActivityAndFinishSelf(SetUp4Activity.class);
    }

    @Override
    public void showPre() {
        startActivityAndFinishSelf(SetUp2Activity.class);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_addcontact://跳转到联系人界面

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data!=null)
        {
            String phone=data.getStringExtra("phone");
            mInputPhone.setText(phone);
        }
    }
}
