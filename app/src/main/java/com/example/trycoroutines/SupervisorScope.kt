package com.example.trycoroutines

import kotlinx.coroutines.*

/**
 * Created by Franz Andel on 27/04/20.
 * Android Engineer
 */

class SupervisorScope {

    fun printSupervisorScope() {
        runBlocking {
            try {
                supervisorScope {
                    val child = launch {
                        try {
                            "Child is sleeping".printLog()
                            delay(Long.MAX_VALUE)
                        } finally {
                            "Child is cancelled".printLog()
                        }
                    }
                    // Give our child a chance to execute and print using yield
                    yield()
                    "Throwing exception from scope".printLog()
                    throw AssertionError()
                }
            } catch(e: AssertionError) {
                "Caught assertion error".printLog()
            }
        }
    }
}