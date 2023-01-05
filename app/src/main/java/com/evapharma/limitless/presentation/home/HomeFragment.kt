package com.evapharma.limitless.presentation.home

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData

import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.evapharma.limitless.R
import com.evapharma.limitless.databinding.FragmentHomeBinding
import com.evapharma.limitless.domain.model.*
import com.evapharma.limitless.domain.model.Product
import com.evapharma.limitless.presentation.home.adapters.CategoryAdapter
import com.evapharma.limitless.presentation.home.adapters.CarouselAdapter
import com.evapharma.limitless.presentation.offers.OffersSectionAdapter
import com.evapharma.limitless.presentation.util.CategoryHomeGrid
import com.evapharma.limitless.presentation.util.CategoryView
import com.evapharma.limitless.presentation.util.Constants
import com.evapharma.limitless.presentation.utils.hide
import com.evapharma.limitless.presentation.utils.show
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.ViewModelProvider
import com.evapharma.limitless.data.local.PreferenceManager
import com.evapharma.limitless.presentation.MainActivity
import com.evapharma.limitless.presentation.cart.CartFragmentDirections
import com.evapharma.limitless.presentation.checkout.CheckoutActivity
import com.evapharma.limitless.presentation.customer.CustomerLoginActivity
import com.evapharma.limitless.presentation.home.adapters.BundleListAdapter
import com.evapharma.limitless.presentation.home.adapters.SliderAdapter
import com.evapharma.limitless.presentation.loginandsignup.signinusingmobilefragment.SigninUsingMobileNumberFragment
import com.evapharma.limitless.presentation.maps.MapViewModel
import com.evapharma.limitless.presentation.maps.MapsActivity
import com.evapharma.limitless.presentation.util.*
import com.evapharma.limitless.presentation.utils.showSnackBar


