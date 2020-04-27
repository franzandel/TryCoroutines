package com.example.trycoroutines

import kotlinx.coroutines.*

/**
 * Created by Franz Andel on 20/04/20.
 * Android Engineer
 */

class PassingLocalData {

    fun passLocalData() {
        val threadLocal = ThreadLocal<String?>()
        runBlocking {
            threadLocal.set("main")
            println("Pre-main, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
            val job = launch(Dispatchers.Default + threadLocal.asContextElement(value = "launch")) {
                println("Launch start, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
                yield()
                println("After yield, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
            }
            job.join()
            println("Post-main, current thread: ${Thread.currentThread()}, thread local value: '${threadLocal.get()}'")
        }
    }
}