package com.example.trycoroutines.extension

import android.util.Log
import kotlinx.coroutines.*

/**
 * Created by Franz Andel on 20/04/20.
 * Android Engineer
 */

class DebuggingCoroutines {

    fun getCurrentCoroutinesThread() {
        runBlocking {
            Log.d("1234", "My job is ${coroutineContext[Job]}")
        }
    }
}