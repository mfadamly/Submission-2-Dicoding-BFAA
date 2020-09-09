package com.dicoding.fada.submition2.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.fada.submition2.R
import com.dicoding.fada.submition2.model.Following
import kotlinx.android.synthetic.main.item_row_user.view.*

class ListFollowingAdapter(private val listFollowing: ArrayList<Following>) :
    RecyclerView.Adapter<ListFollowingAdapter.ListFollowingHolder>() {


    fun setData(item: ArrayList<Following>) {
        listFollowing.clear()
        listFollowing.addAll(item)
        notifyDataSetChanged()
    }

    class ListFollowingHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(following: Following) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(following.ava)
                    .apply(RequestOptions().override(70, 70))
                    .into(imgAva)

                tvFullName.text = following.name
                tvUsername.text = following.username
                tvRepository.text = following.repository
            }
        }
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): ListFollowingHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_user, viewGroup, false)
        return ListFollowingHolder(mView)
    }

    override fun getItemCount(): Int = listFollowing.size

    override fun onBindViewHolder(listFollowingHolder: ListFollowingHolder, position: Int) {
        listFollowingHolder.bind(listFollowing[position])
    }
}