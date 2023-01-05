package com.evapharma.limitless.presentation.checkout.payment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.evapharma.limitless.databinding.CardItemBinding

class CreditCardAdapter : RecyclerView.Adapter<CreditCardAdapter.CreditCardViewHolder>(){

    class CreditCardViewHolder(binding: CardItemBinding) : RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreditCardViewHolder {
        val binding = CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CreditCardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CreditCardViewHolder, position: Int) {
    }

    override fun getItemCount(): Int = 0
}