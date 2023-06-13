package com.gsandroid.moneybag.Fragment;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gsandroid.moneybag.Bean.User;
import com.gsandroid.moneybag.Diaologs.EditPwdContactDialog;
import com.gsandroid.moneybag.Diaologs.EditUserContactDialog;
import com.gsandroid.moneybag.Diaologs.OnDialogCompleted;
import com.gsandroid.moneybag.HomeActivity;
import com.gsandroid.moneybag.LoginActivity;
import com.gsandroid.moneybag.R;

public class PersonalFragment extends Fragment {
    private ImageView personImg;
    private TextView personalName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_personal, container, false);
        personImg = view.findViewById(R.id.person_img);
        personalName = view.findViewById(R.id.personal_name);
        TextView tvEditUser = view.findViewById(R.id.tv_edit_user);
        TextView tvEditPassword = view.findViewById(R.id.tv_edit_password);
        TextView tvExit = view.findViewById(R.id.tv_exit);
        initData();

        // 修改个人信息按钮
        tvEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditContactDialog();
            }
        });

        // 修改密码按钮
        tvEditPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditPassWordContactDialog();
            }
        });

        // 退出登录按钮
        tvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeActivity.LoginUser = null;
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void showEditPassWordContactDialog() {
        EditPwdContactDialog dialog = new EditPwdContactDialog();
        dialog.setOnDialogCompletedListener(new OnDialogCompleted() {
            @Override
            public void dialogCompleted(String dialogResult) {
                initData();
                Toast.makeText(getActivity(), dialogResult, Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show(getParentFragmentManager(), "修改密码");
    }

    private void showEditContactDialog() {
        EditUserContactDialog dialog = new EditUserContactDialog();
        dialog.setOnDialogCompletedListener(new OnDialogCompleted() {
            // 窗体执行完成后
            @Override
            public void dialogCompleted(String dialogResult) {
                initData();
                Toast.makeText(getActivity(), dialogResult, Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show(getParentFragmentManager(), "修改个人信息");
    }

    private void initData() {
        personalName.setText(HomeActivity.LoginUser.getName());
        switch (HomeActivity.LoginUser.getImgUrl()) {
            case 1:
                personImg.setImageResource(R.drawable.student);
                break;
            case 2:
                personImg.setImageResource(R.drawable.worker);
                break;
            case 3:
                personImg.setImageResource(R.drawable.girl);
                break;
            case 4:
                personImg.setImageResource(R.drawable.robot);
                break;
        }

    }
}
