package com.example.amacen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amacen.data.ArticuloClass
import com.example.amacen.databinding.ItemReviewBinding

class ReviewsAdapter (var reviewList: List<ArticuloClass.Review>, val onItemClick:(Int) -> Unit) : RecyclerView.Adapter<Reviews_ViewHolder>() {

    override fun onBindViewHolder(holder: Reviews_ViewHolder, position: Int) {
        val selectReview = reviewList[position]

        holder.render(selectReview)
        holder.itemView.setOnClickListener { onItemClick(position) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Reviews_ViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Reviews_ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    fun updateItems(items: List<ArticuloClass.Review>) {
        this.reviewList = items
        notifyDataSetChanged()
    }
}

class Reviews_ViewHolder(val binding: ItemReviewBinding) :  RecyclerView.ViewHolder(binding.root) {
    fun render(review: ArticuloClass.Review) {
        binding.Name.text = review.reviewerName.toString()
        binding.reviewerEmail.text = review.reviewerEmail.toString()
        binding.date.text = review.date.toString()
        binding.rating.text = review.rating.toString()
        binding.coment.text = review.comment.toString()
    }
}
