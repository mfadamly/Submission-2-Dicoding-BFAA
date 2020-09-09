package com.dicoding.fada.submition2.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.fada.submition2.BuildConfig
import com.dicoding.fada.submition2.model.Follower
import com.dicoding.fada.submition2.view.FollowerFragment
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Exception

class FollowerViewModel : ViewModel() {
    private val listFollowerImmutable = ArrayList<Follower>()
    private val listFollowerMutable = MutableLiveData<ArrayList<Follower>>()

    fun getListFollower(): LiveData<ArrayList<Follower>> {
        return listFollowerMutable
    }

    fun getData(context: Context, id: String) {
        val httpClient = AsyncHttpClient()
        httpClient.addHeader("Authorization", BuildConfig.GITHUB_TOKEN)
        httpClient.addHeader("User-Agent", "request")
        val urlClient = "https://api.github.com/users/$id/followers"

        httpClient.get(urlClient, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                val result = String(responseBody)
                Log.d(FollowerFragment.TAG, result)
                try {
                    val jsonArray = JSONArray(result)
                    for (i in 0 until jsonArray.length()) {
                        val jsonObject = jsonArray.getJSONObject(i)
                        val usernameLogin = jsonObject.getString("login")
                        getDataDetail(usernameLogin, context)
                    }
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

    private fun getDataDetail(usernameLogin: String, context: Context) {
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
                Log.d(FollowerFragment.TAG, result)

                try {
                    val jsonObject = JSONObject(result)
                    val dataUser = Follower()
                    dataUser.name = jsonObject.getString("name")
                    dataUser.username = jsonObject.getString("login")
                    dataUser.ava = jsonObject.getString("avatar_url")
                    dataUser.company = jsonObject.getString("company")
                    dataUser.location = jsonObject.getString("location")
                    dataUser.repository = jsonObject.getString("public_repos")
                    dataUser.follower = jsonObject.getString("followers")
                    dataUser.following = jsonObject.getString("following")
                    listFollowerImmutable.add(dataUser)
                    listFollowerMutable.postValue(listFollowerImmutable)

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
}