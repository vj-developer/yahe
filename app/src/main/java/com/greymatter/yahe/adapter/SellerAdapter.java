package com.greymatter.yahe.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import com.greymatter.yahe.R;
import com.greymatter.yahe.fragment.SellerProductsFragment;
import com.greymatter.yahe.helper.Constant;
import com.greymatter.yahe.model.Seller;

public class SellerAdapter extends RecyclerView.Adapter<SellerAdapter.ViewHolder> {
    public final ArrayList<Seller> SellerList;
    final int layout;
    final Activity activity;
    final String from;
    final int visibleNumber;


    public SellerAdapter(Activity activity, ArrayList<Seller> SellerList, int layout, String from, int visibleNumber) {
        this.SellerList = SellerList;
        this.layout = layout;
        this.activity = activity;
        this.from = from;
        this.visibleNumber = visibleNumber;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Seller model = SellerList.get(position);
        holder.tvTitle.setText(model.getStore_name());

        Picasso.get()
                .load(model.getLogo())
                .fit()
                .centerInside()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.placeholder)
                .into(holder.imgSeller);

        holder.lytMain.setOnClickListener(v -> {
            Fragment fragment = new SellerProductsFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.ID, model.getId());
            bundle.putString(Constant.TITLE, model.getStore_name());
            bundle.putString(Constant.FROM, "Seller");
            fragment.setArguments(bundle);
            ((AppCompatActivity) activity).getSupportFragmentManager().beginTransaction().add(R.id.container, fragment).addToBackStack(null).commit();
        });
    }

    @Override
    public int getItemCount() {
        int categories;
        if (SellerList.size() > visibleNumber && from.equals("home")) {
            categories = visibleNumber;
        } else {
            categories = SellerList.size();
        }
        return categories;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView tvTitle;
        final ImageView imgSeller;
        final LinearLayout lytMain;

        public ViewHolder(View itemView) {
            super(itemView);
            lytMain = itemView.findViewById(R.id.lytMain);
            imgSeller = itemView.findViewById(R.id.imgSeller);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }

    }
}
