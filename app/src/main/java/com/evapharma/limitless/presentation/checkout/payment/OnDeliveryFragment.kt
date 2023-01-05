package com.evapharma.limitless.presentation.checkout.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.evapharma.limitless.R
import com.evapharma.limitless.databinding.FragmentOnDeliveryBinding
import com.evapharma.limitless.domain.model.Summary
import com.evapharma.limitless.presentation.checkout.CheckoutViewModel
import com.evapharma.limitless.presentation.util.SUMMARY_KEY
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnDeliveryFragment : Fragment() {

    lateinit var binding: FragmentOnDeliveryBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
        binding = FragmentOnDeliveryBinding.inflate(layoutInflater)
        binding.summary = requireArguments().getParcelable<Summary>(SUMMARY_KEY)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }
}