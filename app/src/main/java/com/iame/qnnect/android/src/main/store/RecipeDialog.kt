package com.iame.qnnect.android.src.main.store

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.iame.qnnect.android.R
import com.iame.qnnect.android.util.recipe

class RecipeDialog(val item: recipe, val itemClick: (Int) -> Unit) : DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //false로 설정해 주면 화면밖 혹은 뒤로가기 버튼시 다이얼로그라 dismiss 되지 않는다.
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.recipe_dialog, container,false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var recipe_img = view!!.findViewById<ImageView>(R.id.recipe_img)
        Glide.with(context!!)
            .load(item.img)
            .transform(CenterCrop())
            .into(recipe_img)

        var recipe_name = view!!.findViewById<TextView>(R.id.recipe_name)
        recipe_name.text = item.point.toString()+"P "+item.name+"를\n"+"구매하시겠나요?"

        var close_btn = view!!.findViewById<ImageView>(R.id.close_btn)
        close_btn.setOnClickListener {
            dismiss()
        }

        var ok_btn = view!!.findViewById<TextView>(R.id.ok_btn)
        ok_btn.setOnClickListener {
            dismiss()
            itemClick(1)
        }

    }
    override fun onResume() {
        super.onResume()
        val windowManager = context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x
        params?.width = (deviceWidth * 0.8).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }
}