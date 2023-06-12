package com.gsandroid.moneybag.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.gsandroid.moneybag.Bean.Money;
import com.gsandroid.moneybag.Bean.Month;
import com.gsandroid.moneybag.R;

import java.util.List;
import java.util.Map;

public class MoneyBagAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<Month> monthList;
    private Map<Month, List<Money>> moneyMap;

    static class MonthHolder {
        TextView tv_year;
        TextView tv_month;
        TextView tv_out;
        TextView tv_in;
    }

    static class BillHolder {
        TextView tv_type;
        TextView tv_date;
        TextView tv_num;

    }

    public MoneyBagAdapter(Context context, List<Month> monthList, Map<Month, List<Money>> moneyMap) {
        this.context = context;
        this.monthList = monthList;
        this.moneyMap = moneyMap;
    }

    @Override
    public int getGroupCount() {
        return monthList.size();
    }

    @Override
    public int getChildrenCount(int group) {
        Month monthNum = monthList.get(group);
        List<Money> list = moneyMap.get(monthNum);
        assert list != null;
        return list.size();
    }

    @Override
    public Object getGroup(int group) {
        return monthList.get(group);
    }

    @Override
    public Object getChild(int group, int child) {
        Month monthNum = monthList.get(group);
        List<Money> list = moneyMap.get(monthNum);
        assert list != null;
        return list.get(child);
    }

    @Override
    public long getGroupId(int group) {
        return group;
    }

    @Override
    public long getChildId(int group, int child) {
        return child;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public View getGroupView(int group, boolean isLastChild, View view, ViewGroup viewGroup) {
        MonthHolder monthHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_month, viewGroup, false);
            monthHolder = new MonthHolder();
            monthHolder.tv_year = view.findViewById(R.id.tv_year);
            monthHolder.tv_month = view.findViewById(R.id.tv_month);
            monthHolder.tv_out = view.findViewById(R.id.tv_out);
            monthHolder.tv_in = view.findViewById(R.id.tv_in);
            view.setTag(monthHolder);
        } else {
            monthHolder = (MonthHolder) view.getTag();
        }
        Month month = (Month) getGroup(group);
        monthHolder.tv_year.setText(month.getYear().concat("年"));
        monthHolder.tv_month.setText(month.getMonth().concat("月"));
        monthHolder.tv_out.setText("-".concat(String.valueOf(month.getOut())));
        monthHolder.tv_in.setText("+".concat(String.valueOf(month.getIn())));
        return view;
    }


    @Override
    public View getChildView(int group, int child, boolean isLastChild, View view, ViewGroup viewGroup) {
        BillHolder billHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_bill, viewGroup, false);
            billHolder = new BillHolder();
            billHolder.tv_type = view.findViewById(R.id.tv_moneyType);
            billHolder.tv_date = view.findViewById(R.id.tv_moneyDate);
            billHolder.tv_num = view.findViewById(R.id.tv_moneyNum);
            view.setTag(billHolder);
        } else {
            billHolder = (BillHolder) view.getTag();
        }
        Money money = (Money) getChild(group, child);
        billHolder.tv_type.setText(money.getType_name());
        billHolder.tv_date.setText(money.getDate().concat("日"));
        billHolder.tv_num.setText(money.getMoney_to(money.getType()));
        return view;
    }

    @Override
    public boolean isChildSelectable(int group, int child) {
        return true;
    }
}
