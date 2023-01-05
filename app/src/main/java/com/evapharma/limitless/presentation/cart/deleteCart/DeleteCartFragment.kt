package com.evapharma.limitless.presentation.cart.deleteCart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.evapharma.limitless.databinding.FragmentDeleteCartsBinding
import com.evapharma.limitless.presentation.cart.CartViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteCartFragment : DialogFragment() {

    val viewModel:CartViewModel by viewModels(ownerProducer = {requireParentFragment()})

    private val binding: FragmentDeleteCartsBinding by lazy {
        FragmentDeleteCartsBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnDeleteCarts.setOnClickListener {
            viewModel.clearCart()
            dismiss()
        }
        binding.btnCancelDeleteCarts.setOnClickListener { dismiss() }
    }
}