package com.example.github.database

import androidx.room.TypeConverter
import com.example.github.model.GitApiModel
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson
import javax.xml.transform.Source


//This class is to convert object data to string for room database and back to object
class DataConverter {
//From Object to string
    @TypeConverter
    fun fromArticlesList( articles: Source): String? {
        if (articles == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<Source>() {

        }.type
        return gson.toJson(articles, type)
    }
//From String to object
    @TypeConverter
    fun toArticlesList(articles: String?): Source? {
        if (articles == null) {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<List<GitApiModel>>() {

        }.type
        return gson.fromJson<Source>(articles, type)
    }
}