package com.iame.qnnect.android.di

import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.Observer
import com.iame.qnnect.android.MainSearchRecyclerViewAdapter
import com.iame.qnnect.android.MyApplication
import com.iame.qnnect.android.MyConstant.Companion.BASE_URL
import com.iame.qnnect.android.base.NullOnEmptyConverterFactory
import com.iame.qnnect.android.base.XAccessTokenInterceptor
import com.iame.qnnect.android.model.DataModel
import com.iame.qnnect.android.model.DataModelImpl
import com.iame.qnnect.android.model.service.KakaoSearchService
import com.iame.qnnect.android.src.allow.model.AlarmCheckDataModel
import com.iame.qnnect.android.src.allow.service.AlarmCheckAPI
import com.iame.qnnect.android.src.allow.service.AlarmCheckDataImpl
import com.iame.qnnect.android.src.answer.model.PostAnswerDataModel
import com.iame.qnnect.android.src.answer.service.PostAnswerAPI
import com.iame.qnnect.android.src.answer.service.PostAnswerDataImpl
import com.iame.qnnect.android.src.diary.AnswerAdapter
import com.iame.qnnect.android.src.diary.model.DeleteScrapDataModel
import com.iame.qnnect.android.src.diary.model.GetQuestionDataModel
import com.iame.qnnect.android.src.diary.model.PostLikeDataModel
import com.iame.qnnect.android.src.diary.model.PostScrapDataModel
import com.iame.qnnect.android.src.diary.service.*
import com.iame.qnnect.android.src.group.member.GroupMemberAdapter
import com.iame.qnnect.android.src.group.model.GroupDataModel
import com.iame.qnnect.android.src.group.question.GroupQuestionViewPagerAdapter
import com.iame.qnnect.android.src.group.service.GroupAPI
import com.iame.qnnect.android.src.group.service.GroupDataImpl
import com.iame.qnnect.android.src.login.LoginActivity
import com.iame.qnnect.android.src.login.model.LoginDataModel
import com.iame.qnnect.android.src.login.service.LoginAPI
import com.iame.qnnect.android.src.login.service.LoginDataImpl
import com.iame.qnnect.android.src.main.MainActivity
import com.iame.qnnect.android.src.main.bookmark.GroupnameAdapter
import com.iame.qnnect.android.src.main.bookmark.QuestionListAdapter
import com.iame.qnnect.android.src.main.bookmark.model.BookmarkAllDataModel
import com.iame.qnnect.android.src.main.bookmark.model.BookmarkListDataModel
import com.iame.qnnect.android.src.main.bookmark.model.CafeListDataModel
import com.iame.qnnect.android.src.main.bookmark.service.*
import com.iame.qnnect.android.src.main.home.GroupAdapter
import com.iame.qnnect.android.src.main.home.QuestionRecyclerViewAdapter
import com.iame.qnnect.android.src.main.home.home_model.HomeDataModel
import com.iame.qnnect.android.src.main.home.home_service.HomeAPI
import com.iame.qnnect.android.src.main.home.home_service.HomeDataImpl
import com.iame.qnnect.android.src.main.home.model.UserDataModel
import com.iame.qnnect.android.src.main.home.service.UserAPI
import com.iame.qnnect.android.src.main.home.service.UserDataImpl
import com.iame.qnnect.android.src.main.store.RecipeAdapter
import com.iame.qnnect.android.src.profile.model.ProfileDataModel
import com.iame.qnnect.android.src.profile.model.ProfileDefaultDataModel
import com.iame.qnnect.android.src.profile.service.ProfileAPI
import com.iame.qnnect.android.src.profile.service.ProfileDataImpl
import com.iame.qnnect.android.src.profile.service.ProfileDefaultAPI
import com.iame.qnnect.android.src.profile.service.ProfileDefaultDataImpl
import com.iame.qnnect.android.src.question.model.PostQuestionDataModel
import com.iame.qnnect.android.src.question.service.PostQuestionAPI
import com.iame.qnnect.android.src.question.service.PostQuestionDataImpl
import com.iame.qnnect.android.src.reply.ImageAdapter
import com.iame.qnnect.android.src.reply.ReplyAdapter
import com.iame.qnnect.android.src.reply.model.GetReplyDataModel
import com.iame.qnnect.android.src.reply.model.PostReplyDataModel
import com.iame.qnnect.android.src.reply.reply_more.model.EditReplyDataModel
import com.iame.qnnect.android.src.reply.reply_more.service.EditReplyAPI
import com.iame.qnnect.android.src.reply.reply_more.service.EditReplyDataImpl
import com.iame.qnnect.android.src.reply.service.GetReplyAPI
import com.iame.qnnect.android.src.reply.service.GetReplyDataImpl
import com.iame.qnnect.android.src.reply.service.PostReplyAPI
import com.iame.qnnect.android.src.reply.service.PostReplyDataImpl
import com.iame.qnnect.android.src.search.model.SearchDataModel
import com.iame.qnnect.android.src.search.service.SearchAPI
import com.iame.qnnect.android.src.search.service.SearchDataImpl
import com.iame.qnnect.android.src.splash.model.PostRefreshRequest
import com.iame.qnnect.android.src.splash.model.PostRefreshResponse
import com.iame.qnnect.android.src.splash.model.RefreshDataModel
import com.iame.qnnect.android.src.splash.service.RefreshAPI
import com.iame.qnnect.android.src.splash.service.RefreshDataImpl
import com.iame.qnnect.android.viewmodel.*
import io.reactivex.Single
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * MyModule.kt
 */

