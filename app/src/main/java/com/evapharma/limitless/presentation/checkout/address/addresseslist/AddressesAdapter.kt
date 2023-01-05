package com.evapharma.limitless.presentation.checkout.address.addresseslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.evapharma.limitless.R
import com.evapharma.limitless.databinding.ItemAddressBinding
import com.evapharma.limitless.domain.model.Address
import com.evapharma.limitless.domain.model.AddressWithoutPhoneNumber
import okhttp3.internal.notify


class AddressesAdapter(private val onDefaultAddressSelected: OnDefaultAddressSelected) :
    ListAdapter<AddressWithoutPhoneNumber, AddressesAdapter.MyViewHolder>(AddressDiffUtils()) {

    class MyViewHolder(private val binding: ItemAddressBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(address: AddressWithoutPhoneNumber) {
            binding.address = address
            if (address.isDefault == true) {
                binding.addressLayout.setBackgroundResource(R.drawable.background_address_selected)
                binding.radioBtn.isChecked = true
            } else {
                binding.addressLayout.setBackgroundResource(R.drawable.background_address)
                binding.radioBtn.isChecked = false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemAddressBinding.inflate(inflater, parent, false)
        val holder = MyViewHolder(binding)

        binding.root.setOnClickListener {
            val item = getItem(holder.adapterPosition)
            binding.addressLayout.setBackgroundResource(R.drawable.background_address_selected)
            binding.radioBtn.isChecked = true
            item.isDefault = true
            currentList.filter { it.addressName != item.addressName && it.isDefault == true }
                .forEach { it.isDefault = false }
            onDefaultAddressSelected.onAddressSelected(item)
            notifyDataSetChanged()
        }
        return holder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    interface OnDefaultAddressSelected {
        fun onAddressSelected (address: AddressWithoutPhoneNumber)
    }

}