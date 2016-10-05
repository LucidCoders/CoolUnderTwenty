package com.lucidcoders.coolundertwenty.network;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.lucidcoders.coolundertwenty.object.Category;
import com.lucidcoders.coolundertwenty.object.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ApiManager {
    private static final String sBaseUrl = "http://coolundertwenty.com/feed_point/request.php?key=asdkj89cool3raman92y&method=";

    public interface RequestListener<T> {
        void onFinished(T responseObject);
        void onError(String errorMessage);
    }

    public static void getCategories(final RequestListener<List<Category>> requestListener) {
        final String url = sBaseUrl + "categories";

        CacheJsonObjectRequest request = new CacheJsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i("Request", url);
                            Log.i("Response", response.toString());

                            List<Category> categories = new ArrayList<>();

                            for (Iterator<String> iter = response.keys(); iter.hasNext();) {
                                String key = iter.next();
                                String value = (String) response.get(key);
                                value = value.replace("&amp;", "&");

                                Category category = new Category();
                                category.setId(Integer.valueOf(key));
                                category.setName(value);

                                categories.add(category);
                            }

                            requestListener.onFinished(categories);
                        } catch (Exception e) {
                            e.printStackTrace();
                            requestListener.onError("Json deserialization error");
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error != null) {
                            error.printStackTrace();
                            requestListener.onError(error.getMessage());
                        }
                    }
                }
        );

        VolleySingleton.getInstance().getRequestQueue().add(request);
    }

    public static void getProducts(int categoryId, int limit, int page, final RequestListener<List<Product>> requestListener) {
        final String url = sBaseUrl + "getProducts&category_id=" + categoryId + "&limit=" + limit + "&page=" + page;

        Cache cache = VolleySingleton.getInstance().getCache();
        Cache.Entry entry = cache.get(Request.Method.GET + ":" + url);
        if (entry != null) {
            try {
                List<Product> products = new ArrayList<>();

                JSONArray jsonArray = new JSONArray(new String(entry.data));
                for (int i = 0; i < jsonArray.length(); i++) {
                    Product product = new Gson().fromJson(jsonArray.getJSONObject(i).toString(), Product.class);
                    products.add(product);
                }

                requestListener.onFinished(products);
                return;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        CacheJsonArrayRequest request = new CacheJsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.i("Request", url);
                            Log.i("Response", response.toString());

                            List<Product> products = new ArrayList<>();

                            for (int i = 0; i < response.length(); i++) {
                                Product product = new Gson().fromJson(response.getJSONObject(i).toString(), Product.class);
                                products.add(product);
                            }

                            requestListener.onFinished(products);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            requestListener.onError("Json deserialization error");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error != null) {
                            error.printStackTrace();
                            requestListener.onError(error.getMessage());
                        }
                    }
                }
        );
        VolleySingleton.getInstance().getRequestQueue().add(request);
    }

    public static void searchProducts(String searchTerm, int limit, int page, final RequestListener<List<Product>> requestListener) {
        final String url = sBaseUrl + "searchProducts&search_term=" + searchTerm + "&limit=" + limit + "&page=" + page;

        Cache cache = VolleySingleton.getInstance().getCache();
        Cache.Entry entry = cache.get(Request.Method.GET + ":" + url);
        if (entry != null) {
            try {
                List<Product> products = new ArrayList<>();

                JSONArray jsonArray = new JSONArray(new String(entry.data));
                for (int i = 0; i < jsonArray.length(); i++) {
                    Product product = new Gson().fromJson(jsonArray.getJSONObject(i).toString(), Product.class);
                    products.add(product);
                }

                requestListener.onFinished(products);
                return;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        CacheJsonArrayRequest request = new CacheJsonArrayRequest(Request.Method.GET, url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.i("Request", url);
                            Log.i("Response", response.toString());

                            List<Product> products = new ArrayList<>();

                            for (int i = 0; i < response.length(); i++) {
                                Product product = new Gson().fromJson(response.getJSONObject(i).toString(), Product.class);
                                products.add(product);
                            }

                            requestListener.onFinished(products);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            requestListener.onError("Json deserialization error");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error != null) {
                            error.printStackTrace();
                            requestListener.onError(error.getMessage());
                        }
                    }
                }
        );
        VolleySingleton.getInstance().getRequestQueue().add(request);
    }
}
