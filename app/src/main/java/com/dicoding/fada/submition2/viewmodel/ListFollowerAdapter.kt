package com.dicoding.fada.submition2.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.fada.submition2.R
import com.dicoding.fada.submition2.model.Follower
import kotlinx.android.synthetic.main.item_row_user.view.*

class ListFollowerAdapter(private val listFollower: ArrayList<Follower>): RecyclerView.Adapter<ListFollowerAdapter.ListHolder>() {

    fun setData(items: ArrayList<Follower>) {
        listFollower.clear()
        listFollower.addAll(items)
        notifyDataSetChanged()
    }

    class ListHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(follower: Follower) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(follower.ava)
                    .apply(RequestOptions().override(70, 70))
                    .into(imgAva)

                tvFullName.text = follower.name
                tvUsername.text = follower.username
                tvRepository.text = follower.repository
            }
        }
    }

    override fun onCreateViewHolder(view: ViewGroup, viewType: Int): ListHolder {
        return ListHolder(LayoutInflater.from(view.context).inflate(R.layout.item_row_user, view, false))
    }

    override fun onBindViewHolder(holder: ListHolder, position: Int) {
        holder.bind(listFollower[position])
    }

    override fun getItemCount(): Int {
        return listFollower.size
    }
}