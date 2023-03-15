package com.example.sampleappproject.paging

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sampleappproject.MainActivity
import com.example.sampleappproject.fragments.CharacterFragment
import com.example.sampleappproject.R
import com.example.sampleappproject.models.CharacterList
import com.example.sampleappproject.models.Result

//TODO list adapter with diff callback difference with adapter
//TODO helps with paging
class CharacterPagingAdapter :
    PagingDataAdapter<Result, CharacterPagingAdapter.CharacterViewHolder>(
        COMPARATOR
    ) {
    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //other ways for itemView.findViewById
        val characterName: TextView = itemView.findViewById(R.id.characterName)
        val characterAvatar: ImageView = itemView.findViewById(R.id.characterAvatar)
        val characterGender: TextView = itemView.findViewById(R.id.characterGender)
        val characterSpecies: TextView = itemView.findViewById(R.id.characterSpecies)
        val characterStatus: TextView = itemView.findViewById(R.id.characterStatus)
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Result>() {
            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)
        return CharacterViewHolder(view)
    }

    //TODO trailing commas in kotlin
    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = getItem(position)
        holder.apply {
            if (item != null) {
                characterName.text = item.name
                characterGender.text = item.gender
                characterStatus.text = item.status
                characterSpecies.text = item.species
                Glide.with(holder.itemView.context).load(item.image)
                    .placeholder(com.bumptech.glide.R.drawable.abc_spinner_mtrl_am_alpha)
                    .error(com.google.android.material.R.drawable.mtrl_ic_error)
                    .into(characterAvatar)

            }
        }

        holder.itemView.setOnClickListener { v ->
            //TODO context and its types
            val activity = v?.context as AppCompatActivity
            val fragment = CharacterFragment()
            //TODO warnings
            val bundle = Bundle()
            //TODO practices

            bundle.putString(R.string.Title.toString(), item?.name)
            bundle.putString(R.string.image_url.toString(), item?.image)
            bundle.putString(R.string.location_name.toString(), item?.location?.name)
            fragment.arguments = bundle
            //TODO setReorderingAllowed & addToBackStack
            activity.supportFragmentManager.beginTransaction().setReorderingAllowed(true)
                .replace(R.id.mainActivity, fragment)
                .addToBackStack(null).commit()

        }


    }
}


