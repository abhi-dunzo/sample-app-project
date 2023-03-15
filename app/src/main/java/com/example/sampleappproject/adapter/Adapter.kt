package com.example.sampleappproject.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.contentValuesOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sampleappproject.MainActivity
import com.example.sampleappproject.fragments.CharacterFragment
import com.example.sampleappproject.R
import com.example.sampleappproject.models.Result

//TODO list adapter with diff callback difference with adapter
//TODO helps with paging
class Adapter(private val characterList: List<Result>) :
    RecyclerView.Adapter<Adapter.MainViewHolder>() {
    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //other ways for itemView.findViewById
        val characterName: TextView = itemView.findViewById(R.id.characterName)
        val characterAvatar: ImageView = itemView.findViewById(R.id.characterAvatar)
        val characterGender: TextView = itemView.findViewById(R.id.characterGender)
        val characterSpecies: TextView = itemView.findViewById(R.id.characterSpecies)
        val characterStatus: TextView = itemView.findViewById(R.id.characterStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    //TODO trailing commas in kotlin
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.apply {
            characterName.text = characterList[position].name
            characterGender.text = characterList[position].gender
            characterStatus.text = characterList[position].status
            characterSpecies.text = characterList[position].species
            Glide.with(holder.itemView.context).load(characterList[position].image)
                .placeholder(com.bumptech.glide.R.drawable.abc_spinner_mtrl_am_alpha)
                .error(com.google.android.material.R.drawable.mtrl_ic_error)
                .into(characterAvatar)
        }
        //TODO can be handled in some other way
        holder.itemView.setOnClickListener { v ->
            //TODO context and its types
            val activity = v?.context as AppCompatActivity
            val fragment = CharacterFragment()
            //TODO warnings
            val bundle = Bundle()
            //TODO practices
            bundle.putString(R.string.Title.toString(), characterList[position].name)
            bundle.putString(R.string.image_url.toString(), characterList[position].image)
            bundle.putString(
                R.string.location_name.toString(),
                characterList[position].location.name
            )
            fragment.arguments = bundle
            //TODO setReorderingAllowed & addToBackStack
            activity.supportFragmentManager.beginTransaction().setReorderingAllowed(true)
                .replace(R.id.mainActivity, fragment)
                .addToBackStack(null).commit()

        }


    }
}


