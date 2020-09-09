package com.dicoding.fada.submition2.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.fada.submition2.R
import com.dicoding.fada.submition2.model.Following
import com.dicoding.fada.submition2.model.User
import com.dicoding.fada.submition2.viewmodel.FollowingViewModel
import com.dicoding.fada.submition2.viewmodel.ListFollowingAdapter
import kotlinx.android.synthetic.main.fragment_following.*

class FollowingFragment : Fragment() {

    companion object {
        val TAG = FollowingFragment::class.java.simpleName
        const val EXTRA_DETAIL = "extra_detail"
    }

    private val listFollowing: ArrayList<Following> = ArrayList()
    private lateinit var adapter: ListFollowingAdapter
    private lateinit var followingViewModel: FollowingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ListFollowingAdapter(listFollowing)
        followingViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewModel::class.java)

        val dataUser = activity?.intent?.getParcelableExtra(EXTRA_DETAIL) as User
        config()

        followingViewModel.getData(
            activity!!.applicationContext, dataUser.username.toString()
        )
        showLoading(true)

        followingViewModel.getListFollowing().observe(activity!!, Observer { listFollowing ->
            if (listFollowing != null) {
                adapter.setData(listFollowing)
                showLoading(false)
            }
        })
    }

    private fun config() {
        recycleViewFollowing.layoutManager = LinearLayoutManager(activity)
        recycleViewFollowing.setHasFixedSize(true)
        recycleViewFollowing.adapter = adapter
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            PBFollowing.visibility = View.VISIBLE
        } else {
            PBFollowing.visibility = View.INVISIBLE
        }
    }
}