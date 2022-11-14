package com.greymatter.yahe.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;

import com.greymatter.yahe.R;
import com.greymatter.yahe.helper.Constant;
import com.greymatter.yahe.helper.Session;

import static android.content.Context.CLIPBOARD_SERVICE;
import static android.content.Context.INPUT_METHOD_SERVICE;


public class ReferEarnFragment extends Fragment {
    View root;
    TextView tvReferCoin, tvCode, tvCopy, tvInvite;
    Session session;
    String preText = "";
    Activity activity;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_refer_earn, container, false);
        activity = getActivity();
        setHasOptionsMenu(true);


        session = new Session(activity);
        tvReferCoin = root.findViewById(R.id.tvReferCoin);
        if (session.getData(Constant.refer_earn_method).equals("rupees")) {
            preText = session.getData(Constant.CURRENCY) + session.getData(Constant.refer_earn_bonus);
        } else {
            preText = session.getData(Constant.refer_earn_bonus) + "% ";
        }
        tvReferCoin.setText(getString(R.string.refer_text_1) + preText + getString(R.string.refer_text_2) + session.getData(Constant.CURRENCY) + session.getData(Constant.min_refer_earn_order_amount) + getString(R.string.refer_text_3) + session.getData(Constant.CURRENCY) + session.getData(Constant.max_refer_earn_amount) + ".");
        tvCode = root.findViewById(R.id.tvCode);
        tvCopy = root.findViewById(R.id.tvCopy);
        tvInvite = root.findViewById(R.id.tvInvite);

        tvInvite.setCompoundDrawablesWithIntrinsicBounds(AppCompatResources.getDrawable(requireContext(), R.drawable.ic_share), null, null, null);
        tvCode.setText(session.getData(Constant.REFERRAL_CODE));
        tvCopy.setOnClickListener(v -> {

            ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", tvCode.getText());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(activity, R.string.refer_code_copied, Toast.LENGTH_SHORT).show();
        });

        tvInvite.setOnClickListener(view -> {
            if (!tvCode.getText().toString().equals("code")) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.refer_share_msg_1)
                            + getResources().getString(R.string.app_name) + getString(R.string.refer_share_msg_2)
                            + "\n " + Constant.MainBaseUrl + "refer/" + tvCode.getText().toString());
                    startActivity(Intent.createChooser(shareIntent, getString(R.string.invite_friend_title)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(activity, getString(R.string.refer_code_alert_msg), Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        Constant.TOOLBAR_TITLE = getString(R.string.refer);
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
        menu.findItem(R.id.toolbar_cart).setVisible(false);
        menu.findItem(R.id.toolbar_layout).setVisible(false);
        menu.findItem(R.id.toolbar_search).setVisible(false);
        menu.findItem(R.id.toolbar_sort).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

}