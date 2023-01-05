package com.evapharma.limitless.presentation.checkout.summary

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.evapharma.limitless.databinding.FragmentSummaryBinding
import com.evapharma.limitless.presentation.activity.OrderPlacedActivity
import com.evapharma.limitless.presentation.checkout.CheckoutActivity
import com.evapharma.limitless.presentation.checkout.CheckoutViewModel
import com.evapharma.limitless.presentation.utils.hide
import com.evapharma.limitless.presentation.utils.show
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlin.math.log

@AndroidEntryPoint
class SummaryFragment : Fragment() {

    lateinit var binding: FragmentSummaryBinding
    private val activityViewModel: CheckoutViewModel by activityViewModels()
    private val viewModel: SummaryViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSummaryBinding.inflate(layoutInflater)
        binding.address = activityViewModel.selectedAddress
        binding.summary = activityViewModel.summary
        (requireActivity() as CheckoutActivity).setThirdProcess()
        placeOrder()
        changeAddress()
        changePayment()
        return binding.root
    }

    fun changeAddress(){
        binding.changeAddress.setOnClickListener {
            (requireActivity() as CheckoutActivity).unsetThirdProcess()
            findNavController().navigate(SummaryFragmentDirections.actionSummaryFragmentToAddressesFragment())
        }
    }

    fun changePayment(){
        binding.changePayment.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun placeOrder(){
        binding.btnPlaceOrder.setOnClickListener {
            showSummaryProgress()
            viewModel.executeConfirmOrder()
            viewModel.summaryLiveData.observe(viewLifecycleOwner){
                hideSummaryProgress()
                startActivity(Intent(( requireActivity() as CheckoutActivity), OrderPlacedActivity::class.java))
                requireActivity().finish()
            }
        }
    }

    private fun showSummaryProgress(){
        binding.summaryProgress.show()
        binding.summaryProgressBackground.show()
    }

    private fun hideSummaryProgress(){
        binding.summaryProgress.hide()
        binding.summaryProgressBackground.hide()
    }


}