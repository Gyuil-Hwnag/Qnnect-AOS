package com.iame.qnnect.android.src.store

import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iame.qnnect.android.R
import com.iame.qnnect.android.base.BaseActivity
import com.iame.qnnect.android.databinding.*
import com.iame.qnnect.android.src.main.store.RecipeAdapter
import com.iame.qnnect.android.src.main.store.RecipeDialog
import com.iame.qnnect.android.util.*
import com.iame.qnnect.android.viewmodel.MyMaterialViewModel
import kotlinx.android.synthetic.main.activity_mymaterial.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyMaterialActivity : BaseActivity<ActivityMymaterialBinding, MyMaterialViewModel>(){

    override val layoutResourceId: Int
        get() = R.layout.activity_mymaterial // get() : 커스텀 접근자, 코틀린 문법

    override val viewModel: MyMaterialViewModel by viewModel()

    private val recipeAdapter: MaterialAdapter by inject()

    var recipe_list = ArrayList<recipe>()

    override fun initStartView() {
        // member recycler
        recipe_recycler.run {
            adapter = recipeAdapter
            layoutManager = GridLayoutManager(context, 2).apply {
                orientation = GridLayoutManager.VERTICAL
            }
            setHasFixedSize(true)
        }
    }

    override fun initDataBinding() {
        // 전체 나의 재료
        viewModel.mymaterialAllResponse.observe(this, Observer {
            recipeAdapter.clear()
            if(it.isEmpty()){
                empty_img.visibility = View.VISIBLE
                empty_txt.visibility = View.VISIBLE
            }
            else{
                empty_img.visibility = View.GONE
                empty_txt.visibility = View.GONE
            }
            it.forEach { item ->
                recipeAdapter.addItem(item)
            }
            recipeAdapter.notifyDataSetChanged()
        })

        // 부분별 나의 재료
        viewModel.mymaterialResponse.observe(this, Observer {
            recipeAdapter.clear()

            if(it.isEmpty()){
                empty_img.visibility = View.VISIBLE
                empty_txt.visibility = View.VISIBLE
            }
            else{
                empty_img.visibility = View.GONE
                empty_txt.visibility = View.GONE
            }
            it.forEach { item ->
                recipeAdapter.addItem(item)
            }
            recipeAdapter.notifyDataSetChanged()
        })

    }

    override fun initAfterBinding() {
        viewModel.getMyMaterialAll()

        back_btn.setOnClickListener {
            finish()
        }

        all_btn.setOnClickListener {
            viewModel.recipe_click(all_btn, base_btn, main_btn, topping_btn)
            viewModel.getMyMaterialAll()
        }

        base_btn.setOnClickListener {
            viewModel.recipe_click(base_btn, all_btn, main_btn, topping_btn)
            viewModel.getMyMaterial("ice_base")
        }

        main_btn.setOnClickListener {
            viewModel.recipe_click(main_btn, base_btn, all_btn, topping_btn)
            viewModel.getMyMaterial("main")
        }

        topping_btn.setOnClickListener {
            viewModel.recipe_click(topping_btn, base_btn, main_btn, all_btn)
            viewModel.getMyMaterial("topping")
        }

        recipe_recycler.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                // 최하단
                if (!recipe_recycler.canScrollVertically(-1)) {
                    scrollto_btn.visibility = View.GONE
                }
                // 최상단
                else if (!recipe_recycler.canScrollVertically(1)) {
                    scrollto_btn.visibility = View.GONE
                }
                else {
                    scrollto_btn.visibility = View.VISIBLE
                }
            }
        })
    }
}