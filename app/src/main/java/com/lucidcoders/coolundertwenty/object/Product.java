package com.lucidcoders.coolundertwenty.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("img")
    @Expose
    private String img;
    @SerializedName("excerpt")
    @Expose
    private String excerpt;
    @SerializedName("favorite_cnt")
    @Expose
    private String favoriteCount;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("ref_url")
    @Expose
    private String refUrl;

    /**
     * @return The productId
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * @param productId The product_id
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * @return The date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The img
     */
    public String getImg() {
        return img;
    }

    /**
     * @param img The img
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * @return The excerpt
     */
    public String getExcerpt() {
        return excerpt;
    }

    /**
     * @param excerpt The excerpt
     */
    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    /**
     * @return The price
     */
    public String getPrice() {
        return price;
    }

    /**
     * @param price The price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * @return The refUrl
     */
    public String getRefUrl() {
        return refUrl;
    }

    /**
     * @param refUrl The ref_url
     */
    public void setRefUrl(String refUrl) {
        this.refUrl = refUrl;
    }

    public String getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(String favoriteCount) {
        this.favoriteCount = favoriteCount;
    }
}