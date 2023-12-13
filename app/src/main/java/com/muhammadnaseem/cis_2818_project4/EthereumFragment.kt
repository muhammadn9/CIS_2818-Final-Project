package com.muhammadnaseem.cis_2818_project4

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

class EthereumFragment : Fragment() {

    private lateinit var ethereumEditText: EditText
    private lateinit var resultTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ethereum, container, false)

        ethereumEditText = view.findViewById(R.id.ethereumEditText)
        resultTextView = view.findViewById(R.id.resultTextView)

        ethereumEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not used in this example
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Not used in this example
            }

            override fun afterTextChanged(s: Editable?) {
                // Update TextView with entered text
                resultTextView.text = "Entered text: ${s.toString()}"
            }
        })

        return view
    }
}
