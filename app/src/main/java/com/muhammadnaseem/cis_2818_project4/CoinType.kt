package com.muhammadnaseem.cis_2818_project4

data class CoinType(val name: String, val symbol: String, val supply: Int, val price: Float, val change: Float) {

    public override fun toString(): String {
        return name
    }

}