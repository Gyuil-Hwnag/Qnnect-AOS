package com.iame.qnnect.android.src.profile

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.iame.qnnect.android.R

class EditImageSheet(val itemClick: (Int) -> Unit) :
    BottomSheetDialogFragment(){
    private lateinit var dlg : BottomSheetDialog

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // 이 코드를 실행하지 않으면
        // XML에서 round 처리를 했어도 적용되지 않는다.
        dlg = ( super.onCreateDialog(savedInstanceState).apply {
            // window?.setDimAmount(0.2f) // Set dim amount here
            setOnShowListener {
                val bottomSheet = findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
                bottomSheet.setBackgroundResource(android.R.color.transparent)

                // 아래와 같이하면 Drag를 불가능하게 한다.
                //val behavior = BottomSheetBehavior.from(bottomSheet!!)
                //behavior.isDraggable = false
            }
        } ) as BottomSheetDialog
        return dlg
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_edit_image, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // default_img
        view?.findViewById<TextView>(R.id.default_img)?.setOnClickListener {
            itemClick(0)
            dismiss()
        }

        // gallery_img
        view?.findViewById<TextView>(R.id.gllery_img)?.setOnClickListener {
            itemClick(1)
            dismiss()
        }



//        view?.findViewById<TextView>(R.id.no_btn)?.setOnClickListener {
//            itemClick(1)
//            dismiss()
//        }
    }
}