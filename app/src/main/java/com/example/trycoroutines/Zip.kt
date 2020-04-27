package com.example.trycoroutines

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

/**
 * Created by Franz Andel on 27/04/20.
 * Android Engineer
 */

class Zip {
    fun printZip() {
        runBlocking {

            val nums = (1..3).asFlow() // numbers 1..3
            val strs = flowOf("one", "two", "three") // strings
            nums.zip(strs) { a, b -> "$a -> $b" } // compose a single string
                .collect { it.printLog() } // collect and print
        }
    }
}