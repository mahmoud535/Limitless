package com.evapharma.limitless.presentation.history

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.evapharma.limitless.R
import com.evapharma.limitless.databinding.OrderHistoryItemBinding
import com.evapharma.limitless.domain.model.OrderHistory
import com.evapharma.limitless.presentation.util.DateToString

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    var orders:List<OrderHistory> = ArrayList<OrderHistory>()
    lateinit var context:Context

    class HistoryViewHolder(val binding: OrderHistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    fun submitList(orders:List<OrderHistory>){
        this.orders = orders
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        context = parent.context
        val binding =
            OrderHistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        if (orders.isNotEmpty()){

            orders[position].apply {
                holder.binding.apply {
                    cartOrderNumber.text = "Order #$orderNumber"
                    cartOrderDate.text = DateToString.convertToString(DateToString.convertToDate(date))
                    cartOrderItems.text = "3 Items"
                    if (orderStatus.lowercase() == "pending") setupOrderState(OrderStatus.PENDING, holder.binding)
                    if (orderStatus.lowercase() == "on going") setupOrderState(OrderStatus.ONGOING, holder.binding)
                    if (orderStatus.lowercase() == "success") setupOrderState(OrderStatus.SUCCESS, holder.binding)
                    cartTotalPrice.text = "Total: $currency $totalPrice"
                }
            }

        }
    }

    private fun setupOrderState(orderStatus: OrderStatus, binding:OrderHistoryItemBinding){
        when(orderStatus){

            OrderStatus.PENDING-> setPendingStatusBackground(binding)
            OrderStatus.ONGOING-> setOnGoingStatusBackground(binding)
            OrderStatus.SUCCESS-> setSuccessStatusBackground(binding)
        }
    }

    private fun setPendingStatusBackground(binding:OrderHistoryItemBinding){
        binding.apply {
            cartOrderState.text = "Pending"
            cartOrderState.background = ContextCompat.getDrawable(context, R.drawable.order_state_pending)
            cartOrderState.setTextColor(ContextCompat.getColor(context, android.R.color.holo_red_dark))
        }
    }

    private fun setOnGoingStatusBackground(binding:OrderHistoryItemBinding){
        binding.apply {
            cartOrderState.text = "On going"
            cartOrderState.background = ContextCompat.getDrawable(context, R.drawable.order_state_ongoing)
            cartOrderState.setTextColor(ContextCompat.getColor(context, R.color.brown))
        }
    }

    private fun setSuccessStatusBackground(binding:OrderHistoryItemBinding){
        binding.apply {
            cartOrderState.text = "Success"
            cartOrderState.background = ContextCompat.getDrawable(context, R.drawable.order_state_success)
            cartOrderState.setTextColor(ContextCompat.getColor(context, R.color.green))
        }
    }


    override fun getItemCount(): Int = orders.size
}