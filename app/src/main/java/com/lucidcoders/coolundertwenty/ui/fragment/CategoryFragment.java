package com.lucidcoders.coolundertwenty.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lucidcoders.coolundertwenty.R;
import com.lucidcoders.coolundertwenty.object.Category;
import com.lucidcoders.coolundertwenty.util.Util;

public class CategoryFragment extends Fragment {
    private Category mCategory;

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

        TextView nameTextView = (TextView) view.findViewById(R.id.category_name_textView);
        nameTextView.setText(mCategory.getName() + " - " + mCategory.getId());
    }
}
