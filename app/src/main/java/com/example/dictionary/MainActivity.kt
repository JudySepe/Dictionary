package com.example.dictionary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Adapter
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var adapter: meaningadapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
         setContentView(binding.root)

        binding.searchBtn.setOnClickListener {
            val word = binding.searchInput.text.toString()
            getMeaning(word)

        }
        adapter = meaningadapter(emptyList())
        binding.meaningRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.meaningRecyclerView.adapter = adapter

    }

    private fun getMeaning(word : String){
        setInProgress(true)
        GlobalScope.launch {
            try {
            val response = Retrofitinstance.dictionaryAPI.getMeaning(word)
             if(response.body()==null) {
                 throw (Exception())
             }
                 runOnUiThread {
                     setInProgress(false)
                     response.body()?.first()?.let {
                         setUI(it)
                     }
                 }
        }catch (e : Exception){
            runOnUiThread{
                setInProgress(false)
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_LONG).show()
            }

            }

        }
    }
     private fun setUI(response : wordresult){
         binding.wordTextview.text = response.word
         binding.phoneticTextview.text = response.phonetic
         adapter.updateNewData(response.meanings)


     }


    private fun setInProgress(inProgressBar: Boolean){
        if(inProgressBar){
            binding.searchBtn.visibility = View.INVISIBLE
            binding.progressBar.visibility = View.INVISIBLE
        }else{
        binding.searchBtn.visibility = View.VISIBLE
        binding.progressBar.visibility = View.INVISIBLE

        }

    }
}