@AndroidEntryPoint
class HomeFragment : Fragment(), OnItemClickListener, CategoryAdapter.SetOnCategoryClick,
    BundleListAdapter.OnBundleClickListener {
    private var customerAddress: String? = null
    private val binding: FragmentHomeBinding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private val viewModel: HomeViewModel by viewModels()
    private val carouselAdapter = CarouselAdapter(this@HomeFragment)
    private val controller: NavController by lazy { findNavController() }
    private val sliderAdapter: SliderAdapter by lazy { SliderAdapter(this) }

    private val offersAdapter by lazy { OffersSectionAdapter(this, viewLifecycleOwner) }
    private lateinit var bundleAdapter: BundleListAdapter
    private lateinit var mSharedPreference: PreferenceManager
    private val mHomeViewModel by lazy {
        ViewModelProvider(this)[MapViewModel::class.java]

    }

    companion object {
        var offersProducts = listOf<Product>()
        private val REQUEST_PERMISSION_REQUEST_CODE = 2020
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as MainActivity).showBottomNavigationView()

        mSharedPreference = PreferenceManager(requireContext())
        if (viewModel.getLocation.execute(Constants.KEY_LOCATION_STATE)) {
            binding.ADDRESS.text = viewModel.getStringSp.execute(Constants.KEY_LOCATION_ADDRESS)
        } else {
            getLocation()
        }

        setListeners()
        viewModel.refreshBundle()

        customerAddress =
            requireActivity().intent.getStringExtra(getString(R.string.address_key))
        if (customerAddress != null) {
            setupLocationAddress()
        }
        return binding.root
    }

    private fun setUpBundles() {
        setUpBundleRv()
        viewModel.bundleLiveData.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                bundleAdapter.setBundleList(it)
                binding.bundleGroup.show()
                binding.progressBar.hide()
                binding.homeLayout.show()
            }

        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViews()
        observeViewModel()

        binding.expendMore.setOnClickListener {
            categoriesView()
        }
    }

    private fun setUpViews() {
        setUpCarouselRecyclerView()
        setUpSliderRecyclerView()
        setUpOffersRecyclerView()
        setUpViewAllOffers()
        setUpCategories()
        setUpBundles()
        getLocationText()
    }

    private fun getLocationText() {
        val location = viewModel.getStringSp.execute(Constants.Location_Text)
        if (location != null) {
            binding.ADDRESS.text = location
        }
    }

    private fun setupLocationAddress() {
        binding.ADDRESS.text = customerAddress
        viewModel.putString.execute(Constants.Location_Text, customerAddress!!)
    }

    private fun setUpCategories() {
        viewModel.category.observe(viewLifecycleOwner) {
            viewModel.categories = it
            binding.progressBar.hide()
            binding.homeLayout.show()
            collapse(it)
        }
    }

    private fun categoriesView() {
        if (viewModel.categoryExpand)
            expend(viewModel.categories)
        else
            collapse(viewModel.categories)
    }

    private fun setUpCarouselRecyclerView() {
        binding.carouselRecyclerView.adapter = carouselAdapter
    }

    private fun setUpSliderRecyclerView() {
        binding.sliderRecyclerView.adapter = sliderAdapter
    }

    private fun setUpOffersRecyclerView() {
        binding.offersRecyclerView.adapter = offersAdapter
    }

    private fun setUpViewAllOffers() {
        binding.viewAllLabel.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_offersFragment) }
    }


    private fun observeViewModel() {
        viewModel.apply {
            offers.observe(viewLifecycleOwner) {
                it?.let {
                    if (it.isNotEmpty()) {
                        offersAdapter.submitList(it)
                        binding.offersSection.show()
                        offersProducts = it
                        binding.progressBar.hide()
                        binding.homeLayout.show()

                    }
                }
            }

            carouselList.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    binding.carouselRecyclerView.show()
                    carouselAdapter.submitList(it)
                }
            }
            slider.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    binding.sliderRecyclerView.show()
                    sliderAdapter.submitList(it)
                }
            }

        }
    }


    private fun expend(categories: List<Category>) {
        setUpCategoryRV(categories.size, categories)
        binding.apply {
            expendMore.text = "Collapse"
            expendMore.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up, 0)
            viewModel.categoryExpand = false
        }
    }

    private fun collapse(categories: List<Category>) {
        if (categories.size < 6)
            setUpCategoryRV(categories.size, categories)
        else
            setUpCategoryRV(6, categories)
        binding.apply {
            expendMore.text = "Expend more"
            expendMore.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0)
            viewModel.categoryExpand = true
        }
    }


    private fun navigateToOffers() {
        controller.navigate(R.id.action_homeFragment_to_offersFragment)
    }

    private fun navigateToProductDetails(productId: Int) {
        val direction = HomeFragmentDirections.actionHomeFragmentToProductDetailsFragment(productId)
        controller.navigate(direction)
    }


    private fun navigateToCategory(categoryId: Int) {
        val direction = HomeFragmentDirections.actionHomeFragmentToCategoryFragment(categoryId)
        controller.navigate(direction)
    }

    private fun setUpCategoryRV(count: Int, categories: List<Category>) {
        binding.apply {
            rvHomeCategory.apply {
                layoutManager = CategoryHomeGrid(requireContext(), 3)
                val categoryAdapter = CategoryAdapter(count, CategoryView.HOME, this@HomeFragment)
                adapter = categoryAdapter
                categoryAdapter.submitList(categories)
                setHasFixedSize(true)
            }
        }
    }

    override fun onCategoryClick(id: Int) {
        navigateToCategory(id)
    }

    private fun setListeners() {
        binding.ADDRESS.setOnClickListener {
            SupportClass.startActivityWithFlags(requireContext(), MapsActivity::class.java)


        }
        binding.ivViewAllBundles.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_viewAllBundlesFragment)
        }


        binding.ADDRESS.setOnClickListener {
            SupportClass.startActivityWithFlags(requireContext(), MapsActivity::class.java)

        }
        binding.ivViewAllBundles.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_viewAllBundlesFragment)
        }

    }

    private fun setUpBundleRv() {
        binding.BundlesListRecyclerview.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        bundleAdapter = BundleListAdapter( 1,this,this,viewLifecycleOwner)
        binding.BundlesListRecyclerview.adapter = bundleAdapter
    }


    //todo:Maps & permission
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_REQUEST_CODE && grantResults.size > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(requireContext(), "Permission Denied!", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onItemClick(productId: Int) {
        val direction =
            HomeFragmentDirections.actionHomeFragmentToProductDetailsFragment(productId)
        findNavController().navigate(direction)
    }


    override fun <T> onItemClick(item: T) {
        when (item) {
            is Product -> navigateToProductDetails(item.id)
            is Carousel-> showSnackBar(binding.root,item.name)
            is Banner ->  showSnackBar(binding.root,"This offer for you!")
        }

    }

    override fun onItemAddToCartClick(productId: Int): LiveData<Boolean>? {
        if (!viewModel.isAuthenticated()){
            startActivity(Intent(requireActivity(),CustomerLoginActivity::class.java))
            return null
        }
        else {
            viewModel.addToCart(productId)
            return viewModel.cartLoading
        }
    }


}




    

