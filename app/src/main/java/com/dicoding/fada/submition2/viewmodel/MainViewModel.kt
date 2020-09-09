package com.dicoding.fada.submition2.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.fada.submition2.BuildConfig
import com.dicoding.fada.submition2.model.User
import com.dicoding.fada.submition2.view.MainActivity
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpClient.log
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class MainViewModel : ViewModel() {
    private val listUserImmutable = ArrayList<User>()
    private val listUserMutable = MutableLiveData<ArrayList<User>>()

    fun getListUser(): LiveData<ArrayList<User>> {
        return listUserMutable
    }

    fun getDataUser(context: Context) {
        val httpClient = AsyncHttpClient()
        httpClient.addHeader("Authorization", BuildConfig.GITHUB_TOKEN)
        httpClient.addHeader("User-Agent", "request")
        val urlClient = "https://api.github.com/users"

        httpClient.get(urlClient, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                val result = responseBody?.let { String(it) }
                log.d(MainActivity.TAG, result)
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val usernameLogin = jsonObject.getString("login")
                        getDataUserDetail(usernameLogin, context)
                    }
                }catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error?.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun getDataUserDetail(usernameLogin: String, context: Context) {
        val httpClient = AsyncHttpClient()
        httpClient.addHeader("Authorization", BuildConfig.GITHUB_TOKEN)
        httpClient.addHeader("User-Agent", "request")
        val urlClient = "https://api.github.com/users/$usernameLogin"

        httpClient.get(urlClient, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                Log.d(MainActivity.TAG, result)

                try {
                    val jsonObject = JSONObject(result)
                    val dataUser = User()
                    dataUser.name = jsonObject.getString("name")
                    dataUser.username = jsonObject.getString("login")
                    dataUser.ava = jsonObject.getString("avatar_url")
                    dataUser.company = jsonObject.getString("company")
                    dataUser.location = jsonObject.getString("location")
                    dataUser.repository = jsonObject.getString("public_repos")
                    dataUser.follower = jsonObject.getString("followers")
                    dataUser.following = jsonObject.getString("following")
                    listUserImmutable.add(dataUser)
                    listUserMutable.postValue(listUserImmutable)

                } catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getDataUserSearch(query: String, context: Context) {
        val httpClient = AsyncHttpClient()
        httpClient.addHeader("Authorization", BuildConfig.GITHUB_TOKEN)
        httpClient.addHeader("User-Agent", "request")
        val urlClient = "https://api.github.com/search/users?q=$query"

        httpClient.get(urlClient, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                log.d(MainActivity.TAG, result)
                try {
                    listUserImmutable.clear()
                    val jsonArray = JSONObject(result)
                    val item = jsonArray.getJSONArray("items")
                    for (i in 0 until item.length()) {
                        val jsonObject = item.getJSONObject(i)
                        val username = jsonObject.getString("login")
                        getDataUserDetail(username, context)
                    }
                }catch (e: Exception) {
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                val errorMessage = when (statusCode) {
                    401 -> "$statusCode : Bad Request"
                    403 -> "$statusCode : Forbidden"
                    404 -> "$statusCode : Not Found"
                    else -> "$statusCode : ${error.message}"
                }
                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}