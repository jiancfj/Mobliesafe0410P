package cn.mmvtc.mobliesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import cn.mmvtc.mobliesafe.chapter01.adapter.HomeAdapter;
import cn.mmvtc.mobliesafe.chapter02.LostFindActivity;
import cn.mmvtc.mobliesafe.chapter02.dialog.InterPasswordDialog;
import cn.mmvtc.mobliesafe.chapter02.dialog.SetUpPasswordDialog;

import static android.R.attr.start;

public class HomeActivity extends Activity {
    private GridView gv_home;
    private long mExitTime;
    //手机防盗密码的保存
    private SharedPreferences msharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去标题
        setContentView(R.layout.activity_home);
        msharedPreferences = getSharedPreferences("config", MODE_PRIVATE);
        gv_home = (GridView) findViewById(R.id.gv_home);
        gv_home.setAdapter(new HomeAdapter(HomeActivity.this));
        gv_home.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0://手机防盗
                        if (isSetUpPassword()) {
                            showInterPswdDialog();
                        } else {
                            showSetUpPwdDialog();
                        }
                        break;
                    case 1://

                        break;
                    case 2://

                        break;
                    case 3://

                        break;
                    case 4://

                        break;
                    case 5://

                        break;
                    case 6://

                        break;
                    case 7://

                        break;

                    case 8://

                        break;

                }


            }
        });

    }

    /**
     * 弹出设置对话框
     */
    private void showSetUpPwdDialog() {
        final SetUpPasswordDialog setUpPasswordDialog = new SetUpPasswordDialog
                (HomeActivity.this);
        setUpPasswordDialog.setCallBack(new SetUpPasswordDialog.MyCallBack() {

            @Override
            public void ok() {
                //获得首次密码和再次密码的值
                String firstPwsd = setUpPasswordDialog.mFirstPWDET
                        .getText().toString().trim();
                String affirmPwsd = setUpPasswordDialog.mAffirmET
                        .getText().toString().trim();
                if (!TextUtils.isEmpty(firstPwsd) &&
                        !TextUtils.isEmpty(affirmPwsd)) {
                    if (firstPwsd.equals(affirmPwsd)) {
                        //两次密码一致，存储密码
                        savePswd(affirmPwsd);
                        //确认后，对话框消失
                        setUpPasswordDialog.dismiss();
                        //显示输入密码对话框（补充）
                        showInterPswdDialog();
                    } else {
                        Toast.makeText(HomeActivity.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(HomeActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void cancle() {
                setUpPasswordDialog.dismiss();
            }
        });
        setUpPasswordDialog.setCancelable(true);
        setUpPasswordDialog.show();
    }

    /**
     * 弹出输入密码对话框
     */
    private void showInterPswdDialog() {
        final String password = getPassword();
        final InterPasswordDialog minPswdDialog = new InterPasswordDialog
                (HomeActivity.this);
        minPswdDialog.setCallBack(new InterPasswordDialog.MyCallBack() {
            @Override
            public void confirm() {
                if (TextUtils.isEmpty(minPswdDialog.getPassword())) {
                    Toast.makeText(HomeActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                } else if (password.equals(minPswdDialog.getPassword())) {
                    //进入防盗主界面
                    minPswdDialog.dismiss();
//                    Intent intent=new Intent(HomeActivity.this,LostFindActivity.class);
                   startActivityTo(LostFindActivity.class);
                } else {
                    //对话框消失
                    minPswdDialog.dismiss();
                    Toast.makeText(HomeActivity.this, "密码有误，请重新输入密码！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void cancle() {
                minPswdDialog.dismiss();
            }
        });
        minPswdDialog.setCancelable(true);
        minPswdDialog.show();
    }

    /**
     * 保存密码
     *
     * @param affirmPwsd
     */
    private void savePswd(String affirmPwsd) {
        SharedPreferences.Editor edit = msharedPreferences.edit();
        edit.putString("PhoneAntiTheftPWD", affirmPwsd);
        edit.commit();
    }

    /**
     * 读取密码
     */
    private String getPassword() {
        String password = msharedPreferences.getString("PhoneAntiTheftPWD", null);
        if (TextUtils.isEmpty(password)) {
            return "";
        }
        return password;
    }

    /**
     * 判断用户  是否  设置过手机防盗密码
     */
    private boolean isSetUpPassword() {
        String password = msharedPreferences.getString("PhoneAntiTheftPWD", null);
        if (TextUtils.isEmpty(password)) {
            return false;
        }
        return true;
    }

    public void startActivityTo(Class<?> cls){
        Intent intent=new Intent(HomeActivity.this,cls);
        startActivity(intent);
    }


    /**
     * 按两次退出程序
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) < 2000) {
                System.exit(0);
            } else {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
