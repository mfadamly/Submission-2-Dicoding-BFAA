package com.dicoding.fada.submition2.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Follower (
    var name: String? = "",
    var username: String? = "",
    var ava: String? = "",
    var company: String? = "",
    var location: String? = "",
    var repository: String? = "",
    var follower: String? = "",
    var following: String? = ""
) : Parcelable