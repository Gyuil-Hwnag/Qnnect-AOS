package com.iame.qnnect.android.src.group.group_bottom.service

import com.iame.qnnect.android.src.main.home.home_bottom.model.PostAddGroupRequest
import com.iame.qnnect.android.src.main.home.home_bottom.model.PostAddGroupResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*

interface DeleteGroupAPI {
    @PATCH("/api/v1/cafes/leaver")
    fun deleteGroup(@Query("cafeId") cafeId: Int) : Call<Void>
}