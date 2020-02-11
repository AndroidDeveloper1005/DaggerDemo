package com.example.myapplication.data

import androidx.annotation.NonNull
import androidx.databinding.ObservableBoolean
import androidx.room.*
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
class TrendingRepository : Serializable{

    //dont want this field to become a colum of github table
    @Ignore
    var isExpanded : ObservableBoolean= ObservableBoolean(false)

    @NonNull
    @PrimaryKey
    var id: Long? = null

    @ColumnInfo(name= "TimeStamp")
    @SerializedName("created_at")
    @Expose
    var createdAt: Long? = 0

    @SerializedName("author")
    @Expose
    var author: String? = ""

    @ColumnInfo(name ="Name")
    @SerializedName("name")
    @Expose
    var name: String? = ""

    @SerializedName("avatar")
    @Expose
    var avatar: String? = ""

    @SerializedName("url")
    @Expose
    var url: String? = ""

    @SerializedName("description")
    @Expose
    var description: String? = ""

    @SerializedName("language")
    @Expose
    var language: String? = ""

    @SerializedName("languageColor")
    @Expose
    var languageColor: String? = ""

    @ColumnInfo(name = "Stars")
    @SerializedName("stars")
    @Expose
    var stars: Long = 0

    @SerializedName("forks")
    @Expose
    var forks: Long = 0

    @SerializedName("currentPeriodStars")
    @Expose
    var currentPeriodStars: Long = 0

    //can be used as per further project extension
/*
    @SerializedName("builtBy")
    @Expose
    @Embedded
    var builtBy: List<BuiltBy>? = null
*/

}