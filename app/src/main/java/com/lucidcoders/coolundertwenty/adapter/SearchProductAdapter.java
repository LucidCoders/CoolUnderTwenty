package com.lucidcoders.coolundertwenty.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.lucidcoders.coolundertwenty.R;
import com.lucidcoders.coolundertwenty.object.Product;

import java.util.ArrayList;
import java.util.List;

public class SearchProductAdapter extends RecyclerView.Adapter<SearchProductAdapter.ViewHolder> implements Filterable {
    private static final int TYPE_FIRST_ITEM = 0;
    private static final int TYPE_ITEM = 1;

    private List<Product> mProducts = new ArrayList<>();
    private List<Product> mFilteredProducts = new ArrayList<>();
    private ProductFilter mProductFilter;
    private SearchResultClickListener mListener;

    public interface SearchResultClickListener {
        void onProductSearchResultClick(Product product);
    }

    public SearchProductAdapter(SearchResultClickListener listener) {
        mListener = listener;
        getFilter();
    }

    @Override
    public SearchProductAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FIRST_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_row_first_item, parent, false);
            return new ViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_result_row, parent, false);
            return new ViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(SearchProductAdapter.ViewHolder holder, int position) {
        if (mFilteredProducts.size() > 0) {
            holder.product = mFilteredProducts.get(position);
            holder.label.setText(mFilteredProducts.get(position).getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return mFilteredProducts.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return TYPE_FIRST_ITEM;

        return TYPE_ITEM;
    }

    @Override
    public Filter getFilter() {
        if (mProductFilter == null) {
            mProductFilter = new ProductFilter();
        }
        return mProductFilter;
    }

    public void setProducts(List<Product> products) {
        this.mProducts = products;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Product product;
        TextView label;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            label = (TextView) itemView.findViewById(R.id.search_row_text);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onProductSearchResultClick(product);
            }
        }
    }

    private class ProductFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            results.count = 0;
            results.values = new ArrayList<>();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<Product> suggestions = new ArrayList<>();
                for (Product product : mProducts) {
                    if (product.getTitle().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(product);
                    }
                }

                if (suggestions.size() <= 50) {
                    results.values = suggestions;
                    results.count = suggestions.size();
                }

            }

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mFilteredProducts = (List<Product>) results.values;
            notifyDataSetChanged();
        }
    }
}
