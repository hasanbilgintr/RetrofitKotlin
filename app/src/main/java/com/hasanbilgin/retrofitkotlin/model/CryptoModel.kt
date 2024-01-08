package com.hasanbilgin.retrofitkotlin.model

import com.google.gson.annotations.SerializedName

//data koyduk modelde genelde kullanılıyomruş consractor zorunlu kılıyomruş iyi
//@SerializedName("currency") daki currency çekilcek veri yani keyi (name) aynı olmak zorunda altındaki önemi yok yani bu _current
//@SerializedName("currency")  bunlar yazılmayadabilir yazılmazsa değişken ismi key ile aynı olmak zorunda
data class CryptoModel(/*@SerializedName("currency") */val currency: String,/* @SerializedName("price") */val price: String)