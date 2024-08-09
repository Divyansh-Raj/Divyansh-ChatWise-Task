import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.lashsolutions.divyansh_chatwise_task.Product
import com.lashsolutions.divyansh_chatwise_task.ProductDetails
import com.lashsolutions.divyansh_chatwise_task.R
import com.squareup.picasso.Picasso

class AdapterForProductsList(private val context: Context,private val items: List<Product>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_PRODUCTS_LIST_HEADER = 0
        private const val VIEW_TYPE_PRODUCTS = 1
    }

    override fun getItemViewType(position: Int): Int {
        // Return VIEW_TYPE_PRODUCTS_LIST_HEADER for the zeroth position, VIEW_TYPE_PRODUCTS for the rest
        return if (position == 0) VIEW_TYPE_PRODUCTS_LIST_HEADER else VIEW_TYPE_PRODUCTS
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_PRODUCTS_LIST_HEADER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.itemgrid_for_product_list_header, parent, false)
                ViewHolderForProductsListHeader(view)
            }
            VIEW_TYPE_PRODUCTS -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.itemgrid_for_individual_product, parent, false)
                ViewHolderForProducts(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolderForProductsListHeader -> {
                // Bind data for itemgrid_for_product_list_header here using holder
                holder.tv_productsListHeader.text="Products List"
            }

            is ViewHolderForProducts -> {
                // Bind data for itemgrid_for_individual_product here using holder
                val productDataModel=items[position]

                //set the ratings accoeding to the ratings field
                val starViews = arrayOf(
                    holder.star1,
                    holder.star2,
                    holder.star3,
                    holder.star4,
                    holder.star5,
                )
                updateRatingStars(starViews,productDataModel.rating!!)

                //load the image into the imageview
                Picasso.get()
                    .load(productDataModel.thumbnail)
                    .placeholder(R.drawable.product_placeholder_image)
                    .error(R.drawable.product_placeholder_image)
                    .into(holder.iv_productImage)

            holder.tv_productTitle.text=productDataModel.title

                "$ ${productDataModel.price}".also { holder.tv_productPrice.text = it }

                //when the product is clicked , take the user to the products detail activity
                holder.cv_mainCardViewItem.setOnClickListener {
                    val intentToPorductDetails= Intent(context,ProductDetails::class.java)
                    intentToPorductDetails.putExtra("productDataModelDetails",productDataModel)
                    context.startActivity(intentToPorductDetails)
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    // Define the ViewHolder for itemgrid_for_product_list_header
    inner class ViewHolderForProductsListHeader(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // Initialize views for itemgrid_for_product_list_header here
        val tv_productsListHeader: TextView =itemView.findViewById(R.id.tv_productsListHeader)

    }

    // Define the ViewHolder for itemgrid_for_individual_product
    inner class ViewHolderForProducts(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // Initialize views for itemgrid_for_individual_product here
        val cv_mainCardViewItem:MaterialCardView=itemView.findViewById(R.id.cv_mainCardViewItem)

        val iv_productImage:ImageView=itemView.findViewById(R.id.iv_productImage)
        val tv_productTitle:TextView=itemView.findViewById(R.id.tv_productTitle)
        val tv_productPrice:TextView=itemView.findViewById(R.id.tv_productPrice)
        val star1:ImageView=itemView.findViewById(R.id.star1)
        val star2:ImageView=itemView.findViewById(R.id.star2)
        val star3:ImageView=itemView.findViewById(R.id.star3)
        val star4:ImageView=itemView.findViewById(R.id.star4)
        val star5:ImageView=itemView.findViewById(R.id.star5)

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
