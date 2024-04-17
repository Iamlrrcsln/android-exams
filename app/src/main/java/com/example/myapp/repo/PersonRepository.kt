package com.example.myapp.repo

import com.example.myapp.MainActivity.Companion.PAGE_SIZE
import com.example.myapp.model.Address
import com.example.myapp.model.Person
import com.example.myapp.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Locale

class PersonRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://randomuser.me/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    private var cachedPersons: List<Person> = emptyList()

    suspend fun getPersons(page: Int): List<Person> {
        if (cachedPersons.isNotEmpty()) {
            return cachedPersons
        } else {
            val response = apiService.getPersons(page, PAGE_SIZE).results
            cachedPersons = response.map { apiPerson ->
                val formattedBirthday = formatDate(apiPerson.dob.date)
                Person(
                    apiPerson.name.first,
                    apiPerson.name.last,
                    formattedBirthday,
                    apiPerson.dob.age,
                    apiPerson.email,
                    apiPerson.phone,
                    Address(
                        apiPerson.location.street.name,
                        apiPerson.location.city,
                        apiPerson.location.state,
                        apiPerson.location.country,
                        apiPerson.location.postcode
                    ),
                    apiPerson.name.first,
                    apiPerson.phone
                )
            }
            return cachedPersons
        }
    }

    suspend fun refreshData(): List<Person> {
        val response = apiService.getPersons(page = 1, results = PAGE_SIZE).results
        cachedPersons = response.map { apiPerson ->
            val formattedBirthday = formatDate(apiPerson.dob.date)
            Person(
                apiPerson.name.first,
                apiPerson.name.last,
                formattedBirthday,
                apiPerson.dob.age,
                apiPerson.email,
                apiPerson.phone,
                Address(
                    apiPerson.location.street.name,
                    apiPerson.location.city,
                    apiPerson.location.state,
                    apiPerson.location.country,
                    apiPerson.location.postcode
                ),
                apiPerson.name.first,
                apiPerson.phone
            )
        }
        return cachedPersons
    }

    suspend fun getNewPerson(): Person {
        val response = apiService.getPersons(1, PAGE_SIZE)
        if (response.results.isNotEmpty()) {
            val apiPerson = response.results[0]
            val formattedBirthday = formatDate(apiPerson.dob.date)
            return Person(
                apiPerson.name.first,
                apiPerson.name.last,
                formattedBirthday,
                apiPerson.dob.age,
                apiPerson.email,
                apiPerson.phone,
                Address(
                    apiPerson.location.street.name,
                    apiPerson.location.city,
                    apiPerson.location.state,
                    apiPerson.location.country,
                    apiPerson.location.postcode
                ),
                apiPerson.name.first,
                apiPerson.phone
            )
        } else {
            throw Exception("No person found")
        }
    }

    private fun formatDate(birthdayString: String): String {
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())

        val birthdayDate = inputDateFormat.parse(birthdayString)
        return outputDateFormat.format(birthdayDate!!)
    }
}
