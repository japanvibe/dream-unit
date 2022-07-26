package com.example.dream_unit.entity;

import com.google.gson.annotations.SerializedName;

public class UserFavoritePart {
    @SerializedName("user_id")
    private int userId;
    @SerializedName("part_id")
    private int partId;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPartId(int partId) {
        this.partId = partId;
    }
}
