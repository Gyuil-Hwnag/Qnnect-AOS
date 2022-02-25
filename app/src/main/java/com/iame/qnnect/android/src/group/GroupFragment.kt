package com.iame.qnnect.android.src.group

import androidx.recyclerview.widget.RecyclerView
import com.iame.qnnect.android.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.viewpager2.widget.ViewPager2
import com.iame.qnnect.android.R
import com.iame.qnnect.android.databinding.FragmentGroupBinding
import com.iame.qnnect.android.src.main.home.GroupAdapter
import com.iame.qnnect.android.src.main.home.ViewPagerAdapter
import com.iame.qnnect.android.src.main.home.model.group_item
import com.iame.qnnect.android.src.main.home.model.question_item
import com.iame.qnnect.android.viewmodel.GroupViewModel
import kotlinx.android.synthetic.main.fragment_home.*


class GroupFragment : BaseFragment<FragmentGroupBinding, GroupViewModel>(R.layout.fragment_group) {

    override val layoutResourceId: Int
        get() = R.layout.fragment_group // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel: GroupViewModel by viewModel()

    var viewPager2: ViewPager2? = null
    var question_list = ArrayList<question_item>()

    lateinit var groupAdapter: GroupAdapter
    lateinit var groupRecyclerView: RecyclerView
    var group_list = ArrayList<group_item>()


    override fun initStartView() {
        var question_itme1 = question_item("D-7", "아아메팀", "오늘은 뭘먹을까요??")
        question_list.add(question_itme1)
        var question_itme2 = question_item("D-30", "틴틴팀", "저희는 죽기전에는 보나요??")
        question_list.add(question_itme2)
        var question_itme3 = question_item("D-90", "Light_One", "자연어 처리가 뭐에요...")
        question_list.add(question_itme3)
        var question_itme4 = question_item("D-7", "아아메", "빨리 끝내고 취업하고 싶어요!!")
        question_list.add(question_itme4)

        question_viewPager2.run {
            adapter = ViewPagerAdapter(question_list)
        }

        //인디케이터 타입1
        val dotsIndicator = dots_indicator
        dotsIndicator.setViewPager2(question_viewPager2)

        var group_item1 = group_item("아아메팀", "2022.02.19", "5명")
        group_list.add(group_item1)
        var group_item2 = group_item("틴틴팀", "2021.09.12", "5명")
        group_list.add(group_item2)
        var group_item3 = group_item("Light_One", "2021.07.01", "4명")
        group_list.add(group_item3)
        var group_item4 = group_item("아아메", "2022.02.05", "5명")
        group_list.add(group_item4)

//        group_recycler.run {
//            adapter = SubGroupAdapter(group_list)
//            layoutManager = LinearLayoutManager(context).apply {
//                orientation = LinearLayoutManager.HORIZONTAL
//            }
//            setHasFixedSize(true)
//        }


    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
//        add_group_btn.setOnClickListener {
//            val maingroupBottomSheet: MainGroupBottomSheet = MainGroupBottomSheet()
//            maingroupBottomSheet.show(requireActivity().supportFragmentManager, maingroupBottomSheet.tag)
//        }
    }

}