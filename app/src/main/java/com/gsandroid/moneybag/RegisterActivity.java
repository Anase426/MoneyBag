package com.gsandroid.moneybag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gsandroid.moneybag.DButils.MySQLiteDao;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText regName;
    private EditText regPwd;
    private EditText regPwdAgain;
    private TextView regMessage;
    int imgUrl = 0;

    private RadioButton rbStu, rbWorker, rbGirl, rbRobot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        regName = findViewById(R.id.regName);
        regPwd = findViewById(R.id.regPwd);
        regPwdAgain = findViewById(R.id.regPwdAgain);
        regMessage = findViewById(R.id.regMessage);
        Button btnReg = findViewById(R.id.btnReg);

        rbStu = findViewById(R.id.rb_reg_img_student);
        rbWorker = findViewById(R.id.rb_reg_img_worker);
        rbGirl = findViewById(R.id.rb_reg_img_girl);
        rbRobot = findViewById(R.id.rb_reg_img_robot);
        rbStu.setOnClickListener(this);
        rbWorker.setOnClickListener(this);
        rbGirl.setOnClickListener(this);
        rbRobot.setOnClickListener(this);


        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = regName.getText().toString();
                String pwd = regPwd.getText().toString();
                String pwdAgain = regPwdAgain.getText().toString();
                if (name.isEmpty() || pwd.isEmpty() || pwdAgain.isEmpty()) {
                    regMessage.setText(R.string.input_null_err);
                    return;
                }
                if (name.length() < 6 || name.length() > 20) {
                    regMessage.setText(R.string.resister_name_length_err);
                    return;
                }
                if (pwd.length() < 6 || pwd.length() > 20) {
                    regMessage.setText(R.string.resister_pwd_length_err);
                    return;
                }
                if (!pwd.equals(pwdAgain)) {
                    regMessage.setText(R.string.resister_pwd_err);
                    return;
                }
                if (imgUrl == 0) {
                    regMessage.setText(R.string.img_null_err);
                    return;
                }

                MySQLiteDao dao = new MySQLiteDao(RegisterActivity.this);
                dao.register(name, pwd, imgUrl);
                Toast.makeText(RegisterActivity.this, R.string.resister_msg, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.rb_reg_img_student) {
            imgUrl = 1;
            rbStu.setChecked(true);
            rbWorker.setChecked(false);
            rbGirl.setChecked(false);
            rbRobot.setChecked(false);
        } else if (id == R.id.rb_reg_img_worker) {
            imgUrl = 2;
            rbStu.setChecked(false);
            rbWorker.setChecked(true);
            rbGirl.setChecked(false);
            rbRobot.setChecked(false);
        } else if (id == R.id.rb_reg_img_girl) {
            imgUrl = 3;
            rbStu.setChecked(false);
            rbWorker.setChecked(false);
            rbGirl.setChecked(true);
            rbRobot.setChecked(false);
        } else if (id == R.id.rb_reg_img_robot) {
            imgUrl = 4;
            rbStu.setChecked(false);
            rbWorker.setChecked(false);
            rbGirl.setChecked(false);
            rbRobot.setChecked(true);
        }
    }
}