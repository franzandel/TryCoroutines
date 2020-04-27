package com.example.trycoroutines

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.trycoroutines.extension.DebuggingCoroutines
import com.example.trycoroutines.flow.*
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity(), CoroutineScope by CoroutineScope(Dispatchers.Default) {

    private val composingSuspendFunctions = ComposingSuspendFunctions()
    private val debuggingCoroutines = DebuggingCoroutines()
    private val passingLocalData = PassingLocalData()

    private val flowBasic = FlowBasic()
    private val flowAreCold = FlowAreCold()
    private val flowOperators = FlowOperators()
    private val flowChangeContext = FlowChangeContext()
    private val buffer = Buffer()
    private val zip = Zip()
    private val combine = Combine()
    private val supervisorScope = SupervisorScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        asyncStyleFunctions()
//        debuggingCoroutines.getCurrentCoroutinesThread()
//        flowBasic.printFlowBasic()
//        flowAreCold.printFlowAreCold()
//        flowOperators.printFlowSequentialOperators()
//        flowChangeContext.printFlowChangeContext()
//        buffer.printWithoutBuffer()
//        zip.printZip()
//        combine.printCombine()
        supervisorScope.printSupervisorScope()
    }

    private fun composingSuspendFunctions() {
        runBlocking {
            val time =
                measureTimeMillis {
                    val one = composingSuspendFunctions.doSomethingUsefulOne()
                    val two = composingSuspendFunctions.doSomethingUsefulTwo()
                    Log.d("1234", "The answer is ${one + two}")
                }
            Log.d("1234", "Completed in $time")
        }
    }

    private fun composingSuspendFunctionsWithAsync() {
        runBlocking {
            val time =
                measureTimeMillis {
                    val one = async { composingSuspendFunctions.doSomethingUsefulOne() }
                    val two = async { composingSuspendFunctions.doSomethingUsefulTwo() }
                    Log.d("1234", "The answer is ${one.await() + two.await()}")
                }
            Log.d("1234", "Completed in $time")
        }
    }

    private fun composingSuspendFunctionsWithLazyAsync() {
        runBlocking {
            val time =
                measureTimeMillis {
                    val one =
                        async(start = CoroutineStart.LAZY) { composingSuspendFunctions.doSomethingUsefulOne() }
                    val two =
                        async(start = CoroutineStart.LAZY) { composingSuspendFunctions.doSomethingUsefulTwo() }
                    one.start() // async LAZY will be triggered here
                    two.start() // async LAZY will be triggered here
                    Log.d("1234", "The answer is ${one.await() + two.await()}")
                }
            Log.d("1234", "Completed in $time")
        }
    }

    private fun somethingUsefulOneAsync() = GlobalScope.async {
        composingSuspendFunctions.doSomethingUsefulOne()
    }

    private fun somethingUsefulTwoAsync() = GlobalScope.async {
        composingSuspendFunctions.doSomethingUsefulTwo()
    }

    // TODO: STRONGLY DISCOURAGED
    // WHY?
    // If there is logic error between val one & one.await() which throw Exception
    // somethingUsefulOneAsync() will still running while time operation is already aborted
    // TODO: SOLUTION = USE STRUCTURED CONCURRENCY ASYNC
    private fun asyncStyleFunctions() {
        val time =
            measureTimeMillis {
                val one = somethingUsefulOneAsync()
                val two = somethingUsefulTwoAsync()

                runBlocking {
                    Log.d("1234", "The answer is ${one.await() + two.await()}")
                }
            }
        Log.d("1234", "Completed in $time")
    }

    private fun structuredConcurrencyAsync() {
        runBlocking {
            val time =
                measureTimeMillis {
                    Log.d("1234", "The answer is ${concurrentSum()}")
                }
            Log.d("1234", "Completed in $time")
        }
    }

    private suspend fun concurrentSum() =
        coroutineScope {
            val one = async { composingSuspendFunctions.doSomethingUsefulOne() }
            val two = async { composingSuspendFunctions.doSomethingUsefulTwo() }
            one.await() + two.await()
        }
}
