package com.greymatter.yahe.fragment;

import static android.content.Context.INPUT_METHOD_SERVICE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.greymatter.yahe.R;
import com.greymatter.yahe.activity.MainActivity;
import com.greymatter.yahe.adapter.AddressAdapter;
import com.greymatter.yahe.helper.ApiConfig;
import com.greymatter.yahe.helper.Constant;
import com.greymatter.yahe.helper.Session;
import com.greymatter.yahe.model.Address;

public class AddressListFragment extends Fragment {
    public static RecyclerView recyclerView;
    public static ArrayList<Address> addresses;
    @SuppressLint("StaticFieldLeak")
    public static AddressAdapter addressAdapter;
    @SuppressLint("StaticFieldLeak")
    public static TextView tvAlert;
    @SuppressLint("StaticFieldLeak")
    public static Activity activity;
    public static String selectedAddress = "";
    public int total = 0;
    FloatingActionButton fabAddAddress;
    View root;
    SwipeRefreshLayout swipeLayout;
    TextView tvTotalItems;
    TextView tvSubTotal;
    TextView tvConfirmOrder;
    LinearLayout processLyt;
    RelativeLayout confirmLyt;
    private Session session;
    private ShimmerFrameLayout mShimmerViewContainer;

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_address_list, container, false);
        activity = getActivity();
        session = new Session(activity);

        recyclerView = root.findViewById(R.id.recyclerView);
        swipeLayout = root.findViewById(R.id.swipeLayout);
        tvConfirmOrder = root.findViewById(R.id.tvConfirmOrder);
        tvAlert = root.findViewById(R.id.tvAlert);
        fabAddAddress = root.findViewById(R.id.fabAddAddress);
        processLyt = root.findViewById(R.id.processLyt);
        tvSubTotal = root.findViewById(R.id.tvSubTotal);
        tvTotalItems = root.findViewById(R.id.tvTotalItems);
        confirmLyt = root.findViewById(R.id.confirmLyt);
        mShimmerViewContainer = root.findViewById(R.id.mShimmerViewContainer);


        if (!session.getData(Constant.area_wise_delivery_charge).equals("1")) {
            GetDChargeSettings(activity);
        } else {
            getAddresses();
        }


        if (requireArguments().getString(Constant.FROM).equalsIgnoreCase("process")) {
            processLyt.setVisibility(View.VISIBLE);
            confirmLyt.setVisibility(View.VISIBLE);
            tvSubTotal.setText(session.getData(Constant.CURRENCY) + ApiConfig.StringFormat("" + requireArguments().getDouble("total")));
            tvTotalItems.setText(Constant.TOTAL_CART_ITEM + (Constant.TOTAL_CART_ITEM > 1 ? activity.getString(R.string.items) : activity.getString(R.string.item)));

            tvConfirmOrder.setOnClickListener(view -> {
                if (session.getData(Constant.STATUS).equals("1")) {
                    if (!Constant.selectedAddressId.isEmpty()) {
                        assert getArguments() != null;
                        Fragment fragment = new CheckoutFragment();
                        final Bundle bundle = new Bundle();
                        bundle.putString(Constant.FROM, "process");

                        bundle.putDouble("total", Constant.FLOAT_TOTAL_AMOUNT);
                        bundle.putStringArrayList("variantIdList", getArguments().getStringArrayList("variantIdList"));
                        bundle.putStringArrayList("qtyList", getArguments().getStringArrayList("qtyList"));
                        fragment.setArguments(bundle);
                        MainActivity.fm.beginTransaction().add(R.id.container, fragment).addToBackStack(null).commit();
                        try {
                            if (CheckoutFragment.pCodeDiscount != 0) {
                                CheckoutFragment.pCodeDiscount = 0;
                            }
                        } catch (Exception ignore) {

                        }
                    } else {
                        Toast.makeText(activity, R.string.select_delivery_address, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(activity, R.string.user_block_msg, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            processLyt.setVisibility(View.GONE);
            confirmLyt.setVisibility(View.GONE);
        }

        setHasOptionsMenu(true);

        swipeLayout.setColorSchemeColors(ContextCompat.getColor(activity, R.color.colorPrimary));
        swipeLayout.setOnRefreshListener(() -> {
            addresses.clear();
            addressAdapter = null;
            getAddresses();
            swipeLayout.setRefreshing(false);
        });

        fabAddAddress.setOnClickListener(view -> addNewAddress());

        return root;
    }

    public void GetDChargeSettings(final Activity activity) {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.GET_TIMEZONE, Constant.GetVal);
        params.put(Constant.SETTINGS, Constant.GetVal);
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean(Constant.ERROR)) {
                        JSONObject object = jsonObject.getJSONObject(Constant.SETTINGS);
                        Constant.SETTING_MINIMUM_AMOUNT_FOR_FREE_DELIVERY = Double.parseDouble(object.getString(Constant.MINIMUM_AMOUNT));
                        Constant.SETTING_DELIVERY_CHARGE = Double.parseDouble(object.getString(Constant.DELIVERY_CHARGE));
                    }
                    getAddresses();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, activity, Constant.SETTING_URL, params, false);
    }


    public void addNewAddress() {
        Fragment fragment = new AddressAddUpdateFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("model", "");
        bundle.putString("for", "add");
        bundle.putInt("position", 0);

        fragment.setArguments(bundle);
        MainActivity.fm.beginTransaction().add(R.id.container, fragment).addToBackStack(null).commit();
    }

    public void getAddresses() {
        recyclerView.setVisibility(View.GONE);
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmer();
        addresses = new ArrayList<>();
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(linearLayoutManager);

        Map<String, String> params = new HashMap<>();
        params.put(Constant.GET_ADDRESSES, Constant.GetVal);
        params.put(Constant.USER_ID, session.getData(Constant.ID));
        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    Constant.selectedAddressId = "";
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean(Constant.ERROR)) {
                        total = Integer.parseInt(jsonObject.getString(Constant.TOTAL));
                        session.setData(Constant.TOTAL, String.valueOf(total));
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        Gson g = new Gson();

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                            if (jsonObject1 != null) {
                                Address address = g.fromJson(jsonObject1.toString(), Address.class);
                                if (address.getIs_default().equals("1")) {
                                    Constant.selectedAddressId = address.getId();

                                    new Session(activity).setData(Constant.LONGITUDE, address.getLongitude());
                                    new Session(activity).setData(Constant.LATITUDE, address.getLatitude());

                                    if (session.getData(Constant.area_wise_delivery_charge).equals("1")) {
                                        Constant.SETTING_MINIMUM_AMOUNT_FOR_FREE_DELIVERY = Double.parseDouble(address.getMinimum_free_delivery_order_amount());
                                        Constant.SETTING_DELIVERY_CHARGE = Double.parseDouble(address.getDelivery_charges());
                                    }
                                }
                                addresses.add(address);
                            } else {
                                break;
                            }

                        }
                        addressAdapter = new AddressAdapter(activity, addresses);
                        recyclerView.setAdapter(addressAdapter);
                    } else {
                        recyclerView.setVisibility(View.GONE);
                        tvAlert.setVisibility(View.VISIBLE);
                    }
                    mShimmerViewContainer.stopShimmer();
                    mShimmerViewContainer.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    mShimmerViewContainer.stopShimmer();
                    mShimmerViewContainer.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        }, activity, Constant.GET_ADDRESS_URL, params, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        Constant.TOOLBAR_TITLE = getString(R.string.addresses);
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
        menu.findItem(R.id.toolbar_cart).setVisible(false);
        menu.findItem(R.id.toolbar_layout).setVisible(false);
        menu.findItem(R.id.toolbar_sort).setVisible(false);
        menu.findItem(R.id.toolbar_search).setVisible(false);
    }
}