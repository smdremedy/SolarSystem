package com.soldiersofmobile.solarsystem;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MoonsFragment extends Fragment {

    public static final String PLANETS = "planets";

    @Bind(R.id.viewpager)
    ViewPager viewpager;

    private Callback callback;

    private PlanetsWithMoonPagerAdapter adapter;

    public static MoonsFragment newInstance(SolarObject[] planetsWithMoons) {
        MoonsFragment moonsFragment = new MoonsFragment();
        Bundle args = new Bundle();
        args.putSerializable(PLANETS, planetsWithMoons);
        moonsFragment.setArguments(args);
        return moonsFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (Callback) context;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SolarObject[] planetsWithMoon = (SolarObject[]) getArguments().getSerializable(PLANETS);
        adapter = new PlanetsWithMoonPagerAdapter(getChildFragmentManager(), planetsWithMoon);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_moons, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewpager.setAdapter(adapter);
        callback.showTabs(viewpager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        callback.hideTabs();
        ButterKnife.unbind(this);

    }

    public interface Callback {
        void showTabs(ViewPager viewPager);

        void hideTabs();
    }
}
