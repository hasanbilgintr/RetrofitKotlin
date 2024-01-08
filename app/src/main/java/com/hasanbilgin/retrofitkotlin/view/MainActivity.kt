package com.hasanbilgin.retrofitkotlin.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hasanbilgin.retrofitkotlin.R
import com.hasanbilgin.retrofitkotlin.adapter.CryptoAdapter
import com.hasanbilgin.retrofitkotlin.databinding.ActivityMainBinding
import com.hasanbilgin.retrofitkotlin.model.CryptoModel
import com.hasanbilgin.retrofitkotlin.service.CryptoApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Objects

//CryptoAdapter.Listener tıklanan veri ile ilgili olduğu için eklendi ana ekrandan ulaşmak için yani
class MainActivity : AppCompatActivity(), CryptoAdapter.Listener {
    private lateinit var binding: ActivityMainBinding
    private val BASE_URL = "https://raw.githubusercontent.com/";
    private var cryptoModels: ArrayList<CryptoModel>? = null
    private var recyclerViewAdapter: CryptoAdapter? = null

    //    private lateinit var recyclerViewAdapter:CryptoAdapter //buda olabilirdi
    //Disposable
    private var compositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        compositeDisposable = CompositeDisposable()

        //RecyclerView
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        loadData()

    }

    private fun loadData() {
        //addConverterFactory(GsonConverterFactory.create()) gson kullancağımız söledik
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            //.addCallAdapterFactory(RxJava2CallAdapterFactory.create()) rxjava için eklendi
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build().create(CryptoApi::class.java)



        compositeDisposable?.add(retrofit.getData()
            //veriyi alma/kayıt işlemi arka planda
            .subscribeOn(Schedulers.io())
            //veriyi işleme/ gözleme için göstericeyoruz
            .observeOn(AndroidSchedulers.mainThread())
            //sonuç almak için
            .subscribe(this::handleResponse))


        /*
        val call = retrofit.getData()
        //Callback Retrofit seçildi
        call.enqueue(object : Callback<List<CryptoModel>> {
            //hata aldığında
            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                t.printStackTrace()
            }

            //çalıştığında
            override fun onResponse(call: Call<List<CryptoModel>>, response: Response<List<CryptoModel>>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        cryptoModels = ArrayList(it)

                        cryptoModels?.let {
                            recyclerViewAdapter = CryptoAdapter(it, this@MainActivity)
                            binding.recyclerView.adapter = recyclerViewAdapter
                        }
//                        for (cryptoModel: CryptoModel in cryptoModels!!) {
//                            println(cryptoModel.currency)
//                            println(cryptoModel.price)
//                        }
                    }
                }
            }
        })
         */
    }

    private fun handleResponse(crytoList: List<CryptoModel>) {
        cryptoModels = ArrayList(crytoList)

        cryptoModels?.let {
            recyclerViewAdapter = CryptoAdapter(it, this@MainActivity)
            binding.recyclerView.adapter = recyclerViewAdapter
        }
    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this, "Clicked: ${cryptoModel.currency}", Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        //temizlemesi
        compositeDisposable?.clear()
    }
}

//-hava durumu api en ünlleri openweathermap.org tur
//-json beautifer json datalarını güzelleştirebilirsiniz
//https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json