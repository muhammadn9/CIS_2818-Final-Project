package com.muhammadnaseem.cis_2818_project4

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment

class LitecoinFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_litecoin, container, false)

        // Handle CheckBox state change
        val litecoinCheckBox: CheckBox = view.findViewById(R.id.litecoinCheckBox)
        litecoinCheckBox.setOnCheckedChangeListener { _, isChecked ->
            // Add your logic for CheckBox state change
            // For example, you can show a toast with the state
             Toast.makeText(requireContext(), "CheckBox Checked: $isChecked", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}
