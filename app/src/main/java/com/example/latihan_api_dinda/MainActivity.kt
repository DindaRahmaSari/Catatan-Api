package com.example.latihan_api_dinda

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihan_api_dinda.adapter.CatatanAdapter
import com.example.latihan_api_dinda.databinding.ActivityMainBinding
import com.example.latihan_api_dinda.entities.Catatan
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: CatatanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        setupEvents()
    }


    private fun setupRecyclerView() {
        adapter = CatatanAdapter(mutableListOf(), object : CatatanAdapter.CatatanItemEvents {
            override fun onEdit(catatan: Catatan) {

                val intent = Intent(this@MainActivity, EditCatatanActivity::class.java)
                intent.putExtra("id_catatan", catatan.id)
                startActivity(intent)
            }
        })
        binding.container.adapter = adapter
        binding.container.layoutManager = LinearLayoutManager(this)
    }

    private fun setupEvents() {
        binding.btnNavigate.setOnClickListener {
            val intent = Intent(this, CreateCatatan::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.catatanRepository.getCatatan()
                if (!response.isSuccessful) {
                    displayMessage("Gagal: ${response.message()}")
                    return@launch
                }

                val data = response.body()
                if (data != null) {
                    adapter.updateDataset(data) // update RecyclerView
                } else {
                    displayMessage("Tidak ada data")
                }
            } catch (e: Exception) {
                displayMessage("Error: ${e.localizedMessage}")
                e.printStackTrace()
            }
        }
    }

    private fun displayMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
