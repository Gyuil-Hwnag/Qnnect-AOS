package com.iame.qnnect.android.src.diary

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.iame.qnnect.android.R
import com.iame.qnnect.android.src.diary.model.Comments


class QuestionHolderPage internal constructor(itemView: View, var context: Context,
                                              var a_itemClickListener: AnswerAdapter.OnItemClickEventListener,
                                              var d_itemClickListener: AnswerAdapter.OnItemClickEventListener) : RecyclerView.ViewHolder(itemView) {
    private val user_img :ImageView
    private val user_name: TextView
    private val answer_contents: TextView
    private val answer_img: ImageView
    private val answer_btn: ImageView
    private val btn_view: LinearLayout
    private val reply_count: TextView

    var data: Comments? = null
    fun onBind(data: Comments) {
        this.data = data

        var profile = data.profileResponse

        Glide.with(context)
            .load(profile.profileImage)
            .transform(CenterCrop(), RoundedCorners(200))
            .apply(RequestOptions().placeholder(R.mipmap.profile_default_foreground)
                .error(R.mipmap.profile_default_foreground))
            .into(user_img)

        user_name.setText(profile.nickName)

        user_img.setOnClickListener(View.OnClickListener { a_view ->
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                d_itemClickListener.onItemClick(a_view, position)
            }
        })
        user_name.setOnClickListener(View.OnClickListener { a_view ->
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                d_itemClickListener.onItemClick(a_view, position)
            }
        })

        answer_contents.setText(data.content)

        var image_check = true
        var images = data
        if(images.imageUrl1 == null && images.imageUrl2 == null && images.imageUrl3 == null &&
            images.imageUrl4 == null && images.imageUrl5 == null){
            image_check = false
        }

        if(!image_check){
            answer_img.visibility = View.GONE
        }
        else{
            Glide.with(context)
                .load(images.imageUrl1)
                .transform(CenterCrop(), RoundedCorners(50))
                .into(answer_img)
        }

        if(data.replyCount > 0){
            reply_count.visibility = View.VISIBLE
            reply_count.text = data.replyCount.toString()
        }
        else{
            reply_count.visibility = View.GONE
        }

        answer_btn.setOnClickListener(View.OnClickListener { a_view ->
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                a_itemClickListener.onItemClick(a_view, position)
            }
        })

        btn_view.setOnClickListener(View.OnClickListener { a_view ->
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                a_itemClickListener.onItemClick(a_view, position)
            }
        })


    }

    init {
        user_img = itemView.findViewById(R.id.user_img)
        user_name = itemView.findViewById(R.id.user_name)
        answer_contents = itemView.findViewById(R.id.answer_contents)
        answer_img = itemView.findViewById(R.id.answer_img)
        answer_btn = itemView.findViewById(R.id.answer_btn)
        btn_view = itemView.findViewById(R.id.btn_view)
        reply_count = itemView.findViewById(R.id.reply_count)
    }
}
