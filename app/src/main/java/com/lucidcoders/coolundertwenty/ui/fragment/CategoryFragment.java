package com.lucidcoders.coolundertwenty.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.lucidcoders.coolundertwenty.R;
import com.lucidcoders.coolundertwenty.adapter.ProductAdapter;
import com.lucidcoders.coolundertwenty.network.ApiManager;
import com.lucidcoders.coolundertwenty.object.Category;
import com.lucidcoders.coolundertwenty.object.Product;
import com.lucidcoders.coolundertwenty.util.Util;

import java.util.List;

public class CategoryFragment extends Fragment implements ProductAdapter.ProductListener{
    private Category mCategory;
    private ProductAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mRefreshLayout;

    private int mLastIndex = -1;
    private int mPageNumber = 1;

    public static CategoryFragment newInstance(Category category) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putString("category", new Gson().toJson(category, Category.class));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String categoryJson = getArguments().getString("category");
        if (Util.checkString(categoryJson)) {
            mCategory = new Gson().fromJson(categoryJson, Category.class);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.category_swipeRefreshLayout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.category_recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new ProductAdapter(getActivity(), CategoryFragment.this);
        mRecyclerView.setAdapter(mAdapter);

        setupSwipeRefresh();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mCategory != null && mAdapter != null && !Util.checkList(mAdapter.getProducts())) {
            mLastIndex = -1;
            mPageNumber = 1;
            makeProductsRequest();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(false);
        }
    }

    private void setupSwipeRefresh() {
        mRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mLastIndex = -1;
                        mPageNumber = 1;
                        makeProductsRequest();
                    }
                }
        );
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
    }

    private void makeProductsRequest() {
        if (!mRefreshLayout.isRefreshing()) {
            mRefreshLayout.setRefreshing(true);
        }
        ApiManager.getProducts(mCategory.getId(), ProductAdapter.PAGE_SIZE, mPageNumber,
                new ApiManager.RequestListener<List<Product>>() {
                    @Override
                    public void onFinished(List<Product> responseObject) {
                        if (mRefreshLayout.isRefreshing()) {
                            mRefreshLayout.setRefreshing(false);
                        }

                        mAdapter.addProducts(responseObject, mPageNumber);
                        if (mLastIndex != -1 && mRecyclerView != null) {
                            mRecyclerView.scrollToPosition(mLastIndex);
                        }
                        mPageNumber++;
                    }

                    @Override
                    public void onError(String errorMessage) {
                        if (mRefreshLayout.isRefreshing()) {
                            mRefreshLayout.setRefreshing(false);
                        }
                    }
                }
        );
    }

    @Override
    public void onProductClick(Product product) {

    }

    @Override
    public void onShareClick(Product product) {

    }

    @Override
    public void onWishlistClick(Product product) {

    }

    @Override
    public void onLastItemVisible(int position) {
        mLastIndex = position;
        makeProductsRequest();
    }
}
