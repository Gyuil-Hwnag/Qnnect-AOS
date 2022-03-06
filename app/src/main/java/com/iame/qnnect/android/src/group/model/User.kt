package com.iame.qnnect.android.src.group.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("nickName") var nickName: String,
    @SerializedName("point") var point: Int,
    @SerializedName("profileImage") var profileImage: String
)