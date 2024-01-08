package com.hasanbilgin.retrofitkotlin.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputBinding
import androidx.recyclerview.widget.RecyclerView
import com.hasanbilgin.retrofitkotlin.R
import com.hasanbilgin.retrofitkotlin.databinding.RowLayoutBinding
import com.hasanbilgin.retrofitkotlin.model.CryptoModel

class CryptoAdapter(private val cryptoList: ArrayList<CryptoModel>, private val listener: Listener) : RecyclerView.Adapter<CryptoAdapter.RowHolder>() {
    //Array kotlin kütüphanesini kullanıcak
    private val colors: Array<String> = arrayOf("#13bd27","#29c1e1","#b129e1","#d3df13","#f6bd0c")

    //tıklandığında ana ekrandan kontrol için gerekli
    interface Listener {
        fun onItemClick(cryptoModel: CryptoModel)
    }

    class RowHolder(val binding: RowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
//        val binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        val binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RowHolder(binding)
    }

    override fun getItemCount(): Int {
//        return cryptoList.size//aynı
        return cryptoList.count()
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.itemView.setOnClickListener {
            listener.onItemClick(cryptoList.get(position))
        }
        holder.itemView.setBackgroundColor(Color.parseColor(colors[position % 5]))
        holder.binding.textCurrency.text = cryptoList.get(position).currency
        holder.binding.textPrice.text = cryptoList.get(position).price

    }

}