/*
   * bearer 토큰 필요한 api 사용시 accessToken유효한지 검사
   * 유효하지 않다면 재발급 api 호출
   * refreshToken이 유효하다면 정상적으로 accessToken재발급 후 기존 api 동작 완료
   *
*/
class BearerInterceptor: Interceptor {
    //todo 조건 분기로 인터셉터 구조 변경
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var accessToken = MyApplication.sSharedPreferences.getString("X-ACCESS-TOKEN", null)
        var refreshToken = MyApplication.sSharedPreferences.getString("refresh-token", null)
        val response = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RefreshAPI::class.java)

        var request = PostRefreshRequest(accessToken!!, refreshToken!!)
        var result = response.postRefresh(request)

        var editor = MyApplication.editor
        editor.putString("X-ACCESS-TOKEN", result.blockingGet().accessToken)
        editor.putString("refresh-token", result.blockingGet().refreshToken)
        editor.commit()

        accessToken = result.blockingGet().accessToken

        val newRequest = chain.request().newBuilder().addHeader("Authorization", "Bearer ${accessToken}")
            .build()
        return chain.proceed(newRequest)
    }
}

val client: OkHttpClient = OkHttpClient.Builder()
    .readTimeout(5000, TimeUnit.MILLISECONDS)
    .connectTimeout(5000, TimeUnit.MILLISECONDS)
    // 로그캣에 okhttp.OkHttpClient로 검색하면 http 통신 내용을 보여줍니다.
    .addInterceptor(BearerInterceptor())
    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    .addNetworkInterceptor(XAccessTokenInterceptor()) // JWT 자동 헤더 전송
    .build()

var retrofitPart = module {
    single<KakaoSearchService> {
        Retrofit.Builder()
            .baseUrl("https://dapi.kakao.com")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KakaoSearchService::class.java)
    }
    single<LoginAPI> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LoginAPI::class.java)
    }
    single<RefreshAPI> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RefreshAPI::class.java)
    }
    single<AlarmCheckAPI> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AlarmCheckAPI::class.java)
    }
    single<ProfileDefaultAPI> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProfileDefaultAPI::class.java)
    }
    single<ProfileAPI> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProfileAPI::class.java)
    }
    single<UserAPI> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserAPI::class.java)
    }
    single<GroupAPI> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GroupAPI::class.java)
    }
    single<HomeAPI> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HomeAPI::class.java)
    }
    single<CafeListAPI> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CafeListAPI::class.java)
    }
    single<BookmarkListAPI> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BookmarkListAPI::class.java)
    }
    single<PostQuestionAPI> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostQuestionAPI::class.java)
    }
    single<SearchAPI> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SearchAPI::class.java)
    }
    single<BookmarkAllAPI> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BookmarkAllAPI::class.java)
    }
    single<PostScrapAPI> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostScrapAPI::class.java)
    }
    single<DeleteScrapAPI> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DeleteScrapAPI::class.java)
    }
    single<PostLikeAPI> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostLikeAPI::class.java)
    }
    single<GetQuestionAPI> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GetQuestionAPI::class.java)
    }
    single<PostAnswerAPI> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostAnswerAPI::class.java)
    }
    single<GetReplyAPI> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GetReplyAPI::class.java)
    }
    // text/plain 방식
    single<PostReplyAPI> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostReplyAPI::class.java)
    }
    // text/plain 방식
    single<EditReplyAPI> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EditReplyAPI::class.java)
    }
}

