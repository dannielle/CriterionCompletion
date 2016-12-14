package com.dee.android.criterioncompletion;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.UUID;

public class NavFragment extends Fragment {

    private static final String WHICH_NAV_PAGE = "which_nav_page";

    public static NavFragment newInstance(int position) {
        Bundle args = new Bundle();
        args.putSerializable(WHICH_NAV_PAGE, position);

        NavFragment fragment;
        switch (position) {
            case 0:
                fragment = new ListAllFilmsFragment();
                break;
            case 1:
                fragment = new MyFavoritesFragment();
                break;
            case 2:
                fragment = new MyProgressFragment();
                break;
            case 3:
                fragment = new MyProgressFragment();
                break;
            default:
                fragment = new MyProgressFragment();
                break;
        }

        fragment.setArguments(args);
        return fragment;
    }
}
