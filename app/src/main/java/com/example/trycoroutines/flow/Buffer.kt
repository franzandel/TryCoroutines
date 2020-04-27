package com.example.trycoroutines.flow

import android.util.Log
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

/**
 * Created by Franz Andel on 23/04/20.
 * Android Engineer
 */

class Buffer {

    fun printWithoutBuffer() {
        runBlocking<Unit> {
            val time = measureTimeMillis {
                longOperation().collect { value ->
                    delay(300) // pretend we are processing it for 300 ms
                    Log.d("1234", value.toString())
                }
            }
            Log.d("1234", "Collected in $time ms")
        }
    }

    fun printWithBuffer() {
        runBlocking<Unit> {
            val time = measureTimeMillis {
                longOperation().buffer().collect { value ->
                    delay(300) // pretend we are processing it for 300 ms
                    Log.d("1234", value.toString())
                }
            }
            Log.d("1234", "Collected in $time ms")
        }
    }

    private fun longOperation(): Flow<Int> = flow {
        for (i in 1..3) {
            delay(100) // pretend we are asynchronously waiting 100 ms
            emit(i) // emit next value
        }
    }
}