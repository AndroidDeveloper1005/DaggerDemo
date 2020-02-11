package com.example.github_without_dagger.data

import androidx.room.Entity
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
class BuiltBy() : Serializable{
    @SerializedName("username")
    @Expose
    var username: String? = null
    @SerializedName("href")
    @Expose
    var href: String? = null
    @SerializedName("avatar")
    @Expose
    var avatar: String? = null

}