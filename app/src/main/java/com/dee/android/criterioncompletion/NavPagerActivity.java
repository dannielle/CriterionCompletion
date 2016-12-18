package com.dee.android.criterioncompletion;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

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
                NavFragment fragment;
                switch (position) {
                    case 0:
                        fragment =  new ListAllFilmsFragment();
                        break;
                    case 1:
                        fragment = new MyRecsFragment();
                        break;
                    case 2:
                        fragment = new MyProgressFragment();
                        break;
                    case 3:
                        fragment = new MyFavoritesFragment();
                        break;
                    default:
                        fragment = new MyProgressFragment();
                        break;
                }
                return fragment;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return "All";
                    case 1:
                        return "My Recs";
                    case 2:
                        return "My Progress";
                    case 3:
                        return "My Faves";
                    default:
                        return "My Recs";
                }
            }
        });

        mViewPager.setCurrentItem(1);
    }
}
