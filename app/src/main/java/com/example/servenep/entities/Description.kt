package com.example.servenep.entities

import android.os.Parcel
import android.os.Parcelable

data class Description (
    val _id : String? = null,
    val bookedUserId : String? = null,
    val title : String? = null,
    val taskDescription : String? = null,
    val estimatedTime : String? = null,
    val price : String? = null,
    val longitude : Double? = null,
    val latitude : Double? = null,
    val addedby: String? =null
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(bookedUserId)
        parcel.writeString(title)
        parcel.writeString(taskDescription)
        parcel.writeString(estimatedTime)
        parcel.writeString(price)
        parcel.writeValue(longitude)
        parcel.writeValue(latitude)
        parcel.writeString(addedby)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Description> {
        override fun createFromParcel(parcel: Parcel): Description {
            return Description(parcel)
        }

        override fun newArray(size: Int): Array<Description?> {
            return arrayOfNulls(size)
        }
    }
}