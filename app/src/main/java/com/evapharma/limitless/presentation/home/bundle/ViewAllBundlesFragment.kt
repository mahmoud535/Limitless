package com.evapharma.limitless.presentation.home.bundle

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.evapharma.limitless.databinding.FragmentViewAllBundlesBinding
import com.evapharma.limitless.domain.model.Product
import com.evapharma.limitless.presentation.MainActivity
import com.evapharma.limitless.presentation.customer.CustomerLoginActivity
import com.evapharma.limitless.presentation.home.HomeFragmentDirections
import com.evapharma.limitless.presentation.home.adapters.BundleListAdapter
import com.evapharma.limitless.presentation.home.HomeViewModel
import com.evapharma.limitless.presentation.home.OnItemClickListener
import com.evapharma.limitless.presentation.home.adapters.CategoryAdapter
import com.evapharma.limitless.presentation.home.adapters.OffersAdapter
import com.evapharma.limitless.presentation.util.CUSTOMER_ACCESS_TOKEN_KEY
import com.evapharma.limitless.presentation.utils.hide
import com.evapharma.limitless.presentation.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ViewAllBundlesFragment : Fragment(), BundleListAdapter.OnBundleClickListener,
    OnItemClickListener {
    private lateinit var viewAllAdapter: BundleListAdapter
    private lateinit var binding: FragmentViewAllBundlesBinding
    val viewModel: HomeViewModel by viewModels()
    private val controller: NavController by lazy { findNavController() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewAllBundlesBinding.inflate(layoutInflater)
        hideView()
        (requireActivity() as MainActivity).hideBottomNavigationView()
        setUpBundles()
        viewModel.refreshBundle()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.categoryToobar.setNavigationOnClickListener {
            controller.popBackStack()
        }
    }

    private fun showView() {
        binding.categoryProgress.hide()
//            binding.categoryDetailRoot.show()
//            binding.categoryGroup.show()
    }

    private fun hideView() {
        binding.categoryProgress.show()
//            binding.categoryDetailRoot.hide()
//            binding.categoryGroup.hide()
    }

    private fun setUpBundles() {
        setUpBundleRv()
        viewModel.bundleLiveData.observe(viewLifecycleOwner) {
            val products = mutableListOf<Product>()
            it.forEach {
                products.addAll(it.products)
            }
            viewAllAdapter.getAllProduct(products)
            showView()
        }
    }

    private fun setUpBundleRv() {
        binding.BundlesViewAll.layoutManager = GridLayoutManager(requireContext(), 1)
        viewAllAdapter = BundleListAdapter(2, this, this, viewLifecycleOwner)
        binding.BundlesViewAll.adapter = viewAllAdapter
    }

    override fun onItemClick(productId: Int) {
        val direction =
            ViewAllBundlesFragmentDirections.actionViewAllBundlesFragmentToProductDetailsFragment(
                productId
            )
        findNavController().navigate(direction)
    }

    override fun <T> onItemClick(item: T) {
        when (item) {
            //is Product -> navigateToProductDetails(item.id)
        }
    }

    override fun onItemAddToCartClick(productId: Int): LiveData<Boolean>? {
        if (!viewModel.isAuthenticated()){
            startActivity(Intent(requireActivity(), CustomerLoginActivity::class.java))
            return null
        }
        else {
            viewModel.addToCart(productId)
            return viewModel.cartLoading
        }
    }


}