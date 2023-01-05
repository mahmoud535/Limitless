package com.evapharma.limitless.presentation.loginandsignup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.evapharma.limitless.R
import com.evapharma.limitless.databinding.FragmentLoginandSignupBinding
import com.evapharma.limitless.presentation.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginandSignupFragment : Fragment() {

    private lateinit var binding: FragmentLoginandSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentLoginandSignupBinding.inflate(layoutInflater)
        setListeners()
    return binding.root
    }

    private fun setListeners() {
                binding.btnLoginHome.setOnClickListener {
                    val direction = LoginandSignupFragmentDirections.actionLoginandSignupFragment2ToSigninUsingMobileNumberFragment2()
                    findNavController().navigate(direction)

            }
    }

}