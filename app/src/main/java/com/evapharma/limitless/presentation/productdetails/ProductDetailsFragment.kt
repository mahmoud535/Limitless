package com.evapharma.limitless.presentation.productdetails

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.denzcoskun.imageslider.models.SlideModel
import com.evapharma.limitless.R
import com.evapharma.limitless.databinding.FragmentProductDetailsBinding
import com.evapharma.limitless.domain.model.Product
import com.evapharma.limitless.presentation.MainActivity
import com.evapharma.limitless.presentation.customer.CustomerLoginActivity
import com.evapharma.limitless.presentation.home.adapters.SimilarProductsAdapter
import com.evapharma.limitless.presentation.utils.hide
import com.evapharma.limitless.presentation.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment : Fragment(),
    SimilarProductsAdapter.OnSimilarProductItemClickListener {

    private val binding by lazy { FragmentProductDetailsBinding.inflate(layoutInflater) }
    private val viewModel: ProductDetailsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as MainActivity).hideBottomNavigationView()
        viewModel.productId = ProductDetailsFragmentArgs.fromBundle(requireArguments()).productId
        requireActivity().setActionBar(binding.toolBar)
        binding.toolBar.setNavigationOnClickListener { findNavController().navigateUp() }
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProductDetails()
        observeViewModel()

    }

    private lateinit var productDetails: Product
    private fun setUpViews() {
        hideProgressBar()
        setUpSimilarProductRecyclerView()
        setUpDiscountVisibility()
        setUpAddToCartBtn()
        setUpQuantityControllers()
        setUpImageSlider(productDetails.imageUrl)
        setUpProductManufacturer()
        setUpProductDescription()
    }

    private fun setUpProductDescription() {
        binding.layoutProductDescription.setOnClickListener {
            ProductDescriptionBottomSheet(productDetails).show(
                requireActivity().supportFragmentManager, getString(
                    R.string.product_description_bottom_sheet_tag
                )
            )
        }
    }

    private fun setUpAddToCartBtn() {
        binding.addToCartBtn.setOnClickListener {
            if (!viewModel.isAuthenticated()) {
                startActivity(Intent(requireActivity(), CustomerLoginActivity::class.java))

            } else {
                viewModel.addToCart()
                it.hide()
                binding.quantityVisibilityGroup.show()
            }
        }
    }


    private fun setUpQuantityControllers() {
        binding.plusIcon.setOnClickListener { viewModel.increaseQuantity() }
        binding.minusIcon.setOnClickListener { viewModel.decreaseQuantity() }
        binding.cartItemsNumberCardView.setOnClickListener { findNavController().navigate(R.id.action_productDetailsFragment_to_cartFragment) }
    }


    private fun setUpImageSlider(imageUrl: String?) {
        imageUrl?.let {
            val imageList = ArrayList<SlideModel>()
            imageList.add(SlideModel(imageUrl))
            binding.imageSlider.setImageList(imageList)
        }
    }

    private fun setUpSimilarProductRecyclerView() {
        val similarProducts = viewModel.getSimilarProducts(productDetails)
        if (similarProducts.isNotEmpty()) {
            binding.similarProductsRecyclerView.adapter =
                SimilarProductsAdapter(this).apply {
                    submitList(similarProducts)
                }
            binding.similarProductsGroup.show()
        }

    }

    private fun setUpDiscountVisibility() {
        if (productDetails.hasDiscount) {
            binding.discountGroup.show()
        }
    }

    private fun hideProgressBar() {
        binding.progressBar.hide()
        binding.layoutVisibilityGroup.show()
        binding.subGroub.show()
    }

    private fun setUpProductManufacturer() {
        var manufacturers = ""
        productDetails.productManufacturers.forEach {
            manufacturers += "${it.name}, "
        }
        if (manufacturers.isNotEmpty())
            binding.productCompany.text = manufacturers.substring(0, manufacturers.length - 2)
        else
            binding.byWord.hide()
    }

    private fun observeViewModel() {
        viewModel.apply {
            productDetailsLiveData.observe(viewLifecycleOwner) { it ->
                productDetails = it
                binding.product = productDetails
                setUpViews()
            }
            quantityLiveData.observe(viewLifecycleOwner) {
                if (it > 0) {
                    binding.quantityVisibilityGroup.show()
                    binding.addToCartBtn.hide()
                } else
                    binding.addToCartBtn.show()

            }
        }
    }


    override fun onClick(productId: Int) {
        val direction = ProductDetailsFragmentDirections.actionProductDetailsFragmentSelf(productId)
        findNavController().navigate(direction)
    }


}