package com.dicoding.fada.submition2.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.fada.submition2.R
import com.dicoding.fada.submition2.model.Follower
import com.dicoding.fada.submition2.model.User
import com.dicoding.fada.submition2.viewmodel.FollowerViewModel
import com.dicoding.fada.submition2.viewmodel.ListFollowerAdapter
import kotlinx.android.synthetic.main.fragment_follower.*


class FollowerFragment : Fragment() {

    companion object {
        val TAG = FollowerFragment::class.java.simpleName
        const val EXTRA_DETAIL = "extra_detail"
    }

    private val listData: ArrayList<Follower> = ArrayList()
    private lateinit var adapter: ListFollowerAdapter
    private lateinit var followerViewModel: FollowerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ListFollowerAdapter(listData)
        followerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowerViewModel::class.java)

        val dataUser = activity?.intent?.getParcelableExtra(EXTRA_DETAIL) as User
        config()

        followerViewModel.getData(activity!!.applicationContext, dataUser.username.toString())
        showLoading(true)

        followerViewModel.getListFollower().observe(activity!!, Observer { listFollower ->
            if (listFollower != null) {
                adapter.setData(listFollower)
                showLoading(false)
            }
        })
    }

    private fun config() {
        recycleViewFollower.layoutManager = LinearLayoutManager(activity)
        recycleViewFollower.setHasFixedSize(true)
        recycleViewFollower.adapter = adapter
    }

    private fun showLoading(b: Boolean) {
        if (b) {
            PBFollower.visibility = View.VISIBLE
        } else {
            PBFollower.visibility = View.INVISIBLE
        }
    }
}