package com.evapharma.limitless.presentation.offers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.evapharma.limitless.R
import com.evapharma.limitless.databinding.FragmentOffersBinding
import com.evapharma.limitless.presentation.MainActivity
import com.evapharma.limitless.presentation.home.HomeFragment
import com.evapharma.limitless.presentation.home.adapters.OffersAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OffersFragment : Fragment(), OffersAdapter.OnOfferClickListener {
    private val binding by lazy { FragmentOffersBinding.inflate(layoutInflater) }
    private val viewModel: OffersViewModel by viewModels()
    private var viewType: OffersAdapter.ViewType = OffersAdapter.ViewType.GRID
    private val offersAdapter by lazy { OffersAdapter(this) }

    private var productsList = HomeFragment.offersProducts


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().setActionBar(binding.toolBar)
        binding.toolBar.setNavigationOnClickListener { findNavController().navigateUp() }
        (requireActivity() as MainActivity).hideBottomNavigationView()
        //  offersProduct = OffersFragmentArgs.fromBundle(requireArguments()).offersList
        /*  productsList =
              requireArguments().getParcelableArrayList<Product>("offers_list_key") as ArrayList<Product>*/
        offersAdapter.submitList(productsList)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //observeViewViewModel()
        setUpViews()
    }

    private fun setUpViews() {
        setUpOffersRecyclerView()
        toggleListGridViews()
    }

    private fun setUpOffersRecyclerView() {
        binding.offersRecyclerView.adapter = offersAdapter
    }

    private fun toggleListGridViews() {
        binding.gridIcon.setOnClickListener {
            viewType = OffersAdapter.ViewType.GRID
            handleIconsColor(binding.gridIcon, binding.listIcon)
            offersAdapter.setViewType(viewType)

            binding.offersRecyclerView.apply {
                layoutManager = GridLayoutManager(requireContext(), 2)
                adapter = offersAdapter
            }
        }
        binding.listIcon.setOnClickListener {
            viewType = OffersAdapter.ViewType.LIST
            handleIconsColor(binding.listIcon, binding.gridIcon)
            offersAdapter.setViewType(viewType)

            binding.offersRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = offersAdapter
            }
        }
    }

    private fun observeViewViewModel() {
        viewModel.offers.observe(viewLifecycleOwner) {
            it?.let {
                offersAdapter.submitList(it)
            }
        }
    }

    private fun handleIconsColor(icon1: ImageButton, icon2: ImageButton) {
        icon1.setColorFilter(resources.getColor(R.color.green))
        icon2.setColorFilter(resources.getColor(R.color.grey_200))
    }


    override fun onItemClick(productId: Int) {
        val direction =
            OffersFragmentDirections.actionOffersFragmentToProductDetailsFragment(productId)
        findNavController().navigate(direction)
    }

}