package cn.mmvtc.mobliesafe.chapter02;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import cn.mmvtc.mobliesafe.R;

/**
 * 向导二：
 * 通过telephonyManager获取SIM卡序列号，
 * 判断是否绑定SIM卡
 * 滑动屏幕
 * 按钮点击绑定SIM卡
 */

public class SetUp2Activity extends BaseSetUpActivity implements
        View.OnClickListener {
    private TelephonyManager mTelephonyManager;
    private Button mBindSIMBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);
        initView();
    }

    @Override
    public void showNext() {
        if (!isBind()) {
            Toast.makeText(this, "没有绑定SIM卡", Toast.LENGTH_SHORT).show();
            return;
        }
//        startActivityAndFinishSelf();//跳转向导3页面
    }

    @Override
    public void showPre() {
        startActivityAndFinishSelf(SetUp1Activity.class);
    }

    private void initView() {
        //设置第二个小圆点的颜色
        ((RadioButton) findViewById(R.id.rb_second)).setChecked(true);
        mBindSIMBtn = (Button) findViewById(R.id.btn_bind_sim);
        mBindSIMBtn.setOnClickListener(this);
        if (isBind()) {
            mBindSIMBtn.setEnabled(false);//按钮不可用
        } else {
            mBindSIMBtn.setEnabled(true);//按钮可用
        }
    }

    //判断SIM序列号是否为空？在sp里面判断是否为空值。
    private boolean isBind() {
        String simString = sp.getString("sim", null);
        if (TextUtils.isEmpty(simString)) {
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_bind_sim:
                bindSIM();//绑定SIM卡
                break;
        }

    }

    /**
     * 绑定SIM卡，改变按钮的状态，
     */
    private void bindSIM() {
        if (!isBind()) {
            String simSerialNumber=mTelephonyManager.getSimSerialNumber();
            SharedPreferences.Editor edit=sp.edit();
            edit.putString("sim",simSerialNumber);//保存SIM序列号到sim。键值对
            edit.commit();
            Toast.makeText(this, "SIM卡绑定成功", Toast.LENGTH_SHORT).show();
            mBindSIMBtn.setEnabled(false);//按钮不可用
        }else {
            //已经绑定，提醒用户
            Toast.makeText(this, "SIM卡已经绑定", Toast.LENGTH_SHORT).show();
            mBindSIMBtn.setEnabled(false);//按钮不可用
        }
    }
}
