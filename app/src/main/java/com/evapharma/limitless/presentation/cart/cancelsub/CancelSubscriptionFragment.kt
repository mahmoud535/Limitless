package com.evapharma.limitless.presentation.cart.cancelsub

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.evapharma.limitless.databinding.FragmentCancelSubscriptionBinding
import com.evapharma.limitless.presentation.cart.CartViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CancelSubscriptionFragment : BottomSheetDialogFragment() {

    val viewModel: CartViewModel by viewModels(ownerProducer = {requireParentFragment()})

    private val binding: FragmentCancelSubscriptionBinding by lazy {
        FragmentCancelSubscriptionBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnKeepSub.setOnClickListener {
            // viewModel.updateProduct(addToCart)
            dismiss()
        }
        binding.confirmCancel.setOnClickListener {
            dismiss()
        }
    }

}