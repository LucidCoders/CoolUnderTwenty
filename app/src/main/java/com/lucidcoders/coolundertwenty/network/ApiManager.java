package com.lucidcoders.coolundertwenty.network;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.lucidcoders.coolundertwenty.object.Category;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
}
