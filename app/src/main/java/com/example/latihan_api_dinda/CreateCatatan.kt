package com.example.latihan_api_dinda

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.latihan_api_dinda.databinding.ActivityCreateCatatanBinding
import com.example.latihan_api_dinda.entities.Catatan
import kotlinx.coroutines.launch

class CreateCatatan : AppCompatActivity() {
    private lateinit var binding: ActivityCreateCatatanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCreateCatatanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupEvents()
    }

    private fun setupEvents() {
        binding.tombolSimpan.setOnClickListener {
            val judul = binding.inputJudul.text.toString()
            val isi = binding.inputIsi.text.toString()

            if (judul.isEmpty() || isi.isEmpty()) {
                displayMessage("Judul dan isi catatan harus diisi")
                return@setOnClickListener
            }

            val payload = Catatan(
                id = null,
                judul = judul,
                isi = isi,
                userId = 1
            )

            lifecycleScope.launch {
                try {
                    val response = RetrofitClient.catatanRepository.createCatatan(payload)
                    if (response.isSuccessful) {
                        displayMessage("Catatan berhasil dibuat")
                        finish() // kembali ke MainActivity
                    } else {
                        displayMessage("Gagal: ${response.message()}")
                    }
                } catch (e: Exception) {
                    displayMessage("Error: ${e.localizedMessage}")
                    e.printStackTrace()
                }
            }
        }
    }

    private fun displayMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
