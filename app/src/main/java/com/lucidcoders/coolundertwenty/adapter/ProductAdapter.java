package com.lucidcoders.coolundertwenty.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.lucidcoders.coolundertwenty.R;
import com.lucidcoders.coolundertwenty.network.VolleySingleton;
import com.lucidcoders.coolundertwenty.object.Product;
import com.lucidcoders.coolundertwenty.util.Util;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static int PAGE_SIZE = 20;

    private List<Product> mProducts = new ArrayList<>();
    private ImageLoader mImageLoader;
    private LayoutInflater mInflater;
    private ProductListener mListener;
    private Context mContext;

    private int mPageNumber = 1;

    public interface ProductListener {
        void onProductClick(Product product);
        void onShareClick(Product product);
        void onWishlistClick(Product product);
        void onLastItemVisible(int position);
    }

    public ProductAdapter(Context context, ProductListener listener) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mImageLoader = VolleySingleton.getInstance().getImageLoader();
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(mInflater.inflate(R.layout.product_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemHolder) {
            final ItemHolder itemHolder = (ItemHolder) holder;
            Product product = mProducts.get(position);

            itemHolder.product = product;

            //reset fields
            itemHolder.imageView.setImageBitmap(null);

            if (Util.checkString(product.getImg())) {
                mImageLoader.get(product.getImg(), new ImageLoader.ImageListener() {
                    @Override
                    public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                        TransitionDrawable td = new TransitionDrawable(new Drawable[]{
                                new ColorDrawable(ContextCompat.getColor(mContext, android.R.color.transparent)),
                                new BitmapDrawable(mContext.getResources(), response.getBitmap())
                        });
                        itemHolder.imageView.setImageDrawable(td);
                        td.startTransition(250);
                    }

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
            }
            itemHolder.nameTextView.setText(product.getTitle());
            itemHolder.descriptionTextView.setText(product.getExcerpt());
            String price = "$" + product.getPrice();
            itemHolder.priceTextView.setText(price);
        }

        if (position == mProducts.size() - 1 && mProducts.size() >= (PAGE_SIZE * mPageNumber) && mListener != null) {
            mListener.onLastItemVisible(position);
        }
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public void addProducts(List<Product> products, int pageNumber) {
        mProducts.addAll(products);
        mPageNumber = pageNumber;
        notifyDataSetChanged();
    }

    public List<Product> getProducts() {
        return mProducts;
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Product product;
        ImageView imageView;
        TextView nameTextView;
        TextView descriptionTextView;
        TextView priceTextView;
        FrameLayout shareLayout;
        FrameLayout wishlistLayout;

        public ItemHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.product_item_image);
            nameTextView = (TextView) itemView.findViewById(R.id.product_item_name);
            descriptionTextView = (TextView) itemView.findViewById(R.id.product_item_description);
            priceTextView = (TextView) itemView.findViewById(R.id.product_item_price);
            shareLayout = (FrameLayout) itemView.findViewById(R.id.product_item_share);
            wishlistLayout = (FrameLayout) itemView.findViewById(R.id.product_item_wish_list);

            itemView.setOnClickListener(this);
            shareLayout.setOnClickListener(this);
            wishlistLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                switch (v.getId()) {
                    case R.id.product_item_share:
                        mListener.onShareClick(product);
                        break;

                    case R.id.product_item_wish_list:
                        mListener.onWishlistClick(product);
                        break;

                    default:
                        mListener.onProductClick(product);
                        break;
                }
            }
        }
    }
}
