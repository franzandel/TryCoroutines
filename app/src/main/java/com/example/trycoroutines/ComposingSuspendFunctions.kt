package com.example.trycoroutines

import kotlinx.coroutines.delay

/**
 * Created by Franz Andel on 19/04/20.
 * Android Engineer
 */

class ComposingSuspendFunctions {
    suspend fun doSomethingUsefulOne(): Int {
        delay(1000)
        return 13
    }

    suspend fun doSomethingUsefulTwo(): Int {
        delay(1000)
        return 27
    }
}