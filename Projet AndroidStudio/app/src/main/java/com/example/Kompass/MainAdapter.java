package com.example.Kompass;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MainAdapter extends FragmentPagerAdapter {

    private int numOfTabs;
    Tab1 tab1;
    Tab2 tab2;
    Tab3 tab3;

    public MainAdapter(@NonNull FragmentManager fm, int numOfTabs, interfaceTablayout interfaceTablayout) {
        super(fm);
        this.numOfTabs = numOfTabs;
        tab1 = new Tab1(interfaceTablayout);
        tab2 = new Tab2(interfaceTablayout);
        tab3 = new Tab3(interfaceTablayout);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return tab1;
            case 1:
                return tab2;
            case 2:
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}