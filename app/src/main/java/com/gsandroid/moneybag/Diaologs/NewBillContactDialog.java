package com.gsandroid.moneybag.Diaologs;

import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.gsandroid.moneybag.DButils.MySQLiteDao;
import com.gsandroid.moneybag.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class NewBillContactDialog extends DialogFragment {

    private Spinner sp_add_type, sp_add_type_name, add_date_month, add_date_ri;

    private EditText et_money_num;
    private OnDialogCompleted onDialogCompletedListener;

    private Resources res;

    private int year;

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
        params.width = (int) (point.x * 0.9);
        win.setAttributes(params);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_add_money, container, false);
        sp_add_type = view.findViewById(R.id.sp_add_type);
        sp_add_type_name = view.findViewById(R.id.sp_add_type_name);
        add_date_month = view.findViewById(R.id.add_date_month);
        add_date_ri = view.findViewById(R.id.add_date_ri);
        et_money_num = view.findViewById(R.id.et_money_num);
        Button add_btn_ok = view.findViewById(R.id.add_btn_ok);
        Button add_btn_no = view.findViewById(R.id.add_btn_no);

        // 获取当前时间
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH) - 1;

        // 获取字符串列表
        res = getResources();

        // 根据月份获取日
        add_date_month.setSelection(month);
//        ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(getActivity(),
//                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, getDayList());
//        add_date_ri.setAdapter(dayAdapter);
//        dayAdapter.notifyDataSetChanged();
//        add_date_ri.setSelection(day);
        add_date_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<String> dayAdapter = new ArrayAdapter<String>(getActivity(),
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, getDayList());
                dayAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                add_date_ri.setAdapter(dayAdapter);
                dayAdapter.notifyDataSetChanged();
                add_date_ri.setSelection(day);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // 支出或收入的类型
        sp_add_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<String> typeNameAdapter = new ArrayAdapter<String>(getActivity(),
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, getTypeNameList());
                typeNameAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
                sp_add_type_name.setAdapter(typeNameAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        add_btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDialogCompletedListener.dialogCompleted("取消添加");
                dismiss();
            }
        });
        add_btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int type = sp_add_type.getSelectedItemPosition();
                String type_name = sp_add_type_name.getSelectedItem().toString();
                String month = add_date_month.getSelectedItem().toString();
                String day = add_date_ri.getSelectedItem().toString();
                String moneyNum = et_money_num.getText().toString();
                if (type_name.isEmpty() || month.isEmpty() || day.isEmpty() || moneyNum.isEmpty()) {
                    Toast.makeText(getActivity(), R.string.input_null_err, Toast.LENGTH_SHORT).show();
                    return;
                }
                int money = 0;
                try {
                    money = Integer.parseInt(moneyNum);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), R.string.input_type_err, Toast.LENGTH_SHORT).show();
                    return;
                }
                MySQLiteDao dao = new MySQLiteDao(getActivity());
                dao.addMoneybag(type, type_name, year, month, day, money);
                onDialogCompletedListener.dialogCompleted("添加成功");
                dismiss();
            }
        });


        return view;
    }

    private List<String> getDayList() {
        String[] strings;
        List<String> day31 = new ArrayList<>(Arrays.asList("01", "03", "05", "07", "08", "10", "12"));
        List<String> day30 = new ArrayList<>(Arrays.asList("04", "06", "09", "11"));
        if (day31.contains(add_date_month.getSelectedItem().toString()))
            strings = res.getStringArray(R.array.sp_data_31ri);
        else if (day30.contains(add_date_month.getSelectedItem().toString()))
            strings = res.getStringArray(R.array.sp_data_30ri);
        else if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0)
            strings = res.getStringArray(R.array.sp_data_29ri);
        else
            strings = res.getStringArray(R.array.sp_data_28ri);
        return new ArrayList<String>(Arrays.asList(strings));
    }

    private List<String> getTypeNameList() {

        String[] strings = new String[0];
        if (sp_add_type.getSelectedItemPosition() == 0)
            strings = res.getStringArray(R.array.sp_type_name_out);
        else if (sp_add_type.getSelectedItemPosition() == 1)
            strings = res.getStringArray(R.array.sp_type_name_in);
        return new ArrayList<String>(Arrays.asList(strings));
    }
}
