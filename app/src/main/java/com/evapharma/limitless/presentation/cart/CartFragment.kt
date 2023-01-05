package com.evapharma.limitless.presentation.cart

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.evapharma.limitless.R
import com.evapharma.limitless.databinding.FragmentCartBinding
import com.evapharma.limitless.domain.model.CartDetails
import com.evapharma.limitless.domain.model.Cart
import com.evapharma.limitless.domain.model.Summary
import com.evapharma.limitless.domain.model.mapToSummary
import com.evapharma.limitless.presentation.cart.deleteCart.DeleteCartFragment
import com.evapharma.limitless.presentation.checkout.CheckoutActivity
import com.evapharma.limitless.presentation.util.CUSTOMER_ACCESS_TOKEN_KEY
import com.evapharma.limitless.presentation.util.SUMMARY_KEY
import com.evapharma.limitless.presentation.utils.hide
import com.evapharma.limitless.presentation.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment(), SetOnCartItemClick {
    private val binding: FragmentCartBinding by lazy { FragmentCartBinding.inflate(layoutInflater) }
    private val cartAdapter: CartAdapter by lazy { CartAdapter(this) }
    val viewModel: CartViewModel by viewModels()
    val controller: NavController by lazy { findNavController() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.refreshCart()
        return binding.root
    }

    private fun navigateToLogin() {
        if (viewModel.getString.execute(CUSTOMER_ACCESS_TOKEN_KEY) == null) {
            val direction = CartFragmentDirections.actionCartFragmentToCustomerLoginActivity()
            controller.navigate(direction)
        } else {
            startActivity(Intent(requireActivity(), CheckoutActivity::class.java).apply {
                putExtra(SUMMARY_KEY, summary)
            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.cartProgress.show()
        setUpView()
        binding.clearCarts.setOnClickListener {
            DeleteCartFragment().show(childFragmentManager, "clear carts")
        }

        binding.noCartExploreMore.setOnClickListener {
            controller.navigate(CartFragmentDirections.actionCartFragmentToHomeFragment())
        }
    }

    private fun setUpView() {
        setUpProducts()
        binding.btnCheckout.setOnClickListener { navigateToLogin() }
    }

    private fun cartEmpty() {
        binding.noCart.show()
        binding.cartExist.hide()
        binding.cartExistParent.hide()
    }

    private fun cartNotEmpty(cart: Cart) {
        setUpProductsRV()
        cartAdapter.submitList(cart.products)
        binding.apply {
            noCart.hide()
            cartExist.show()
            cartExistParent.show()
        }
        putPrices(cart)
    }
    lateinit var summary : Summary
    private fun setUpProducts() {
        viewModel.cartViewModel.observe(viewLifecycleOwner) {
            if (it.products.isEmpty())
                cartEmpty()
            else {
                cartNotEmpty(it)
                summary = it.mapToSummary()
            }
            binding.cartProgress.hide()
            hideQuantityProgress()
        }
    }

    private fun putPrices(cart: Cart) {
        binding.apply {
            subtotalPrice.text =  cart.products[0].currency + " " + cart.oneTimeSubTotal
            totalPrice.text = cart.products[0].currency + " " + cart.totalPrice
            deliveryFeePrice.text = "${cart.products[0].currency} 0.0"
        }
    }

    private fun setUpProductsRV() {
        binding.cartRecyclerView.apply {
            setHasFixedSize(true)
            adapter = cartAdapter
        }
    }

    override fun onCartItemClicked(productId: Int) {
        val direction = CartFragmentDirections.actionCartFragmentToProductDetailsFragment(productId)
        controller.navigate(direction)
    }

    override fun onIncreaseQuantity(productId: Int, quantity: Int) {
        val updateQuantity = CartDetails(productId, quantity, null, null)
        viewModel.updateProduct(updateQuantity)
        showQuantityProgress()
    }

    private fun showQuantityProgress() {
        binding.updateQuantityProgress.show()
        binding.cartBackgroundView.show()
    }

    private fun hideQuantityProgress() {
        binding.updateQuantityProgress.hide()
        binding.cartBackgroundView.hide()
    }

    override fun onDecreaseQuantity(productId: Int, quantity: Int) {
        val updateQuantity = CartDetails(productId, quantity, null, null)
        viewModel.updateProduct(updateQuantity)
        showQuantityProgress()
    }

    override fun onCancelSubscription(check: Boolean, productId: Int) {
        /*
            if (check)
                viewModel.updateProduct()
            else{
                val direction = CartFragmentDirections.actionCartFragmentToCancelSubscriptionFragment()
                findNavController().navigate(direction)
            }
            */
    }
}
