package com.soldiersofmobile.solarsystem;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

class PlanetsWithMoonPagerAdapter extends FragmentStatePagerAdapter {

    private final SolarObject[] planetsWithMoon;

    public PlanetsWithMoonPagerAdapter(FragmentManager fm, SolarObject[] planetsWithMoon) {
        super(fm);
        this.planetsWithMoon = planetsWithMoon;
    }

    @Override
    public Fragment getItem(int position) {

        SolarObject[] moons = planetsWithMoon[position].getMoons();
        if (moons == null) {
            moons = new SolarObject[0];
        }
        return PlanetsFragment.newInstance(moons);
    }

    @Override
    public int getCount() {
        return planetsWithMoon.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return planetsWithMoon[position].getName();
    }
}
