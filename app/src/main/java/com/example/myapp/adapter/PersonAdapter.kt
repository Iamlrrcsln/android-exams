package com.example.myapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.model.Person

class PersonAdapter(
    private var persons: MutableList<Person>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(person: Person)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newPersons: List<Person>) {
        persons.clear()
        persons.addAll(newPersons)
        notifyDataSetChanged()
    }

    fun addPerson(person: Person) {
        persons.add(person)
        notifyItemInserted(persons.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_person, parent, false)
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = persons[position]
        holder.bind(person)
    }

    override fun getItemCount(): Int {
        return persons.size
    }

    inner class PersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val fullNameTextView: TextView = itemView.findViewById(R.id.full_name_text_view)
        private val ageTextView: TextView = itemView.findViewById(R.id.age_text_view)
        private val emailTextView: TextView = itemView.findViewById(R.id.email_text_view)
        private val phoneTextView: TextView = itemView.findViewById(R.id.phone_text_view)
        private val birthdayTextView: TextView = itemView.findViewById(R.id.birthday_text_view)
        private val addressTextView: TextView = itemView.findViewById(R.id.address_text_view)
        private val contactPersonTextView: TextView =
            itemView.findViewById(R.id.contact_person_text_view)
        private val contactPersonPhoneTextView: TextView =
            itemView.findViewById(R.id.contact_person_phone_text_view)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(person: Person) {
            "${person.firstName} ${person.lastName}".also { fullNameTextView.text = it }
            "Age: ${person.age}".also { ageTextView.text = it }
            "Email: ${person.email}".also { emailTextView.text = it }
            "Phone: ${person.phone}".also { phoneTextView.text = it }
            "Birthday: ${person.birthday}".also { birthdayTextView.text = it }
            "Address: ${person.address.street}, ${person.address.city}, ${person.address.state}, ${person.address.country} ${person.address.postcode}".also {
                addressTextView.text = it
            }
            "Contact Person: ${person.contactPerson}".also { contactPersonTextView.text = it }
            "Contact Person's Phone: ${person.contactPersonPhone}".also {
                contactPersonPhoneTextView.text = it
            }
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val person = persons[position]
                listener.onItemClick(person)
            }
        }
    }
}


