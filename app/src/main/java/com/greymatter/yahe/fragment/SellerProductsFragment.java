package com.greymatter.yahe.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.greymatter.yahe.R;
import com.greymatter.yahe.adapter.ProductLoadMoreAdapter;
import com.greymatter.yahe.helper.ApiConfig;
import com.greymatter.yahe.helper.Constant;
import com.greymatter.yahe.helper.Session;
import com.greymatter.yahe.model.Product;

import static android.content.Context.INPUT_METHOD_SERVICE;
import static com.greymatter.yahe.helper.ApiConfig.GetSettings;


public class SellerProductsFragment extends Fragment {
    public static ArrayList<Product> productArrayList;
    @SuppressLint("StaticFieldLeak")
    public static ProductLoadMoreAdapter mAdapter;
    View root;
    Session session;
    int total;
    NestedScrollView nestedScrollView;
    Activity activity;
    int offset = 0;
    String id, filterBy, from;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeLayout;
    int filterIndex;
    TextView tvAlert;
    final boolean isSort = false;
    boolean isLoadMore = false;
    boolean isGrid = false;
    int resource;
    private ShimmerFrameLayout mShimmerViewContainer;
    ImageView imgTitle;
    TextView tvTitle;
    LinearLayout lytList, lytGrid;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_seller_products, container, false);
        setHasOptionsMenu(true);
        offset = 0;
        activity = getActivity();

        session = new Session(activity);

        assert getArguments() != null;
        id = requireArguments().getString(Constant.ID);
        from = getArguments().getString(Constant.FROM);



        swipeLayout = root.findViewById(R.id.swipeLayout);
        tvAlert = root.findViewById(R.id.tvAlert);
        nestedScrollView = root.findViewById(R.id.nestedScrollView);
        mShimmerViewContainer = root.findViewById(R.id.mShimmerViewContainer);
        imgTitle = root.findViewById(R.id.imgTitle);
        tvTitle = root.findViewById(R.id.tvTitle);
        lytGrid = root.findViewById(R.id.lytGrid);
        lytList = root.findViewById(R.id.lytList);



        if (session.getBoolean("grid")) {
            resource = R.layout.lyt_item_grid;
            isGrid = true;
            lytGrid.setVisibility(View.VISIBLE);
            lytList.setVisibility(View.GONE);
            recyclerView = root.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new GridLayoutManager(activity, 2));

        } else {
            resource = R.layout.lyt_item_list;
            isGrid = false;
            lytGrid.setVisibility(View.GONE);
            lytList.setVisibility(View.VISIBLE);
            recyclerView = root.findViewById(R.id.recyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        }

        GetSettings(activity);

        filterIndex = -1;

            GetSellerData();

        swipeLayout.setColorSchemeResources(R.color.colorPrimary);

        swipeLayout.setOnRefreshListener(() -> {
            if (productArrayList != null && productArrayList.size() > 0) {
                offset = 0;
                swipeLayout.setRefreshing(false);
                productArrayList.clear();
                GetSellerData();
            }
        });

        return root;
    }

    public void GetSellerData() {
        Map<String, String> params = new HashMap<>();
        params.put(Constant.GET_SELLER_DATA, Constant.GetVal);
        params.put(Constant.SELLER_ID, id);

        ApiConfig.RequestToVolley((result, response) -> {

            if (result) {
                try {
                    JSONObject object = new JSONObject(response);
                    if (!object.getBoolean(Constant.ERROR)) {
                        JSONObject jsonObject = object.getJSONArray(Constant.DATA).getJSONObject(0);
                        tvTitle.setText(jsonObject.getString(Constant.STORE_NAME));
                        Picasso.get()
                                .load(jsonObject.getString(Constant.LOGO).equals("") ? "-" : jsonObject.getString(Constant.LOGO))
                                .fit()
                                .centerInside()
                                .placeholder(R.drawable.placeholder)
                                .error(R.drawable.placeholder)
                                .into(imgTitle);
                        GetData();
                    }
                } catch (JSONException e) {
                        e.printStackTrace();
                }
            }
        }, activity, Constant.GET_SELLER_DATA_URL, params, false);
    }

    void GetData() {
        recyclerView.setVisibility(View.GONE);
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmer();
        Map<String, String> params = new HashMap<>();
        params.put(Constant.GET_ALL_PRODUCTS, Constant.GetVal);
        params.put(Constant.SELLER_ID, id);
        params.put(Constant.USER_ID, session.getData(Constant.ID));
        if (session.getBoolean(Constant.GET_SELECTED_PINCODE) && !session.getData(Constant.GET_SELECTED_PINCODE_ID).equals("0")) {
            params.put(Constant.PINCODE_ID, session.getData(Constant.GET_SELECTED_PINCODE_ID));
        }
        params.put(Constant.LIMIT, "" + Constant.LOAD_ITEM_LIMIT);
        params.put(Constant.OFFSET, "" + offset);
        if (filterIndex != -1) {
            params.put(Constant.SORT, filterBy);
        }

        ApiConfig.RequestToVolley((result, response) -> {
            if (result) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (!jsonObject.getBoolean(Constant.ERROR)) {
                        total = Integer.parseInt(jsonObject.getString(Constant.TOTAL));
                        if (offset == 0) {
                            productArrayList = new ArrayList<>();
                            tvAlert.setVisibility(View.GONE);
                        }
                        JSONObject object = new JSONObject(response);
                        JSONArray jsonArray = object.getJSONArray(Constant.DATA);
                        try {
                            productArrayList.addAll(ApiConfig.GetProductList(jsonArray));
                        } catch (Exception e) {
                            mShimmerViewContainer.stopShimmer();
                            mShimmerViewContainer.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                        }
                        if (offset == 0) {
                            mAdapter = new ProductLoadMoreAdapter(activity, productArrayList, resource, from);
                            mAdapter.setHasStableIds(true);
                            recyclerView.setAdapter(mAdapter);
                            mShimmerViewContainer.stopShimmer();
                            mShimmerViewContainer.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            nestedScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {

                                // if (diff == 0) {
                                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                                    if (productArrayList.size() < total) {
                                        if (!isLoadMore) {
                                            if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == productArrayList.size() - 1) {
                                                //bottom of list!
                                                productArrayList.add(null);
                                                mAdapter.notifyItemInserted(productArrayList.size() - 1);

                                                offset += Integer.parseInt("" + Constant.LOAD_ITEM_LIMIT);
                                                Map<String, String> params1 = new HashMap<>();
                                                params1.put(Constant.GET_ALL_PRODUCTS, Constant.GetVal);
                                                params1.put(Constant.SELLER_ID, id);
                                                if (session.getBoolean(Constant.GET_SELECTED_PINCODE) && !session.getData(Constant.GET_SELECTED_PINCODE_ID).equals("0")) {
                                                    params1.put(Constant.PINCODE_ID, session.getData(Constant.GET_SELECTED_PINCODE_ID));
                                                }
                                                params1.put(Constant.USER_ID, session.getData(Constant.ID));
                                                params1.put(Constant.LIMIT, "" + Constant.LOAD_ITEM_LIMIT);
                                                params1.put(Constant.OFFSET, "" + offset);
                                                if (filterIndex != -1) {
                                                    params1.put(Constant.SORT, filterBy);
                                                }

                                                ApiConfig.RequestToVolley((result1, response1) -> {

                                                    if (result1) {
                                                        try {
                                                            JSONObject jsonObject1 = new JSONObject(response1);
                                                            if (!jsonObject1.getBoolean(Constant.ERROR)) {

                                                                JSONObject object1 = new JSONObject(response1);
                                                                JSONArray jsonArray1 = object1.getJSONArray(Constant.DATA);
                                                                productArrayList.remove(productArrayList.size() - 1);
                                                                mAdapter.notifyItemRemoved(productArrayList.size());
                                                                try {
                                                                    productArrayList.addAll(ApiConfig.GetProductList(jsonArray1));
                                                                } catch (Exception e) {
                                                                    e.printStackTrace();
                                                                }
                                                                mAdapter.notifyDataSetChanged();
                                                                mAdapter.setLoaded();
                                                                isLoadMore = false;
                                                            }
                                                        } catch (JSONException e) {
                                                            e.printStackTrace();

                                                        }
                                                    }
                                                }, activity, Constant.GET_PRODUCTS_URL, params1, false);
                                                isLoadMore = true;
                                            }
                                        }
                                    }
                                }
                            });
                        }
                    } else {
                        if (offset == 0) {
                            mShimmerViewContainer.stopShimmer();
                            mShimmerViewContainer.setVisibility(View.GONE);
                            recyclerView.setVisibility(View.VISIBLE);
                            tvAlert.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (JSONException e) {
                        e.printStackTrace();
                    mShimmerViewContainer.stopShimmer();
                    mShimmerViewContainer.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        }, activity, Constant.GET_PRODUCTS_URL, params, false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.toolbar_sort) {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(activity.getResources().getString(R.string.filter_by));
            builder.setSingleChoiceItems(Constant.filterValues, filterIndex, (dialog, item1) -> {
                filterIndex = item1;
                switch (item1) {
                    case 0:
                        filterBy = Constant.NEW;
                        break;
                    case 1:
                        filterBy = Constant.OLD;
                        break;
                    case 2:
                        filterBy = Constant.HIGH;
                        break;
                    case 3:
                        filterBy = Constant.LOW;
                        break;
                }
                if (item1 != -1)
                    GetData();
                dialog.dismiss();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else if (item.getItemId() == R.id.toolbar_layout) {
            if (isGrid) {
                lytGrid.setVisibility(View.GONE);
                lytList.setVisibility(View.VISIBLE);
                isGrid = false;
                recyclerView.setAdapter(null);
                resource = R.layout.lyt_item_list;
                recyclerView.setLayoutManager(new LinearLayoutManager(activity));
            } else {
                lytGrid.setVisibility(View.VISIBLE);
                lytList.setVisibility(View.GONE);
                isGrid = true;
                recyclerView.setAdapter(null);
                resource = R.layout.lyt_item_grid;
                recyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
            }
            session.setBoolean("grid", isGrid);
            mAdapter = new ProductLoadMoreAdapter(activity, productArrayList, resource, from);
            recyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
            activity.invalidateOptionsMenu();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        activity.getMenuInflater().inflate(R.menu.main_menu, menu);

        menu.findItem(R.id.toolbar_sort).setVisible(isSort);
        menu.findItem(R.id.toolbar_search).setVisible(true);
        menu.findItem(R.id.toolbar_cart).setIcon(ApiConfig.buildCounterDrawable(Constant.TOTAL_CART_ITEM, activity));
        menu.findItem(R.id.toolbar_layout).setVisible(true);

        Drawable myDrawable;
        if (isGrid) {
            myDrawable = ContextCompat.getDrawable(activity, R.drawable.ic_list_); // The ID of your drawable
        } else {
            myDrawable = ContextCompat.getDrawable(activity, R.drawable.ic_grid_); // The ID of your drawable.
        }
        menu.findItem(R.id.toolbar_layout).setIcon(myDrawable);

        super.onPrepareOptionsMenu(menu);
    }

    public void startShimmer() {
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        mShimmerViewContainer.startShimmer();
    }

    public void stopShimmer() {
        mShimmerViewContainer.stopShimmer();
        mShimmerViewContainer.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        assert getArguments() != null;
        Constant.TOOLBAR_TITLE = getArguments().getString(Constant.NAME);
        activity.invalidateOptionsMenu();
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
    public void onPause() {
        super.onPause();
        ApiConfig.AddMultipleProductInCart(session, activity, Constant.CartValues);
    }
}