package com.gsandroid.moneybag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gsandroid.moneybag.Bean.User;
import com.gsandroid.moneybag.DButils.MyDbHelper;
import com.gsandroid.moneybag.DButils.MySQLiteDao;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText loginName;
    private EditText loginPwd;
    private CheckBox chkPRememberPwd;

    private SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginName = (EditText) findViewById(R.id.loginName);
        loginPwd = (EditText) findViewById(R.id.loginPwd);
        chkPRememberPwd = (CheckBox) findViewById(R.id.chkPRememberPwd);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        TextView tvRegister = (TextView) findViewById(R.id.tvRegister);

        settings = getSharedPreferences("user", MODE_PRIVATE);
        if (!settings.getString("name", "").isEmpty())
            loginName.setText(settings.getString("name", ""));

        if (!settings.getString("pwd", "").isEmpty()){
            loginPwd.setText(settings.getString("pwd", ""));
            chkPRememberPwd.setChecked(true);
        }


        btnLogin.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int item = view.getId();
        if (item == R.id.btnLogin) {
            String name = loginName.getText().toString();
            String pwd = loginPwd.getText().toString();
            if (name.isEmpty() || pwd.isEmpty()) {
                Toast.makeText(LoginActivity.this, R.string.input_null_err, Toast.LENGTH_SHORT).show();
                return;
            }

            MySQLiteDao dao = new MySQLiteDao(LoginActivity.this);
            User LoginUser = dao.Login(name, pwd);
//            dao.removeUser();
            if (LoginUser != null) {
                //登录成功
                HomeActivity.LoginUser = LoginUser;
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("name", name);
                if (chkPRememberPwd.isChecked()) {
                    editor.putString("pwd", pwd);
                } else {
                    editor.remove("pwd");
                }
                editor.apply();
                Toast.makeText(LoginActivity.this, R.string.login_Ok, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            } else {
                //错误
                Toast.makeText(LoginActivity.this, R.string.login_error_msg, Toast.LENGTH_SHORT).show();
            }
        }
        if (item == R.id.tvRegister) {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
    }
}