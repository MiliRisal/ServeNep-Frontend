package com.example.servenep.entities

import android.os.Parcel
import android.os.Parcelable

data class Users(
    val _id : String? = null,
    val fullName : String? = null,
    val email : String? = null,
    val phone : String? = null,
    val address : String? = null,
    val password : String? = null,
    val role : String? = null,
    val bio : String? =null,
    val category : String? = null,
    val price : String? = null,
    val profileImage : String? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(fullName)
        parcel.writeString(email)
        parcel.writeString(phone)
        parcel.writeString(address)
        parcel.writeString(password)
        parcel.writeString(role)
        parcel.writeString(bio)
        parcel.writeString(category)
        parcel.writeString(price)
        parcel.writeString(profileImage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Users> {
        override fun createFromParcel(parcel: Parcel): Users {
            return Users(parcel)
        }

        override fun newArray(size: Int): Array<Users?> {
            return arrayOfNulls(size)
        }
    }
}
