package com.example.myapp.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("results") val results: List<ApiPerson>
)

data class ApiPerson(
    @SerializedName("name") val name: ApiName,
    @SerializedName("dob") val dob: ApiDob,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("location") val location: ApiLocation
)

data class ApiName(
    @SerializedName("first") val first: String,
    @SerializedName("last") val last: String
)

data class ApiDob(
    @SerializedName("date") val date: String,
    @SerializedName("age") val age: Int
)

data class ApiLocation(
    @SerializedName("street") val street: ApiStreet,
    @SerializedName("city") val city: String,
    @SerializedName("state") val state: String,
    @SerializedName("country") val country: String,
    @SerializedName("postcode") val postcode: String
)

data class ApiStreet(
    @SerializedName("name") val name: String
)

