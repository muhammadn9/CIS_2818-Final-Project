package com.muhammadnaseem.cis_2818_project4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment

class BitcoinFragment : Fragment() {

    private lateinit var bitcoinButton: Button
    private lateinit var bitcoinImage: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bitcoin, container, false)

        bitcoinButton = view.findViewById(R.id.bitcoinButton)
        bitcoinImage = view.findViewById(R.id.bitcoinImage)

        bitcoinButton.setOnClickListener {
            toggleImageVisibility()
        }

        return view
    }

    private fun toggleImageVisibility() {
        if (bitcoinImage.visibility == View.VISIBLE) {
            bitcoinImage.visibility = View.INVISIBLE
        } else {
            bitcoinImage.visibility = View.VISIBLE
        }
    }
}
