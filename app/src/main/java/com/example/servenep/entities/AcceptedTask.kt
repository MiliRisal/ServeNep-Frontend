package com.example.servenep.entities

import android.os.Parcel
import android.os.Parcelable

data class AcceptedTask (
    val userid : String? = null,
    val descTitle : String? = null,
    val description : String? = null,
    val time : String? = null,
    val rate : String? = null,
    val date : String? = null,
    val longitude : Double? = null,
    val latitude : Double? = null,
    val acceptedby : String? = null,
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
        parcel.writeString(userid)
        parcel.writeString(descTitle)
        parcel.writeString(description)
        parcel.writeString(time)
        parcel.writeString(rate)
        parcel.writeString(date)
        parcel.writeValue(longitude)
        parcel.writeValue(latitude)
        parcel.writeString(acceptedby)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AcceptedTask> {
        override fun createFromParcel(parcel: Parcel): AcceptedTask {
            return AcceptedTask(parcel)
        }

        override fun newArray(size: Int): Array<AcceptedTask?> {
            return arrayOfNulls(size)
        }
    }
}