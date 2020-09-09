package com.dicoding.fada.submition2.viewmodel

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.fada.submition2.R
import com.dicoding.fada.submition2.model.User
import com.dicoding.fada.submition2.view.DetailActivity
import kotlinx.android.synthetic.main.item_row_user.view.*

class ListUserAdapter(private val listDataUser: ArrayList<User>) : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    fun setData(items: ArrayList<User>) {
        listDataUser.clear()
        listDataUser.addAll(items)
        notifyDataSetChanged()
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            with(itemView) {
                Glide.with(itemView.context)
                    .load(user.ava)
                    .apply(RequestOptions().override(70, 70))
                    .into(imgAva)

                tvFullName.text = user.name
                tvUsername.text = user.username
                tvRepository.text = user.repository
            }
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.item_row_user, viewGroup, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listDataUser.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listDataUser[position])

        val user = listDataUser[position]
        holder.itemView.setOnClickListener {
            val userIntent = User(
                user.name,
                user.username,
                user.ava,
                user.company,
                user.location,
                user.repository,
                user.follower,
                user.following
            )
            val mIntent = Intent(it.context, DetailActivity::class.java)
            mIntent.putExtra(DetailActivity.EXTRA_DETAIL, userIntent)
            it.context.startActivity(mIntent)
        }
    }
}