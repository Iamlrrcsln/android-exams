package com.example.myapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.adapter.PersonAdapter
import com.example.myapp.databinding.ActivityMainBinding
import com.example.myapp.model.Person
import com.example.myapp.repo.PersonRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(DelicateCoroutinesApi::class)
class MainActivity : AppCompatActivity(), PersonAdapter.OnItemClickListener {

    companion object {
        const val PAGE_SIZE = 10
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var personRepository: PersonRepository
    private lateinit var personAdapter: PersonAdapter
    private var isLoading = false
    private var isLastPage = false
    private var currentPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        personRepository = PersonRepository()
        personAdapter = PersonAdapter(mutableListOf(), this@MainActivity)

        binding.apply {
            recyclerView.apply {
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = personAdapter
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                        val visibleItemCount = layoutManager.childCount
                        val totalItemCount = layoutManager.itemCount
                        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                        if (!isLoading && !isLastPage) {
                            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                                && firstVisibleItemPosition >= 0
                                && totalItemCount >= PAGE_SIZE
                            ) {
                                addNewPerson()
                            }
                        }
                    }
                })
            }

            swipeRefreshLayout.setOnRefreshListener {
                refreshData()
            }
        }

        loadData()
    }

    private fun loadData() {
        isLoading = true
        GlobalScope.launch(Dispatchers.IO) {
            val persons = personRepository.getPersons(currentPage)
            withContext(Dispatchers.Main) {
                personAdapter.setData(persons)
                isLoading = false
                currentPage++
            }
        }
    }

    private fun refreshData() {
        currentPage = 1
        GlobalScope.launch(Dispatchers.IO) {
            val persons = personRepository.refreshData()
            withContext(Dispatchers.Main) {
                personAdapter.setData(persons)
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun addNewPerson() {
        isLoading = true
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val newPerson = personRepository.getNewPerson()
                withContext(Dispatchers.Main) {
                    personAdapter.addPerson(newPerson)
                    isLoading = false
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    showToast("Error: ${e.message}")
                }
                isLoading = false
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(person: Person) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra(DetailActivity.PERSON_EXTRA, person)
        }
        startActivity(intent)
    }
}




