package com.example.trycoroutines.flow

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking

/**
 * Created by Franz Andel on 23/04/20.
 * Android Engineer
 */

class FlowChangeContext {

    fun printFlowChangeContext() = runBlocking {
        correctLongOperation().collect { value ->
            Log.d("1234", "Collected $value")
        }
    }

    private fun correctLongOperation(): Flow<Int> = flow {
        for (i in 1..3) {
            Thread.sleep(100) // pretend we are computing it in CPU-consuming way
            Log.d("1234", "Emitting $i")
            emit(i) // emit next value
        }
    }.flowOn(Dispatchers.Default) // RIGHT way to change context for CPU-consuming code in flow builder

    private fun wrongLongOperation(): Flow<Int> = flow {
        // The WRONG way to change context for CPU-consuming code in flow builder
        kotlinx.coroutines.withContext(Dispatchers.Default) {
            for (i in 1..3) {
                Thread.sleep(100) // pretend we are computing it in CPU-consuming way
                emit(i) // emit next value
            }
        }
    }
}