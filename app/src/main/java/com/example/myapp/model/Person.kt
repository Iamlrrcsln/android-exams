package com.example.myapp.model

import android.os.Parcel
import android.os.Parcelable

data class Person(
    val firstName: String,
    val lastName: String,
    val birthday: String,
    val age: Int,
    val email: String,
    val phone: String,
    val address: Address,
    val contactPerson: String,
    val contactPersonPhone: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readParcelable(Address::class.java.classLoader)!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(firstName)
        parcel.writeString(lastName)
        parcel.writeString(birthday)
        parcel.writeInt(age)
        parcel.writeString(email)
        parcel.writeString(phone)
        parcel.writeParcelable(address, flags)
        parcel.writeString(contactPerson)
        parcel.writeString(contactPersonPhone)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Person> {
        override fun createFromParcel(parcel: Parcel): Person {
            return Person(parcel)
        }

        override fun newArray(size: Int): Array<Person?> {
            return arrayOfNulls(size)
        }
    }
}

data class Address(
    val street: String,
    val city: String,
    val state: String,
    val country: String,
    val postcode: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(street)
        parcel.writeString(city)
        parcel.writeString(state)
        parcel.writeString(country)
        parcel.writeString(postcode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Address> {
        override fun createFromParcel(parcel: Parcel): Address {
            return Address(parcel)
        }

        override fun newArray(size: Int): Array<Address?> {
            return arrayOfNulls(size)
        }
    }
}

