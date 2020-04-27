package com.example.trycoroutines.flow

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Created by Franz Andel on 23/04/20.
 * Android Engineer
 */

class FlowBasic {

    fun printFlowBasic() = runBlocking {
        // Launch a concurrent coroutine to check if the main thread is blocked
        launch {
            for (k in 1..3) {
                Log.d("1234", "I'm not blocked $k")
                delay(100)
            }
        }
        // Collect the flow
        longOperation().collect { value -> Log.d("1234", value.toString()) }
    }

    private fun longOperation(): Flow<Int> = flow { // flow builder
        for (i in 1..3) {
            delay(100) // pretend we are doing something useful here
            emit(i) // emit next value
        }
    }
}