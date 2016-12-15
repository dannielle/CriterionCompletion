package com.dee.android.criterioncompletion;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class NavPagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_films);

        mViewPager = (ViewPager) findViewById(R.id.nav_view_pager);

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public int getCount() {
                return 4;
            }

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return new ListAllFilmsFragment();
                    case 1:
                        return new MyRecsFragment();
                    case 2:
                        return new MyProgressFragment();
                    case 3:
                        return new MyFavoritesFragment();
                    default:
                        return new MyProgressFragment();
                }
            }
        });

        mViewPager.setCurrentItem(1);
    }
}
