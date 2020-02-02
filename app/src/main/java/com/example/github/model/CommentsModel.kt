package com.example.github.model

import com.google.gson.annotations.SerializedName

data class CommentsModel(@SerializedName("url") val url : String,
                         @SerializedName("id") val id : Int,
                         @SerializedName("user") val user : User2,
                         @SerializedName("created_at") val created_at : String,
                         @SerializedName("updated_at") val updated_at : String,
                         @SerializedName("body") val body : String
) {
}

data class User2 (
    @SerializedName("login") val login : String,
    @SerializedName("id") val id : Int,
    @SerializedName("avatar_url") val avatar_url : String
){

}