var adapterPart = module {
    factory {
        MainSearchRecyclerViewAdapter()
    }
    factory {
        GroupQuestionViewPagerAdapter()
    }
    factory {
        GroupMemberAdapter()
    }
    factory {
        RecipeAdapter()
    }
    factory {
        QuestionRecyclerViewAdapter()
    }
    factory {
        GroupAdapter()
    }
    factory {
        GroupnameAdapter()
    }
    factory {
        QuestionListAdapter()
    }
    factory {
        AnswerAdapter()
    }
    factory {
        ReplyAdapter()
    }
    factory {
        ImageAdapter()
    }
}

var modelPart = module {
    factory<DataModel> {
        DataModelImpl(get())
    }
    factory<LoginDataModel> {
        LoginDataImpl(get())
    }
    factory<RefreshDataModel> {
        RefreshDataImpl(get())
    }
    factory<AlarmCheckDataModel> {
        AlarmCheckDataImpl(get())
    }
    factory<ProfileDataModel> {
        ProfileDataImpl(get())
    }
    factory<ProfileDefaultDataModel> {
        ProfileDefaultDataImpl(get())
    }
    factory<UserDataModel> {
        UserDataImpl(get())
    }
    factory<GroupDataModel> {
        GroupDataImpl(get())
    }
    factory<HomeDataModel> {
        HomeDataImpl(get())
    }
    factory<CafeListDataModel> {
        CafeListDataImpl(get())
    }
    factory<BookmarkListDataModel> {
        BookmarkListDataImpl(get())
    }
    factory<PostQuestionDataModel> {
        PostQuestionDataImpl(get())
    }
    factory<SearchDataModel> {
        SearchDataImpl(get())
    }
    factory<BookmarkAllDataModel> {
        BookmarkAllDataImpl(get())
    }
    factory<PostScrapDataModel> {
        PostScrapDataImpl(get())
    }
    factory<DeleteScrapDataModel> {
        DeleteScrapDataImpl(get())
    }
    factory<PostLikeDataModel> {
        PostLikeDataImpl(get())
    }
    factory<GetQuestionDataModel> {
        GetQuestionDataImpl(get())
    }
    factory<PostAnswerDataModel> {
        PostAnswerDataImpl(get())
    }
    factory<GetReplyDataModel> {
        GetReplyDataImpl(get())
    }
    factory<PostReplyDataModel> {
        PostReplyDataImpl(get())
    }
    factory<EditReplyDataModel> {
        EditReplyDataImpl(get())
    }
}

var viewModelPart = module {
    viewModel { MainViewModel(get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { SplashViewModel(get()) }
    viewModel { AllowViewModel(get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { EditProfileViewModel(get(), get(), get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { MypageViewModel(get()) }
    viewModel { GroupViewModel(get()) }
    viewModel { BookmarkViewModel(get(), get(), get())}
    viewModel { QuestionViewModel(get())}
    viewModel { SearchViewModel(get())}
    viewModel { DiaryViewModel(get(), get(), get(), get(), get())}
    viewModel { AnswerViewModel(get(), get()) }
    viewModel { QuestionListViewModel(get())}
    viewModel { ReplyViewModel(get(), get()) }
    viewModel { EditReplyViewModel(get()) }

    viewModel { StoreViewModel() }
    viewModel { StoreActivityViewModel() }
    viewModel { MyMaterialViewModel() }
    viewModel { RecipeViewModel() }
    viewModel { DrinkViewModel() }
    viewModel { EditDrinkViewModel() }
    viewModel { OnboardViewModel() }
    viewModel { EditQuestionViewModel() }
}

var myDiModule = listOf(retrofitPart, adapterPart, modelPart, viewModelPart)