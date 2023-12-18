package com.example.dictionary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionary.databinding.MeaningRecyclerRowBinding

class meaningadapter(private var meaningList : List<Meaning>) : RecyclerView.Adapter<meaningadapter.MeaningViewHolder>() {

class MeaningViewHolder( private val binding: MeaningRecyclerRowBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(meaning: Meaning){
        binding.partOfSpeechTextview.text = meaning.partOfSpeech
        binding.definitionsTextview.text = meaning.definitions.joinToString("\n\n") {
            var currentIndex = meaning.definitions.indexOf(it)
            (currentIndex+1).toString()+". "+it.definition.toString()
        }
        if(meaning.synonyms.isEmpty()) {
            binding.synonymsTitleTextview.visibility = View.GONE
            binding.synonymsTextview.visibility = View.GONE
            binding.synonymsTextview.text = meaning.synonyms.joinToString {","}
        }else{
            binding.synonymsTitleTextview.visibility = View.VISIBLE
            binding.synonymsTextview.visibility = View.VISIBLE

        }
        if(meaning.antonyms.isEmpty()) {
            binding.antonymsTitleTextview.visibility = View.GONE
            binding.antonymsTextview.visibility = View.GONE
            binding.antonymsTextview.text = meaning.synonyms.joinToString {","}
        }else {
            binding.antonymsTitleTextview.visibility = View.VISIBLE
            binding.antonymsTextview.visibility = View.VISIBLE
        }
     //BIND ALL THE MEANINGS

    }
}

    fun updateNewData(newMeaningList : List<Meaning>){
        meaningList = newMeaningList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningViewHolder {
        val binding = MeaningRecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MeaningViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return meaningList.size
    }

    override fun onBindViewHolder(holder: MeaningViewHolder, position: Int) {
        holder.bind(meaningList[position])

    }
}