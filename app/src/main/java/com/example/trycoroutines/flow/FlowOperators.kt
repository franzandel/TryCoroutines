package com.example.trycoroutines.flow

import com.example.trycoroutines.printLog
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking


/**
 * Created by Franz Andel on 23/04/20.
 * Android Engineer
 */

class FlowOperators {

    fun printFlowOperators() = runBlocking {
        (1..3).asFlow() // a flow of requests
            .map { request -> performRequest(request) }
            .collect { response -> response.printLog() }
    }

    fun printFlowTransforms() = runBlocking {
        (1..3).asFlow() // a flow of requests
            .transform { request ->
                emit("Making request $request")
                emit(performRequest(request))
            }
            .collect { response -> response.printLog() }
    }

    // Todo: Cancellation in Coroutines always throw an exception
    fun printFlowSizeLimiter() = runBlocking {
        numbers()
            .take(2) // take only the first two
            .collect { value -> value.printLog() }
    }

    fun printFlowSequentialOperators() = runBlocking {
        (1..5).asFlow()
            .filter {
                "Filter $it".printLog()
                it % 2 == 0
            }
            .map {
                "Map $it".printLog()
                "string $it"
            }.collect {
                "Collect $it".printLog()
            }
    }

    private suspend fun performRequest(request: Int): String {
        delay(1000) // imitate long-running asynchronous work
        return "response $request"
    }

    private fun numbers(): Flow<Int> = flow {
        try {
            emit(1)
            emit(2)
            "This line will not execute".printLog()
            emit(3)
        } finally {
            "Finally in numbers".printLog()
        }
    }
}