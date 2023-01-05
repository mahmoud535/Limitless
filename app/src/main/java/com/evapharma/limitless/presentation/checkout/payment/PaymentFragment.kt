package com.evapharma.limitless.presentation.checkout.payment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.evapharma.limitless.databinding.FragmentPaymentBinding
import com.evapharma.limitless.presentation.checkout.CheckoutActivity
import com.evapharma.limitless.presentation.checkout.CheckoutViewModel
import com.evapharma.limitless.presentation.util.PaymentMethod
import com.evapharma.limitless.presentation.util.SUMMARY_KEY
import com.evapharma.limitless.presentation.utils.hide
import com.evapharma.limitless.presentation.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PaymentFragment : Fragment() {

    lateinit var binding: FragmentPaymentBinding
    lateinit var manager: FragmentManager
    val viewModel: PaymentViewModel by viewModels()
    private val activityViewModel: CheckoutViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentBinding.inflate(layoutInflater)
        manager = childFragmentManager
        (requireActivity() as CheckoutActivity).setSecondProcess()
        (requireActivity() as CheckoutActivity).unsetThirdProcess()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()

        binding.radioCreditCard.setOnClickListener {
            if (viewModel.paymentMethod == PaymentMethod.CREDIT_CARD) {
                showFragment(viewModel.paymentMethod)
                binding.btnToSummary.hide()
                viewModel.paymentMethod = PaymentMethod.CASH_ON_DELIVERY
            }
        }
        binding.radioCashOnDelivery.setOnClickListener {
            if (viewModel.paymentMethod == PaymentMethod.CASH_ON_DELIVERY) {
                showFragment(viewModel.paymentMethod)
                binding.btnToSummary.show()
                viewModel.paymentMethod = PaymentMethod.CREDIT_CARD
            }
        }
    }


    private fun setupViews() {
        showFragment(PaymentMethod.CASH_ON_DELIVERY)
        setUpContinueToSummaryButton()
        viewModel.paymentMethod = PaymentMethod.CREDIT_CARD
    }

    private fun setUpContinueToSummaryButton() {
        binding.btnToSummary.setOnClickListener {
            showPaymentProgress()
            viewModel.executePaymentMethod()
            viewModel.paymentLiveData.observe(viewLifecycleOwner){
                hidePaymentProgress()
                findNavController().navigate(
                    PaymentFragmentDirections.actionPaymentFragmentToSummaryFragment()
                )
            }
        }
    }

    private fun showPaymentProgress(){
        binding.paymentProgress.show()
        binding.paymentProgressBackground.show()
    }

    private fun hidePaymentProgress(){
        binding.paymentProgress.hide()
        binding.paymentProgressBackground.hide()
    }

    private fun showFragment(method: PaymentMethod) {
        when (method) {
            PaymentMethod.CASH_ON_DELIVERY -> loadMethodFragment(
                OnDeliveryFragment::class.java,
                null
            )
            PaymentMethod.CREDIT_CARD -> loadMethodFragment(null, CreditCardFragment::class.java)
        }
    }

    private fun loadMethodFragment(
        onDelivery: Class<OnDeliveryFragment>?,
        card: Class<CreditCardFragment>?
    ) {
        val bundle = Bundle().apply { putParcelable(SUMMARY_KEY, activityViewModel.summary) }

        manager.beginTransaction()
            .replace(binding.paymentFragmentContainer.id, onDelivery ?: card!!, bundle)
            .setReorderingAllowed(true)
            .commit()
    }
}