package com.hasanbilgin.retrofitkotlin.service

import com.hasanbilgin.retrofitkotlin.model.CryptoModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET

interface CryptoApi {

    //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json

    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    //Call retrofitten seçilir
    //    fun getData(): Call<List<CryptoModel>>
    //rxjava  için
    //Observable io.reactivex kütüphanesini kullandık
    fun getData(): Observable<List<CryptoModel>>


}
