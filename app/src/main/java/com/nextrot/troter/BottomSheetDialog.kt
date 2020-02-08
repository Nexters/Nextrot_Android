package com.nextrot.troter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

// TODO : 이 BottomSheet 는 여러가지 이슈로 우선 보류하고, visibility 로 처리하도록 한다..ㅠ
class BottomSheetDialog: BottomSheetDialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.bottom_sheet_dialog, container, false)
}