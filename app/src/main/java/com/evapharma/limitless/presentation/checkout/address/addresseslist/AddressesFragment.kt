package com.evapharma.limitless.presentation.checkout.address.addresseslist

import android.location.Address
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.evapharma.limitless.R
import com.evapharma.limitless.databinding.FragmentAddressesBinding
import com.evapharma.limitless.domain.model.AddressWithoutPhoneNumber
import com.evapharma.limitless.presentation.checkout.CheckoutActivity
import com.evapharma.limitless.presentation.checkout.CheckoutViewModel
import com.evapharma.limitless.presentation.checkout.address.bottomsheet.AddressBottomSheet
import com.evapharma.limitless.presentation.utils.hide
import com.evapharma.limitless.presentation.utils.show
import com.evapharma.limitless.presentation.utils.showSnackBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressesFragment : Fragment(), AddressBottomSheet.OnAddressAddedListener,
    AddressesAdapter.OnDefaultAddressSelected {

    private val binding by lazy { FragmentAddressesBinding.inflate(layoutInflater) }
    private val addressesAdapter: AddressesAdapter by lazy { AddressesAdapter(this) }
    private val viewModel: AddressesViewModel by viewModels()
    private val activityViewModel: CheckoutViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setUpViews()
        observeOnViewModel()
        return binding.root
    }

    private fun setUpViews() {
        (requireActivity() as CheckoutActivity).setFirstProcess()
        (requireActivity() as CheckoutActivity).unsetSecondProcess()
        setUpAddressesRecyclerView()
        setUpButtons()
        setUpContinueButton()
    }

    private fun setUpAddressesRecyclerView() {
        binding.addressesRecyclerView.adapter = addressesAdapter
    }

    private fun setUpButtons() {
        binding.addFirstAddressBtn.setOnClickListener { showAddressBottomSheet() }
        binding.addNewAddressBtn.setOnClickListener { showAddressBottomSheet() }
    }

    private fun showAddressBottomSheet() {
        AddressBottomSheet(this).show(
            requireActivity().supportFragmentManager,
            ""
        )
    }

    private fun setUpContinueButton() {
            binding.continueToPaymentButton.setOnClickListener {
                activityViewModel.selectedAddress?.let {
                    showAddressProgress()
                    viewModel.executeBillingAddress(it.id)
                    viewModel.addressLiveData.observe(viewLifecycleOwner){
                        hideAddressProgress()
                        findNavController().navigate(AddressesFragmentDirections.actionAddressesFragmentToPaymentFragment())
                    }
                }?: Toast.makeText(requireContext(), "Please Select Address", Toast.LENGTH_LONG).show()
            }
    }

    private fun showAddressProgress(){
        binding.addressProgress.show()
        binding.addressProgressBackground.show()
    }

    private fun hideAddressProgress(){
        binding.addressProgress.hide()
        binding.addressProgressBackground.hide()
    }

    private fun observeOnViewModel() {
        viewModel.apply {
            addressesList.observe(viewLifecycleOwner) {
                addressesAdapter.submitList(it)
            }
            anyAddressExist.observe(viewLifecycleOwner) {
                if (it) {
                    binding.addressesListGroup.show()
                    binding.emptyAddressesGroup.hide()
                    binding.continueToPaymentButton.isEnabled = true
                    binding.continueToPaymentButton.setBackgroundResource(R.drawable.background_checkout_button)

                } else {
                    binding.addressesListGroup.hide()
                    binding.emptyAddressesGroup.show()
                    binding.continueToPaymentButton.setBackgroundResource(R.drawable.background_checkout_out_btn_not_enabled)

                }
            }
        }
    }

    override fun onAddressAdded(resultMessage: String) {
        showSnackBar(binding.root, resultMessage, Snackbar.LENGTH_LONG)
        viewModel.getAddresses()
    }

    override fun onAddressSelected(address: AddressWithoutPhoneNumber) {
        activityViewModel.selectedAddress = address
    }


}