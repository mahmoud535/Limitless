package com.evapharma.limitless.presentation.loginandsignup.signinusingmobilefragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.evapharma.limitless.R
import com.evapharma.limitless.data.util.ApiTokenSingleton
import com.evapharma.limitless.databinding.FragmentOTPBinding
import com.evapharma.limitless.presentation.MainActivity
import com.evapharma.limitless.presentation.checkout.CheckoutActivity
import com.evapharma.limitless.presentation.home.HomeFragment
import com.evapharma.limitless.presentation.util.CUSTOMER_ACCESS_TOKEN_KEY
import com.evapharma.limitless.presentation.utils.hide
import com.evapharma.limitless.presentation.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtpFragment : Fragment() {
    lateinit var viewModel: SignInViewModel
    private val args: OtpFragmentArgs by navArgs()
    private lateinit var binding: FragmentOTPBinding
    private val controller: NavController by lazy { findNavController() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOTPBinding.inflate(layoutInflater)

        setUpViews()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }


    private fun setUpViews() {
        inItViewModel()
    }

    private fun inItViewModel() {

        viewModel = ViewModelProvider(this)[SignInViewModel::class.java]
        binding.btnLogin.setOnClickListener {
            binding.btnLogin.visibility = View.INVISIBLE
            binding.progressBar.show()
            viewModel.verifyOtp("Bearer ${args.accessToken}").observe(viewLifecycleOwner) {
                if (it == null) {
                    binding.progressBar.hide()
                    binding.btnLogin.show()
                    Toast.makeText(requireContext(), "Invalid key ", Toast.LENGTH_LONG).show()
                } else {
                    println("Token ${it.data.accessToken}")
                    ApiTokenSingleton.API_TOKEN = it.data.accessToken
                    viewModel.putString.execute(CUSTOMER_ACCESS_TOKEN_KEY, it.data.accessToken)
                    Toast.makeText(requireContext(), "Successfully created User", Toast.LENGTH_LONG)
                        .show()
                    requireActivity().finish()
                }
            }
        }
    }

}
