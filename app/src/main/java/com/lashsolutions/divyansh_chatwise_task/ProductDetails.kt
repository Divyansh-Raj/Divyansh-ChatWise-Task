package com.lashsolutions.divyansh_chatwise_task

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.squareup.picasso.Picasso

class ProductDetails : AppCompatActivity() {

    private lateinit var iv_productImageAtTop: ImageView
    private lateinit var tv_productTitleInProductDetails:TextView
    private lateinit var tv_productPriceInProductDetails:TextView
    private lateinit var tv_minimumOrderValue:TextView
    private lateinit var tv_warrantyValue:TextView
    private lateinit var tv_descriptionValue:TextView
    private lateinit var rv_ratingsAndReview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product_details)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.clMainForProductDetails)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        iv_productImageAtTop=findViewById(R.id.iv_productImageAtTop)
        tv_productTitleInProductDetails=findViewById(R.id.tv_productTitleInProductDetails)
        tv_productPriceInProductDetails=findViewById(R.id.tv_productPriceInProductDetails)
        tv_minimumOrderValue=findViewById(R.id.tv_minimumOrderValue)
        tv_warrantyValue=findViewById(R.id.tv_warrantyValue)
        tv_descriptionValue=findViewById(R.id.tv_descriptionValue)
        rv_ratingsAndReview=findViewById(R.id.rv_ratingsAndReview)

        val productDetails: Product? = intent.getParcelableExtra("productDataModelDetails")

        //load the image into the imageview
        Picasso.get()
            .load(productDetails!!.images.get(0))
            .placeholder(R.drawable.product_placeholder_image)
            .error(R.drawable.product_placeholder_image)
            .into(iv_productImageAtTop)

        //set the details of the product in the views
        tv_productTitleInProductDetails.text=productDetails.title
        tv_productPriceInProductDetails.text=productDetails.price.toString()

        tv_minimumOrderValue.text=productDetails.minimumOrderQuantity.toString()
        tv_warrantyValue.text=productDetails.warrantyInformation

        tv_descriptionValue.text=productDetails.description

        //set the ratings according to the ratings field
        val starViews = arrayOf(
            findViewById<ImageView>(R.id.star1),
            findViewById<ImageView>(R.id.star2),
            findViewById<ImageView>(R.id.star3),
            findViewById<ImageView>(R.id.star4),
            findViewById<ImageView>(R.id.star5),

        )
        updateRatingStars(starViews,productDetails.rating)

        //set the adapter for the ratings and the reviews
        rv_ratingsAndReview.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        val adapterForRatingsAndReviews=AdapterforRatingsAndReviews(this,productDetails.reviews)
        rv_ratingsAndReview.adapter=adapterForRatingsAndReviews

    }

    private fun updateRatingStars(starViews: Array<ImageView>, rating: Double) {
        for (i in starViews.indices) {
            if (i < rating) {
                starViews[i].setImageResource(R.drawable.star_icon_yellow)
            } else {
                starViews[i].setImageResource(R.drawable.star_icon_gray)
            }
        }
    }

}