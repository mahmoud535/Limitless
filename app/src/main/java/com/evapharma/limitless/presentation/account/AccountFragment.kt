package com.evapharma.limitless.presentation.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.evapharma.limitless.R
import com.evapharma.limitless.databinding.FragmentAccountBinding


class AccountFragment : Fragment() {
    private lateinit var binding: FragmentAccountBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        binding = FragmentAccountBinding.inflate(layoutInflater)
        onClick()

        return binding.root
    }

    private fun onClick(){
        binding.PersonalInformation.setOnClickListener { findNavController().navigate(R.id.action_accountFragment_to_personalInformationFragment) }
        binding.orderHistory.setOnClickListener { findNavController().navigate(AccountFragmentDirections.actionAccountFragmentToOrderHistoryFragment()) }
    }

}