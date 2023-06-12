package com.gsandroid.moneybag.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gsandroid.moneybag.Adapter.MoneyBagAdapter;
import com.gsandroid.moneybag.Bean.Money;
import com.gsandroid.moneybag.Bean.Month;
import com.gsandroid.moneybag.DButils.MySQLiteDao;
import com.gsandroid.moneybag.Diaologs.EditBillContactDialog;
import com.gsandroid.moneybag.Diaologs.NewBillContactDialog;
import com.gsandroid.moneybag.Diaologs.OnDialogCompleted;
import com.gsandroid.moneybag.HomeActivity;
import com.gsandroid.moneybag.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MoneybagFragment extends Fragment {

    ExpandableListView listBill;
    private MoneyBagAdapter moneyBagAdapter;
    private List<Month> monthList;
    private Map<Month, List<Money>> moneyMap;

    private ImageView loginImg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_moneybag, container, false);

        listBill = view.findViewById(R.id.listBill);
        Button btnAdd = view.findViewById(R.id.btnAdd);
        loginImg = view.findViewById(R.id.loginImg);

        // 初始化夫列表和子列表
        monthList = new ArrayList<Month>();
        moneyMap = new HashMap<Month, List<Money>>();
        initData();




        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNewContactDialog();
            }
        });
        return view;
    }

    private void showEditContactDialog(Money money) {
        EditBillContactDialog dlg = new EditBillContactDialog(money);
        dlg.setOnDialogCompletedListener(new OnDialogCompleted() {
            @Override
            public void dialogCompleted(String dialogResult) {
                initData();
                moneyBagAdapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), dialogResult, Toast.LENGTH_SHORT).show();
            }
        });
        dlg.show(getParentFragmentManager(), "修改");
    }

    private void showNewContactDialog() {
        NewBillContactDialog dlg = new NewBillContactDialog();
        dlg.setOnDialogCompletedListener(new OnDialogCompleted() {
            @Override
            public void dialogCompleted(String dialogResult) {
                initData();
                moneyBagAdapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), dialogResult, Toast.LENGTH_SHORT).show();
            }
        });
        dlg.show(getParentFragmentManager(), "添加");
    }

    private void initData() {
        monthList.clear();
        moneyMap.clear();
        switch (HomeActivity.LoginUser.getImgUrl()) {
            case 1:
                loginImg.setImageResource(R.drawable.student);
                break;
            case 2:
                loginImg.setImageResource(R.drawable.worker);
                break;
            case 3:
                loginImg.setImageResource(R.drawable.girl);
                break;
            case 4:
                loginImg.setImageResource(R.drawable.robot);
                break;
        }

        MySQLiteDao dao = new MySQLiteDao(getActivity());
        Map<String, Object> map = dao.findBill(HomeActivity.LoginUser.getID());
        monthList = (List<Month>) map.get("monthList");
        moneyMap = (Map<Month, List<Money>>) map.get("moneyMap");

        // 加载列表画面
        moneyBagAdapter = new MoneyBagAdapter(getActivity(), monthList, moneyMap);
        listBill.setAdapter(moneyBagAdapter);
        listBill.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
                Money money = Objects.requireNonNull(moneyMap.get(monthList.get(i))).get(i1);
                showEditContactDialog(money);
                return true;
            }
        });
    }

}
