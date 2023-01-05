package com.evapharma.limitless.presentation.productdetails

import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.evapharma.limitless.databinding.BottomSheetProductDescriptionBinding
import com.evapharma.limitless.domain.model.Product
import com.evapharma.limitless.domain.model.ProductAttribute
import com.evapharma.limitless.presentation.utils.show
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ProductDescriptionBottomSheet(val product: Product) : BottomSheetDialogFragment() {

    val binding by lazy { BottomSheetProductDescriptionBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().setActionBar(binding.toolBar)
        binding.toolBar.setNavigationOnClickListener { dismiss() }
        binding.smallDescription.text = product.shortDescription
        setUpProductAttributes(product.attributes)
        return binding.root
    }

    private fun setUpProductAttributes(attributes: List<ProductAttribute>) {
        if (attributes.isNotEmpty()) {
            binding.attributesGroup.show()
            binding.ingredientsText.text = fromHtml(attributes[2])
            binding.formsText.text = fromHtml(attributes[0])
            binding.dosesText.text = fromHtml(attributes[1])
        }
    }

    private fun fromHtml(productAttribute: ProductAttribute): String {
        val dirtyString = Html.fromHtml(productAttribute.values[0].valueRaw)
        val cleanString = dirtyString.replace(Regex("(?m)^[ \t]*\r?\n"), "");
        return cleanString
    }


}