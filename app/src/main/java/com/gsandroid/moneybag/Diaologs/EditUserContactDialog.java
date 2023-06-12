package com.gsandroid.moneybag.Diaologs;

import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.gsandroid.moneybag.DButils.MySQLiteDao;
import com.gsandroid.moneybag.HomeActivity;
import com.gsandroid.moneybag.R;

import java.util.Objects;

public class EditUserContactDialog  extends DialogFragment implements View.OnClickListener {
    private EditText etUsername;
    private RadioButton rbStu, rbWorker, rbGirl, rbRobot;

    private Button btnOk, btnNo;

    private OnDialogCompleted onDialogCompletedListener;

    private int imgUrl = 0;

    public void setOnDialogCompletedListener(OnDialogCompleted onDialogCompleted) {
        this.onDialogCompletedListener = onDialogCompleted;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window win = Objects.requireNonNull(getDialog()).getWindow();

        Point point = new Point();
        requireActivity().getWindowManager().getDefaultDisplay().getSize(point);
        WindowManager.LayoutParams params = win.getAttributes();

        params.height = (int) (point.y * 0.6);
        params.width = (int) (point.x * 0.8);
        win.setAttributes(params);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_edit_user, container, false);
        etUsername = view.findViewById(R.id.et_edit_name);
        rbStu = view.findViewById(R.id.rb_edit_img_student);
        rbWorker = view.findViewById(R.id.rb_edit_img_worker);
        rbGirl = view.findViewById(R.id.rb_edit_img_girl);
        rbRobot = view.findViewById(R.id.rb_edit_img_robot);
        btnOk = view.findViewById(R.id.btn_edit_user_ok);
        btnNo = view.findViewById(R.id.btn_edit_user_no);
        etUsername.setText(HomeActivity.LoginUser.getName());


        rbStu.setOnClickListener(this);
        rbWorker.setOnClickListener(this);
        rbGirl.setOnClickListener(this);
        rbRobot.setOnClickListener(this);

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDialogCompletedListener.dialogCompleted("取消修改");
                dismiss();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etUsername.getText().toString();
                if(name.isEmpty()){
                    Toast.makeText(getActivity(), R.string.input_null_err, Toast.LENGTH_SHORT).show();
                    return;
                }
                if(name.length()<6||name.length()>20){
                    Toast.makeText(getActivity(), R.string.resister_name_length_err, Toast.LENGTH_SHORT).show();
                    return;
                }
                if(imgUrl == 0){
                    Toast.makeText(getActivity(), R.string.img_null_err, Toast.LENGTH_SHORT).show();
                    return;
                }
                MySQLiteDao dao = new MySQLiteDao(getActivity());
                dao.editUser(HomeActivity.LoginUser, name, imgUrl);
                HomeActivity.LoginUser.setName(name);
                HomeActivity.LoginUser.setImgUrl(imgUrl);
                onDialogCompletedListener.dialogCompleted("修改成功");
                dismiss();
            }
        });
        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.rb_edit_img_student) {
            imgUrl = 1;
            rbStu.setChecked(true);
            rbWorker.setChecked(false);
            rbGirl.setChecked(false);
            rbRobot.setChecked(false);
        } else if (id == R.id.rb_edit_img_worker) {
            imgUrl = 2;
            rbStu.setChecked(false);
            rbWorker.setChecked(true);
            rbGirl.setChecked(false);
            rbRobot.setChecked(false);
        } else if (id == R.id.rb_edit_img_girl) {
            imgUrl = 3;
            rbStu.setChecked(false);
            rbWorker.setChecked(false);
            rbGirl.setChecked(true);
            rbRobot.setChecked(false);
        } else if (id == R.id.rb_edit_img_robot) {
            imgUrl = 4;
            rbStu.setChecked(false);
            rbWorker.setChecked(false);
            rbGirl.setChecked(false);
            rbRobot.setChecked(true);
        }
    }
}
