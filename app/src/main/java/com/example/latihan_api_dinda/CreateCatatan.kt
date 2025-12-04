package com.example.latihan_api_dinda

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.latihan_api_dinda.databinding.ActivityCreateCatatanBinding
import com.example.latihan_api_dinda.entities.Catatan
import kotlinx.coroutines.launch
import okhttp3.Response


class CreateCatatan : AppCompatActivity() {
    private lateinit var binding: ActivityCreateCatatanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding=ActivityCreateCatatanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupEvents()
    }
    fun setupEvents() {
        binding.tombolSimpan.setOnClickListener {
            val judul =binding.inputJudul.text.toString()
            val isi = binding.inputIsi.text.toString()

            if(judul.isEmpty() || isi.isEmpty()) {
                displayMessage("judul dan isi catatan harus di isi")
                return@setOnClickListener
            }

            val payload = Catatan(
                judul = judul,
                isi = isi,
                userId = 1,
                id = null,
            )

            lifecycleScope.launch {
                val response = RetrofitClient.catatanRepository.createCatatan(payload)
                if (response.isSuccessful){
                    displayMessage("Catatan berhasil di buat")
                    finish()

                    val intent = Intent(this@CreateCatatan, MainActivity::class.java)
                    startActivity(intent)

                } else {
                    displayMessage("Gagal : ${response.message()}")
                }
            }

        }
    }
    fun displayMessage(message: String) {
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }
}