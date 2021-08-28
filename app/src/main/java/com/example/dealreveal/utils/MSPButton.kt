package com.example.dealreveal.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatButton

class MSPButton(context: Context, attrs : AttributeSet):AppCompatButton(context,attrs){
    init {
        applyfont()
    }

    private fun applyfont() {

        val typeface: Typeface = Typeface.createFromAsset(context.assets, "Montserrat-Regular.ttf")
        setTypeface(typeface)
    }
}