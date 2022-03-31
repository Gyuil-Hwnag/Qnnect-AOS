package com.iame.qnnect.android.src.question

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.iame.qnnect.android.R
import com.iame.qnnect.android.src.main.bookmark.QuestionListHolderPage
import com.iame.qnnect.android.src.main.bookmark.model.Bookmark
import com.iame.qnnect.android.src.main.bookmark.model.Cafe
import com.iame.qnnect.android.src.main.bookmark.model.bookmark_question
import com.iame.qnnect.android.src.main.home.model.group_item
import com.iame.qnnect.android.src.question.model.GetUserQuestionListResponse
import com.iame.qnnect.android.src.reply.ReplyHolderPage


class UserQuestionListAdapter() :
    RecyclerView.Adapter<UserQuestionListHolderPage>(){

    private val itemList = ArrayList<GetUserQuestionListResponse>()

    fun setOnItemClickListener(a_listener: OnItemClickEventListener) {
        mItemClickListener = a_listener
    }

    private var mItemClickListener: OnItemClickEventListener? = null
    interface OnItemClickEventListener {
        fun onItemClick(a_view: View?, a_position: Int)
    }


    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView){
        var question_group: TextView = itemView.findViewById(R.id.question_group)
        var question_contents: TextView = itemView.findViewById(R.id.question_contents)
        var date: TextView = itemView.findViewById(R.id.question_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserQuestionListHolderPage {
        val context: Context = parent.context
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.bookmark_question_item, parent, false)
        return UserQuestionListHolderPage(view, context, mItemClickListener!!)
    }

    override fun onBindViewHolder(holder: UserQuestionListHolderPage, position: Int) {
        if (holder is UserQuestionListHolderPage) {
            val viewHolder: UserQuestionListHolderPage = holder as UserQuestionListHolderPage
            viewHolder.onBind(itemList[position])
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun addItem(item: GetUserQuestionListResponse) {
        itemList.add(item)
    }

    fun clear() {
        itemList.clear()
        this.notifyDataSetChanged()
    }

    fun getItem(position: Int): GetUserQuestionListResponse {
        return itemList.get(position)
    }
}