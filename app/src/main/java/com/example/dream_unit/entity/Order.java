package com.example.dream_unit.entity;

import com.google.gson.annotations.SerializedName;

public class Order {
    @SerializedName("user_id")
    private int userId;
    @SerializedName("goods")
    private String goods;
    @SerializedName("goods_type")
    private int goodsType;
    @SerializedName("order_date")
    private String orderDate;
    @SerializedName("status")
    private String status;
    @SerializedName("sum")
    private int sum;
    @SerializedName("amount")
    private int amount;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public int getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(int goodsType) {
        this.goodsType = goodsType;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
