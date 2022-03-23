package com.iame.qnnect.android.src.diary

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.iame.qnnect.android.BuildConfig
import com.iame.qnnect.android.R
import com.iame.qnnect.android.base.BaseActivity
import com.iame.qnnect.android.databinding.ActivityDiaryBinding
import com.iame.qnnect.android.databinding.ActivityLoginBinding
import com.iame.qnnect.android.src.allow.AllowActivity
import com.iame.qnnect.android.src.answer.AnswerActivity
import com.iame.qnnect.android.src.declare.DeclareBottomSheet
import com.iame.qnnect.android.src.diary.model.answer_item
import com.iame.qnnect.android.src.edit_question.EditQuestionActivity
import com.iame.qnnect.android.src.login.model.PostLoginRequest
import com.iame.qnnect.android.src.main.MainActivity
import com.iame.qnnect.android.src.main.home.GroupAdapter
import com.iame.qnnect.android.src.main.home.model.group_item
import com.iame.qnnect.android.src.reply.ReplyActivity
import com.iame.qnnect.android.src.reply.ReplyAdapter
import com.iame.qnnect.android.src.reply.model.Replies
import com.iame.qnnect.android.src.reply.reply_more.DeleteReplyDialog
import com.iame.qnnect.android.src.reply.reply_more.ReplyMoreBottomSheet
import com.iame.qnnect.android.src.reply.reply_more.service.DeleteReplyService
import com.iame.qnnect.android.viewmodel.DiaryViewModel
import com.iame.qnnect.android.viewmodel.LoginViewModel
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.activity_diary.*
import kotlinx.android.synthetic.main.activity_diary.back_btn
import kotlinx.android.synthetic.main.activity_edit_profile.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.String

class DiaryActivity : BaseActivity<ActivityDiaryBinding, DiaryViewModel>() {

    override val layoutResourceId: Int
        get() = R.layout.activity_diary // get() : 커스텀 접근자, 코틀린 문법

    var liked = false
    var writer = false
    var scraped = false
    var cafeQuestionId = 0

    var date = ""
    var dday = ""
    var questioner = ""
    var question = ""


    override val viewModel: DiaryViewModel by viewModel()

    private val answerAdapter: AnswerAdapter by inject()

    override fun initStartView() {
        cafeQuestionId = intent.getIntExtra("cafeQuestionId", 0)
        Log.d("diary_response", cafeQuestionId.toString())

        answer_recycler.run {
            adapter = answerAdapter
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            setHasFixedSize(true)
        }
    }

    override fun onResume() {
        super.onResume()

        answerAdapter.clear()
        viewModel.getQuestion(cafeQuestionId)
        showLoadingDialog(this)
    }

    override fun initDataBinding() {
        viewModel.questionResponse.observe(this, Observer {
            var main = it.questionMainResponse
            liked = it.liked
            writer = main.writer
            scraped = it.scraped

            if(writer){
                edit_btn.visibility = View.VISIBLE
                delete_btn.visibility = View.VISIBLE
            }
            if(scraped){
                bookmark_btn.setImageResource(R.mipmap.ic_bookmark_select_foreground)
            }
            if(liked){
                like_btn.setImageResource(R.mipmap.ic_like_select_btn_foreground)
            }

            create_date.text = main.createdAt
            dday_txt.text = "D-"+main.daysLeft
            who_question.text = main.questioner+"의 질문"
            question_txt.text = main.question

            date = main.createdAt
            dday = "D-"+main.daysLeft.toString()
            questioner = main.questioner
            question = main.question

            // item add
            if(it.currentUserComment != null){
                answer_main.visibility = View.GONE
                my_profile_name.visibility = View.GONE
                my_profile_img.visibility = View.GONE
                answerAdapter.addItem(it.currentUserComment)
            }
            else{
                answer_main.visibility = View.VISIBLE
                my_profile_name.visibility = View.VISIBLE
                my_profile_img.visibility = View.VISIBLE
            }

            it.comments.forEach { item ->
                answerAdapter.addItem(item)
            }

            answerAdapter.notifyDataSetChanged()
            dismissLoadingDialog()
        })

        viewModel.userResponse.observe(this, Observer {
            var image = it.profileImage

            // Profile Url
            Glide.with(this)
                .load(image)
                .transform(CenterCrop(), RoundedCorners(200))
                .into(my_profile_img)
            // User Name
            my_profile_name.text = it.nickName
        })

        viewModel.likeResponse.observe(this, Observer {
            liked = true
            like_btn.setImageResource(R.mipmap.ic_like_select_btn_foreground)
            dismissLoadingDialog()
        })

        viewModel.likeResponse.observe(this, Observer {
            liked = false
            like_btn.setImageResource(R.mipmap.ic_like_btn_foreground)
            dismissLoadingDialog()
        })

        viewModel.scrapResponse.observe(this, Observer {
            scraped = false
            bookmark_btn.setImageResource(R.mipmap.ic_bookmark_bottom_foreground)
            dismissLoadingDialog()
        })

        viewModel.scrapResponse.observe(this, Observer {
            scraped = true
            bookmark_btn.setImageResource(R.mipmap.ic_bookmark_select_foreground)
            dismissLoadingDialog()
        })

        viewModel.deQuestionResponse.observe(this, Observer {
            dismissLoadingDialog()
            finish()
        })

        viewModel.declareResponse.observe(this, Observer {
            dismissLoadingDialog()
            onResume()
        })

        viewModel.erdeclareResponse.observe(this, Observer {
            Toast.makeText(this, "본인은 신고할 수 없습니다.", Toast.LENGTH_SHORT).show()
        })
    }

