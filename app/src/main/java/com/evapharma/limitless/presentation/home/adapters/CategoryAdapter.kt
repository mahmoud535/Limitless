package com.evapharma.limitless.presentation.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.evapharma.limitless.R
import com.evapharma.limitless.databinding.CategorySimilarItemBinding
import com.evapharma.limitless.databinding.HomeCategoryItemBinding
import com.evapharma.limitless.domain.model.Category
import com.evapharma.limitless.presentation.adapters.bindImage
import com.evapharma.limitless.presentation.util.CategoryView
import com.evapharma.limitless.presentation.util.CategoryView.HOME
import com.evapharma.limitless.presentation.util.CategoryView.ProductWITHCATEGORY

class CategoryAdapter(
    private val count: Int,
    private val categoryView: CategoryView,
    private val listener: SetOnCategoryClick
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var categories: List<Category> = ArrayList<Category>()

    inner class CategoryViewHolder(val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    fun submitList(categories: List<Category>) {
        this.categories = categories
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding: ViewBinding
        if (categoryView == HOME)
            binding =
                HomeCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        else
            binding = CategorySimilarItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        val holder = CategoryViewHolder(binding)
        binding.root.setOnClickListener {
            if (categoryView == HOME) listener.onCategoryClick(categories[holder.adapterPosition].id)
            else listener.onCategoryClick(categories[holder.adapterPosition].products[0].id)
        }
        return holder
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        when (categoryView) {
            HOME -> homeCategory(position, holder.binding as HomeCategoryItemBinding)
            ProductWITHCATEGORY -> otherProducts(
                category,
                holder.binding as CategorySimilarItemBinding
            )
        }
    }

    private fun otherProducts(category: Category, binding: CategorySimilarItemBinding) {
        val product = category.products[0]
        binding.apply {
            tvCategoryTitle.text = product.name
            bindImage(binding.categoryPhoto, product.imageUrl)
        }
    }

    private val categoriesPictures = listOf(
        R.drawable.immunity,
        R.drawable.bones,
        R.drawable.kids,
        R.drawable.weights,
        R.drawable.pain,
        R.drawable.skincare,
        R.drawable.renewable_energy,
        R.drawable.liquid_soap
    )

    private fun homeCategory(position: Int, binding: HomeCategoryItemBinding) {
        val category = categories[position]
        binding.apply {
            categoryPhoto.setImageResource(categoriesPictures[position])
            tvCategoryTitle.text = category.name
        }
    }

    override fun getItemCount(): Int {
        return count
    }

    interface SetOnCategoryClick {
        fun onCategoryClick(id: Int)
    }
}