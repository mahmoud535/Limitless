package com.evapharma.limitless.presentation.loginandsignup.signinusingmobilefragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.evapharma.limitless.databinding.FragmentSigninUsingMobileNumberBinding
import com.evapharma.limitless.domain.model.LoginModel
import com.evapharma.limitless.presentation.utils.hide
import com.evapharma.limitless.presentation.utils.show
import com.evapharma.limitless.presentation.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SigninUsingMobileNumberFragment : Fragment() {
    private lateinit var binding: FragmentSigninUsingMobileNumberBinding
    lateinit var viewModel: SignInViewModel
    var bundle = Bundle()
    private val controller: NavController by lazy { findNavController() }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSigninUsingMobileNumberBinding.inflate(layoutInflater)
        initViewModel()
        onClick()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.backButton.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun onClick() {
        binding.btnLogin.setOnClickListener {
            val phone = binding.phoneNumberEditText.text.toString()
            if (
                phone.isNotEmpty() && (phone.length == 11 || phone.length == 10)
            ) {
                showProgress()
                createUser()
            } else {
                showSnackBar(binding.root, "Enter valid phone number!")
                return@setOnClickListener
            }
        }
    }

    private fun createUser() {
        val user = LoginModel(
            binding.countryCodePicker.selectedCountryCode + binding.phoneNumberEditText.text.toString(),
            false,
            ""
        )
        viewModel.executeNewUser(user)

    }

    fun showProgress() {
        binding.progressBackground.show()
        binding.loginProgress.show()
    }

    fun hideProgress() {
        binding.progressBackground.hide()
        binding.loginProgress.hide()
    }

    private fun initViewModel() {

        viewModel = ViewModelProvider(this)[SignInViewModel::class.java]

        viewModel.userLiveData.observe(viewLifecycleOwner, Observer<LoginModel?> {

            val direction =
                SigninUsingMobileNumberFragmentDirections.actionSigninUsingMobileNumberFragment2ToOtpFragment(
                    it.accessToken
                )
            Log.d("TAG", "${it.accessToken}")
            binding.cartProgress.visibility = View.VISIBLE
            if (it == null) {
                Toast.makeText(requireContext(), "Failed to Add Number", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Successfully Added Number ${binding.phoneNumberEditText.text}",
                    Toast.LENGTH_LONG
                ).show()
                hideProgress()
                findNavController().navigate(direction)
                binding.cartProgress.visibility = View.GONE
            }
        })
    }
}