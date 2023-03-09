package com.example.sampleappproject.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sampleappproject.MainActivity
import com.example.sampleappproject.fragments.CharacterFragment
import com.example.sampleappproject.R
import com.example.sampleappproject.models.Result

class Adapter(private val characterList: List<Result>) : RecyclerView.Adapter<Adapter.MainViewHolder>() {

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val characterName :TextView= itemView.findViewById(R.id.characterName)
        val characterAvatar : ImageView = itemView.findViewById(R.id.characterAvatar)
        val characterGender : TextView  = itemView.findViewById(R.id.characterGender)
        val characterSpecies : TextView  = itemView.findViewById(R.id.characterSpecies)
        val characterStatus: TextView  = itemView.findViewById(R.id.characterStatus)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.apply {
            characterName.text = characterList[position].name
            characterGender.text = characterList[position].gender
            characterStatus.text = characterList[position].status
            characterSpecies.text = characterList[position].species

            Glide.with(holder.itemView.context).load(characterList[position].image).into(characterAvatar)

        }
        holder.itemView.setOnClickListener { v ->
            val activity = v?.context as AppCompatActivity
            val fragment =  CharacterFragment()
            var bundle  =Bundle()
            bundle.putString("title" , characterList[position].name)
            bundle.putString("image" , characterList[position].image)
            bundle.putString("location" , characterList[position].location.name)
            fragment.arguments = bundle

            activity.supportFragmentManager.beginTransaction().setReorderingAllowed(true).replace(R.id.mainActivity, fragment)
                .addToBackStack(null).commit()

        }


    }
}


