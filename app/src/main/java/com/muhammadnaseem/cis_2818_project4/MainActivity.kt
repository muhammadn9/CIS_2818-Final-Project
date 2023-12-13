package com.muhammadnaseem.cis_2818_project4

import com.android.volley.Request
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.muhammadnaseem.cis_2818_project4.databinding.ActivityMainBinding
import org.json.JSONObject
import org.json.JSONArray

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val queue = Volley.newRequestQueue(this)
        val url = "https://api.coincap.io/v2/assets"
        val request = StringRequest(
            Request.Method.GET, url, {
                    response ->
                val dataObject = JSONObject(response)
                val dataArray = dataObject.getJSONArray("data")

                val coins = ArrayList<CoinType>()

                for (i in 0 until dataArray.length()) {
                    val coin = dataArray.getJSONObject(i)

                    val coinName = coin.getString("name")
                    val coinSymbol = coin.getString("symbol")
                    val coinSupply = coin.getString("supply").toFloat().toInt()
                    val coinPrice = coin.getString("priceUsd").toFloat()
                    val coinChange = coin.getString("changePercent24Hr").toFloat()

                    val coinObject = CoinType(coinName, coinSymbol, coinSupply, coinPrice, coinChange)

                    coins.add(coinObject)

                    Log.i("Volley Data", "${coinObject.name} ${coinObject.symbol} ${coinObject.supply}")
                }

//                val coinAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, coins)
//                binding.coinSpinner.adapter = coinAdapter

//                binding.coinSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
//                    override fun onItemSelected(
//                        parent: AdapterView<*>?,
//                        view: View?,
//                        position: Int,
//                        id: Long
//                    ) {
//                        binding.tvName.text = coins.get(position).name
//                        binding.tvSymbol.text = coins.get(position).symbol
//                        binding.tvSupply.text = coins.get(position).supply.toString()
//                        binding.tvPrice.text = coins.get(position).price.toString()
//                        binding.tvChange.text = coins.get(position).change.toString()
//                    }
//
//                    override fun onNothingSelected(parent: AdapterView<*>?) {
//                        TODO("Not yet implemented")
//                    }
//
//                }

            },
            Response.ErrorListener { Log.i("Volley Error", "Error Ocurred") }
        )

        queue.add(request)

    }
}