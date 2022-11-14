package com.greymatter.yahe.adapter;

import static com.greymatter.yahe.helper.ApiConfig.toTitleCase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.greymatter.yahe.R;
import com.greymatter.yahe.helper.Constant;
import com.greymatter.yahe.helper.Session;
import com.greymatter.yahe.model.Transaction;


public class TransactionAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // for load more
    public final int VIEW_TYPE_ITEM = 0;
    public final int VIEW_TYPE_LOADING = 1;
    final Activity activity;
    final ArrayList<Transaction> transactions;
    public boolean isLoading;
    String id = "0";


    public TransactionAdapter(Activity activity, ArrayList<Transaction> transactions) {
        this.activity = activity;
        this.transactions = transactions;
    }

    public void add(int position, Transaction item) {
        transactions.add(position, item);
        notifyItemInserted(position);
    }

    public void setLoaded() {
        isLoading = false;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, final int viewType) {
        View view;
        switch (viewType){
            case (VIEW_TYPE_ITEM):
                view = LayoutInflater.from(activity).inflate(R.layout.lyt_transection_list, parent, false);
                return new HolderItems(view);
            case (VIEW_TYPE_LOADING):
                view = LayoutInflater.from(activity).inflate(R.layout.item_progressbar, parent, false);
                return new ViewHolderLoading(view);
            default:
                throw new IllegalArgumentException("unexpected viewType: " + viewType);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holderParent, final int position) {

        if (holderParent instanceof HolderItems) {
            final HolderItems holder = (HolderItems) holderParent;
            final Transaction transaction = transactions.get(position);
            id = transaction.getId();

            holder.tvTxDateAndTime.setText(transaction.getDate_created());
            holder.tvTxMessage.setText(activity.getString(R.string.hash) + transaction.getOrder_id() + " " + transaction.getMessage());
            holder.tvTxAmount.setText(activity.getString(R.string.amount_) + new Session(activity).getData(Constant.CURRENCY) + Float.parseFloat(transaction.getAmount()));
            holder.tvTxNo.setText(activity.getString(R.string.hash) + transaction.getTxn_id());
            holder.tvPaymentMethod.setText(activity.getString(R.string.via) + transaction.getType());

            holder.tvTxStatus.setText(toTitleCase(transaction.getStatus()));

            if (transaction.getStatus().equalsIgnoreCase(Constant.CREDIT) || transaction.getStatus().equalsIgnoreCase(Constant.SUCCESS) || transaction.getStatus().equalsIgnoreCase("capture") || transaction.getStatus().equalsIgnoreCase("challenge") || transaction.getStatus().equalsIgnoreCase("pending")) {
                holder.cardViewTxStatus.setCardBackgroundColor(ContextCompat.getColor(activity, R.color.tx_success_bg));
            } else {
                holder.cardViewTxStatus.setCardBackgroundColor(ContextCompat.getColor(activity, R.color.tx_fail_bg));
            }

        } else if (holderParent instanceof ViewHolderLoading) {
            ViewHolderLoading loadingViewHolder = (ViewHolderLoading) holderParent;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    @Override
    public int getItemViewType(int position) {
        return transactions.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public long getItemId(int position) {
        Transaction transaction = transactions.get(position);
        if (transaction != null)
            return Integer.parseInt(transaction.getId());
        else
            return position;
    }

    static class ViewHolderLoading extends RecyclerView.ViewHolder {
        public final ProgressBar progressBar;

        public ViewHolderLoading(View view) {
            super(view);
            progressBar = view.findViewById(R.id.itemProgressbar);
        }
    }

    public static class HolderItems extends RecyclerView.ViewHolder {

        final TextView tvTxNo;
        final TextView tvTxDateAndTime;
        final TextView tvTxMessage;
        final TextView tvTxAmount;
        final TextView tvTxStatus;
        final TextView tvPaymentMethod;
        final CardView cardViewTxStatus;

        public HolderItems(@NonNull View itemView) {
            super(itemView);

            tvTxNo = itemView.findViewById(R.id.tvTxNo);
            tvTxDateAndTime = itemView.findViewById(R.id.tvTxDateAndTime);
            tvTxMessage = itemView.findViewById(R.id.tvTxMessage);
            tvTxAmount = itemView.findViewById(R.id.tvTxAmount);
            tvTxStatus = itemView.findViewById(R.id.tvTxStatus);
            tvPaymentMethod = itemView.findViewById(R.id.tvPaymentMethod);

            cardViewTxStatus = itemView.findViewById(R.id.cardViewTxStatus);

        }
    }
}