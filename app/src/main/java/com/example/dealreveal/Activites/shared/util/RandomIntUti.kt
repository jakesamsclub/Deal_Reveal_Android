package com.example.dealreveal.Activites.shared.util

import java.util.concurrent.atomic.AtomicInteger

object RandomIntUti {

    private val seed = AtomicInteger()

    fun getRandomInt() = seed.getAndIncrement() + System.currentTimeMillis().toInt()
}