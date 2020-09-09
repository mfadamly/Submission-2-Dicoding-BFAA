package com.dicoding.fada.submition2.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.fada.submition2.R
import com.dicoding.fada.submition2.model.User
import com.dicoding.fada.submition2.viewmodel.ListUserAdapter
import com.dicoding.fada.submition2.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var listUser: ArrayList<User> = ArrayList()
    private lateinit var listAdapter: ListUserAdapter
    private lateinit var mainViewModel: MainViewModel

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listAdapter = ListUserAdapter(listUser)
        mainViewModel = ViewModelProvider (
                this, ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        configMainViewModel(listAdapter)
        searchUser()
        viewConfig()
        runGetDataGit()
    }

    private fun configMainViewModel(adapter: ListUserAdapter) {
        mainViewModel.getListUser().observe(this, Observer { listUsers ->
            if (listUsers != null) {
                adapter.setData(listUsers)
                showLoading(false)
            }
        })
    }

    private fun viewConfig() {
        rvUser.layoutManager = LinearLayoutManager(this)
        rvUser.setHasFixedSize(true)

        listAdapter.notifyDataSetChanged()
        rvUser.adapter = listAdapter
    }

    private fun runGetDataGit() {
        mainViewModel.getDataUser(applicationContext)
        showLoading(true)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            PBLoading.visibility = View.VISIBLE
        } else {
            PBLoading.visibility = View.INVISIBLE
        }
    }

    private fun searchUser() {
        Usersearch.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    listUser.clear()
                    mainViewModel.getDataUserSearch(query, applicationContext)
                    showLoading(true)
                } else {
                    return true
                }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.changeLanguage) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }
}