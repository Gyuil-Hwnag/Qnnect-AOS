package com.iame.qnnect.android.src.question

import android.content.Intent
import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.iame.qnnect.android.R
import com.iame.qnnect.android.base.BaseActivity
import com.iame.qnnect.android.base.HomeFragment_case
import com.iame.qnnect.android.databinding.ActivityQuestionlistBinding
import com.iame.qnnect.android.databinding.ActivitySearchBinding
import com.iame.qnnect.android.src.diary.DiaryActivity
import com.iame.qnnect.android.src.main.bookmark.QuestionListAdapter
import com.iame.qnnect.android.src.main.bookmark.model.Bookmark
import com.iame.qnnect.android.src.search.SearchActivity
import com.iame.qnnect.android.viewmodel.QuestionListViewModel
import kotlinx.android.synthetic.main.activity_questionlist.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class QuestionListActivity : BaseActivity<ActivityQuestionlistBinding, QuestionListViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_questionlist // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel: QuestionListViewModel by viewModel()
    private val questionListAdapter: QuestionListAdapter by inject()

    var home = HomeFragment_case()

    override fun initStartView() {
        // question list recycler
        binding.questionRecycler.run {
            adapter = questionListAdapter
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
        viewModel.questionResponse.observe(this, Observer {
            if(it.cafeQuestionList.size == 0){
                binding.emptyImg.visibility = View.VISIBLE
                binding.emptyTxt.visibility = View.VISIBLE
            }
            else{
                binding.emptyImg.visibility = View.GONE
                binding.emptyTxt.visibility = View.GONE
                it.cafeQuestionList.forEach { item ->
                    questionListAdapter.addItem(item)
                }
            }
            questionListAdapter.notifyDataSetChanged()
            dismissLoadingDialog()
        })
    }

    override fun initAfterBinding() {
        questionListAdapter.clear()

        var cafeId = home.getGroupname(this)

        viewModel.getQuestion(cafeId!!)
        showLoadingDialog(this)

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.searchBtn.setOnClickListener {
            var intent = Intent(this, SearchQuestionActivity::class.java)
            startActivity(intent)
        }

        questionListAdapter.setOnItemClickListener { _, a_position ->
            val item: Bookmark = questionListAdapter.getItem(a_position)
            var intent = Intent(this@QuestionListActivity, DiaryActivity::class.java)
            intent.putExtra("cafeQuestionId", item.cafeQuestionId)
            startActivity(intent)
        }
    }
}