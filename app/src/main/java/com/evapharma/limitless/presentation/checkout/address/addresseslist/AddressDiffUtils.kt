package com.evapharma.limitless.presentation.checkout.address.addresseslist

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.evapharma.limitless.domain.model.Address
import com.evapharma.limitless.domain.model.AddressWithoutPhoneNumber

class AddressDiffUtils : DiffUtil.ItemCallback<AddressWithoutPhoneNumber>() {
    override fun areItemsTheSame(oldItem: AddressWithoutPhoneNumber, newItem: AddressWithoutPhoneNumber): Boolean = oldItem.addressName == newItem.addressName

    override fun areContentsTheSame(oldItem: AddressWithoutPhoneNumber, newItem: AddressWithoutPhoneNumber): Boolean = oldItem == newItem
}