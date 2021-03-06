package com.iame.qnnect.android.src.notarrivecafe

import android.content.Intent
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.Observer
import com.iame.qnnect.android.R
import com.iame.qnnect.android.base.BaseActivity
import com.iame.qnnect.android.databinding.ActivityNotArriveEditBinding
import com.iame.qnnect.android.src.allow.AllowActivity
import com.iame.qnnect.android.src.main.MainActivity
import com.iame.qnnect.android.src.notarrivecafe.model.NotArriveEditRequest
import com.iame.qnnect.android.viewmodel.NotArriveEditViewModel
import kotlinx.android.synthetic.main.activity_not_arrive_edit.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotArriveEditActivity : BaseActivity<ActivityNotArriveEditBinding, NotArriveEditViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_not_arrive_edit // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel: NotArriveEditViewModel by viewModel()

    var check = false

    var content = ""
    var questionId = 0

    override fun initStartView() {
        content = intent.getStringExtra("content")!!
        questionId = intent.getIntExtra("questionId", 0)
    }

    override fun initDataBinding() {
        binding.contents.setText(content)
        viewModel.editResponse.observe(this, Observer {
            finish()
        })
    }

    override fun initAfterBinding() {
        binding.contents.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int, ) {
                var len = contents.text.toString()
                check = if(len.length >= 10 && contents.text.toString() != content){
                    binding.saveBtn.setTextColor(Color.parseColor("#FD774C"))
                    true
                } else{
                    binding.saveBtn.setTextColor(Color.parseColor("#BDBDBD"))
                    false
                }
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        binding.saveBtn.setOnClickListener {
            if(check){
                val request = NotArriveEditRequest(contents.text.toString())
                viewModel.patchEdit(questionId, request)
                showLoadingDialog(this)
            }
        }

        binding.backBtn.setOnClickListener {
            finish()
        }
    }
}