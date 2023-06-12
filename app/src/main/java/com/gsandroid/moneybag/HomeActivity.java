package com.gsandroid.moneybag;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.gsandroid.moneybag.Adapter.HomeAdapter;
import com.gsandroid.moneybag.Adapter.MoneyBagAdapter;
import com.gsandroid.moneybag.Adapter.MyPagerAdapter;
import com.gsandroid.moneybag.Bean.User;
import com.gsandroid.moneybag.Fragment.MoneybagFragment;
import com.gsandroid.moneybag.Fragment.PersonalFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    ViewPager vp_home;
    RadioGroup rg;
    RadioButton rb_moneybag;
    RadioButton rb_personal;


    public static User LoginUser;

    private List<Fragment> getFragmentList() {
        List<Fragment> fragmentList = new ArrayList<Fragment>();
        MoneybagFragment f1 = new MoneybagFragment();
        PersonalFragment f2 = new PersonalFragment();
        fragmentList.add(f1);
        fragmentList.add(f2);
        return fragmentList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        vp_home = findViewById(R.id.vp_home);
        rg = findViewById(R.id.rg);
        rb_moneybag = findViewById(R.id.rb_moneybag);
        rb_personal = findViewById(R.id.rb_personal);
        if(LoginUser==null){
            Toast.makeText(this, R.string.login_null_err, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        vp_home.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        rb_moneybag.setChecked(true);
                        break;
                    case 1:
                        rb_personal.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb_moneybag)
                    vp_home.setCurrentItem(0);
                if (i == R.id.rb_personal)
                    vp_home.setCurrentItem(1);
            }
        });
        HomeAdapter homeAdapter = new HomeAdapter(getSupportFragmentManager(), getFragmentList());
        vp_home.setAdapter(homeAdapter);

    }
}