package com.greymatter.yahe.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import com.greymatter.yahe.R;
import com.greymatter.yahe.activity.MainActivity;
import com.greymatter.yahe.fragment.FullScreenViewFragment;
import com.greymatter.yahe.fragment.SubCategoryFragment;
import com.greymatter.yahe.helper.Constant;
import com.greymatter.yahe.model.Slider;

public class SliderAdapter extends PagerAdapter {

    final ArrayList<Slider> dataList;
    final Activity activity;
    final int layout;
    final String from;

    public SliderAdapter(ArrayList<Slider> dataList, Activity activity, int layout, String from) {
        this.dataList = dataList;
        this.activity = activity;
        this.layout = layout;
        this.from = from;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup view, final int position) {
        View imageLayout = LayoutInflater.from(activity).inflate(layout, view, false);

        assert imageLayout != null;
        ImageView imgSlider = imageLayout.findViewById(R.id.imgSlider);
        CardView lytMain = imageLayout.findViewById(R.id.lytMain);

        final Slider singleItem = dataList.get(position);


        Picasso.get()
                .load(singleItem.getImage())
                .fit()
                .centerInside()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(imgSlider);
        view.addView(imageLayout, 0);

        lytMain.setOnClickListener(v -> {
            if (from.equalsIgnoreCase("detail")) {
                Fragment fragment = new FullScreenViewFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("pos", position);
                fragment.setArguments(bundle);
                MainActivity.fm.beginTransaction().add(R.id.container, fragment).addToBackStack(null).commit();

            } else {
                switch (singleItem.getType()) {
                    case "category": {
                        Fragment fragment = new SubCategoryFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(Constant.ID, singleItem.getType_id());
                        bundle.putString(Constant.NAME, singleItem.getName());
                        bundle.putString(Constant.FROM, "category");
                        fragment.setArguments(bundle);
                        MainActivity.fm.beginTransaction().add(R.id.container, fragment).addToBackStack(null).commit();
                        break;
                    }
                    case "product": {
//                        Fragment fragment = new ProductDetailFragment();
//                        Bundle bundle = new Bundle();
//                        bundle.putString(Constant.ID, singleItem.getType_id());
//                        bundle.putString(Constant.FROM, "slider");
//                        bundle.putInt(Constant.VARIANT_POSITION, 0);
//                        fragment.setArguments(bundle);
//                        MainActivity.fm.beginTransaction().add(R.id.container, fragment).addToBackStack(null).commit();
                        break;
                    }
                    case "slider_url": {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(singleItem.getLink()));
                        activity.startActivity(browserIntent);
                        break;
                    }
                }

            }
        });

        return imageLayout;
    }


    @Override
    public int getCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
