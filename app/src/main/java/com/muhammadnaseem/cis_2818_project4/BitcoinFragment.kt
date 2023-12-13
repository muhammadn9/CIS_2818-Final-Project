package com.muhammadnaseem.cis_2818_project4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class BitcoinFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bitcoin, container, false)

        // Handle button click
        val bitcoinButton: Button = view.findViewById(R.id.bitcoinButton)
        bitcoinButton.setOnClickListener {
            // Add your logic for button click
            // For example, you can show a toast
            // Toast.makeText(requireContext(), "Bitcoin Button Clicked", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}
