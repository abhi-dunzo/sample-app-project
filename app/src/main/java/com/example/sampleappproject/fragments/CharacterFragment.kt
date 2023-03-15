package com.example.sampleappproject.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import com.bumptech.glide.Glide
import com.example.sampleappproject.MainActivity
import com.example.sampleappproject.R
import org.w3c.dom.Text

class CharacterFragment : Fragment() {
    private var title: String? = null
    private var imageUrl: String? = null
    private var location :String? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(R.string.Title.toString())
            imageUrl = it.getString(R.string.image_url.toString())
            location = it.getString(R.string.location_name.toString())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_character, container, false)
        val titleField: TextView = view.findViewById(R.id.charTitle)
        val charImage: ImageView = view.findViewById(R.id.charImage)
        val locationView : TextView  = view.findViewById(R.id.charLocation)
        locationView.text = location
        Glide.with(view).load(imageUrl)
            .placeholder(com.google.android.material.R.drawable.mtrl_ic_error)
            .error(com.google.android.material.R.drawable.mtrl_ic_error)
            .into(charImage)

        titleField.text = title

//        val callback = object : OnBackPressedCallback(true)
//        {
//            override fun handleOnBackPressed() {
//                val i = Intent( activity , MainActivity::class.java)
//                activity?.startActivity(i)
//            }
//        }
//        requireActivity().onBackPressedDispatcher.addCallback(callback)

        return view
    }


}