package com.example.github.model
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.github.database.DataConverter
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "issues")
data class GitApiModel(
    @SerializedName("comments_url")
    val comments_url : String?,
    @SerializedName("id")
    val id : Int=0,
    @PrimaryKey
    @SerializedName("number")
    var number : Int=0,
    @SerializedName("title")
    val title : String?,
    @Embedded
    @TypeConverters(DataConverter::class)
    @SerializedName("user")
    val user : User?,
    @SerializedName("comments")
    val comments : Int=0,
    @SerializedName("created_at")
    val created_at : String?,
    @SerializedName("updated_at")
    val updated_at : String?,
    @SerializedName("closed_at")
    val closed_at : String?,
    @SerializedName("body")
    val body : String?
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readParcelable(User::class.java.classLoader),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(comments_url)
        parcel.writeInt(id)
        parcel.writeInt(number)
        parcel.writeString(title)
        parcel.writeParcelable(user, flags)
        parcel.writeInt(comments)
        parcel.writeString(created_at)
        parcel.writeString(updated_at)
        parcel.writeString(closed_at)
        parcel.writeString(body)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GitApiModel> {
        override fun createFromParcel(parcel: Parcel): GitApiModel {
            return GitApiModel(parcel)
        }

        override fun newArray(size: Int): Array<GitApiModel?> {
            return arrayOfNulls(size)
        }
    }
    fun getDate(): Date? {
        val date1 = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(updated_at!!)
        return date1

    }

}


data class User (

    @SerializedName("login")
    val login : String?,
    @SerializedName("avatar_url")
    val avatar_url : String?,
    @SerializedName("type")
    val type : String?
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(login)
        parcel.writeString(avatar_url)
        parcel.writeString(type)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

}