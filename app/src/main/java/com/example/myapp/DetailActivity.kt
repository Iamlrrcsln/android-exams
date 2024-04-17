package com.example.myapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.myapp.databinding.ActivityDetailBinding
import com.example.myapp.model.Person

class DetailActivity : AppCompatActivity() {

    companion object {
        const val PERSON_EXTRA = "person"
    }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val person = intent.getParcelableExtra<Person>(PERSON_EXTRA)
        if (person != null) {
            displayPersonDetails(person)
        } else {
            Toast.makeText(this, "Error: Person data not found", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun displayPersonDetails(person: Person) {
        binding.apply {
            "${person.firstName} ${person.lastName}".also { fullName.text = it }
            "Age: ${person.age}".also { age.text = it }
            "Email: ${person.email}".also { email.text = it }
            "Phone: ${person.phone}".also { phone.text = it }
            "Birthday: ${person.birthday}".also { birthday.text = it }
            "Address: ${person.address.street}, ${person.address.city}, ${person.address.state}, ${person.address.country} ${person.address.postcode}".also {
                address.text = it
            }
            "Contact Person: ${person.contactPerson}".also { contact.text = it }
            "Contact Person's Phone: ${person.contactPersonPhone}".also {
                contactPersonPhone.text = it
            }
        }
    }
}