    override fun initAfterBinding() {
        viewModel.getUser()

        back_btn.setOnClickListener {
            finish()
        }

        answer_main.setOnClickListener {
            var intent = Intent(this, AnswerActivity::class.java)
            intent.putExtra("cafeQuestionId", cafeQuestionId)
            intent.putExtra("date", date)
            intent.putExtra("dday", dday)
            intent.putExtra("questioner", questioner)
            intent.putExtra("question", question)
            startActivity(intent)
        }

        bookmark_btn.setOnClickListener {
            if(scraped){
                viewModel.deleteScrap(cafeQuestionId)
                showLoadingDialog(this)
            }
            else{
                viewModel.postScrap(cafeQuestionId)
                showLoadingDialog(this)
            }
        }

        answerAdapter.setOnItemClickListener(object : AnswerAdapter.OnItemClickEventListener {
            override fun onItemClick(a_view: View?, a_position: Int) {
                var item = answerAdapter.getItem(a_position)
                var intent = Intent(this@DiaryActivity, ReplyActivity::class.java)
                intent.putExtra("commentId", item.commentId)
                intent.putExtra("cafeQuestionId", cafeQuestionId)
                intent.putExtra("date", date)
                intent.putExtra("dday", dday)
                intent.putExtra("questioner", questioner)
                intent.putExtra("question", question)
                startActivity(intent)
            }
        })

        answerAdapter.setOnItemClickListener2(object : AnswerAdapter.OnItemClickEventListener {
            override fun onItemClick(a_view: View?, a_position: Int) {
                var item = answerAdapter.getItem(a_position)

                val declareBottomSheet: DeclareBottomSheet = DeclareBottomSheet() {
                    when (it) {
                        // 신고하기
                        0 -> {
                            viewModel.declare(item.profileResponse.reportId)
                            val email = Intent(Intent.ACTION_SEND)
                            email.type = "plain/text"
                            val address = arrayOf("qnnect.app@gmail.com")
                            email.putExtra(Intent.EXTRA_EMAIL, address)
                            email.putExtra(Intent.EXTRA_SUBJECT, "큐넥트 글및 유저 신고")
                            email.putExtra(Intent.EXTRA_TEXT, "내용 미리보기 (미리적을 수 있음)")
                            email.putExtra(
                                Intent.EXTRA_TEXT,
                                String.format(
                                    "App Version : %s\nAndroid(SDK) : %d(%s)\n 유저 닉네임 : %s\n내용 : ",
                                    BuildConfig.VERSION_NAME,
                                    Build.VERSION.SDK_INT,
                                    Build.VERSION.RELEASE,
                                    item.profileResponse.nickName
                                )
                            )
                            startActivity(email)
                            showLoadingDialog(this@DiaryActivity)
                        }
                        // 차단하기
                        1 ->{
                            viewModel.declare(item.profileResponse.reportId)
                            showLoadingDialog(this@DiaryActivity)
                        }
                    }
                }
                declareBottomSheet.show(supportFragmentManager, declareBottomSheet.tag)
            }
        })

        like_btn.setOnClickListener {
            if(liked){
                viewModel.postLike(cafeQuestionId, !liked)
                showLoadingDialog(this)
            }
            else{
                viewModel.postLike(cafeQuestionId, !liked)
                showLoadingDialog(this)
            }
        }

        edit_btn.setOnClickListener {
            var intent = Intent(this, EditQuestionActivity::class.java)
            intent.putExtra("cafeQuestionId", cafeQuestionId)
            intent.putExtra("content", question)
            startActivity(intent)
        }

        delete_btn.setOnClickListener {
            val deleteDialog: DeleteReplyDialog = DeleteReplyDialog {
                when (it) {
                    // 삭제하기
                    1 -> {
                        viewModel.deleteScrap(cafeQuestionId)
                        showLoadingDialog(this)
                    }
                }
            }
            deleteDialog.show(supportFragmentManager, deleteDialog.tag)
        }
    }
}