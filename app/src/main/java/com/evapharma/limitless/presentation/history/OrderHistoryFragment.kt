package com.evapharma.limitless.presentation.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.evapharma.limitless.databinding.FragmentOrderHistoryBinding
import com.evapharma.limitless.domain.model.OrderHistory
import com.evapharma.limitless.presentation.utils.hide
import com.evapharma.limitless.presentation.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderHistoryFragment : Fragment() {


    val controller: NavController by lazy { findNavController() }
    val viewModel:HistoryViewModel by viewModels()
    private val binding: FragmentOrderHistoryBinding by lazy { FragmentOrderHistoryBinding.inflate(layoutInflater)}
    private val historyAdapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.orderProgress.show()
        viewModel.refreshOrders()
        setupOrders()
        binding.orderHistoryToolbar.setNavigationOnClickListener { controller.popBackStack() }
    }

    private fun setupOrders(){
        viewModel.ordersLiveData.observe(viewLifecycleOwner){
            if (it.isEmpty())
                setEmptyOrders()
            else
                setOrders(it)
            binding.orderProgress.hide()
        }
    }

    private fun setEmptyOrders(){
        binding.noOrderGroup.show()
        binding.rvOrderHistory.hide()
        binding.noOrderExploreMore.setOnClickListener {
            controller.navigate(OrderHistoryFragmentDirections.actionOrderHistoryFragmentToHomeFragment())
        }
    }

    private fun setOrders(orders: List<OrderHistory>){
        binding.rvOrderHistory.show()
        binding.noOrderGroup.hide()
        setOrdersRV()
        historyAdapter.submitList(orders)
    }

    private fun setOrdersRV(){
        binding.rvOrderHistory.apply {
            setHasFixedSize(true)
            adapter = historyAdapter
        }
    }

}