package com.lashsolutions.divyansh_chatwise_task

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class AdapterforRatingsAndReviews (private val context : Context,private val ratingsAndReviewsList:List<Review>):
    RecyclerView.Adapter<AdapterforRatingsAndReviews.AdapterforRatingsAndReviewsVH>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterforRatingsAndReviewsVH {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.itemgrid_for_ratings_and_reviews, parent, false)
        return AdapterforRatingsAndReviewsVH(view)
    }

    override fun getItemCount(): Int {
        return ratingsAndReviewsList.size
    }

    override fun onBindViewHolder(holder: AdapterforRatingsAndReviewsVH, position: Int) {
        val reviewOfOneUser=ratingsAndReviewsList[position]

        //set the data of the reviewer
        holder.tv_userName.text=reviewOfOneUser.reviewerName
        holder.tv_userNameReview.text=reviewOfOneUser.comment

        //set the ratings accoeding to the ratings field
        val starViews = arrayOf(
            holder.star1,
            holder.star2,
            holder.star3,
            holder.star4,
            holder.star5,
        )
        updateRatingStars(starViews,reviewOfOneUser.rating)
    }

    inner class AdapterforRatingsAndReviewsVH(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tv_userName:TextView=itemView.findViewById(R.id.tv_userName)
        val tv_userNameReview:TextView=itemView.findViewById(R.id.tv_userNameReview)
        val star1:ImageView=itemView.findViewById(R.id.star1)
        val star2:ImageView=itemView.findViewById(R.id.star2)
        val star3:ImageView=itemView.findViewById(R.id.star3)
        val star4:ImageView=itemView.findViewById(R.id.star4)
        val star5:ImageView=itemView.findViewById(R.id.star5)

    }

    private fun updateRatingStars(starViews: Array<ImageView>, rating: Int) {
        for (i in starViews.indices) {
            if (i < rating) {
                starViews[i].setImageResource(R.drawable.star_icon_yellow)
            } else {
                starViews[i].setImageResource(R.drawable.star_icon_gray)
            }
        }
    }

}