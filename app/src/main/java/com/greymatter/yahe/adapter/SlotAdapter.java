package com.greymatter.yahe.adapter;

import static com.greymatter.yahe.helper.ApiConfig.getMonth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.greymatter.yahe.R;
import com.greymatter.yahe.activity.PaymentActivity;
import com.greymatter.yahe.model.Slot;

public class SlotAdapter extends RecyclerView.Adapter<SlotAdapter.ViewHolder> {
    public final ArrayList<Slot> categoryList;
    final Activity activity;
    final String deliveryTime;
    int selectedPosition = 0;
    boolean isToday;

    public SlotAdapter(String deliveryTime, Activity activity, ArrayList<Slot> categoryList) {
        this.deliveryTime = deliveryTime;
        this.activity = activity;
        this.categoryList = categoryList;
        PaymentActivity.deliveryTime = "";
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lyt_time_slot, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Slot model = categoryList.get(position);
        holder.rdBtn.setText(model.getTitle());
        holder.rdBtn.setTag(position);
        holder.rdBtn.setChecked(position == selectedPosition);

        String pattern = "HH:mm:ss";
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String now = sdf.format(new Date());

        Date currentTime = null;
        Date SlotTime = null;
        try {
            currentTime = sdf.parse(now);
            SlotTime = sdf.parse(model.getLastOrderTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = Calendar.getInstance();
        isToday = PaymentActivity.deliveryDay.equals(calendar.get(Calendar.DATE) + "-" + getMonth((calendar.get(Calendar.MONTH) + 1), activity) + "-" + calendar.get(Calendar.YEAR));

        assert currentTime != null;
        if (isToday) {
            if (currentTime.compareTo(SlotTime) > 0) {
                holder.rdBtn.setChecked(false);
                holder.rdBtn.setClickable(false);
                holder.rdBtn.setTextColor(ContextCompat.getColor(activity, R.color.gray));
                holder.rdBtn.setButtonDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_uncheck_circle));
            } else {
                holder.rdBtn.setClickable(true);
                holder.rdBtn.setTextColor(ContextCompat.getColor(activity, R.color.black));
                holder.rdBtn.setButtonDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_active_circle));
            }
        } else {
            holder.rdBtn.setClickable(true);
            holder.rdBtn.setTextColor(ContextCompat.getColor(activity, R.color.black));
            holder.rdBtn.setButtonDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_active_circle));
        }

        Date finalCurrentTime = currentTime;
        Date finalSlotTime = SlotTime;
        holder.rdBtn.setOnClickListener(v -> {
            if (isToday) {
                if (finalCurrentTime.compareTo(finalSlotTime) < 0) {
                    PaymentActivity.deliveryTime = model.getTitle();
                    selectedPosition = (Integer) v.getTag();
                    notifyDataSetChanged();
                }
            } else {
                PaymentActivity.deliveryTime = model.getTitle();
                selectedPosition = (Integer) v.getTag();
                notifyDataSetChanged();
            }

        });

        if (holder.rdBtn.isChecked()) {
            holder.rdBtn.setButtonDrawable(ContextCompat.getDrawable(activity, R.drawable.ic_radio_button_checked));
            holder.rdBtn.setTextColor(ContextCompat.getColor(activity, R.color.black));
            PaymentActivity.deliveryTime = model.getTitle();
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final RadioButton rdBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            rdBtn = itemView.findViewById(R.id.rdBtn);
        }
    }
}