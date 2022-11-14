package com.greymatter.yahe.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;

import com.greymatter.yahe.R;
import com.greymatter.yahe.adapter.SliderAdapter;
import com.greymatter.yahe.helper.ApiConfig;
import com.greymatter.yahe.helper.Constant;
import com.greymatter.yahe.model.Slider;

import static android.content.Context.INPUT_METHOD_SERVICE;


public class FullScreenViewFragment extends Fragment {
    View root;
    int pos;
    ArrayList<Slider> imgList;
    LinearLayout mMarkersLayout;
    ViewPager viewPager;
    Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_full_screen_view, container, false);

        mMarkersLayout = root.findViewById(R.id.layout_markers);
        viewPager = root.findViewById(R.id.pager);

        activity = getActivity();
        activity = activity;


        setHasOptionsMenu(true);

        imgList = new ArrayList<>();
        imgList = ProductDetailFragment.sliderArrayList;
        pos = requireArguments().getInt("pos", 0);

        viewPager.setAdapter(new SliderAdapter(imgList, activity, R.layout.lyt_fullscreenimg, "fullscreen"));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int position) {
                ApiConfig.addMarkers(position, imgList, mMarkersLayout, activity);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

        viewPager.setCurrentItem(pos);
        ApiConfig.addMarkers(pos, imgList, mMarkersLayout, activity);


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        Constant.TOOLBAR_TITLE = getString(R.string.app_name);
        activity.invalidateOptionsMenu();
        hideKeyboard();
    }

    public void hideKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(INPUT_METHOD_SERVICE);
            assert inputMethodManager != null;
            inputMethodManager.hideSoftInputFromWindow(root.getApplicationWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.toolbar_cart).setVisible(true);
        menu.findItem(R.id.toolbar_sort).setVisible(false);
        menu.findItem(R.id.toolbar_search).setVisible(true);
    }

}