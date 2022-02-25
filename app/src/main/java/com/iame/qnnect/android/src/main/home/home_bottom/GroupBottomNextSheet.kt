package com.iame.qnnect.android.src.main.home.home_bottom

import androidx.viewpager2.widget.ViewPager2
import com.iame.qnnect.android.R
import com.iame.qnnect.android.base.BaseFragment
import com.iame.qnnect.android.databinding.FragmentGroupBottomNextBinding
import com.iame.qnnect.android.viewmodel.GroupBottomNextViewModel
import kotlinx.android.synthetic.main.fragment_group_bottom_next.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class GroupBottomNextSheet(view: MainGroupBottomSheet, viewPager: ViewPager2) : BaseFragment<FragmentGroupBottomNextBinding, GroupBottomNextViewModel>(R.layout.fragment_group_bottom_next) {

    override val layoutResourceId: Int
        get() = R.layout.fragment_group_bottom_next // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel: GroupBottomNextViewModel by viewModel()
    var parents_view = view
    var viewPager = viewPager

    override fun initStartView() {
    }

    override fun initDataBinding() {
    }

    override fun initAfterBinding() {
        before_btn.setOnClickListener {
            viewPager.setCurrentItem(0, false)
        }
        next_btn.setOnClickListener {
            parents_view.dismiss()
        }
    }
}