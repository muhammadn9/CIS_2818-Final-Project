package com.muhammadnaseem.cis_2818_project4

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private lateinit var cryptoSpinner: Spinner
    private lateinit var cryptoInfoLayout: LinearLayout
    private lateinit var cryptoNameTextView: TextView
    private lateinit var cryptoSymbolTextView: TextView
    private lateinit var cryptoSupplyTextView: TextView
    private lateinit var cryptoPriceTextView: TextView
    private lateinit var cryptoPercentChangeTextView: TextView
    private lateinit var loadingProgressBar: ProgressBar // Declare loadingProgressBar here

    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cryptoSpinner = findViewById(R.id.cryptoSpinner)
        cryptoInfoLayout = findViewById(R.id.cryptoInfoLayout)
        cryptoNameTextView = findViewById(R.id.cryptoNameTextView)
        cryptoSymbolTextView = findViewById(R.id.cryptoSymbolTextView)
        cryptoSupplyTextView = findViewById(R.id.cryptoSupplyTextView)
        cryptoPriceTextView = findViewById(R.id.cryptoPriceTextView)
        cryptoPercentChangeTextView = findViewById(R.id.cryptoPercentChangeTextView)
        loadingProgressBar = findViewById(R.id.loadingProgressBar) // Initialize loadingProgressBar

        requestQueue = Volley.newRequestQueue(this)

        // Populate the Spinner with cryptocurrency names
        val cryptoNames = listOf("Bitcoin", "Ethereum", "Litecoin")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, cryptoNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        cryptoSpinner.adapter = adapter

        // Set a listener for item selection in the Spinner
        cryptoSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCryptoName = cryptoNames[position]
                fetchCryptoData(selectedCryptoName)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
    }

    private fun fetchCryptoData(cryptoName: String) {
        cryptoInfoLayout.visibility = View.GONE
        loadingProgressBar.visibility = View.VISIBLE

        val url = "https://api.coincap.io/v2/assets"
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                Log.d("CryptoApp", "API Response: $response") // Log entire response for inspection
                try {
                    // Parse the JSON response and log the details
                    val data = response.getJSONArray("data")
                    var found = false
                    for (i in 0 until data.length()) {
                        val cryptoObject = data.getJSONObject(i)
                        val nameFromAPI = cryptoObject.getString("name")

                        // Log the names for debugging
                        Log.d("CryptoApp", "Name from API: $nameFromAPI, Selected Name: $cryptoName")

                        if (nameFromAPI == cryptoName) {
                            Log.d("CryptoApp", "Crypto Data: $cryptoObject") // Log cryptocurrency's data
                            updateCryptoInfo(cryptoObject)
                            found = true
                            break
                        }
                    }

                    if (!found) {
                        Log.d("CryptoApp", "No match found for $cryptoName")
                        // Handle the case where no match is found
                        // You might want to clear or hide the displayed information in this case
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Log.e("CryptoApp", "JSON parsing error: ${e.message}")
                } finally {
                    loadingProgressBar.visibility = View.GONE
                }
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
                Log.e("CryptoApp", "Volley error: ${error.message}")
                loadingProgressBar.visibility = View.GONE
            })

        // Add the request to the RequestQueue
        requestQueue.add(jsonObjectRequest)
    }

    private fun updateCryptoInfo(cryptoObject: JSONObject) {
        // Update TextViews with cryptocurrency information
        cryptoNameTextView.text = cryptoObject.getString("name")
        cryptoSymbolTextView.text = cryptoObject.getString("symbol")
        cryptoSupplyTextView.text = "Supply: ${cryptoObject.getDouble("supply").toInt()}"
        cryptoPriceTextView.text = "Price: $${String.format("%.2f", cryptoObject.getDouble("priceUsd"))}"
        cryptoPercentChangeTextView.text =
            "Change (24h): ${String.format("%.2f", cryptoObject.getDouble("changePercent24Hr"))}%"

        // Make the cryptoInfoLayout visible
        cryptoInfoLayout.visibility = View.VISIBLE
    }
}