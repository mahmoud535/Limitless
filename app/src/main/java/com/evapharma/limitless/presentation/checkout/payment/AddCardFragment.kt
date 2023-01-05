package com.evapharma.limitless.presentation.checkout.payment

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.hardware.display.DisplayManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.getSystemService
import com.evapharma.limitless.R
import com.evapharma.limitless.databinding.FragmentAddCardBinding
import com.evapharma.limitless.databinding.NumberPickerBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class AddCardFragment : BottomSheetDialogFragment() {
    lateinit var binding: FragmentAddCardBinding
    lateinit var numberBinding:NumberPickerBinding
    lateinit var alertDialog: AlertDialog

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddCardBinding.inflate(layoutInflater)
        numberBinding =
            NumberPickerBinding.inflate(LayoutInflater.from(requireContext()), null, false)
        alertDialog = AlertDialog.Builder(requireContext()).setView(numberBinding.root).create()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.cancelAddCard.setOnClickListener { dismiss() }
        binding.bottomSheetAddCard.setOnClickListener {
            dismiss()
        }

        binding.expireYear.setOnClickListener {
            showYearDialog()
        }

        binding.expireMonth.setOnClickListener {
            showMonthDialog()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            setFullScreen(it as BottomSheetDialog)
        }

        return dialog
    }

    private fun setFullScreen(bottomSheetDialog: BottomSheetDialog) {

        val sheet: ConstraintLayout? = bottomSheetDialog.findViewById(R.id.bottom_sheet)
        val behavior = BottomSheetBehavior.from(sheet!!)
        val layoutParams = sheet.layoutParams

        val windowHeight = getWindowHeight()
        if (layoutParams != null) {
            layoutParams.height = windowHeight
        }
        sheet.layoutParams = layoutParams
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (requireActivity()).getSystemService<DisplayManager>()
            ?.getDisplay(Display.DEFAULT_DISPLAY)?.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    private fun showYearDialog() {
        val minYear = Calendar.getInstance().get(Calendar.YEAR)
        val maxYear = minYear + 20
        numberBinding.numberPickerTitle.text = "Year"
        numberBinding.cardNumPicker.minValue = minYear
        numberBinding.cardNumPicker.maxValue = maxYear
        alertDialog.show()
        numberBinding.numberPickerOk.setOnClickListener {

        }
        numberBinding.numberPickerCancel.setOnClickListener {
            alertDialog.dismiss()
        }
        alertDialog.window?.setLayout(
            resources.getDimension(com.intuit.sdp.R.dimen._140sdp).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun showMonthDialog() {
        val month = Calendar.getInstance().get(Calendar.MONTH)
        numberBinding.numberPickerTitle.text = "Month"
        numberBinding.cardNumPicker.minValue = 1
        numberBinding.cardNumPicker.maxValue = 12
        numberBinding.cardNumPicker.value = month
        alertDialog.show()
        alertDialog.window?.setLayout(
            resources.getDimension(com.intuit.sdp.R.dimen._140sdp).toInt(),
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        numberBinding.numberPickerOk.setOnClickListener {

        }
        numberBinding.numberPickerCancel.setOnClickListener {
            alertDialog.dismiss()
        }
    }


}