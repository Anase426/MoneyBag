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

public class EditPwdContactDialog extends DialogFragment {
    private EditText etOldPassWord, etPassWord, etPassWordAgain;

    private Button btnOk, btnNo;
    private OnDialogCompleted onDialogCompletedListener;

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

        params.height = (int) (point.y * 0.5);
        params.width = (int) (point.x * 0.8);
        win.setAttributes(params);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_edit_pwd, container, false);
        etOldPassWord = view.findViewById(R.id.et_edit_pwd_old);
        etPassWord = view.findViewById(R.id.et_edit_pwd);
        etPassWordAgain = view.findViewById(R.id.et_edit_pwd_again);
        btnNo = view.findViewById(R.id.btn_edit_pwd_no);
        btnOk = view.findViewById(R.id.btn_edit_pwd_ok);


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
                String old = etOldPassWord.getText().toString();
                String password = etPassWord.getText().toString();
                String passwordAgain = etPassWordAgain.getText().toString();
                if (old.isEmpty() || password.isEmpty() || passwordAgain.isEmpty()) {
                    Toast.makeText(getActivity(), R.string.input_null_err, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6 || password.length() > 20) {
                    Toast.makeText(getActivity(), R.string.resister_pwd_length_err, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(passwordAgain)) {
                    Toast.makeText(getActivity(), R.string.resister_pwd_err, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(HomeActivity.LoginUser.getPwd())) {
                    Toast.makeText(getActivity(), R.string.pwd_error_msg, Toast.LENGTH_SHORT).show();
                    return;
                }
                MySQLiteDao dao = new MySQLiteDao(getActivity());
                dao.editPwd(HomeActivity.LoginUser, password);
                onDialogCompletedListener.dialogCompleted("修改成功");
                dismiss();
            }
        });
        return view;
    }
}
