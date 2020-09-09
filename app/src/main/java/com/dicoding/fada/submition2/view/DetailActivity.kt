package com.dicoding.fada.submition2.view

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.fada.submition2.R
import com.dicoding.fada.submition2.model.User
import com.dicoding.fada.submition2.viewmodel.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            viewPager.layoutParams.height = resources.getDimension(R.dimen.height).toInt()
        } else {
            viewPager.layoutParams.height = resources.getDimension(R.dimen.height2).toInt()
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setData()
        viewPager()
    }

    private fun viewPager() {
        val viewPagerAdapter = ViewPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = viewPagerAdapter
        tabs.setupWithViewPager(viewPager)
        supportActionBar?.elevation = 0f
    }



    private fun setData() {
        val dataUser = intent.getParcelableExtra(EXTRA_DETAIL) as User
        Glide.with(this)
            .load(dataUser.ava)
            .apply(RequestOptions().override(140, 140))
            .into(imgDetailAva)
        tvFullName.text = dataUser.name
        tvUsername.text = dataUser.username
        tvCompany.text = dataUser.company
        tvLocation.text = dataUser.location
        tvFollower.text = getString(R.string.follower, dataUser.follower)
        tvFollowing.text = getString(R.string.following, dataUser.following)
        tvRepository.text = getString(R.string.repository, dataUser.repository)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}