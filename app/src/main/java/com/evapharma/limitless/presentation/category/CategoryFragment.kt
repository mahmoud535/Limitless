package com.evapharma.limitless.presentation.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evapharma.limitless.R
import com.evapharma.limitless.databinding.FragmentCategoryBinding
import com.evapharma.limitless.presentation.home.adapters.CategoryAdapter
import com.evapharma.limitless.presentation.util.CategoryView
import com.evapharma.limitless.presentation.util.LayoutManager.GRID
import com.evapharma.limitless.presentation.util.LayoutManager.LIST
import com.evapharma.limitless.presentation.utils.hide
import com.evapharma.limitless.presentation.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment(), Toolbar.OnMenuItemClickListener,
    CategoryAdapter.SetOnCategoryClick, SetOnProductClick {

    val binding: FragmentCategoryBinding by lazy { FragmentCategoryBinding.inflate(layoutInflater) }
    val viewModel: CategoryViewModel by viewModels()
    private lateinit var categoryDetailAdapter: CategoryDetailAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private val controller: NavController by lazy { findNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = CategoryFragmentArgs.fromBundle(requireArguments()).categoryId
        viewModel.refresh(id)
        viewModel.loadCategory()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        hideView()
        setUpView()
        binding.categoryToobar.setOnMenuItemClickListener(this)
        binding.categoryToobar.setNavigationOnClickListener {
            controller.popBackStack()
        }
    }

    private fun setUpView(){
        setUpProducts()
        setUpOtherProducts()
    }

    private fun showView(){
        binding.categoryProgress.hide()
        binding.categoryDetailRoot.show()
        binding.categoryGroup.show()
    }

    private fun hideView(){
        binding.categoryProgress.show()
        binding.categoryDetailRoot.hide()
        binding.categoryGroup.hide()
    }

    private fun setUpProducts(){
        viewModel.products.observe(viewLifecycleOwner) {
            binding.categoryName.text = it.categoryName ?: ""
            categoryDetailAdapter = CategoryDetailAdapter(this, LIST, it)
            productByCategory(LinearLayoutManager(requireContext()))
            viewModel.productsByCategory = it
            showView()
        }
    }

    private fun setUpOtherProducts(){
        viewModel.category.observe(viewLifecycleOwner){
            categoryAdapter = CategoryAdapter(it.size, CategoryView.ProductWITHCATEGORY, this)
            categoryAdapter.submitList(it)
            otherProducts()
        }
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.category_layout_manager -> {
                if (!viewModel.grid) {
                    categoryDetailAdapter =
                        CategoryDetailAdapter(this, LIST, viewModel.productsByCategory!!)
                    viewModel.grid = true
                    productByCategory(LinearLayoutManager(requireContext()))
                    item.setIcon(R.drawable.ic_grid)
                } else {
                    categoryDetailAdapter =
                        CategoryDetailAdapter(this, GRID, viewModel.productsByCategory!!)
                    viewModel.grid = false
                    productByCategory(GridLayoutManager(requireContext(), 2))
                    item.setIcon(R.drawable.ic_list)
                }
            }
        }
        return true
    }

    private fun productByCategory(manager: RecyclerView.LayoutManager) {
        binding.rvCategory.apply {
            layoutManager = manager
            adapter = categoryDetailAdapter
            setHasFixedSize(true)

        }
    }

    private fun otherProducts() {
        binding.rvCategoryOther.apply {
            adapter = categoryAdapter
            setHasFixedSize(true)
        }
    }


    override fun onCategoryClick(id: Int) {
        navigateToProductDetail(id)
    }

    override fun onProductClicked(productId: Int) {
        navigateToProductDetail(productId)
    }

    private fun navigateToProductDetail(productId: Int){
        val direction = CategoryFragmentDirections.actionCategoryFragmentToProductDetailsFragment(productId)
        controller.navigate(direction)
    }


}