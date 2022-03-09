package com.iame.qnnect.android.src.question

import android.content.Intent
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iame.qnnect.android.R
import com.iame.qnnect.android.base.BaseActivity
import com.iame.qnnect.android.base.HomeFragment_case
import com.iame.qnnect.android.databinding.ActivityQuestionBinding
import com.iame.qnnect.android.src.question.model.PostQuestionRequest
import com.iame.qnnect.android.viewmodel.QuestionViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_question.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class QuestionActivity : BaseActivity<ActivityQuestionBinding, QuestionViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_question // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel: QuestionViewModel by viewModel()

    var check = false

    override fun initStartView() {
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        // rx java 사용
        val observableTextQuery = Observable
            .create(ObservableOnSubscribe { emitter: ObservableEmitter<String>? ->
                contents.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                    }

                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int,
                    ) {
                        emitter?.onNext(s.toString())
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    }
                })
            })
            .debounce(500, TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())

        observableTextQuery.subscribe(object : io.reactivex.rxjava3.core.Observer<String> {
            override fun onComplete() {
            }

            override fun onSubscribe(d: Disposable?) {
            }

            override fun onNext(t: String) {
                var len = contents.text.toString()
                if(len.length > 0){
                    save_btn.setTextColor(Color.parseColor("#FD774C"))
                    check = true
                }
                else{
                    save_btn.setTextColor(Color.parseColor("#BDBDBD"))
                    check = false
                }
            }
            override fun onError(e: Throwable?) {
            }

        })

        save_btn.setOnClickListener {
            if(check){
                var cafeId = HomeFragment_case().getGroupname(this)
                var request = PostQuestionRequest(contents.text.toString())
                viewModel.postQuestion(cafeId!!, request)

                viewModel.postquestionResponse.observe(this, Observer {
                    Log.d("question test", "질문 성공 "+it.toString()+"번째 질문")
                    finish()
                })
            }
        }

        back_btn.setOnClickListener {
            finish()
        }
    }
}