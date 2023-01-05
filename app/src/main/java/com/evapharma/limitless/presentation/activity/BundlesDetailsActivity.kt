package com.evapharma.limitless.presentation.activity

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.evapharma.limitless.R
import com.evapharma.limitless.databinding.ActivityBundlesDetailsBinding
import com.evapharma.limitless.presentation.home.adapters.BundleListAdapter
import com.evapharma.limitless.domain.model.Product
import com.evapharma.limitless.data.local.PreferenceManager
import com.evapharma.limitless.domain.model.BundleData
import com.evapharma.limitless.presentation.adapters.bindImage
import com.google.android.gms.maps.model.LatLng

class BundlesDetailsActivity : AppCompatActivity() {

    var productsList:List<Product> = ArrayList()
     lateinit var recyclerAdapter: BundleListAdapter
     lateinit var binding: ActivityBundlesDetailsBinding
    private lateinit var mSharedPreference: PreferenceManager
    private var currentLatLong: LatLng? = null

    private var mProducts: List<BundleData> = ArrayList<BundleData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_bundles_details)

       binding = ActivityBundlesDetailsBinding.inflate(layoutInflater)


//       // initBundleRecyclerview(productsList)
//        initViewModel()
       // getDetails()

        val image = intent.getSerializableExtra(BundleListAdapter.INTENT_PARCELABLE) as? Product //<Product>(BundleListAdapter.INTENT_PARCELABLE)

        val imgSrc = findViewById<ImageView>(R.id.iv_item_image_details)
        val imgTitle = findViewById<TextView>(R.id.tv_title_details)
        val imgDesc = findViewById<TextView>(R.id.tv_description_details)

        val imgSrc2 = findViewById<TextView>(R.id.tv_Current_price_details)
        val imgTitle3 = findViewById<TextView>(R.id.tv_dashboard_item_price)
        val imgDesc4 = findViewById<TextView>(R.id.tv_description_details)


        //imgSrc.setImageResource(image.imageSrc)
        if (image != null) {
            imgTitle.text = image.name
            imgDesc.text = image.shortDescription
            imgSrc2.text = image.price
            imgTitle3.text = image.discountPercentage.toString()
            imgDesc4.text = image.oldPrice
        }

        setContentView(binding.root)
    }

//    fun getDetails(){
//        bindImage(holder.itemView.iv_item_image, model.imageUrl)
//        holder.itemView.tv_title.text = model.name
//        binding.bundlesItemDetails=mProducts.get().products
//        holder.itemView.tv_description.text = model.shortDescription
//        holder.itemView.tv_Current_price.text = model.price
//        holder.itemView.discount_percent_tv.text = "Save ${model.discountPercentage} % "
//        holder.itemView.tv_dashboard_item_price.text = model.oldPrice
//        holder.itemView.tv_dashboard_item_price.paintFlags =
//            holder.itemView.tv_dashboard_item_price.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
//    }

   // recyclerview bundles
//    private fun initBundleRecyclerview(bundleItemList:List<BundleModel>){
//        binding.details.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//        recyclerAdapter= BundleListAdapter(requireActivity(),requireContext(),bundleItemList,1)
//        binding.BundlesListRecyclerview.adapter =recyclerAdapter
//
//
//
//    }

//
//    //List Bundles
//    private fun initViewModel() {
//        //Show the progress dialog
////        showProgressDialog(resources.getString(R.string.please_wait))
//
//        val viewModel: BundleViewModel = ViewModelProvider(this).get(BundleViewModel::class.java)
//        viewModel.getLiveDataObserver().observe(this, Observer {
//            if(it != null) {
//                recyclerAdapter.setBundleList(it)
//                recyclerAdapter.notifyDataSetChanged()
//                Log.d("mahmoud",it.size.toString())
//               // hideProgressDialog()
//            } else {
//                Toast.makeText(this, "Error in getting list", Toast.LENGTH_SHORT).show()
//            }
//        })
//        viewModel.makeAPICall()
//
//
//    }
}