package com.example.trycoroutines.flow

import com.example.trycoroutines.printLog
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking


/**
 * Created by Franz Andel on 23/04/20.
 * Android Engineer
 */

class FlowAreCold {
    // Meaning : Flow will only be fires when .collect being called

    fun printFlowAreCold() = runBlocking {
        "Calling foo...".printLog()
        val flow = longOperation()
        "Calling collect...".printLog()
        flow.collect { value -> value.printLog() }
        "Calling collect again...".printLog()
        flow.collect { value -> value.printLog() }
    }

    private fun longOperation(): Flow<Int> = flow {
        "Flow started".printLog()
        for (i in 1..3) {
            delay(100)
            emit(i)
        }
    }
}