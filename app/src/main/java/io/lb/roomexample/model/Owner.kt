package io.lb.roomexample.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

data class Owner(
    @field:SerializedName("avatar_url")
    val avatarUrl: String
)

class TypeConverterOwner {
    private val gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data: String?): Owner? {
        if (data.isNullOrEmpty()) {
            return null
        }
        val listType = object : TypeToken<Owner?>() {}.type
        return gson.fromJson<Owner?>(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObject: Owner?): String {
        return gson.toJson(someObject)
    }
}