package com.evapharma.limitless.presentation.checkout.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.evapharma.limitless.databinding.FragmentCreditCardBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreditCardFragment : Fragment() {

    lateinit var binding: FragmentCreditCardBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreditCardBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.addNewCard.setOnClickListener {
            showBottomSheet()
        }

        binding.noCardBtn.setOnClickListener {
            showBottomSheet()
        }
    }


    private fun showBottomSheet() {
        AddCardFragment().show(childFragmentManager, "add_card_fragment")
    }

}